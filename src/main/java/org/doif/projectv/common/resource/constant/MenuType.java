package org.doif.projectv.common.resource.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum  MenuType implements EnumModel {
    CATEGORY("카테고리"),
    MENU("메뉴");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
