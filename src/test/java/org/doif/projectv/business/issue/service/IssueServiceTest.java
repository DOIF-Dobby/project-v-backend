package org.doif.projectv.business.issue.service;

import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.dto.IssueDto;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class IssueServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    IssueService issueService;

    @BeforeEach
    public void init() {
        Issue issue = new Issue("임진성은 왜 늦는가", "진성씨는 그냥 늦는 것이다. 딱히 이유는 없다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        em.persist(issue);
    }

    @Test
    public void 이슈_조회_서비스_테스트() throws Exception {
        // given
        IssueDto.Search search = new IssueDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<IssueDto.Result> results = issueService.searchByCondition(search, pageRequest);
        List<IssueDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getIssueName()).isEqualTo("임진성은 왜 늦는가");
        assertThat(content.get(0).getCategory()).isEqualTo(IssueCategory.ERROR_MODIFY);
    }
    
    @Test
    public void 이슈_추가_서비스_테스트() throws Exception {
        // given
        IssueDto.Insert insert = new IssueDto.Insert();
        insert.setIssueName("임진성씨는 왜 국밥을 좋아하는가");
        insert.setContents("뚝배기에 담기면 다 국밥인 것 같다.");
        insert.setStatus(IssueStatus.OPEN);
        insert.setCategory(IssueCategory.NEW_DEVELOP);
        
        IssueDto.Search search = new IssueDto.Search();
        search.setContents("국밥");
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        CommonResponse response = issueService.insert(insert);
        Page<IssueDto.Result> results = issueService.searchByCondition(search, pageRequest);
        List<IssueDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(content).extracting("issueName").containsExactly("임진성씨는 왜 국밥을 좋아하는가");
    }

    @Test
    public void 이슈_수정_서비스_테스트() throws Exception {
        // given
        IssueDto.Search search = new IssueDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long issueId = issueService.searchByCondition(search, pageRequest).getContent().get(0).getIssueId();

        IssueDto.Update update = new IssueDto.Update();
        update.setIssueName("태용씨는 왜 부대찌개에 미쳐있는가");
        update.setContents("막상 먹으러가면 많이 안 먹는 것 같다....");
        update.setStatus(IssueStatus.CLOSE);
        update.setCategory(IssueCategory.ERROR_MODIFY);

        // when
        CommonResponse response = issueService.update(issueId, update);
        Page<IssueDto.Result> results = issueService.searchByCondition(search, pageRequest);
        List<IssueDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getIssueName()).isEqualTo("태용씨는 왜 부대찌개에 미쳐있는가");
    }

    @Test
    public void 이슈_삭제_서비스_테스트() throws Exception {
        // given
        IssueDto.Search search = new IssueDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long issueId = issueService.searchByCondition(search, pageRequest).getContent().get(0).getIssueId();

        // when
        CommonResponse response = issueService.delete(issueId);
        Page<IssueDto.Result> results = issueService.searchByCondition(search, pageRequest);
        List<IssueDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content).isEmpty();
    }

    @Test
    public void 버전ID를_받아서_해당_버전과_맵핑_되지_않은_이슈_조회_테스트() throws Exception {
        // given
        Project project = new Project("프로젝트1");
        Module module = new Module("모듈1", project, "모듈1 입니다", VcsType.SVN, "", BuildTool.MAVEN);
        Version version1 = new Version("1.0.0", "1.0.0", module);
        Issue issue1 = new Issue("이슈1", "이슈1 입니다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        Issue issue2 = new Issue("이슈2", "이슈2 입니다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        Issue issue3 = new Issue("이슈3", "이슈3 입니다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        VersionIssue versionIssue1 = new VersionIssue(version1, issue1, "202104", VersionIssueProgress.PROGRESSING, "kjpmj", "안녕");
        VersionIssue versionIssue2 = new VersionIssue(version1, issue2, "202104", VersionIssueProgress.PROGRESSING, "kjpmj", "안녕");

        em.persist(project);
        em.persist(module);
        em.persist(version1);
        em.persist(issue1);
        em.persist(issue2);
        em.persist(issue3);
        em.persist(versionIssue1);
        em.persist(versionIssue2);

        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<IssueDto.Result> results = issueService.searchIssuesNotMappingVersion(version1.getId(), pageRequest);
        List<IssueDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content).extracting("issueName").containsExactly("임진성은 왜 늦는가", "이슈3");
    }
}