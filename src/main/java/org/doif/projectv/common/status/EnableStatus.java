package org.doif.projectv.common.status;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum EnableStatus implements EnumModel {

    ENABLE("가능"),
    DISABLE("불가능");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
