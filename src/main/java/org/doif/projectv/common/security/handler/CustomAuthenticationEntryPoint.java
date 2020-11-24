package org.doif.projectv.common.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *     SecurityContext에 인증된 사용자가 존재하지도 않고,
 *     어떠한 인증도 되지 않은 익명의 사용자가 보호된 리소스에 접근하였을 때, 수행되는 EntryPoint 핸들러이다.
 *     AuthenticationEntryPoint 구현한 CustomAuthenticationEntryPoint를 등록하였다.
 * </pre>
 * @date : 2020-10-19
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        log.info("[:: CustomAuthenticationEntryPoint > commence ::]");
        log.info("[:: Exception: {} ::]", authException.getMessage());

        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}
