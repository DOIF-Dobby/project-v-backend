package org.doif.projectv.business.patchlog.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.client.repository.ClientRepository;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
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

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PatchLogServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PatchLogService patchLogService;

    @Autowired
    VersionRepository versionRepository;

    @Autowired
    ClientRepository clientRepository;

    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        Module module = new Module("금융결제원 PG WEB/ADMIN", project, "", VcsType.SVN, "repo", BuildTool.MAVEN);
        Version version = new Version("v1.0.1", "버전 1.0.1 입니다.", module);
        Client client = new Client("금융결제원", "VVIP");
        PatchLog patchLog = new PatchLog(client, PatchTarget.DEV, PatchStatus.SCHEDULE, LocalDate.of(2020, 11, 24), "kjpmj", "");
        PatchLogVersion patchLogVersion = new PatchLogVersion(patchLog, version);

        em.persist(project);
        em.persist(module);
        em.persist(version);
        em.persist(client);
        em.persist(patchLog);
        em.persist(patchLogVersion);
    }

    @Test
    public void 패치로그_조회_서비스_테스트() throws Exception {
        // given
        Long clientId = clientRepository.findAll().get(0).getId();
        PatchLogDto.Search search = new PatchLogDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<PatchLogDto.Result> results = patchLogService.searchByCondition(clientId, search, pageRequest);
        List<PatchLogDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getStatus().getCode()).isEqualTo(PatchStatus.SCHEDULE.getCode());
    }

    @Test
    public void 패치로그_추가_서비스_테스트() throws Exception {
        // given
        Long clientId = clientRepository.findAll().get(0).getId();

        PatchLogDto.Insert insert = new PatchLogDto.Insert();
        insert.setClientId(clientId);
        insert.setTarget(PatchTarget.DEV);
        insert.setPatchScheduleDate(LocalDate.of(2020, 11, 25));
        insert.setStatus(PatchStatus.SCHEDULE);
        insert.setWorker("kjpmj");
        insert.setRemark("패치 로그 작성");

        PatchLogDto.Search search = new PatchLogDto.Search();
        search.setPatchScheduleDateGoe(LocalDate.of(2020, 11, 25));
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        CommonResponse response = patchLogService.insert(insert);
        Page<PatchLogDto.Result> results = patchLogService.searchByCondition(clientId, search, pageRequest);
        List<PatchLogDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(results).extracting("worker").containsExactly("kjpmj");
    }

    @Test
    public void 패치로그_수정_서비스_테스트() throws Exception {
        // given
        Long clientId = clientRepository.findAll().get(0).getId();
        PatchLogDto.Search search = new PatchLogDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long patchLogId = patchLogService.searchByCondition(clientId, search, pageRequest).getContent().get(0).getPatchLogId();

        PatchLogDto.Update update = new PatchLogDto.Update();
        update.setTarget(PatchTarget.DEV);
        update.setPatchScheduleDate(LocalDate.of(2020, 11, 24));
        update.setPatchDate(LocalDate.of(2020, 11, 26));
        update.setStatus(PatchStatus.COMPLETE);
        update.setWorker("kjpmj");
        update.setRemark("패치 로그 작성");

        // when
        CommonResponse response = patchLogService.update(patchLogId, update);
        Page<PatchLogDto.Result> results = patchLogService.searchByCondition(clientId, search, pageRequest);
        List<PatchLogDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getPatchDate()).isEqualTo(LocalDate.of(2020, 11, 26));
        assertThat(content.get(0).getStatus()).isEqualTo(PatchStatus.COMPLETE);
    }

    @Test
    public void 패치로그_삭제_서비스_테스트() throws Exception {
        // given
        Long clientId = clientRepository.findAll().get(0).getId();
        PatchLogDto.Search search = new PatchLogDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long patchLogId = patchLogService.searchByCondition(clientId, search, pageRequest).getContent().get(0).getPatchLogId();

        // when
        Exception e = assertThrows(DataIntegrityViolationException.class, () -> {
            patchLogService.delete(patchLogId);
            patchLogService.searchByCondition(clientId, search, pageRequest);
        });
        
        // then
        System.out.println("e.getMessage() = " + e.getMessage());
    }
}