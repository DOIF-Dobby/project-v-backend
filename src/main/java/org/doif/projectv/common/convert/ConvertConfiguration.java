package org.doif.projectv.common.convert;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class ConvertConfiguration {

    @Bean
    public ObjectMapper objectMapper() {
        log.info("Register Custom ObjectMapper bean");
        ObjectMapper objectMapper = new ObjectMapper();
        // enum으로 컨버팅 할 수 없는 값이 들어오면 NULL 값으로 처리 validation 체크는 @Valid 어노테이션으로 한다.
        objectMapper.configure(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_AS_NULL, true);

        return objectMapper;
    }
}
