package org.doif.projectv.common.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.enumeration.EnumModel;

@Getter
@RequiredArgsConstructor
public enum ResponseCode implements EnumModel {
    OK("성공적으로 처리되었습니다."),
    NOT_FOUND("리소스를 찾지 못했습니다."),
    BAD_PARAMETER("요청 파라미터가 잘못되었습니다."),
    UNAUTHORIZED("인증에 실패하였습니다."),
    SERVER_ERROR("서버 에러입니다.");

    private final String message;

    @Override
    public String getCode() {
        return this.name();
    }

}
