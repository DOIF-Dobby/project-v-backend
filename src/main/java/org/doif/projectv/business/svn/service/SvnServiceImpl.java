package org.doif.projectv.business.svn.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.module.repository.ModuleRepository;
import org.doif.projectv.business.svn.dto.SvnDto;
import org.doif.projectv.business.svn.dto.SvnSearchCondition;
import org.doif.projectv.business.svn.dto.SvnTagDto;
import org.doif.projectv.common.security.util.SecurityUtil;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNException;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.*;

import java.io.File;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SvnServiceImpl implements SvnService{

    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;

    @Value("${svn.tempDir}")        // svn 임시 저장 경로
    private String tempDir;

    @Override
    public List<SvnDto> search(SvnSearchCondition condition) {
        SVNRepository repository = null;

        try {
            // SvnRepository 객체 얻음
            Optional<Module> optionalModule = moduleRepository.findById(condition.getModuleId());
            Module module = optionalModule.orElseThrow(() -> new IllegalArgumentException("모듈을 찾을 수 없음"));
            repository = getAuthenticatedSvnRepository(module.getSvnUrl());

            // 여기도 null 체크
            Date startDate = Date.from(condition.getStartDate().atStartOfDay(ZoneId.systemDefault()).toInstant());
            Date endDate = Date.from(condition.getEndDate().plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

            // 날짜로 해당 리비전 번호를 얻음
            long startRevision = repository.getDatedRevision(startDate);
            long endRevision = repository.getDatedRevision(endDate);

            // 로그 엔트리를 얻음
            Collection<SVNLogEntry> logEntries = repository.log(new String[]{""}, null, startRevision, endRevision, false, true);

            return logEntries.stream()
                    .map(svnLogEntry -> {
                        Long revision = svnLogEntry.getRevision();
                        String author = svnLogEntry.getAuthor();
                        String message = svnLogEntry.getMessage();
                        LocalDateTime date = svnLogEntry.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

                        return new SvnDto(revision, date, author, message);
                    })
                    .collect(Collectors.toList());

        } catch (SVNException e) {
            e.printStackTrace();
        } finally {
            if(repository != null) {
                repository.closeSession();;
            }
        }

        return null;
    }

    @Override
    public Optional<SvnTagDto> tag(String svnUrl, String versionName) {
        SVNRepository repository = null;

        try {
            // SvnRepository 객체 얻음
            repository = getAuthenticatedSvnRepository(svnUrl);

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

            SvnTagDto svnTagDto = new SvnTagDto();
            svnTagDto.setRevision(latestRevision);
            svnTagDto.setTag(targetPath);
            return Optional.of(svnTagDto);

        } catch (SVNException e) {
            e.printStackTrace();
        } finally {
            if(repository != null) {
                repository.closeSession();;
            }
        }

        return Optional.empty();
    }

    @Override
    public File checkout(String svnUrl) {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnCheckout checkout = factory.createCheckout();
        String moduleName = svnUrl.substring(svnUrl.lastIndexOf("/") + 1);

        // checkout 받은 svn 폴더를 임시저장할 곳은 property로 뺀다.
        File svnTempDirectory = new File(tempDir, moduleName);

        try {
            SVNURL svnurl = SVNURL.parseURIEncoded(svnUrl);
            SvnTarget svnSource = SvnTarget.fromURL(svnurl);

            // 파일만 가져오면 그나마 빠름
            checkout.setDepth(SVNDepth.FILES);
            checkout.setSource(svnSource);
            checkout.addTarget(SvnTarget.fromFile(svnTempDirectory));

            checkout.run();
        } catch (SVNException e) {
            e.printStackTrace();
        }

        return svnTempDirectory;
    }

    @Override
    public void commit(File pomFile, String commitMessage) {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnCommit commit = factory.createCommit();

        try {
            commit.addTarget(SvnTarget.fromFile(pomFile));
            commit.setCommitMessage(commitMessage);

            commit.run();
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    /**
     * 요청한 유저의 Svn 접속정보와 parameter로 넘어온 moduleId로 모듈을 조회 후 해당 모듈의 svn주소로 인증된 SnvRepository 객체를 반환
     * @param svnUrl
     * @return
     * @throws SVNException
     */
    private SVNRepository getAuthenticatedSvnRepository(String svnUrl) throws SVNException{
        Optional<User> optionalUser = SecurityUtil.getUserByContext();
        User requestUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("인증 오류"));
        String requestUserId = requestUser.getId();

        User user = userRepository.findById(requestUserId).orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음"));

        String svnId = user.getSvnId();
        String svnPassword = user.getSvnPassword();

        SVNURL url = SVNURL.parseURIEncoded(svnUrl);
        SVNRepository repository = SVNRepositoryFactory.create(url);
        BasicAuthenticationManager authManager = BasicAuthenticationManager.newInstance(svnId, svnPassword.toCharArray());
        repository.setAuthenticationManager(authManager);

        return repository;
    }
    
}
