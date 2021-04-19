package org.doif.projectv.common.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.security.constant.SecurityConstant;
import org.doif.projectv.common.security.service.JwtTokenService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;

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
@RequiredArgsConstructor
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

    private final JwtTokenService jwtTokenService;

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        log.info("[:: CustomLogoutSuccessHandler > onLogoutSuccess ::]");

        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(SecurityConstant.REFRESH_TOKEN.equals(cookie.getName())) {
                String encodeJwtToken = cookie.getValue();
                byte[] decodeJwtToken = Base64.getDecoder().decode(encodeJwtToken);
                String refreshToken = new String(decodeJwtToken);

                // 유효한 토큰이 아니라면 어차피 로그아웃된 것이나 다름없다.
                if(!jwtTokenService.isValidToken(refreshToken)) {
                    super.onLogoutSuccess(request, response, authentication);
                }

                String username = jwtTokenService.getUsername(refreshToken);
                log.info("[:: Logout user: {} ::]", username);
                log.info("[:: Logout time: {} ::]", LocalDateTime.now());

                cookie.setMaxAge(0);
                response.addCookie(cookie);

                RequestDispatcher requestDispatcher = request.getRequestDispatcher("/auth/logout");
                requestDispatcher.forward(request, response);
            }
        }

        super.onLogoutSuccess(request, response, authentication);
    }
}
