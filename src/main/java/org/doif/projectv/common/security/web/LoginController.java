package org.doif.projectv.common.security.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.security.service.JwtTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <pre>
 *     로그인 관련 컨트롤러 클래스이다.
 * </pre>
 * @date : 2020-10-21
 * @author : 김명진
 * @version : 1.0.0
**/
@RestController
@RequestMapping("/login")
@Slf4j
@RequiredArgsConstructor
public class LoginController {

    private final JwtTokenService jwtTokenService;

    /**
     * 로그인 성공 시 code와 token 값을 반환한다.
     * @param request
     * @param response
     * @return
     */
    @PostMapping("/success")
    public ResponseEntity<CommonResponse> loginSuccess(HttpServletRequest request, HttpServletResponse response) {
//        String token = (String) request.getAttribute(jwtTokenService.getAuthKey());
//        Map<String, String> map = new HashMap<>();
//        map.put("code", "SUCCESS");
//        map.put(jwtTokenService.getAuthKey(), token);

        return ResponseEntity.ok(ResponseUtil.ok());
    }
}
