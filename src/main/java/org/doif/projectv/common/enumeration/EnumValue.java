package org.doif.projectv.common.enumeration;

import lombok.Getter;

@Getter
public class EnumValue {
    private String code;
    private String message;

    public EnumValue(EnumModel enumModel) {
        this.code = enumModel.getCode();
        this.message = enumModel.getMessage();
    }
}
