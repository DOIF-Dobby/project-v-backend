package org.doif.projectv.business.maven.service;

import org.apache.maven.model.Model;
import org.apache.maven.model.Scm;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.model.io.xpp3.MavenXpp3Writer;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Namespace;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.junit.jupiter.api.Test;
import org.tmatesoft.svn.core.SVNLogEntry;
import org.tmatesoft.svn.core.SVNProperties;
import org.tmatesoft.svn.core.SVNProperty;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.BasicAuthenticationManager;
import org.tmatesoft.svn.core.io.SVNRepository;
import org.tmatesoft.svn.core.io.SVNRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc2.SvnLog;
import org.tmatesoft.svn.core.wc2.SvnOperationFactory;
import org.tmatesoft.svn.core.wc2.SvnRevisionRange;
import org.tmatesoft.svn.core.wc2.SvnTarget;

import java.io.*;
import java.nio.file.Files;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MavenServiceTest {

//    @Test
    public void 테스트() throws Exception {
        MavenXpp3Reader pomReader = new MavenXpp3Reader();
        MavenXpp3Writer pomWriter = new MavenXpp3Writer();

        File file = new File("C:\\Users\\MJ\\dev\\tnc-project\\KOVAN\\T-STCS", "pom.xml");
        File file2 = new File("C:\\Users\\MJ\\dev\\tnc-project\\KOVAN\\T-STCS", "pom2.xml");


        ByteArrayInputStream fis = new ByteArrayInputStream(Files.readAllBytes(file.toPath()));

        Model model = pomReader.read(fis, true);

//        String artifactId = model.getArtifactId();
//        System.out.println("artifactId = " + artifactId);
//        String version = model.getVersion();
//        System.out.println("version = " + version);
//        model.setVersion("0.1.6");
//        String version1 = model.getVersion();
//        System.out.println("version1 = " + version1);

        OutputStream fos = new FileOutputStream(file2);
        pomWriter.write(fos, model);

//        InputStream fis = new FileInputStream("C:\\Users\\MJ\\Desktop\\mydoc\\2020-10-28 new Computer\\JPA\\debug.log");
//
//        Model model = pomReader.read(fis);
//        String artifactId = model.getArtifactId();
//        System.out.println("artifactId = " + artifactId);

        fis.close();
        fos.close();

    }

//    @Test
    public void 테테테스트() throws Exception {
        SVNURL url = SVNURL.parseURIEncoded("");
        String pomFileName = "pom.xml";
        SVNRepository repository = SVNRepositoryFactory.create(url);
        BasicAuthenticationManager authManager = BasicAuthenticationManager.newInstance("", "".toCharArray());
        repository.setAuthenticationManager(authManager);

        SVNProperties svnProperties = new SVNProperties();

        SvnRevisionRange svnRevisionRange = SvnRevisionRange.create(SVNRevision.create(-1), SVNRevision.create(-1));
        SvnOperationFactory factory = new SvnOperationFactory();
        SvnLog log = factory.createLog();
        log.addRange(svnRevisionRange);
        log.setSingleTarget(SvnTarget.fromURL(url));
        log.setLimit(1);

        SVNLogEntry svnLogEntry = log.run();
        long revision = svnLogEntry.getRevision();

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        repository.getFile(pomFileName, revision, svnProperties, outputStream);

        byte[] bytes = outputStream.toByteArray();

        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);

        MavenXpp3Reader pomReader = new MavenXpp3Reader();
        Model model = pomReader.read(byteArrayInputStream);

        // 모델로 변환하는 과정에서 comments가 사라져서 쓰면 안될 듯...

        String version = model.getVersion();
        System.out.println("version = " + version);

        outputStream.close();
        repository.closeSession();
    }

//    @Test
    public void jdom_테스트() throws Exception {
        File pomFile = new File("C:\\Users\\MJ\\IdeaProjects\\TNC\\PG\\KOVAN\\T-STCS", "pom.xml");
        SAXBuilder saxBuilder = new SAXBuilder();
        Document document = saxBuilder.build(pomFile);

        // 버전 찾아내서 바꾼다음
        Element rootElement = document.getRootElement();
        List<Element> children = rootElement.getChildren();
        for (Element child : children) {
            if("version".equals(child.getName())) {
                String version = child.getValue();
                System.out.println("version = " + version);
                child.setText("0.1.6");
                break;
            }
        }

        // 쓰기
        FileOutputStream outputStream = new FileOutputStream(pomFile);
        XMLOutputter xmlOutputter = new XMLOutputter();
        xmlOutputter.output(document, outputStream);

        outputStream.close();
    }

}