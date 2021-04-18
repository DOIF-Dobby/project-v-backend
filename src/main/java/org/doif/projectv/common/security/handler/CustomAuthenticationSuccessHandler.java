package org.doif.projectv.common.security.handler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.security.constant.SecurityConstant;
import org.doif.projectv.common.security.service.JwtTokenService;
import org.doif.projectv.common.security.vo.UserDetailsInfo;
import org.doif.projectv.common.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Collection;

/**
 * <pre>
 *     Form Login(AuthenticationFilter)에서 인증이 성공했을 때 수행될 핸들러이다.
 *     SimpleUrlAuthenticationSuccessHandler를 상속한 SavedRequestAwareAuthenticationSuccessHandler를
 *     다시 상속한 CustomAuthenticationSuccessHandler를 등록해주었다.
 *
 *     상속 대신 AuthenticationSuccessHandler 인터페이스를 구현해도 된다.
 * </pre>
 * @date : 2020-10-19
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler  {

    private final JwtTokenService jwtTokenService;

    /**
     * 로그인 성공 시,
     * SecurityContext에 Authentication 저장 후, 로그인 컨트롤러로 forward 한다.
     * @param request
     * @param response
     * @param authentication
     * @throws ServletException
     * @throws IOException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        UserDetailsInfo userDetailsInfo = (UserDetailsInfo) authentication.getPrincipal();
        User user = userDetailsInfo.getUser();

//        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("[:: CustomAuthenticationSuccessHandler > onAuthenticationSuccess ::]");
        log.info("[:: Login success user: {} ::]", user.getId());
        log.info("[:: Login success time: {} ::]", LocalDateTime.now());

        String jwtToken = jwtTokenService.generateJwtToken(user.getId());
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encodedJwtToken = encoder.encode(jwtToken.getBytes());
        Cookie cookie = new Cookie(SecurityConstant.REFRESH_TOKEN, new String(encodedJwtToken));
        cookie.setHttpOnly(true);
        cookie.setMaxAge(SecurityConstant.REFRESH_TOKEN_MAX_AGE);
        response.addCookie(cookie);
//        response.setHeader(jwtTokenService.getAuthKey(), jwtTokenService.generateJwtToken(user));
//        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/login/success");
//        requestDispatcher.forward(request, response);
    }
}
