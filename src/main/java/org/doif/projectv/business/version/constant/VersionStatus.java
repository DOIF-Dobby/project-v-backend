package org.doif.projectv.business.version.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum VersionStatus implements EnumModel {
    DEVELOP("개발"),
    RELEASE("배포");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
