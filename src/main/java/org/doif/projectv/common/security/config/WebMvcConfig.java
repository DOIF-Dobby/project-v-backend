package org.doif.projectv.common.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 정적 자원 위치
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = { 
            "classpath:/static/", 
            "classpath:/public/", 
            "classpath:/",
            "classpath:/resources/", 
            "classpath:/META-INF/resources/", 
            "classpath:/META-INF/resources/webjars/" 
    };

    /**
     * 정적 자원 위치 등록
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }

//    /**
//     * 인터셉터 추가
//     * @param registry
//     */
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(jwtTokenInterceptor())
//                .addPathPatterns("/api/**");
//    }
}
