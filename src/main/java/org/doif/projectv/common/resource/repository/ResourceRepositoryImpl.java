package org.doif.projectv.common.resource.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.AuthCheckDto;
import org.doif.projectv.common.resource.dto.QAuthCheckDto_ResourceAuthorityCheck;
import org.doif.projectv.common.resource.dto.QAuthCheckDto_ResourcePageCheck;
import org.doif.projectv.common.resource.entity.QPage;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.doif.projectv.common.resource.entity.QPage.*;
import static org.doif.projectv.common.resource.entity.QResourceAuthority.resourceAuthority;
import static org.doif.projectv.common.role.entity.QRole.role;
import static org.doif.projectv.common.role.entity.QRoleResource.roleResource;
import static org.doif.projectv.common.user.entity.QUser.user;
import static org.doif.projectv.common.user.entity.QUserRole.userRole;

@RequiredArgsConstructor
@Repository
public class ResourceRepositoryImpl implements ResourceQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AuthCheckDto.ResourceAuthorityCheck> searchAuthorityResource(String userId) {
        return queryFactory
                .select(new QAuthCheckDto_ResourceAuthorityCheck(
                        resourceAuthority.url,
                        resourceAuthority.httpMethod,
                        resourceAuthority.page.id
                ))
                .from(resourceAuthority)
                .join(resourceAuthority.roleResources, roleResource)
                .join(roleResource.role, role)
                .join(role.userRoles, userRole)
                .join(userRole.user, user)
                .where(
                        resourceAuthority.status.eq(EnableStatus.ENABLE),
                        role.status.eq(EnableStatus.ENABLE),
                        user.status.eq(UserStatus.VALID),
                        user.id.eq(userId)
                )
                .fetch();
    }

    @Override
    public List<AuthCheckDto.ResourcePageCheck> searchPageResource(String userId) {
        return queryFactory
                .select(new QAuthCheckDto_ResourcePageCheck(
                        page.url
                ))
                .from(page)
                .join(page.roleResources, roleResource)
                .join(roleResource.role, role)
                .join(role.userRoles, userRole)
                .join(userRole.user, user)
                .where(
                        page.status.eq(EnableStatus.ENABLE),
                        role.status.eq(EnableStatus.ENABLE),
                        user.status.eq(UserStatus.VALID),
                        user.id.eq(userId)
                )
                .fetch();
    }
}
