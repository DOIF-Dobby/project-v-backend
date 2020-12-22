package org.doif.projectv.business.buildtool.operator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
public class GradleOperator implements BuildToolOperator {
    
    @Override
    public void updateVersionName(File buildToolFile, String versionName) {
        try {
            // build.gradle 파일의 모든 내용을 String 으로 저장
            String allText = FileCopyUtils.copyToString(new FileReader(buildToolFile));

            // 버전 라인을 찾는다.
            Pattern findVersionLinePattern = Pattern.compile("version\\s*=\\s*.*[\"'].*[\"']");
            Matcher findVersionLineMatcher = findVersionLinePattern.matcher(allText);
            
            if( !findVersionLineMatcher.find() ) {
                throw new IllegalArgumentException("Gradle 파일에서 Version 정보를 찾을 수 없음");
            }
            
            String versionLine = findVersionLineMatcher.group();

            // 버전 라인에서 실제 버전을 찾는다.
            Pattern findVersionPattern = Pattern.compile("(?<=['\"]).*(?=['\"])");
            Matcher findVersionMatcher = findVersionPattern.matcher(versionLine);

            if( !findVersionMatcher.find() ) {
                throw new IllegalArgumentException("Gradle 파일에서 Version 정보를 찾을 수 없음");
            }

            String version = findVersionMatcher.group();

            // 버전을 바꿔서 모든 텍스트를 replace 한 후
            String replaceVersionLine = versionLine.replace(version, versionName);
            String replaceAllText = allText.replace(versionLine, replaceVersionLine);

            // 파일 Writer
            FileCopyUtils.copy(replaceAllText.getBytes(), buildToolFile);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
