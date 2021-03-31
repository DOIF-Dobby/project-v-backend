package org.doif.projectv.business.patchlog.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.module.entity.QModule;
import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.business.patchlog.dto.QPatchLogVersionDto_Result;
import org.doif.projectv.business.patchlog.entity.QPatchLog;
import org.doif.projectv.business.patchlog.entity.QPatchLogVersion;
import org.doif.projectv.business.version.entity.QVersion;
import org.springframework.stereotype.Repository;

import java.util.List;

import static org.doif.projectv.business.module.entity.QModule.*;
import static org.doif.projectv.business.patchlog.entity.QPatchLog.*;
import static org.doif.projectv.business.patchlog.entity.QPatchLogVersion.*;
import static org.doif.projectv.business.version.entity.QVersion.*;

@Repository
@RequiredArgsConstructor
public class PatchLogVersionRepositoryImpl implements PatchLogVersionQueryRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<PatchLogVersionDto.Result> searchPatchLogVersionsByPatchLogId(Long patchLogId) {
        return queryFactory
                .select(new QPatchLogVersionDto_Result(
                        patchLogVersion.id,
                        patchLog.id,
                        version.id,
                        version.name,
                        version.description,
                        module.moduleName
                ))
                .from(patchLogVersion)
                .join(patchLogVersion.patchLog, patchLog)
                .join(patchLogVersion.version, version)
                .join(version.module, module)
                .where(
                        patchLog.id.eq(patchLogId)
                )
                .orderBy(
                        module.id.asc()
                )
                .fetch();
    }
}
