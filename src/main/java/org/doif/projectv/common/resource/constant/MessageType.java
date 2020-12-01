package org.doif.projectv.common.resource.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum MessageType implements EnumModel {
    INFO("정보"),
    WARN("경고"),
    ERROR("에러");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
