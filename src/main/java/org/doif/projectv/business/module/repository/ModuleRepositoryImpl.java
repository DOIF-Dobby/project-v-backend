package org.doif.projectv.business.module.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.module.dto.QModuleDto_Result;
import org.doif.projectv.business.module.entity.QModule;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.doif.projectv.business.module.entity.QModule.*;

@Repository
@RequiredArgsConstructor
public class ModuleRepositoryImpl implements ModuleQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ModuleDto.Result> searchByProjectId(Long projectId) {
        return queryFactory
                .select(new QModuleDto_Result(
                        module.id,
                        module.moduleName,
                        module.description,
                        module.vcsType,
                        module.vcsRepository,
                        module.buildTool
                ))
                .from(module)
                .where(
                        module.project.id.eq(projectId)
                )
                .orderBy(module.id.asc())
                .fetch();
    }
}
