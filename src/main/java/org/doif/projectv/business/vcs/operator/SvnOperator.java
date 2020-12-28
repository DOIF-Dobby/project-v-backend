package org.doif.projectv.business.vcs.operator;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.business.vcs.dto.VcsDto;
import org.doif.projectv.business.vcs.entity.VcsAuthInfo;
import org.doif.projectv.business.vcs.repository.VcsAuthInfoRepository;
import org.doif.projectv.business.vcs.service.VcsAuthInfoService;
import org.doif.projectv.common.security.util.SecurityUtil;
import org.doif.projectv.common.user.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SvnOperator implements VcsOperator {

    private final VcsAuthInfoService vcsAuthInfoService;

    @Override
    public List<VcsDto.Log> getLogs(String repositoryInfo, LocalDate startDate, LocalDate endDate) {
        SVNRepository repository = null;

        try {
            // SvnRepository 객체 얻음
            repository = getAuthenticatedSvnRepository(repositoryInfo);

            // 여기도 null 체크
            Date startDt = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDt = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            // 날짜로 해당 리비전 번호를 얻음
            // 해당 날짜의 첫번째 번호보다 전의 것을 찾아옴 그래서 +1 해줌
            long startRevision = repository.getDatedRevision(startDt) + 1;
            long endRevision = repository.getDatedRevision(endDt);

            String vcsAuthId = getVcsAuthId();

            // 로그 엔트리를 얻음
            Collection<SVNLogEntry> logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, false, false);

            List<VcsDto.Log> logList = logEntries.stream()
                    .map(svnLogEntry -> {
                        Long revision = svnLogEntry.getRevision();
                        String author = svnLogEntry.getAuthor();
                        String message = svnLogEntry.getMessage();
                        LocalDateTime date = svnLogEntry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                        return new VcsDto.Log(String.valueOf(revision), date, author, message);
                    })
                    .filter(log -> vcsAuthId.equals(log.getAuthor()))       // 요청한 유저의 로그만 보여주기 위해
                    .collect(Collectors.toList());

            // 최신로그가 맨 첫번째오도록 reverse
            Collections.reverse(logList);

            return logList;

        } catch (SVNException e) {
            e.printStackTrace();
        } finally {
            if(repository != null) {
                repository.closeSession();
            }
        }

        return null;
    }

    @Override
    public VcsDto.Tag tag(String repositoryInfo, String versionName) {
        SVNRepository repository = null;

        try {
            // SvnRepository 객체 얻음
            repository = getAuthenticatedSvnRepository(repositoryInfo);

            // Svn 명령을 할 수 있는 factory를 생성
            SvnOperationFactory factory = new SvnOperationFactory();
            // 원격 Repository에서 복사를 할 수 있게 해주는 객체
            SvnRemoteCopy remoteCopy = factory.createRemoteCopy();

            // 해당 url의 가장 최신의 리비전 번호를 얻기 위한 셋팅
            // repository.getLatestRevision() 은 해당 svn repository 루트의 가장 최신의 리비전 번호를 얻는 것이기 때문에 따로 이렇게 얻어야 정확한 리비전을 알 수 있음
            SvnRevisionRange svnRevisionRange = SvnRevisionRange.create(SVNRevision.create(-1), SVNRevision.create(-1));
            SVNURL location = repository.getLocation();
            SvnLog log = factory.createLog();
            log.addRange(svnRevisionRange);
            log.setSingleTarget(SvnTarget.fromURL(location));
            log.setLimit(1);

            // 로그 객체를 얻음
            SVNLogEntry svnLogEntry = log.run();
            long latestRevision = svnLogEntry.getRevision();

            // tags 폴더로 복사를 하기 위한 셋팅
            SVNRevision svnRevision = SVNRevision.create(latestRevision);
            SvnTarget svnTarget = SvnTarget.fromURL(location, svnRevision);
            SvnCopySource svnCopySource = SvnCopySource.create(svnTarget, svnRevision);

            remoteCopy.addCopySource(svnCopySource);

            // trunk 혹은 branches 경로를 tags로 변경
            // 사실 trunk에 있는 것만 버전을 달아주는 게 버전 관리 관점에 맞지만, Tnc PG 제품들은 "PG" 폴더 안에 몽땅 들어가 있어서 branches 폴더도 고려해야함...
            String path = location.toDecodedString();
            String tagsPath = path.contains("trunk")
                    ? path.replace("/trunk/", "/tags/")
                    : path.replace("/branches/", "/tags/");

            String targetPath = tagsPath + "/" + versionName;

            SVNURL destinationUrl = SVNURL.parseURIEncoded(targetPath);
            SvnTarget destinationTarget = SvnTarget.fromURL(destinationUrl);
            remoteCopy.addTarget(destinationTarget);

            // 상위 폴더가 없는 경우 만들 것인지 여부
            remoteCopy.setMakeParents(true);
            // 이동 작업으로 복사를 수행할지 여부를 설정
            remoteCopy.setMove(false);
            // 대상이 이미 존재하는 경우 실패 여부
            remoteCopy.setFailWhenDstExists(true);

            // 복사
            remoteCopy.run();

            VcsDto.Tag svnTagDto = new VcsDto.Tag();
            svnTagDto.setRevision(String.valueOf(latestRevision));
            svnTagDto.setTag(targetPath);

            return svnTagDto;

        } catch (SVNException e) {
            e.printStackTrace();
        } finally {
            if(repository != null) {
                repository.closeSession();
            }
        }

        // TODO: NULL를 Return 하는게 맞냐?
        return null;
    }

    @Override
    public File checkout(String repositoryInfo) {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnCheckout checkout = factory.createCheckout();

        File svnCheckoutDirectory = null;

        try {
            SVNURL svnurl = SVNURL.parseURIEncoded(repositoryInfo);
            SvnTarget svnSource = SvnTarget.fromURL(svnurl);

            svnCheckoutDirectory = Files.createTempDirectory("svnTempDir").toFile();

            // 파일만 가져오면 그나마 빠름
            checkout.setDepth(SVNDepth.FILES);
            checkout.setSource(svnSource);
            checkout.addTarget(SvnTarget.fromFile(svnCheckoutDirectory));

            checkout.run();
        } catch (SVNException | IOException e) {
            e.printStackTrace();
        }

        return svnCheckoutDirectory;
    }

    @Override
    public void commit(File vcsFile, String commitMessage) {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnCommit commit = factory.createCommit();

        try {
            commit.addTarget(SvnTarget.fromFile(vcsFile));
            commit.setCommitMessage(commitMessage);

            commit.run();
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteDirectory(File directory) {
        return FileSystemUtils.deleteRecursively(directory);
    }

    /**
     * VcsAuthId 조회
     * @return
     */
    private String getVcsAuthId() {
        Optional<User> optionalUser = SecurityUtil.getUserByContext();
        User requestUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("인증 오류"));
        String requestUserId = requestUser.getId();

        VcsAuthInfoDto.Result vcsAuthInfo = vcsAuthInfoService.searchByUserIdAndVcsType(requestUserId, VcsType.SVN);
        return vcsAuthInfo.getVcsAuthId();
    }

    /**
     * 요청한 유저의 id의 매칭되는 SVN 인증정보를 조회해서 해당 인증정보로 SVN Repository를 연결한 다음 반환
     * @param repositoryInfo
     * @return
     * @throws SVNException
     */
    private SVNRepository getAuthenticatedSvnRepository(String repositoryInfo) throws SVNException {
        Optional<User> optionalUser = SecurityUtil.getUserByContext();
        User requestUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("인증 오류"));
        String requestUserId = requestUser.getId();

        VcsAuthInfoDto.Result vcsAuthInfo = vcsAuthInfoService.searchByUserIdAndVcsType(requestUserId, VcsType.SVN);

        String authId = vcsAuthInfo.getVcsAuthId();
        String authPassword = vcsAuthInfo.getVcsAuthPassword();

        SVNURL url = SVNURL.parseURIEncoded(repositoryInfo);
        SVNRepository repository = SVNRepositoryFactory.create(url);
        BasicAuthenticationManager authManager = BasicAuthenticationManager.newInstance(authId, authPassword.toCharArray());
        repository.setAuthenticationManager(authManager);

        return repository;
    }
}
