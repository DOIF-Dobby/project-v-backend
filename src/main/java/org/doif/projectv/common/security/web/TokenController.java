package org.doif.projectv.common.security.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.security.constant.SecurityConstant;
import org.doif.projectv.common.security.service.JwtTokenService;
import org.doif.projectv.common.security.util.SecurityUtil;
import org.doif.projectv.common.user.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Base64;

/**
 * <pre>
 *     토큰 관련 컨트롤러 클래스이다.
 * </pre>
 * @date : 2020-10-21
 * @author : 김명진
 * @version : 1.0.0
**/
@RestController
@RequestMapping("/token")
@Slf4j
@RequiredArgsConstructor
public class TokenController {

    private final JwtTokenService jwtTokenService;

    /**
     * 요청 쿠키에 refresh-token이 유효하다면
     * access-token을 반환한다.
     * @param request
     * @param response
     * @return
     */
    @GetMapping("/access-token")
    public ResponseEntity<CommonResponse> getAccessToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(SecurityConstant.REFRESH_TOKEN.equals(cookie.getName())) {
                String refreshToken = cookie.getValue();
                byte[] decodeJwtToken = Base64.getDecoder().decode(refreshToken);
                String jwtToken = new String(decodeJwtToken);
                boolean isValid = jwtTokenService.isValidToken(jwtToken);

                if(isValid) {
                    String username = jwtTokenService.getUsername(jwtToken);
                    response.setHeader(SecurityConstant.AUTH_KEY, jwtTokenService.generateJwtToken(username));
                    return ResponseEntity.ok(ResponseUtil.ok());
                } else {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseUtil.unAuthorized());
                }
            };
        }
//        String token = (String) request.getAttribute(jwtTokenService.getAuthKey());
//        Map<String, String> map = new HashMap<>();
//        map.put("code", "SUCCESS");
//        map.put(jwtTokenService.getAuthKey(), token);

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ResponseUtil.unAuthorized());
    }

    /**
     * 요청 쿠키에 refresh-token이 유효한지 체크한다.
     * @param request
     * @return
     */
    @PostMapping("/check-login")
    public ResponseEntity<Boolean> checkLogin(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if(SecurityConstant.REFRESH_TOKEN.equals(cookie.getName())) {
                String refreshToken = cookie.getValue();
                byte[] decodeJwtToken = Base64.getDecoder().decode(refreshToken);
                String jwtToken = new String(decodeJwtToken);
                boolean isValid = jwtTokenService.isValidToken(jwtToken);

                if(isValid) {
                    return ResponseEntity.ok(true);
                } else {
                    return ResponseEntity.ok(false);
                }
            }
        }

        return ResponseEntity.ok(false);
    }

}
