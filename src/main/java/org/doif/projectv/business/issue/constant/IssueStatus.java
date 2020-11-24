package org.doif.projectv.business.issue.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum IssueStatus implements EnumModel {
    OPEN("열림"),
    CLOSE("닫힘");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
