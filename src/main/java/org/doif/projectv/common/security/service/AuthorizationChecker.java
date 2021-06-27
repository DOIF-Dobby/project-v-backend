package org.doif.projectv.common.security.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.resource.dto.AuthCheckDto;
import org.doif.projectv.common.resource.service.ResourceService;
import org.doif.projectv.common.user.entity.User;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 *     권한을 동적으로 체크하기 위한 클래스이다.
 *     true면 통과, false면 EntiryPoint 클래스 commence 메서드 실행
 * </pre>
 * @date : 2020-10-23
 * @author : 김명진
 * @version : 1.0.0
**/
@Component
@Slf4j
@RequiredArgsConstructor
public class AuthorizationChecker {

    private final ResourceService resourceService;
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();
    private final ObjectMapper objectMapper;

    public boolean check(HttpServletRequest request, Authentication authentication) {
        String httpMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("[:: AuthorizationChecker > check ::]");
        log.info("[:: AuthorizationChecker > check requestURI: {} ::]", httpMethod + " " + requestURI);

        Object principal = authentication.getPrincipal();

        // pricipal이 User가 아니라면 Login 하지 않은 것이므로 false
        if(!(principal instanceof User)) {
            log.error("[:: AuthorizationChecker > check not allow reason: User authentication invalid ::]");
            return false;
        }

        User user = (User) principal;

        // '/api/side-menu' 로 시작하면 GET 메서드인지만 체크 한다.
        if(requestURI.startsWith("/api/side-menu")) {
            return httpMethod.equals(HttpMethod.GET.name());
        }

        // '/api/accessible-menu' 로 시작하면 GET 메서드인지만 체크 한다.
        if(requestURI.startsWith("/api/accessible-menu")) {
            return httpMethod.equals(HttpMethod.GET.name());
        }

        // '/api/codes' 로 시작하면 GET 메서드인지만 체크 한다.
        if(requestURI.startsWith("/api/codes")) {
            return httpMethod.equals(HttpMethod.GET.name());
        }

        // '/api/users/login-user' 로 시작하면 GET 메서드인지만 체크 한다.
        if(requestURI.startsWith("/api/users/login-user")) {
            return httpMethod.equals(HttpMethod.GET.name());
        }

        // '/api/pages/' 로 시작하면 ResourcePage 에 접근 권한이 있는지 체크 한다.
        if(requestURI.startsWith("/api/pages/")) {
            // page 요청인데 GET 요청이 아니면 false
            if(!httpMethod.equals(HttpMethod.GET.name())) {
                return false;
            }

            List<AuthCheckDto.ResourcePageCheck> resourcePageChecks = resourceService.searchPageResource(user.getId());

            for (AuthCheckDto.ResourcePageCheck check : resourcePageChecks) {
                if(antPathMatcher.match(check.getUrl(), requestURI)) {
                    log.info("[:: AuthorizationChecker > check allow [ Authority: {} User: {} ] ::]", httpMethod + " " + requestURI, user.getId());
                    return true;
                }
            }

            // page 요청인데 url이 일치하는 페이지 리소스가 없다면 return false
            log.error("[:: AuthorizationChecker > check not allow [ Authority: {} User: {} ] ::]", httpMethod + " " + requestURI, user.getId());
            return false;
        }

        // '/api/' 로 시작하면 ResourceAuthority 에 접근 권한이 있는지 체크한다. PAGE_ID 까지 체크한다.
        Long pageId = null;
        String pageIdHeader = request.getHeader("pageId");
        if( pageIdHeader != null ) {
            try {
                pageId = Long.parseLong(pageIdHeader);
            } catch (NumberFormatException e) {
                log.error("[:: AuthorizationChecker > check not allow pageId invalid ::]");
                return false;
            }
        }

        // pageId가 요청 헤더로 넘어오지 않으면 return false
        if( pageId == null ) {
            log.error("[:: AuthorizationChecker > check not allow pageId invalid ::]");
            return false;
        }

        // ResourceAuthority 조회
        List<AuthCheckDto.ResourceAuthorityCheck> resourceAuthorityChecks = resourceService.searchAuthorityResource(user.getId());

        for (AuthCheckDto.ResourceAuthorityCheck check: resourceAuthorityChecks) {
            if(httpMethod.equals(check.getHttpMethod().name())
                    && antPathMatcher.match(check.getUrl(), requestURI)
                    && pageId.equals(check.getPageId())) {
                log.info("[:: AuthorizationChecker > check allow [ Authority: {} User: {} ] ::]", httpMethod + " " + requestURI, user.getId());
                return true;
            }
        }

        log.error("[:: AuthorizationChecker > check not allow [ Authority: {} User: {} ] ::]", httpMethod + " " + requestURI, user.getId());
        return false;
    }
}
