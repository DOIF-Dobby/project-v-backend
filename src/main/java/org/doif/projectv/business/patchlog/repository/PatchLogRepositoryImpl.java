package org.doif.projectv.business.patchlog.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.business.patchlog.dto.PatchLogSearchCondition;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static org.doif.projectv.business.module.entity.QModule.module;
import static org.doif.projectv.business.patchlog.entity.QPatchLog.patchLog;
import static org.springframework.util.StringUtils.isEmpty;

public class PatchLogRepositoryImpl extends Querydsl4RepositorySupport implements PatchLogQueryRepository{

    public PatchLogRepositoryImpl() {
        super(PatchLog.class);
    }

    @Override
    public Page<PatchLogDto> searchByCondition(PatchLogSearchCondition condition, Pageable pageable) {
//        return applyPagination(pageable, contentQuery -> contentQuery
//                .select(new QPatchLogDto(
//                        patchLog.id,
//                        module.moduleName,
//                        patchLog.target,
//                        patchLog.status,
//                        patchLog.patchScheduleDate,
//                        patchLog.patchDate,
//                        patchLog.worker,
//                        patchLog.version,
//                        patchLog.remark
//                ))
//                .from(patchLog)
//                .join(patchLog.module, module)
//                .where(
//                        moduleIdEq(condition.getModuleId()),
//                        targetEq(condition.getTarget()),
//                        statusEq(condition.getStatus()),
//                        patchScheduleDateGoe(condition.getPatchScheduleDateGoe()),
//                        patchScheduleDateLt(condition.getPatchScheduleDateLt()),
//                        patchDateGoe(condition.getPatchDateGoe()),
//                        patchDateLt(condition.getPatchDateLt()),
//                        workerEq(condition.getWorker())
//                )
//                .orderBy(patchLog.id.asc())
//        );
        return null;
    }

    private BooleanExpression moduleIdEq(Long moduleId) {
        return isEmpty(moduleId) ? null : module.id.eq(moduleId);
    }

    private BooleanExpression targetEq(PatchTarget target) {
        return isEmpty(target) ? null : patchLog.target.eq(target);
    }

    private BooleanExpression statusEq(PatchStatus status) {
        return isEmpty(status) ? null : patchLog.status.eq(status);
    }

    private BooleanExpression patchScheduleDateGoe(LocalDate patchScheduleDateGoe) {
        return isEmpty(patchScheduleDateGoe) ? null : patchLog.patchScheduleDate.goe(patchScheduleDateGoe);
    }

    private BooleanExpression patchScheduleDateLt(LocalDate patchScheduleDateLt) {
        return isEmpty(patchScheduleDateLt) ? null : patchLog.patchScheduleDate.lt(patchScheduleDateLt.plusDays(1));
    }

    private BooleanExpression patchDateGoe(LocalDate patchDateGoe) {
        return isEmpty(patchDateGoe) ? null : patchLog.patchDate.goe(patchDateGoe);
    }

    private BooleanExpression patchDateLt(LocalDate patchDateLt) {
        return isEmpty(patchDateLt) ? null : patchLog.patchDate.lt(patchDateLt.plusDays(1));
    }

    private BooleanExpression workerEq(String worker) {
        return isEmpty(worker) ? null : patchLog.worker.eq(worker);
    }
}
