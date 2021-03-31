package org.doif.projectv.business.patchlog.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.patchlog.entity.PatchLogVersion;
import org.doif.projectv.business.patchlog.repository.PatchLogRepository;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.project.repository.ProjectRepository;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class PatchLogVersionServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PatchLogVersionService patchLogVersionService;

    @Autowired
    PatchLogRepository patchLogRepository;

    @Autowired
    ProjectRepository projectRepository;


    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        Module module = new Module("금융결제원 PG WEB/ADMIN", project, "", VcsType.SVN, "repo", BuildTool.MAVEN);
        Module module2 = new Module("금융결제원 PG 정산 모듈", project, "", VcsType.SVN, "repo", BuildTool.MAVEN);
        Version version = new Version("v1.0.1", "버전 1.0.1 입니다.", module);
        Version version2 = new Version("v1.5.2", "버전 1.5.2 입니다.", module2);

        Client client = new Client("금융결제원", "VVIP");
        PatchLog patchLog1 = new PatchLog(client, PatchTarget.DEV, PatchStatus.COMPLETE, LocalDate.of(2020, 11, 26), LocalDate.of(2020, 11, 26), "kjpmj", "");
        PatchLogVersion patchLogVersion1 = new PatchLogVersion(patchLog1, version);
        PatchLogVersion patchLogVersion2 = new PatchLogVersion(patchLog1, version2);

        em.persist(project);
        em.persist(module);
        em.persist(module2);
        em.persist(version);
        em.persist(version2);
        em.persist(client);
        em.persist(patchLog1);
        em.persist(patchLogVersion1);
        em.persist(patchLogVersion2);
    }

    @Test
    public void 패치에_포함된_버전들_조회_테스트() throws Exception {
        // given
        Long patchLogId = patchLogRepository.findAll().get(0).getId();

        // when
        List<PatchLogVersionDto.Result> results = patchLogVersionService.searchPatchLogVersionsByPatchLogId(patchLogId);

        // then
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("versionName").containsExactly("v1.0.1", "v1.5.2");
    }

    @Test
    public void 패치에_버전_추가_테스트() throws Exception {
        // given
        Project project = projectRepository.findAll().get(0);
        Module module = new Module("금융결제원 PG 매입 모듈", project, "", VcsType.SVN, "repo", BuildTool.MAVEN);
        Version version = new Version("v1.8.5", "버전 1.8.5 입니다.", module);

        Long patchLogId = patchLogRepository.findAll().get(0).getId();

        em.persist(module);
        em.persist(version);

        PatchLogVersionDto.Insert insert = new PatchLogVersionDto.Insert();
        insert.setVersionId(version.getId());
        insert.setPatchLogId(patchLogId);

        // when
        CommonResponse response = patchLogVersionService.insert(insert);
        List<PatchLogVersionDto.Result> results = patchLogVersionService.searchPatchLogVersionsByPatchLogId(patchLogId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(3);
        assertThat(results).extracting("versionName").containsExactly("v1.0.1", "v1.5.2", "v1.8.5");
    }
}