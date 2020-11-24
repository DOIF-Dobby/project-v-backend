package org.doif.projectv.common.enumeration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnumConfig {

    @Bean
    public EnumMapper enumMapper() {
        EnumMapper enumMapper = new EnumMapper();
        CodeEnum[] codeEnums = CodeEnum.values();

        for (CodeEnum codeEnum : codeEnums) {
            enumMapper.put(codeEnum.getKey(), codeEnum.getType());
        }

        return enumMapper;
    }
}
