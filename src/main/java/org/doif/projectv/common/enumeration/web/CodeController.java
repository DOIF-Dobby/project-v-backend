package org.doif.projectv.common.enumeration.web;

import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.enumeration.EnumModel;
import org.doif.projectv.common.enumeration.dto.CodeDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class CodeController {
    
    @GetMapping("/api/codes")
    public List<CodeDto> getCodes(@RequestParam("groupCode") String groupCode) {
        List<CodeDto> codeDtoList = new ArrayList<>();
        CodeEnum[] codeEnums = CodeEnum.values();

        for (CodeEnum codeEnum : codeEnums) {
            if(codeEnum.name().equals(groupCode)) {
                Class<? extends EnumModel> type = codeEnum.getType();
                EnumModel[] enumConstants = type.getEnumConstants();
                for (EnumModel enumConstant : enumConstants) {
                    CodeDto codeDto = new CodeDto(enumConstant.getCode(), enumConstant.getMessage());
                    codeDtoList.add(codeDto);
                }

                break;
            }
        }       
        
        return codeDtoList;
    }
}
