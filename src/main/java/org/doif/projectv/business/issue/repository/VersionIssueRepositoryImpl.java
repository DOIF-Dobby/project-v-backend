package org.doif.projectv.business.issue.repository;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.dto.QVersionIssueDto_Result;
import org.doif.projectv.business.issue.dto.QVersionIssueDto_ResultOverview;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.entity.QIssue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.QModule;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.entity.QPatchLog;
import org.doif.projectv.business.project.entity.QProject;
import org.doif.projectv.business.task.entity.QTask;
import org.doif.projectv.business.version.entity.QVersion;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.util.StringUtils;

import java.util.List;

import static org.doif.projectv.business.issue.entity.QIssue.*;
import static org.doif.projectv.business.issue.entity.QVersionIssue.*;
import static org.doif.projectv.business.module.entity.QModule.*;
import static org.doif.projectv.business.patchlog.entity.QPatchLog.*;
import static org.doif.projectv.business.project.entity.QProject.*;
import static org.doif.projectv.business.task.entity.QTask.*;
import static org.doif.projectv.business.version.entity.QVersion.*;
import static org.springframework.util.StringUtils.*;
import static org.springframework.util.StringUtils.isEmpty;

public class VersionIssueRepositoryImpl extends Querydsl4RepositorySupport implements VersionIssueQueryRepository {

    public VersionIssueRepositoryImpl() {
        super(VersionIssue.class);
    }

    @Override
    public List<VersionIssueDto.Result> searchByIssueId(Long issueId) {
        return getQueryFactory()
                .select(new QVersionIssueDto_Result(
                        versionIssue.id,
                        module.moduleName,
                        version.name,
                        issue.issueName,
                        issue.status,
                        issue.contents,
                        versionIssue.issueYm,
                        versionIssue.progress,
                        versionIssue.assignee,
                        versionIssue.remark
                ))
                .from(versionIssue)
                .join(versionIssue.version, version)
                .join(versionIssue.issue, issue)
                .join(version.module, module)
                .where(versionIssue.issue.id.eq(issueId))
                .fetch();
    }

    @Override
    public List<VersionIssueDto.Result> searchByVersionId(Long versionId) {
        return getQueryFactory()
                .select(new QVersionIssueDto_Result(
                        versionIssue.id,
                        module.moduleName,
                        version.name,
                        issue.issueName,
                        issue.status,
                        issue.contents,
                        versionIssue.issueYm,
                        versionIssue.progress,
                        versionIssue.assignee,
                        versionIssue.remark
                ))
                .from(versionIssue)
                .join(versionIssue.version, version)
                .join(versionIssue.issue, issue)
                .join(version.module, module)
                .where(versionIssue.version.id.eq(versionId))
                .fetch();
    }

    @Override
    public Page<VersionIssueDto.ResultOverview> searchOverview(VersionIssueDto.Search search, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QVersionIssueDto_ResultOverview(
                        versionIssue.id,
                        project.projectName,
                        module.moduleName,
                        version.name,
                        issue.issueName,
                        issue.status,
                        issue.category,
                        versionIssue.progress,
                        versionIssue.issueYm,
                        versionIssue.assignee,
                        task.startDate.min(),
                        task.endDate.max(),
                        task.manDay.sum(),
                        JPAExpressions
                                .select(patchLog.patchDate)
                                .from(patchLog)
                                .where(
                                        patchLog.target.eq(PatchTarget.DEV),
                                        patchLog.version.eq(version)
                                ),
                        JPAExpressions
                                .select(patchLog.patchDate)
                                .from(patchLog)
                                .where(
                                        patchLog.target.eq(PatchTarget.PROD),
                                        patchLog.version.eq(version)
                                )
                ))
                .from(versionIssue)
                .leftJoin(versionIssue.tasks, task)
                .join(versionIssue.version, version)
                .join(versionIssue.issue, issue)
                .join(version.module, module)
                .join(module.project, project)
                .where(
                        projectEq(search.getProjectId()),
                        moduleEq(search.getModuleId()),
                        versionEq(search.getVersionId()),
                        statusEq(search.getStatus()),
                        categoryEq(search.getCategory()),
                        issueYmEq(search.getIssueYm()),
                        progressEq(search.getProgress()),
                        assigneeEq(search.getAssignee())
                )
                .groupBy(
                        versionIssue.id,
                        project.projectName,
                        module.moduleName,
                        issue.issueName,
                        issue.status,
                        issue.category,
                        versionIssue.progress,
                        versionIssue.issueYm,
                        versionIssue.assignee
                )
                .orderBy(versionIssue.id.asc()),
            countQuery -> countQuery
                .select(versionIssue.id)
                .from(versionIssue)
                .join(versionIssue.version, version)
                .join(versionIssue.issue, issue)
                .join(version.module, module)
                .join(module.project, project)
                .where(
                        projectEq(search.getProjectId()),
                        moduleEq(search.getModuleId()),
                        statusEq(search.getStatus()),
                        categoryEq(search.getCategory()),
                        issueYmEq(search.getIssueYm()),
                        progressEq(search.getProgress()),
                        assigneeEq(search.getAssignee())
                )
        );
    }

    private BooleanExpression projectEq(Long projectId) {
        return isEmpty(projectId) ? null : project.id.eq(projectId);
    }

    private BooleanExpression moduleEq(Long moduleId) {
        return isEmpty(moduleId) ? null : module.id.eq(moduleId);
    }

    private BooleanExpression versionEq(Long versionId) {
        return isEmpty(versionId) ? null : version.id.eq(versionId);
    }

    private BooleanExpression statusEq(IssueStatus status) {
        return isEmpty(status) ? null : issue.status.eq(status);
    }

    private BooleanExpression categoryEq(IssueCategory category) {
        return isEmpty(category) ? null : issue.category.eq(category);
    }

    private BooleanExpression issueYmEq(String issueYm) {
        return isEmpty(issueYm) ? null : versionIssue.issueYm.eq(issueYm);
    }

    private BooleanExpression progressEq(VersionIssueProgress progress) {
        return isEmpty(progress) ? null : versionIssue.progress.eq(progress);
    }

    private BooleanExpression assigneeEq(String assignee) {
        return isEmpty(assignee) ? null : versionIssue.assignee.eq(assignee);
    }
}
