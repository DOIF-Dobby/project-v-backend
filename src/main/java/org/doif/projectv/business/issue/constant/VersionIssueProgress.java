package org.doif.projectv.business.issue.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum VersionIssueProgress implements EnumModel {
    TODO("할 일"),
    PROGRESSING("진행 중"),
    COMPLETE("완료");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
