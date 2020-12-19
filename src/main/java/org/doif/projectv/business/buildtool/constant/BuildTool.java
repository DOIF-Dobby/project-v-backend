package org.doif.projectv.business.buildtool.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum BuildTool implements EnumModel {

    MAVEN("Maven", "mavenOperator", "pom.xml"),
    GRADLE("Gradle","gradleOperator", "build.gradle");

    private final String message;
    private final String operatorBeanName;
    private final String buildToolFileName;

    @Override
    public String getCode() {
        return this.name();
    }
}
