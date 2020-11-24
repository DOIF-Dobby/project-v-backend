package org.doif.projectv.business.task.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum TaskType implements EnumModel {
    DEVELOP("개발"),
    RND("R&D"),
    SUPPORT("운영지원"),
    ETC("기타");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
