package org.doif.projectv.common.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <pre>
 *     Form Login 실패시 수행되는 핸들러이다.
 *     SimpleUrlAuthenticationFailureHandler를 상속한 CustomAuthenticationFailureHandler를 등록해주었다.
 *
 *     상속 대신 AuthenticationFailureHandler 인터페이스를 구현해도 된다.
 * </pre>
 * @date : 2020-10-19
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("[:: CustomAuthenticationFailureHandler > onAuthenticationFailure ::]");
        log.info("[:: Login fail message: {} ::]", exception.getMessage());
        log.info("[:: Login fail time: {} ::]", LocalDateTime.now());
        super.onAuthenticationFailure(request, response, exception);
    }
}
