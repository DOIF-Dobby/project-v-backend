package org.doif.projectv.common.security.filter;

import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.security.service.JwtTokenService;
import org.doif.projectv.common.security.vo.JwtAuthenticationToken;
import org.doif.projectv.common.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <pre>
 *     로그인이 인증된 이후의 요청에 대해 Header 인증을 담당할 Filter이다.
 *     HTTP 기본 인증 헤더를 감시하고 이를 처리하는 역할.
 *     BasicAuthenticationFilter를 상속한 JwtAuthenticationFilter를 등록하였다.
 *     JWT 기반 인증에서 실제 JWT 토큰의 인증이 이루어질 필터 부분이다.
 * </pre>
 * @date : 2020-10-20
 * @author : 김명진
 * @version : 1.0.0
**/
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenService jwtTokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = request.getHeader(jwtTokenService.getAuthKey());
        String requestURI = request.getRequestURI();
        boolean isApiRequest = requestURI.startsWith("/api/");

        log.info("[:: JwtAuthenticationFilter > doFilterInternal isApiRequest: {} ::]", isApiRequest);

        // API 요청이 아니라면 다음 필터 수행
        if(!isApiRequest) {
            chain.doFilter(request, response);
            return;
        }

        boolean isValidToken = jwtTokenService.isValidToken(token);
        log.info("[:: JwtAuthenticationFilter > doFilterInternal isValidToken: {} ::]", isValidToken);

        // API 요청인데도 토큰이 유효하지 않으면 error response
        if(!isValidToken) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        // 토큰에서 권한과 userId 추출
        String id = jwtTokenService.getId(token);
        User user = new User(id);

        // Authentication 객체 생성 후, SecurityContext에 저장
        JwtAuthenticationToken jwtAuthenticationToken = new JwtAuthenticationToken(user, null, null);
        SecurityContextHolder.getContext().setAuthentication(jwtAuthenticationToken);

        chain.doFilter(request, response);
    }
}
