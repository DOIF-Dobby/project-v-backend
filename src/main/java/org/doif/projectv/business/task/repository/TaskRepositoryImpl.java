package org.doif.projectv.business.task.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.task.dto.QTaskDto_Result;
import org.doif.projectv.business.task.dto.TaskDto;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

import static org.doif.projectv.business.issue.entity.QIssue.*;
import static org.doif.projectv.business.issue.entity.QVersionIssue.*;
import static org.doif.projectv.business.task.entity.QTask.*;
import static org.doif.projectv.business.version.entity.QVersion.*;
import static org.springframework.util.StringUtils.*;

public class TaskRepositoryImpl extends Querydsl4RepositorySupport implements TaskQueryRepository{

    public TaskRepositoryImpl() {
        super(Task.class);
    }

    @Override
    public List<TaskDto.Result> searchByModuleIssueId(Long moduleIssueId) {
//        return getQueryFactory()
//                .select(new QTaskDto_Result(
//                        task.id,
//                        issue.issueName,
//                        task.type,
//                        task.startDate,
//                        task.endDate,
//                        task.manDay,
//                        task.contents,
//                        task.worker,
//                        task.remark
//                ))
//                .from(task)
//                .join(task.moduleIssue, moduleIssue)
//                .join(moduleIssue.issue, issue)
//                .where(
//                        moduleIssue.id.eq(moduleIssueId)
//                )
//                .fetch();
        return null;
    }

    @Override
    public Page<TaskDto.Result> searchByCondition(TaskDto.Search search, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QTaskDto_Result(
                        task.id,
                        issue.issueName,
                        version.name,
                        task.type,
                        task.startDate,
                        task.endDate,
                        task.manDay,
                        task.contents,
                        task.worker,
                        task.remark
                ))
                .from(task)
                .join(task.versionIssue, versionIssue)
                .join(versionIssue.version, version)
                .join(versionIssue.issue, issue)
                .where(
                        startDateGoe(search.getStartDate()),
                        endDateLt(search.getEndDate()),
                        moduleIdEq(search.getModuleId()),
                        versionIdEq(search.getVersionId()),
                        versionIssueIdEq(search.getVersionIssueId()),
                        typeEq(search.getType()),
                        workerEq(search.getWorker())
                )
                .orderBy(task.id.asc())
        );
    }

    private BooleanExpression startDateGoe(LocalDate startDate) {
        return isEmpty(startDate) ? null : task.startDate.goe(startDate);
    }

    private BooleanExpression endDateLt(LocalDate endDate) {
        return isEmpty(endDate) ? null : task.endDate.lt(endDate.plusDays(1));
    }

    private BooleanExpression moduleIdEq(Long moduleId) {
        return isEmpty(moduleId) ? null : versionIssue.version.module.id.eq(moduleId);
    }

    private BooleanExpression versionIdEq(Long versionId) {
        return isEmpty(versionId) ? null : versionIssue.version.id.eq(versionId);
    }

    private BooleanExpression versionIssueIdEq(Long versionIssueId) {
        return isEmpty(versionIssueId) ? null : versionIssue.id.eq(versionIssueId);
    }

    private BooleanExpression typeEq(TaskType type) {
        return isEmpty(type) ? null : task.type.eq(type);
    }

    private BooleanExpression workerEq(String worker) {
        return isEmpty(worker) ? null : task.worker.eq(worker);
    }


}
