package org.doif.projectv.common.user.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.user.entity.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static org.doif.projectv.common.role.entity.QRole.role;
import static org.doif.projectv.common.user.entity.QUser.user;
import static org.doif.projectv.common.user.entity.QUserRole.userRole;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public Optional<User> searchUserFetchRole(String userId) {
        User findUser = queryFactory
                .selectFrom(user)
                .leftJoin(user.userRoles, userRole).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(findUser);
    }
}
