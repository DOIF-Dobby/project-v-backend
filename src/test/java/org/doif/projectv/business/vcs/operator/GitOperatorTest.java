package org.doif.projectv.business.vcs.operator;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.revwalk.filter.CommitTimeRevFilter;
import org.eclipse.jgit.revwalk.filter.RevFilter;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.junit.jupiter.api.Test;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.FileSystemUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class GitOperatorTest {

    private static final String REMOTE_URL = "https://github.com/DOIF-Dobby/Jgit-test.git";

    private static final String TEMP_DIR = "C:\\Users\\MJ\\dev\\temp";

//    @Test
    public void gitCloneTest() throws GitAPIException {
        File file = new File(TEMP_DIR, "jgit-test");

        Git repo = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(file)
                .call();

        Iterable<RevCommit> call = repo.log()
                .call();

        for (RevCommit revCommit : call) {
            System.out.println("revCommit = " + revCommit.getFullMessage() + " " + revCommit.getAuthorIdent().getName());
        }

//        System.out.println("repo = " + repo.getRepository().getDirectory());
    }

//    @Test
    void gitCommitTest() throws GitAPIException, IOException {
        File file = new File(TEMP_DIR, "jgit-test");

        Git git = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(file)
                .call();

        String parent = git.getRepository().getDirectory().getParent();

        File buildGradle = new File(parent, "build.gradle");

        String s = FileCopyUtils.copyToString(new FileReader(buildGradle));
        String replace = s.replace("version = '0.0.1-SNAPSHOT'", "version = '0.0.2'");

        FileCopyUtils.copy(replace.getBytes(), buildGradle);

        git.add()
                .addFilepattern("build.gradle")
                .call();

        git.commit()
                .setMessage("change version by ProjectV")
                .call();

        git.push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("", ""))
                .call();

    }

//    @Test
    void deleteTest() throws GitAPIException, IOException {
        File jgit = Files.createTempDirectory("jgit").toFile();
        Git git = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(jgit)
                .call();

        git.close();

        File directory = git.getRepository().getDirectory();

        Files.walkFileTree(directory.toPath(), new ChangeAuthorizationFileVisitor());
        FileSystemUtils.deleteRecursively(git.getRepository().getDirectory().getParentFile());

    }
    
//    @Test
    void tagTest() throws GitAPIException, IOException {
        File jgit = Files.createTempDirectory("jgit").toFile();
        Git git = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(jgit)
                .call();

        Repository repository = git.getRepository();
        ObjectId id = repository.resolve("HEAD");

        RevWalk walk = new RevWalk(repository);
        RevCommit commit = walk.parseCommit(id);

        System.out.println("commit.getFullMessage() = " + commit.getFullMessage());

        git.tag()
                .setObjectId(commit)
                .setName("tag_for_testing")
                .call();

        walk.dispose();

        git.push()
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider("", ""))
                .setPushTags()
                .call();

        git.close();

        File directory = git.getRepository().getDirectory();

        Files.walkFileTree(directory.toPath(), new ChangeAuthorizationFileVisitor());
        FileSystemUtils.deleteRecursively(git.getRepository().getDirectory().getParentFile());
    }

//    @Test
    void logTest() throws GitAPIException, IOException {
        File jgit = Files.createTempDirectory("jgit").toFile();
        Git git = Git.cloneRepository()
                .setURI(REMOTE_URL)
                .setDirectory(jgit)
                .call();

        LocalDate startDate = LocalDate.of(2020, 12, 22);
        LocalDate endDate = LocalDate.of(2020, 12, 22);

        Date since = Date.from(startDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date until = Date.from(endDate.plusDays(1).atStartOfDay(ZoneId.systemDefault()).toInstant());

        RevFilter between = CommitTimeRevFilter.between(since, until);

        Iterable<RevCommit> commits = git.log()
                .setRevFilter(between)
                .call();

        for (RevCommit commit : commits) {
            System.out.println("revision = " + commit.getId().getName());
            System.out.println("Author = " + commit.getAuthorIdent().getName());
            System.out.println("message = " + commit.getFullMessage());
            System.out.println("date = " + commit.getAuthorIdent().getWhen().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime());
            System.out.println("---------------------------------------------");
        }


        git.close();

        File directory = git.getRepository().getDirectory();

        Files.walkFileTree(directory.toPath(), new ChangeAuthorizationFileVisitor());
        FileSystemUtils.deleteRecursively(git.getRepository().getDirectory().getParentFile());
    }

    public static class ChangeAuthorizationFileVisitor extends SimpleFileVisitor<Path> {
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
    }
}
