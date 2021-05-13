package org.doif.projectv.common.response;

import lombok.*;
import org.doif.projectv.common.exception.ValidationExceptionDto;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommonResponse {
    private String code;
    private String message;
    private Map<String, String> validationMap;

    public CommonResponse(ResponseCode code) {
        this.code = code.getCode();
        this.message = code.getMessage();
    }

    public CommonResponse(ResponseCode code, Map<String, String> validationMap) {
        this.code = code.getCode();
        this.message = code.getMessage();
        this.validationMap = validationMap;
    }
}
