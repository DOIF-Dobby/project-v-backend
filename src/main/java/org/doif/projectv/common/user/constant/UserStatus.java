package org.doif.projectv.common.user.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum UserStatus implements EnumModel {
    VALID("유효"),
    INVALID("무효");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
