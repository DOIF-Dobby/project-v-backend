package org.doif.projectv.common.security.util;

import org.doif.projectv.common.user.entity.User;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * <pre>
 *     Security Util Class
 * </pre>
 * @date : 2020-11-18
 * @author : 김명진
 * @version : 1.0.0
 **/
public class SecurityUtil {

    public static Optional<User> getUserByContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if(authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        Object principal = authentication.getPrincipal();

        // pricipal이 User가 아니라면 Login 하지 않은 것이므로 empty
        if(!(principal instanceof User)) {
            return Optional.empty();
        }

        User user = (User) principal;

        return Optional.ofNullable(user);
    }

}
