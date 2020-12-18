package org.doif.projectv.common.user.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.doif.projectv.common.user.entity.QUser.user;
import static org.doif.projectv.common.user.entity.QUserRole.userRole;
import static org.springframework.util.StringUtils.isEmpty;

public class UserRepositoryImpl extends Querydsl4RepositorySupport implements UserQueryRepository {

    public UserRepositoryImpl() {
        super(User.class);
    }

    @Override
    public Optional<User> searchUserFetchRole(String userId) {
        User findUser = getQueryFactory()
                .selectFrom(user)
                .leftJoin(user.userRoles, userRole).fetchJoin()
                .where(user.id.eq(userId))
                .fetchOne();

        return Optional.ofNullable(findUser);
    }

    @Override
    public Page<User> selectByCondition(UserDto.Search search, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .selectFrom(user)
                .where(
                        idEq(search.getId()),
                        nameLike(search.getName()),
                        statusEq(search.getStatus())
                )

        );
    }

    private BooleanExpression idEq(String id) {
        return isEmpty(id) ? null : user.id.eq(id);
    }

    private BooleanExpression nameLike(String name) {
        return isEmpty(name) ? null : user.name.contains(name);
    }

    private BooleanExpression statusEq(UserStatus status) {
        return isEmpty(status) ? null : user.status.eq(status);
    }
}
