package org.doif.projectv.business.task.service;

import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.issue.repository.VersionIssueRepository;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.task.dto.TaskDto;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.business.version.constant.VersionStatus;
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

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@Transactional
class TaskServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    TaskService taskService;

    @Autowired
    VersionIssueRepository versionIssueRepository;

    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        Module module = new Module("금융결제원 PG WEB/ADMIN", project);
        Version version = new Version("v1.0.1", "버전 1.0.1 입니다.", module);
        Issue issue = new Issue("임진성은 왜 늦는가", "진성씨는 그냥 늦는 것이다. 딱히 이유는 없다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        VersionIssue versionIssue = new VersionIssue(version, issue, "202011", VersionIssueProgress.PROGRESSING);

        Task task = new Task(
                versionIssue,
                LocalDate.of(2020, 11, 17),
                LocalDate.of(2020, 11, 20),
                "늦으면 점심 쏘기 벌칙이 있으니 빨리왔음" ,
                TaskType.DEVELOP,
                3.0,
                "kjpmj",
                ""
                );

        em.persist(project);
        em.persist(module);
        em.persist(version);
        em.persist(issue);
        em.persist(versionIssue);
        em.persist(task);
    }

    @Test
    public void 작업_조회_서비스_테스트() throws Exception {
        // given
        TaskDto.Search search = new TaskDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<TaskDto.Result> results = taskService.searchByCondition(search, pageRequest);
        List<TaskDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getIssueName()).isEqualTo("임진성은 왜 늦는가");
        assertThat(content.get(0).getVersionName()).isEqualTo("v1.0.1");
    }

    @Test
    public void 작업_추가_서비스_테스트() throws Exception {
        // given
        Long versionIssueId = versionIssueRepository.findAll().get(0).getId();

        TaskDto.Insert insert = new TaskDto.Insert();
        insert.setVersionIssueId(versionIssueId);
        insert.setContents("점심은 뭘 먹을까");
        insert.setStartDate(LocalDate.of(2020, 11, 17));
        insert.setEndDate(LocalDate.of(2020, 11, 18));
        insert.setType(TaskType.ETC);
        insert.setWorker("kjpmj");
        insert.setRemark("모르겠음");
        insert.setManDay(1.0);

        TaskDto.Search search = new TaskDto.Search();
        search.setType(TaskType.ETC);
        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        CommonResponse response = taskService.insert(insert);
        Page<TaskDto.Result> results = taskService.searchByCondition(search, pageRequest);
        List<TaskDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(results).extracting("contents").containsExactly("점심은 뭘 먹을까");
    }

    @Test
    public void 작업_수정_서비스_테스트() throws Exception {
        // given
        TaskDto.Search search = new TaskDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long taskId = taskService.searchByCondition(search, pageRequest).getContent().get(0).getTaskId();

        TaskDto.Update update = new TaskDto.Update();
        update.setContents("점심은 부대찌개");
        update.setStartDate(LocalDate.of(2020, 11, 17));
        update.setEndDate(LocalDate.of(2020, 11, 18));
        update.setType(TaskType.ETC);
        update.setWorker("kjpmj");
        update.setRemark("부대찌개는 왕두꺼비 부대찌개");
        update.setManDay(1.0);

        // when
        CommonResponse response = taskService.update(taskId, update);
        Page<TaskDto.Result> results = taskService.searchByCondition(search, pageRequest);
        List<TaskDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.get(0).getContents()).isEqualTo("점심은 부대찌개");
    }

    @Test
    public void 작업_삭제_서비스_테스트() throws Exception {
        // given
        TaskDto.Search search = new TaskDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long taskId = taskService.searchByCondition(search, pageRequest).getContent().get(0).getTaskId();

        // when
        CommonResponse response = taskService.delete(taskId);
        Page<TaskDto.Result> results = taskService.searchByCondition(search, pageRequest);
        List<TaskDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content).isEmpty();
    }
}