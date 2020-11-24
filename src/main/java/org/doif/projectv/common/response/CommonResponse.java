package org.doif.projectv.common.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse {
    private String code;
    private String message;

    public CommonResponse(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }
}
