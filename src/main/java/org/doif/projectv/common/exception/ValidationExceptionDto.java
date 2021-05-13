package org.doif.projectv.common.exception;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ValidationExceptionDto {
    private String field;
    private String message;
}
