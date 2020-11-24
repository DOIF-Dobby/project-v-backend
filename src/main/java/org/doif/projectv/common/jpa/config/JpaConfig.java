package org.doif.projectv.common.jpa.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.extern.slf4j.Slf4j;
import org.doif.projectv.common.user.entity.User;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.persistence.EntityManager;
import java.util.Optional;

@Configuration
@EnableJpaAuditing
@Slf4j
public class JpaConfig {

    /**
     * createdBy, lastModifiedBy 자동 주입
     * @return
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        return () -> {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

            if(authentication == null || !authentication.isAuthenticated()) {
                return Optional.empty();
            }

            Object principal = authentication.getPrincipal();

            // pricipal이 User가 아니라면 Login 하지 않은 것이므로 false
            if(!(principal instanceof User)) {
                throw new AuthenticationServiceException("Invalid Token");
            }

            User user = (User) principal;

            log.info("Jpa Audtior userId: {}", user.getId());
            return Optional.of(user.getId());
        };

    }

    /**
     * Querydsl 작성에 필요한 JPAQueryFactory을 Bean으로 등록
     * @param em
     * @return
     */
    @Bean
    public JPAQueryFactory queryFactory(EntityManager em) {
        return new JPAQueryFactory(em);
    }
}
