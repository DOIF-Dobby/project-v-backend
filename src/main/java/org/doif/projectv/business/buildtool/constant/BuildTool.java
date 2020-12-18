package org.doif.projectv.business.buildtool.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum BuildTool implements EnumModel {

    MAVEN("Maven"),
    GRADLE("Gradle");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
