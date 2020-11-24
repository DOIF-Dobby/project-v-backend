package org.doif.projectv.business.svn.service;

import org.junit.jupiter.api.Test;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.*;

import java.io.File;

class SvnServiceTest {

//    @Test
    public void 테스트() throws Exception {
        // given
        SVNURL url = SVNURL.parseURIEncoded(""); // svn 주소
        SVNRepository repository = SVNRepositoryFactory.create(url);
        BasicAuthenticationManager authManager = BasicAuthenticationManager.newInstance("", "".toCharArray()); // svn 인증 정보
        repository.setAuthenticationManager(authManager);

        SvnRevisionRange svnRevisionRange = SvnRevisionRange.create(SVNRevision.create(-1), SVNRevision.create(-1));
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnLog log = factory.createLog();
        log.addRange(svnRevisionRange);
        log.setSingleTarget(SvnTarget.fromURL(url));
        log.setLimit(1);

        SVNLogEntry svnLogEntry = log.run();
        long revision = svnLogEntry.getRevision();
        System.out.println("revision = " + revision);
        System.out.println("message = " + svnLogEntry.getMessage());
    }

//    @Test
    public void 체크아웃_테스트() throws Exception {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnCheckout checkout = factory.createCheckout();
        File pomFile = new File("C:\\Users\\MJ\\Desktop\\myDoc\\DEV", "T-STCS");

        // SvnCheckout은 파일단위로 안된다... 폴더 단위로 해야한다. (아마 .svn 폴더까지 만들어야 svn관리 폴더가 되서 그런가 보다.)
        SVNURL svnurl = SVNURL.parseURIEncoded("");
        SvnTarget svnTarget = SvnTarget.fromURL(svnurl);

        // 파일만 가져오면 그나마 빠름
        checkout.setDepth(SVNDepth.FILES);
        checkout.setSource(svnTarget);
        checkout.addTarget(SvnTarget.fromFile(pomFile));
        checkout.run();
    }

//    @Test
    public void Export_테스트() throws Exception {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnExport export = factory.createExport();

        SVNURL svnurl = SVNURL.parseURIEncoded("");
        SvnTarget svnTarget = SvnTarget.fromURL(svnurl);

        File pomFile = new File("C:\\Users\\MJ\\Desktop\\myDoc\\DEV", "pom.xml");

        export.setSource(svnTarget);
        export.addTarget(SvnTarget.fromFile(pomFile));
        export.run();
    }

//    @Test
    public void Commit_테스트() throws Exception {
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnCommit commit = factory.createCommit();

        File pomFile = new File("C:\\Users\\MJ\\Desktop\\myDoc\\DEV\\T-STCS", "pom.xml");

        commit.addTarget(SvnTarget.fromFile(pomFile));
        commit.setCommitMessage("svnkit 테스트 중");
        commit.run();

    }

}