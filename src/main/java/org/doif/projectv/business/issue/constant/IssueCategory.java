package org.doif.projectv.business.issue.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum IssueCategory implements EnumModel {
    NEW_DEVELOP("신규 개발"),
    ERROR_MODIFY("오류 수정"),
    OPERATION_SUPPORT("운영 지원");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
