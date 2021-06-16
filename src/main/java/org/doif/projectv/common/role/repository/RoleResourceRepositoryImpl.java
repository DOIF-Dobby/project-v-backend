package org.doif.projectv.common.role.repository;

import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.entity.*;
import org.doif.projectv.common.role.dto.*;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.doif.projectv.common.resource.entity.QButton.*;
import static org.doif.projectv.common.resource.entity.QMenu.*;
import static org.doif.projectv.common.resource.entity.QMenuCategory.*;
import static org.doif.projectv.common.resource.entity.QPage.page;
import static org.doif.projectv.common.resource.entity.QTab.*;
import static org.doif.projectv.common.role.entity.QRoleResource.roleResource;

@RequiredArgsConstructor
@Repository
public class RoleResourceRepositoryImpl implements RoleResourceQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<RoleResourceDto.ResultPage> selectPage(RoleResourceDto.SearchPage search) {
        return queryFactory
                .select(new QRoleResourceDto_ResultPage(
                        page.id,
                        page.name,
                        page.status,
                        JPAExpressions
                            .select(roleResource.id.isNotNull())
                            .from(roleResource)
                            .where(
                                    roleResource.role.id.eq(search.getRoleId()),
                                    roleResource.resource.id.eq(page.id)
                            )
                ))
                .from(page)
                .orderBy(page.url.asc())
                .fetch();
    }

    @Override
    public List<RoleResourceDto.ResultButton> selectButton(RoleResourceDto.Search search) {
        return queryFactory
                .select(new QRoleResourceDto_ResultButton(
                        button.id,
                        page.id,
                        button.name,
                        button.description,
                        button.status,
                        JPAExpressions
                                .select(roleResource.id.isNotNull())
                                .from(roleResource)
                                .where(
                                        roleResource.role.id.eq(search.getRoleId()),
                                        roleResource.resource.id.eq(button.id)
                                )
                ))
                .from(button)
                .where(button.page.id.eq(search.getPageId()))
                .orderBy(button.id.asc())
                .fetch();
    }

    @Override
    public List<RoleResourceDto.ResultTab> selectTab(RoleResourceDto.Search search) {
        return queryFactory
                .select(new QRoleResourceDto_ResultTab(
                        tab.id,
                        page.id,
                        tab.name,
                        tab.description,
                        tab.status,
                        JPAExpressions
                                .select(roleResource.id.isNotNull())
                                .from(roleResource)
                                .where(
                                        roleResource.role.id.eq(search.getRoleId()),
                                        roleResource.resource.id.eq(tab.id)
                                )
                ))
                .from(tab)
                .where(tab.page.id.eq(search.getPageId()))
                .orderBy(tab.id.asc())
                .fetch();
    }
}
