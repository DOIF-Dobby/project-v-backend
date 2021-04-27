package org.doif.projectv.common.enumeration.dto;

import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {
    private String code;
    private String name;
    private String render;

    public CodeDto(String code, String name) {
        this.code = code;
        this.name = name;
    }
}
