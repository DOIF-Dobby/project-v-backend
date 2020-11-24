package org.doif.projectv.business.project.service;

import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ProjectServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    ProjectService projectService;

    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        em.persist(project);
    }

    @Test
    public void 프로젝트_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<ProjectDto.Result> results = projectService.select();

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getProjectName()).isEqualTo("금융결제원 PG");
    }

    @Test
    public void 프로젝트_추가_서비스_테스트() throws Exception {
        // given
        ProjectDto.Insert insert = new ProjectDto.Insert();
        insert.setProjectName("코벤 PG");
        insert.setDescription("되는 것이 없음");

        // when
        CommonResponse response = projectService.insert(insert);
        List<ProjectDto.Result> results = projectService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("projectName").containsExactly("금융결제원 PG", "코벤 PG");
        assertThat(results).extracting("description").containsExactly(null, "되는 것이 없음");
    }

    @Test
    public void 프로젝트_수정_서비스_테스트() throws Exception {
        // given
        Long id = projectService.select().get(0).getProjectId();
        ProjectDto.Update update = new ProjectDto.Update();
        update.setProjectName("금융결제원 PG");
        update.setDescription("신사 고객");

        // when
        CommonResponse response = projectService.update(id, update);
        // 이 부분에서 flush가 되므로 정상 동작한다.
        List<ProjectDto.Result> results = projectService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.get(0).getDescription()).isEqualTo("신사 고객");
    }

    @Test
    public void 프로젝트_삭제_서비스_테스트() throws Exception {
        // given
        Long id = projectService.select().get(0).getProjectId();

        // when
        CommonResponse response = projectService.delete(id);
        List<ProjectDto.Result> results = projectService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

}