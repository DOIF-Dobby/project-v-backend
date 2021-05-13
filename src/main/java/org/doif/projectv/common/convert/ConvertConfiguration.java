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
        // json 데이터로 넘어왔지만 class field에는 없을 경우 무시
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // 빈 문자열은 NULL로 간주한다.
//        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);

        return objectMapper;
    }
}
