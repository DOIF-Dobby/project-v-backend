package org.doif.projectv.common.security.config;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.security.filter.CustomUsernamePasswordAuthenticationFilter;
import org.doif.projectv.common.security.filter.JwtAuthenticationFilter;
import org.doif.projectv.common.security.handler.*;
import org.doif.projectv.common.security.provider.CustomAuthenticationProvider;
import org.doif.projectv.common.security.service.JwtTokenService;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 스프링 시큐리티가 사용자를 인증하는 방법이 담긴 객체
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(customAuthenticationProvider());
    }

    /**
     * 스프링 시큐리티 룰을 무시하게 하는 URL 규칙
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 정적 자원에 대해서는 시큐리티 설정을 적용하지 않음
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    /**
     * 스프링 시큐리티 룰
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()      // rest api 이므로 기본설정 사용안함. 기본설정은 비인증시 로그인폼 화면으로 리다이렉트 된다.
                .csrf().disable()       // rest api이므로 csrf 보안이 필요없으므로 disable처리.
                    .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // jwt token으로 인증할것이므로 세션 사용 안함.
                .and()
                    .exceptionHandling()
                    .authenticationEntryPoint(authenticationEntryPoint())
                .and()
                    .authorizeRequests()
                    .antMatchers("/api/**").access("@authorizationChecker.check(request, authentication)")// 요청에 대한 사용권한 체크
                .and()
                    .logout()
                    .logoutUrl("/logout")   // logout 요청 url 설정
                    .logoutSuccessHandler(logoutSuccessHandler())
                .and()
                    .addFilterBefore(customUsernamePasswordAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class) // UsernamePasswordAuthenticationFilter 전에 커스텀 필터 등록
                    .addFilterBefore(jwtAuthenticationFilter(), CustomUsernamePasswordAuthenticationFilter.class)
                    .exceptionHandling()
                    .accessDeniedHandler(accessDeniedHandler());


    }

    /**
     * Json Web Token을 생성하고 검증하는 서비스 클래스
     * @return
     */
    @Bean
    public JwtTokenService jwtTokenService() {
        return new JwtTokenService();
    }

    /**
     * 암호화 알고리즘으로 BCrypt를 사용
     * @return
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 실제 인증을 수행하는 Provider
     * @return
     */
    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider();
    }

    /**
     * 로그인 시 걸리는 Filter bean register
     * @return
     * @throws Exception
     */
    @Bean
    public CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter() throws Exception {
        CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter = new CustomUsernamePasswordAuthenticationFilter(authenticationManager());
        customUsernamePasswordAuthenticationFilter.setFilterProcessesUrl("/login");
        customUsernamePasswordAuthenticationFilter.setUsernameParameter("id");
        customUsernamePasswordAuthenticationFilter.setPasswordParameter("password");

        customUsernamePasswordAuthenticationFilter.setAuthenticationSuccessHandler(authenticationSuccessHandler());
        customUsernamePasswordAuthenticationFilter.setAuthenticationFailureHandler(authenticationFailureHandler());

        customUsernamePasswordAuthenticationFilter.afterPropertiesSet();

        return customUsernamePasswordAuthenticationFilter;
    }

    /**
     * SuccessHandler bean register
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler authenticationSuccessHandler() {
        CustomAuthenticationSuccessHandler successHandler = new CustomAuthenticationSuccessHandler(jwtTokenService());
//        successHandler.setDefaultTargetUrl("/index");
        return successHandler;
    }

    /**
     * FailureHandler bean register
     * @return
     */
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        CustomAuthenticationFailureHandler failureHandler = new CustomAuthenticationFailureHandler();
//        failureHandler.setDefaultFailureUrl("/loginPage?error=error");
        return failureHandler;
    }

    /**
     * LogoutSuccessHandler bean register
     * @return
     */
    @Bean
    public LogoutSuccessHandler logoutSuccessHandler() {
        CustomLogoutSuccessHandler logoutSuccessHandler = new CustomLogoutSuccessHandler();
//        logoutSuccessHandler.setDefaultTargetUrl("/loginPage?logout=logout");
        return logoutSuccessHandler;
    }

    /**
     * AccessDeniedHandler bean register
     * @return
     */
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        CustomAccessDeniedHandler accessDeniedHandler = new CustomAccessDeniedHandler();
//        accessDeniedHandler.setErrorPage("/error/403");
        return accessDeniedHandler;
    }

    /**
     * AuthenticationEntryPoint bean register
     * @return
     */
    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return new CustomAuthenticationEntryPoint();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        JwtAuthenticationFilter jwtAuthenticationFilter = new JwtAuthenticationFilter();
        return jwtAuthenticationFilter;
    }

}
