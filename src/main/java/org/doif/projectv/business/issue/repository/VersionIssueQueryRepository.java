package org.doif.projectv.business.issue.repository;

import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.dto.VersionIssueOverviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface VersionIssueQueryRepository {

    /**
     * <b>[ issueId로 Module-Issue 조회 ]</b>
     * @param issueId
     * @return
     */
    List<VersionIssueDto.Result> searchByIssueId(Long issueId);

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
    Page<VersionIssueOverviewDto> searchOverview(VersionIssueDto.Search search, Pageable pageable);

    /**
     * <b>[ 패치로그에 맵핑 되어 있지 않은 모듈-이슈 조회 ]</b>
     * <p></p>
     * 진행상황이 COMPLETE이고, 파라미터로 넘어온 모듈이 같고, 패치 타겟이 반대인 모듈-이슈
     * @param search
     * @param pageable
     * @return
     */
    Page<VersionIssueDto.Result> searchNonePatchModuleIssue(VersionIssueDto.Search search, Pageable pageable);

}
