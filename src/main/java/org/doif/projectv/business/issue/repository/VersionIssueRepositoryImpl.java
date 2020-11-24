package org.doif.projectv.business.issue.repository;

import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.dto.VersionIssueOverviewDto;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class VersionIssueRepositoryImpl extends Querydsl4RepositorySupport implements VersionIssueQueryRepository {

    public VersionIssueRepositoryImpl() {
        super(VersionIssue.class);
    }

    @Override
    public List<VersionIssueDto.Result> searchByIssueId(Long issueId) {
//        return getQueryFactory()
//                .select(new QModuleIssueDto_Result(
//                        moduleIssue.id,
//                        module.moduleName,
//                        issue.issueName,
//                        issue.status,
//                        issue.contents,
//                        moduleIssue.issueYm,
//                        moduleIssue.progress,
//                        moduleIssue.assignee,
//                        moduleIssue.remark
//                ))
//                .from(moduleIssue)
//                .join(moduleIssue.module, module)
//                .join(moduleIssue.issue, issue)
//                .where(moduleIssue.issue.id.eq(issueId))
//                .fetch();
        return null;
    }

    @Override
    public Page<VersionIssueOverviewDto> searchOverview(VersionIssueDto.Search search, Pageable pageable) {
//        return applyPagination(pageable, contentQuery -> contentQuery
//                .select(new QModuleIssueOverviewDto(
//                        project.projectName,
//                        module.moduleName,
//                        issue.issueName,
//                        issue.status,
//                        issue.category,
//                        moduleIssue.progress,
//                        moduleIssue.issueYm,
//                        moduleIssue.assignee,
//                        task.startDate.min(),
//                        task.endDate.max(),
//                        task.manDay.sum(),
//                        JPAExpressions
//                                .select(patchLog.patchDate)
//                                .from(patchLogModuleIssue)
//                                .join(patchLogModuleIssue.patchLog, patchLog)
//                                .where(
//                                        patchLog.target.eq(PatchTarget.DEV),
//                                        patchLogModuleIssue.moduleIssue.eq(moduleIssue)
//                                ),
//                        JPAExpressions
//                                .select(patchLog.patchDate)
//                                .from(patchLogModuleIssue)
//                                .join(patchLogModuleIssue.patchLog, patchLog)
//                                .where(
//                                        patchLog.target.eq(PatchTarget.PROD),
//                                        patchLogModuleIssue.moduleIssue.eq(moduleIssue)
//                                )
//                ))
//                .from(moduleIssue)
//                .leftJoin(moduleIssue.tasks, task)
//                .join(moduleIssue.module, module)
//                .join(moduleIssue.issue, issue)
//                .join(module.project, project)
//                .where(
//                        projectEq(search.getProjectId()),
//                        moduleEq(search.getModuleId()),
//                        statusEq(search.getStatus()),
//                        categoryEq(search.getCategory()),
//                        issueYmEq(search.getIssueYm()),
//                        progressEq(search.getProgress()),
//                        assigneeEq(search.getAssignee())
//                )
//                .groupBy(
//                        moduleIssue.id,
//                        project.projectName,
//                        module.moduleName,
//                        issue.issueName,
//                        issue.status,
//                        issue.category,
//                        moduleIssue.progress,
//                        moduleIssue.issueYm,
//                        moduleIssue.assignee
//                )
//                .orderBy(moduleIssue.id.asc()),
//            countQuery -> countQuery
//                .select(moduleIssue.id)
//                .from(moduleIssue)
//                .join(moduleIssue.module, module)
//                .join(moduleIssue.issue, issue)
//                .join(module.project, project)
//                .where(
//                        projectEq(search.getProjectId()),
//                        moduleEq(search.getModuleId()),
//                        statusEq(search.getStatus()),
//                        categoryEq(search.getCategory()),
//                        issueYmEq(search.getIssueYm()),
//                        progressEq(search.getProgress()),
//                        assigneeEq(search.getAssignee())
//                )
//        );
        return null;
    }

    @Override
    public Page<VersionIssueDto.Result> searchNonePatchModuleIssue(VersionIssueDto.Search search, Pageable pageable) {
//        return applyPagination(pageable, contentQuery -> contentQuery
//                .select(new QModuleIssueDto_Result(
//                        moduleIssue.id,
//                        module.moduleName,
//                        issue.issueName,
//                        issue.status,
//                        issue.contents,
//                        moduleIssue.issueYm,
//                        moduleIssue.progress,
//                        moduleIssue.assignee,
//                        moduleIssue.remark
//                ))
//                .from(moduleIssue)
//                .join(moduleIssue.module, module)
//                .join(moduleIssue.issue, issue)
//                .where(
//                        moduleIssue.module.id.eq(search.getModuleId()),
//                        moduleIssue.progress.eq(ModuleIssueProgress.COMPLETE),
//                        JPAExpressions
//                            .selectOne()
//                            .from(patchLogModuleIssue)
//                            .join(patchLogModuleIssue.patchLog, patchLog)
//                            .where(
//                                    patchLog.target.eq(search.getPatchTarget()),
//                                    patchLogModuleIssue.moduleIssue.eq(moduleIssue)
//                            )
//                            .notExists()
//                )
//        );
        return null;
    }

//    private BooleanExpression projectEq(Long projectId) {
//        return isEmpty(projectId) ? null : project.id.eq(projectId);
//    }
//
//    private BooleanExpression moduleEq(Long moduleId) {
//        return isEmpty(moduleId) ? null : module.id.eq(moduleId);
//    }
//
//    private BooleanExpression statusEq(IssueStatus status) {
//        return isEmpty(status) ? null : issue.status.eq(status);
//    }
//
//    private BooleanExpression categoryEq(IssueCategory category) {
//        return isEmpty(category) ? null : issue.category.eq(category);
//    }
//
//    private BooleanExpression issueYmEq(String issueYm) {
//        return isEmpty(issueYm) ? null : moduleIssue.issueYm.eq(issueYm);
//    }
//
//    private BooleanExpression progressEq(ModuleIssueProgress progress) {
//        return isEmpty(progress) ? null : moduleIssue.progress.eq(progress);
//    }
//
//    private BooleanExpression assigneeEq(String assignee) {
//        return isEmpty(assignee) ? null : moduleIssue.assignee.eq(assignee);
//    }

}
