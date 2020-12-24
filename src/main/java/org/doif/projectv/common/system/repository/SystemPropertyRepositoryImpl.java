package org.doif.projectv.common.system.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.system.constant.PropertyGroupType;
import org.doif.projectv.common.system.dto.SystemPropertyDto;
import org.doif.projectv.common.system.entity.QSystemProperty;
import org.doif.projectv.common.system.entity.SystemProperty;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.doif.projectv.common.system.entity.QSystemProperty.*;

@Repository
@RequiredArgsConstructor
public class SystemPropertyRepositoryImpl implements SystemPropertyQueryRepository {

    private final JPAQueryFactory queryFactory;


    @Override
    public List<SystemProperty> searchByCondition(SystemPropertyDto.Search search) {
        return queryFactory
                .selectFrom(systemProperty)
                .where(
                        propertyGroupEq(search.getPropertyGroup())
                )
                .orderBy(systemProperty.propertyGroup.asc(), systemProperty.property.asc())
                .fetch();
    }

    private BooleanExpression propertyGroupEq(PropertyGroupType propertyGroup) {
        return StringUtils.isEmpty(propertyGroup) ? null : systemProperty.propertyGroup.eq(propertyGroup);
    }
}
