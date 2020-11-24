package org.doif.projectv.common.security.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandlerImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <pre>
 *     권한 체크 실패시 수행되는 핸들러이다.
 *     AccessDeniedHandlerImpl를 상속한 CustomAccessDeniedHandler를 등록하였다.
 *     권한 체크 실패시 적절한 에러코드와 메시지를 HttpServletResponse에 담아서 반환하는 역할을 한다.
 *
 *     상속 대신 AccessDeniedHandler 인터페이스를 구현해도 된다.
 * </pre>
 * @date : 2020-10-19
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class CustomAccessDeniedHandler extends AccessDeniedHandlerImpl {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        log.info("[:: CustomAccessDeniedHandler > handle ::]");
        log.info("[:: access denied reason: {} ::]", accessDeniedException.getMessage());
        super.handle(request, response, accessDeniedException);
    }
}
