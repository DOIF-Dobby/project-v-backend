package org.doif.projectv.common.resource.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.AuthorityResourceDto;
import org.doif.projectv.common.resource.dto.QAuthorityResourceDto;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.springframework.stereotype.Repository;

import javax.management.relation.RoleStatus;
import java.util.List;

import static org.doif.projectv.common.resource.entity.QResourceAuthority.*;
import static org.doif.projectv.common.role.entity.QRole.*;
import static org.doif.projectv.common.role.entity.QRoleResource.roleResource;
import static org.doif.projectv.common.user.entity.QUser.*;
import static org.doif.projectv.common.user.entity.QUserRole.*;

@RequiredArgsConstructor
@Repository
public class ResourceRepositoryImpl implements ResourceQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<AuthorityResourceDto> searchAuthorityResource(String userId) {
        return queryFactory
                .select(new QAuthorityResourceDto(
                        resourceAuthority.url,
                        resourceAuthority.httpMethod
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
}
