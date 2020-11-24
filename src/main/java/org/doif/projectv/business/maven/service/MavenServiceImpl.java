package org.doif.projectv.business.maven.service;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class MavenServiceImpl implements MavenService {

    @Override
    public void updateVersion(File pomFile, String version) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = null;

            document = saxBuilder.build(pomFile);
            // 버전 찾아내서 바꾼다음
            Element rootElement = document.getRootElement();
            List<Element> children = rootElement.getChildren();
            for (Element child : children) {
                if ("version".equals(child.getName())) {
                    child.setText(version);
                    break;
                }
            }

            // 쓰기
            FileOutputStream outputStream = new FileOutputStream(pomFile);
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.output(document, outputStream);

            outputStream.close();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }
}
