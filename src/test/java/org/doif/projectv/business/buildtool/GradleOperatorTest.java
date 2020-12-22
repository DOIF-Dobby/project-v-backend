package org.doif.projectv.business.buildtool;

import org.doif.projectv.business.buildtool.operator.GradleOperator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
public class GradleOperatorTest {

    @Autowired
    GradleOperator gradleOperator;

//    @Test
    void changeVersionNameTest() throws IOException {
        File buildGradle = new File("C:\\Users\\MJ\\AppData\\Local\\Temp\\jgit3502953127406534895", "build.gradle");

        String allText = FileCopyUtils.copyToString(new FileReader(buildGradle));

        Pattern pattern1 = Pattern.compile("version\\s*=\\s*.*[\"'].*[\"']");
        Matcher matcher1 = pattern1.matcher(allText);
        matcher1.find();
        String versionLine = matcher1.group();

        Pattern pattern2 = Pattern.compile("(?<=['\"]).*(?=['\"])");
        Matcher matcher2 = pattern2.matcher(versionLine);
        matcher2.find();
        String version = matcher2.group();

        String replaceVersionLine = versionLine.replace(version, "0.0.3");

        String replace = allText.replace(versionLine, replaceVersionLine);

        System.out.println(replace);

    }

//    @Test
    void test () {
        File buildGradle = new File("C:\\Users\\MJ\\AppData\\Local\\Temp\\jgit3502953127406534895", "build.gradle");
        gradleOperator.updateVersionName(buildGradle, "1.2.4");
    }

}
