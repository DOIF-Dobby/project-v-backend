package org.doif.projectv.business.version.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.doif.projectv.business.version.constant.VersionStatus;
import org.doif.projectv.business.version.dto.QVersionDto_Result;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.doif.projectv.business.version.entity.QVersion.version;
import static org.springframework.util.StringUtils.isEmpty;

public class VersionRepositoryImpl extends Querydsl4RepositorySupport implements VersionQueryRepository {

    public VersionRepositoryImpl() {
        super(Version.class);
    }

    @Override
    public Page<VersionDto.Result> searchByCondition(VersionDto.Search search, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QVersionDto_Result(
                        version.id,
                        version.name,
                        version.description,
                        version.module.id,
                        version.status,
                        version.revision,
                        version.tag
                ))
                .from(version)
                .where(
                        moduleIdEq(search.getModuleId()),
                        versionNameEq(search.getVersionName()),
                        descriptionLike(search.getDescription()),
                        versionStatusEq(search.getVersionStatus())
                )
                .orderBy(version.id.asc())
        );
    }

    private BooleanExpression moduleIdEq(Long moduleId) {
        return isEmpty(moduleId) ? null : version.module.id.eq(moduleId);
    }

    private BooleanExpression versionNameEq(String versionName) {
        return isEmpty(versionName) ? null : version.name.eq(versionName);
    }

    private BooleanExpression descriptionLike(String description) {
        return isEmpty(description) ? null : version.description.contains(description);
    }

    private BooleanExpression versionStatusEq(VersionStatus versionStatus) {
        return isEmpty(versionStatus) ? null : version.status.eq(versionStatus);
    }
}
