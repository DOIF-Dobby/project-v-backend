package org.doif.projectv.common.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.user.dto.QUserRoleDto_ResultRole;
import org.doif.projectv.common.user.dto.UserRoleDto;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.doif.projectv.common.role.entity.QRole.role;
import static org.doif.projectv.common.user.entity.QUserRole.userRole;

@RequiredArgsConstructor
@Repository
public class UserRoleRepositoryImpl implements UserRoleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<UserRoleDto.ResultRole> selectRole(String userId) {
        return queryFactory
                .select(new QUserRoleDto_ResultRole(
                        role.id,
                        role.name,
                        role.description,
                        role.status,
                        userRole.user.id
                        .when(userId).then("Y")
                        .otherwise("N")
                ))
                .from(role)
                .leftJoin(role.userRoles, userRole)
                .where(
                        userRole.user.id.eq(userId)
                        .or(userRole.user.id.isNull())
                )
                .orderBy(role.id.asc())
                .fetch();
    }
}
