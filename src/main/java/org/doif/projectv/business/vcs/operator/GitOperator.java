package org.doif.projectv.business.vcs.operator;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.business.vcs.dto.VcsDto;
import org.doif.projectv.business.vcs.service.VcsAuthInfoService;
import org.doif.projectv.common.security.util.SecurityUtil;
import org.doif.projectv.common.user.entity.User;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Component;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class GitOperator implements VcsOperator {

    private final VcsAuthInfoService vcsAuthInfoService;

    @Override
    public List<VcsDto.Log> getLogs(String repositoryInfo, LocalDate startDate, LocalDate endDate) {
        Git git = null;
        Date since = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date until = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<VcsDto.Log> logs = new ArrayList<>();

        RevFilter between = CommitTimeRevFilter.between(since, until);

        try {
            git = getGit(repositoryInfo);
            Iterable<RevCommit> commits = git.log()
                    .setRevFilter(between)
                    .call();

            for (RevCommit commit : commits) {
                VcsDto.Log log = new VcsDto.Log();
                log.setRevision(commit.getId().getName());
                log.setAuthor(commit.getAuthorIdent().getName());
                log.setMessage(commit.getFullMessage());
                log.setDate(commit.getAuthorIdent().getWhen().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());

                logs.add(log);
            }

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        } finally {
            deleteGitTempDirectory(git);
        }

        return logs;
    }

    @Override
    public VcsDto.Tag tag(String repositoryInfo, String versionName) {
        Git git = null;

        try {
            git = getGit(repositoryInfo);

            Repository repository = git.getRepository();
            ObjectId head = repository.resolve("HEAD");
            RevWalk revWalk = new RevWalk(repository);
            RevCommit commit = revWalk.parseCommit(head);

            git.tag()
                    .setObjectId(commit)
                    .setName(versionName)
                    .call();

            revWalk.dispose();

            git.push()
                    .setCredentialsProvider(getCredentialsProvider())
                    .setPushTags()
                    .call();

            VcsDto.Tag tag = new VcsDto.Tag();
            tag.setRevision(commit.getId().getName());
            tag.setTag(versionName);

            return tag;

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        } finally {
            deleteGitTempDirectory(git);
        }

        return null;
    }

    @Override
    public File checkout(String repositoryInfo) {
        Git git = null;

        try {
            git = getGit(repositoryInfo);
            return git.getRepository().getDirectory().getParentFile();

        } catch (GitAPIException | IOException e) {
            e.printStackTrace();
        } finally {
            if(git != null) {
                git.getRepository().close();
                git.close();
            }
        }

        return null;
    }

    @Override
    public void commit(File vcsFile, String commitMessage) {
        Git git = null;

        try {
            File gitDir = new File(vcsFile.getParent(), ".git");

            Repository repository = FileRepositoryBuilder.create(gitDir);
            git = new Git(repository);

            git.add()
                    .addFilepattern(vcsFile.getName())
                    .call();

            git.commit()
                    .setMessage(commitMessage)
                    .call();

            git.push()
                    .setCredentialsProvider(getCredentialsProvider())
                    .call();

        } catch (IOException | GitAPIException e) {
            e.printStackTrace();
        } finally {
            deleteGitTempDirectory(git);
        }
    }

    @Override
    public boolean deleteDirectory(File directory) {
        return FileSystemUtils.deleteRecursively(directory);
    }

    private UsernamePasswordCredentialsProvider getCredentialsProvider() {
        Optional<User> optionalUser = SecurityUtil.getUserByContext();
        User requestUser = optionalUser.orElseThrow(() -> new IllegalArgumentException("인증 오류"));
        String requestUserId = requestUser.getId();

        VcsAuthInfoDto.Result vcsAuthInfo = vcsAuthInfoService.searchByUserIdAndVcsType(requestUserId, VcsType.GIT);
        String authId = vcsAuthInfo.getVcsAuthId();
        String authPassword = vcsAuthInfo.getVcsAuthPassword();

        return new UsernamePasswordCredentialsProvider(authId, authPassword);
    }

    private Git getGit(String repositoryInfo) throws GitAPIException, IOException {
        File tempDir = Files.createTempDirectory("gitTempDir").toFile();
        return Git.cloneRepository()
                .setURI(repositoryInfo)
                .setDirectory(tempDir)
                .call();
    }

    private boolean deleteGitTempDirectory(Git git) {
        if(git == null) {
            return false;
        }

        git.getRepository().close();
        git.close();

        File directory = git.getRepository().getDirectory();

        if(!directory.exists()) {
            return false;
        }

        try {
            Files.walkFileTree(directory.toPath(), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes basicFileAttributes) throws IOException {
                    file.toFile().setWritable(true);
                    Files.delete(file);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path directory, IOException ioException) throws IOException {
                    directory.toFile().setWritable(true);
                    Files.delete(directory);

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e){
            return false;
        }

        return FileSystemUtils.deleteRecursively(git.getRepository().getDirectory().getParentFile());
    }
}
