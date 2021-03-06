package org.doif.projectv.business.issue.repository;

import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionIssueQueryRepository {

    /**
     * <b>[ issueId로 Version-Issue 조회 ]</b>
     * @param issueId
     * @return
     */
    List<VersionIssueDto.Result> searchByIssueId(Long issueId);

    /**
     * <b>[ versionId로 Version-Issue 조회 ]</b>
     * @param versionId
     * @return
     */
    List<VersionIssueDto.Result> searchByVersionId(Long versionId);

    /**
     * <b>[ 이슈 현황 조회 ]</b>
     * <p></p>
     * 테스트 할 때는 데이터가 적어서 속도가 잘 나오지만, 중복되지 않는 데이터가 많아지면 스칼라 서브쿼리에서 성능 이슈가 발생할 수 있다.
     * 인라인 뷰에 해당 ModuleIssue.id로 PatchLog를 조회 후 그룹바이해서 조인하고 싶은데, JPQL이 인라인 뷰를 지원을 안한다.
     * 만약 성능이슈가 발생하면 NativeQuery를 고려해보도록 하자.
     * @param search
     * @param pageable
     * @return
     */
    Page<VersionIssueDto.ResultOverview> searchOverview(VersionIssueDto.Search search, Pageable pageable);

}
