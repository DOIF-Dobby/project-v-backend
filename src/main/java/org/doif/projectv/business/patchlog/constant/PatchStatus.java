package org.doif.projectv.business.patchlog.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum PatchStatus implements EnumModel {
    SCHEDULE("패치 예정"),
    COMPLETE("패치 완료"),
    FAIL("패치 실패");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }
}
