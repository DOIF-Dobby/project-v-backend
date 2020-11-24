package org.doif.projectv.common.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * <pre>
 *     로그아웃에 성공했을 시 수행되는 핸들러이다.
 *     SimpleUrlLogoutSuccessHandler를 상속한 CustomLogoutSuccessHandler를 등록해주었다.
 * </pre>
 * @date : 2020-10-19
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("[:: CustomLogoutSuccessHandler > onLogoutSuccess ::]");
        log.info("[:: Logout user: {} ::]", authentication.getPrincipal());
        log.info("[:: Logout time: {} ::]", LocalDateTime.now());
        super.onLogoutSuccess(request, response, authentication);
    }
}
