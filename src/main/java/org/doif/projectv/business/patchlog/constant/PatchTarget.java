package org.doif.projectv.business.patchlog.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum PatchTarget implements EnumModel {
    DEV("개발기"),
    PROD("상용기");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
