package org.doif.projectv.business.vcs.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum VcsType implements EnumModel {

    SVN("SVN", "svnOperator"),
    GIT("Git", "gitOperator");

    private final String message;
    private final String operatorBeanName;

    @Override
    public String getCode() {
        return this.name();
    }
}
