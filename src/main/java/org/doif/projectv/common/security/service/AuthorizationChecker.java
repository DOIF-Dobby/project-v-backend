package org.doif.projectv.common.security.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.resource.dto.ResourceAuthorityDto;
import org.doif.projectv.common.resource.service.ResourceService;
import org.doif.projectv.common.user.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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

    public boolean check(HttpServletRequest request, Authentication authentication) {
        String httpMethod = request.getMethod();
        String requestURI = request.getRequestURI();
        log.info("[:: AuthorizationChecker > check ::]");
        log.info("[:: AuthorizationChecker > check requestURI: {} ::]", httpMethod + " " + requestURI);

        Object principal = authentication.getPrincipal();

        // pricipal이 User가 아니라면 Login 하지 않은 것이므로 false
        if(!(principal instanceof User)) {
            return false;
        }

        User user = (User) principal;

        List<ResourceAuthorityDto.Result> resourceAuthorityDtos = resourceService.searchAuthorityResource(user.getId());

        for (ResourceAuthorityDto.Result dto: resourceAuthorityDtos) {
            if(httpMethod.equals(dto.getHttpMethod().name()) && antPathMatcher.match(dto.getUrl(), requestURI)) {
                log.info("[:: AuthorizationChecker > check allow [ Authority: {} User: {} ] ::]", httpMethod + " " + requestURI, user.getId());
                return true;
            }
        }

        log.error("[:: AuthorizationChecker > check not allow [ Authority: {} User: {} ] ::]", httpMethod + " " + requestURI, user.getId());
        return false;
    }
}
