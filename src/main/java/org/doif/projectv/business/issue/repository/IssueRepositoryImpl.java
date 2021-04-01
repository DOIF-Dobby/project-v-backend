package org.doif.projectv.business.issue.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.dto.IssueDto;
import org.doif.projectv.business.issue.dto.QIssueDto_Result;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.QVersionIssue;
import org.doif.projectv.common.jpa.support.Querydsl4RepositorySupport;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import static org.doif.projectv.business.issue.entity.QIssue.issue;
import static org.doif.projectv.business.issue.entity.QVersionIssue.*;
import static org.springframework.util.StringUtils.isEmpty;

public class IssueRepositoryImpl extends Querydsl4RepositorySupport implements IssueQueryRepository{


    public IssueRepositoryImpl() {
        super(Issue.class);
    }

    /**
     * IssueDto로 바로 조회
     * 검색조건은 IssueSearchCondition
     * @param search
     * @return
     */
    @Override
    public Page<IssueDto.Result> searchByCondition(IssueDto.Search search, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QIssueDto_Result(
                        issue.id,
                        issue.issueName,
                        issue.contents,
                        issue.status,
                        issue.category
                ))
                .from(issue)
                .where(
                        statusEq(search.getStatus()),
                        categoryEq(search.getCategory()),
                        contentsLike(search.getContents())
                )
                .orderBy(issue.id.asc())
        );
    }

    /**
     * VersionId를 받아서 해당 버전과 매핑되지 않은 Issue 조회
     * @param versionId
     * @param pageable
     * @return
     */
    @Override
    public Page<IssueDto.Result> searchIssuesNotMappingVersion(Long versionId, Pageable pageable) {
        return applyPagination(pageable, contentQuery -> contentQuery
                .select(new QIssueDto_Result(
                        issue.id,
                        issue.issueName,
                        issue.contents,
                        issue.status,
                        issue.category
                ))
                .from(issue)
                .where(
                        issue.status.eq(IssueStatus.OPEN),
                        JPAExpressions
                                .selectOne()
                                .from(versionIssue)
                                .where(
                                        versionIssue.version.id.eq(versionId),
                                        versionIssue.issue.eq(issue)
                                )
                                .notExists()
                )
                .orderBy(issue.id.asc())
        );
    }

    private BooleanExpression statusEq(IssueStatus status) {
        return isEmpty(status) ? null : issue.status.eq(status);
    }

    private BooleanExpression categoryEq(IssueCategory category) {
        return isEmpty(category) ? null : issue.category.eq(category);
    }

    private BooleanExpression contentsLike(String contents) {
        return isEmpty(contents) ? null : issue.contents.contains(contents);
    }
}
