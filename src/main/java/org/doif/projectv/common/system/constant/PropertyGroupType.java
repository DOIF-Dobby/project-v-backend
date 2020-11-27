package org.doif.projectv.common.system.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum PropertyGroupType implements EnumModel {
    COMMON("공통");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
