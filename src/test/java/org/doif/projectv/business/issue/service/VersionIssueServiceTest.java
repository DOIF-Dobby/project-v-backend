package org.doif.projectv.business.issue.service;

import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.issue.repository.IssueRepository;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.patchlog.entity.PatchLogVersion;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VersionIssueServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    VersionIssueService versionIssueService;

    @Autowired
    IssueRepository issueRepository;

    @Autowired
    VersionRepository versionRepository;

    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        Module module = new Module("금융결제원 PG WEB/ADMIN", project, "", VcsType.SVN, "repo", BuildTool.MAVEN);
        Version version = new Version("v1.0.1", "버전 1.0.1 입니다.", module);
        Issue issue = new Issue("임진성은 왜 늦는가", "진성씨는 그냥 늦는 것이다. 딱히 이유는 없다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        VersionIssue versionIssue = new VersionIssue(version, issue, "202011", VersionIssueProgress.PROGRESSING, "kjpmj", "");
        Task task1 = new Task(versionIssue, LocalDate.of(2020, 11, 24), LocalDate.of(2020, 11, 24), "작업작업", TaskType.DEVELOP, 1.0, "kjpmj", "");
        Task task2 = new Task(versionIssue, LocalDate.of(2020, 11, 25), LocalDate.of(2020, 11, 25), "작업작업", TaskType.DEVELOP, 1.5, "kjpmj", "");

        Client client = new Client("금융결제원", "VVIP");
        PatchLog patchLog1 = new PatchLog(client, PatchTarget.DEV, PatchStatus.COMPLETE, LocalDate.of(2020, 11, 26), LocalDate.of(2020, 11, 26), "kjpmj", "");
        PatchLog patchLog2 = new PatchLog(client, PatchTarget.PROD, PatchStatus.COMPLETE, LocalDate.of(2020, 11, 26), LocalDate.of(2020, 11, 27),"kjpmj", "");
        PatchLogVersion patchLogVersion1 = new PatchLogVersion(patchLog1, version);
        PatchLogVersion patchLogVersion2 = new PatchLogVersion(patchLog2, version);

        em.persist(project);
        em.persist(module);
        em.persist(version);
        em.persist(issue);
        em.persist(versionIssue);
        em.persist(task1);
        em.persist(task2);
        em.persist(client);
        em.persist(patchLog1);
        em.persist(patchLog2);
        em.persist(patchLogVersion1);
        em.persist(patchLogVersion2);
    }

    @Test
    public void 버전_이슈_이슈ID로_조회_서비스_테스트() throws Exception {
        // given
        Long issueId = issueRepository.findAll().get(0).getId();

        // when
        List<VersionIssueDto.Result> results = versionIssueService.searchByIssueId(issueId);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getModuleName()).isEqualTo("금융결제원 PG WEB/ADMIN");
        assertThat(results.get(0).getIssueName()).isEqualTo("임진성은 왜 늦는가");
        assertThat(results.get(0).getProgress()).isEqualTo(VersionIssueProgress.PROGRESSING);
    }

    @Test
    public void 버전_이슈_버전ID로_조회_서비스_테스트() throws Exception {
        // given
        Long versionId = versionRepository.findAll().get(0).getId();

        // when
        List<VersionIssueDto.Result> results = versionIssueService.searchByVersionId(versionId);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getModuleName()).isEqualTo("금융결제원 PG WEB/ADMIN");
        assertThat(results.get(0).getIssueName()).isEqualTo("임진성은 왜 늦는가");
        assertThat(results.get(0).getProgress()).isEqualTo(VersionIssueProgress.PROGRESSING);
    }

    @Test
    public void 버전_이슈_추가_서비스_테스트() throws Exception {
        // given
        Issue issue = new Issue("이슈이슈", "이슈 입니다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        em.persist(issue);
        Long versionId = versionRepository.findAll().get(0).getId();

        VersionIssueDto.Insert insert = new VersionIssueDto.Insert();
        insert.setVersionId(versionId);
        insert.setIssueId(issue.getId());
        insert.setAssignee("kjpmj");
        insert.setIssueYm("202012");
        insert.setProgress(VersionIssueProgress.COMPLETE);
        insert.setRemark("쿄쿄쿄");

        // when
        CommonResponse response = versionIssueService.insert(insert);
        List<VersionIssueDto.Result> results = versionIssueService.searchByIssueId(issue.getId());

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results).extracting("issueYm").containsExactly("202012");
    }

    @Test
    public void 버전_이슈_수정_서비스_테스트() throws Exception {
        // given
        Long issueId = issueRepository.findAll().get(0).getId();
        Long versionIssueId = versionIssueService.searchByIssueId(issueId).get(0).getVersionIssueId();

        VersionIssueDto.Update update = new VersionIssueDto.Update();
        update.setAssignee("kjpmj");
        update.setIssueYm("202012");
        update.setProgress(VersionIssueProgress.COMPLETE);
        update.setRemark("크크크");

        // when
        CommonResponse response = versionIssueService.update(versionIssueId, update);
        List<VersionIssueDto.Result> results = versionIssueService.searchByIssueId(issueId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getProgress()).isEqualTo(VersionIssueProgress.COMPLETE);
        assertThat(results.get(0).getIssueYm()).isEqualTo("202012");
    }

    @Test
    public void 버전_이슈_삭제_서비스_테스트() throws Exception {
        // given
        Long issueId = issueRepository.findAll().get(0).getId();
        Long versionIssueId = versionIssueService.searchByIssueId(issueId).get(0).getVersionIssueId();

        // when
        Exception exception = assertThrows(DataIntegrityViolationException.class, () -> {
            versionIssueService.delete(versionIssueId);
            versionIssueService.searchByIssueId(issueId);
        });
        
        // then
    }

    @Test
    public void 버전_이슈_오버뷰_조회_서비스_테스트() throws Exception {
        // given
        VersionIssueDto.Search search = new VersionIssueDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<VersionIssueDto.ResultOverview> resultOverviews = versionIssueService.searchOverview(search, pageRequest);
        List<VersionIssueDto.ResultOverview> content = resultOverviews.getContent();

        // then
        for (VersionIssueDto.ResultOverview resultOverview : content) {
            System.out.println("resultOverview = " + resultOverview);
        }

        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getStatusName()).isEqualTo("열림");
        assertThat(content.get(0).getManDay()).isEqualTo(2.5);
        assertThat(content.get(0).getPatchDateDev()).isEqualTo(LocalDate.of(2020, 11, 26));
        assertThat(content.get(0).getPatchDateProd()).isEqualTo(LocalDate.of(2020, 11, 27));
    }
}