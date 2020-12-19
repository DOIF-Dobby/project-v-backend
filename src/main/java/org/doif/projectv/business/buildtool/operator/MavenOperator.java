package org.doif.projectv.business.buildtool.operator;

import lombok.RequiredArgsConstructor;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MavenOperator implements BuildToolOperator {

    @Override
    public void updateVersion(File buildToolFile, String versionName) {
        try {
            SAXBuilder saxBuilder = new SAXBuilder();
            Document document = null;

            document = saxBuilder.build(buildToolFile);
            // 버전 찾아내서 바꾼다음
            Element rootElement = document.getRootElement();
            List<Element> children = rootElement.getChildren();
            for (Element child : children) {
                if ("version".equals(child.getName())) {
                    child.setText(versionName);
                    break;
                }
            }

            // 쓰기
            FileOutputStream outputStream = new FileOutputStream(buildToolFile);
            XMLOutputter xmlOutputter = new XMLOutputter();
            xmlOutputter.output(document, outputStream);

            outputStream.close();
        } catch (JDOMException | IOException e) {
            e.printStackTrace();
        }
    }
}
