package org.doif.projectv.business.module.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.project.service.ProjectService;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ModuleServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ProjectService projectService;

    @Autowired
    ModuleService moduleService;

    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        Module module = new Module("금융결제원 PG WEB/ADMIN", project);

        em.persist(project);
        em.persist(module);
    }

    @Test
    public void 모듈_조회_서비스_테스트() throws Exception {
        // given
        Long projectId = projectService.select().get(0).getProjectId();

        // when
        List<ModuleDto.Result> results = moduleService.searchByProjectId(projectId);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getModuleName()).isEqualTo("금융결제원 PG WEB/ADMIN");
    }

    @Test
    public void 모듈_추가_서비스_테스트() throws Exception {
        // given
        Long projectId = projectService.select().get(0).getProjectId();

        ModuleDto.Insert insert = new ModuleDto.Insert();
        insert.setProjectId(projectId);
        insert.setModuleName("금융결제원 PG WEB/PAY");
        insert.setDescription("금결원 PG Web Pay 모듈");
        insert.setSvnUrl("");

        // when
        CommonResponse response = moduleService.insert(insert);
        List<ModuleDto.Result> results = moduleService.searchByProjectId(projectId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("moduleName").containsExactly("금융결제원 PG WEB/ADMIN", "금융결제원 PG WEB/PAY");
    }

    @Test
    public void 모듈_수정_서비스_테스트() throws Exception {
        // given
        Long projectId = projectService.select().get(0).getProjectId();
        Long id = moduleService.searchByProjectId(projectId).get(0).getModuleId();
        ModuleDto.Update update = new ModuleDto.Update();
        update.setModuleName("금융결제원 PG WEB/ADMIN");
        update.setDescription("금결원 굳");
        update.setSvnUrl("");

        // when
        CommonResponse response = moduleService.update(id, update);
        List<ModuleDto.Result> results = moduleService.searchByProjectId(projectId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.get(0).getDescription()).isEqualTo("금결원 굳");
    }

    @Test
    public void 모듈_삭제_서비스_테스트() throws Exception {
        // given
        Long projectId = projectService.select().get(0).getProjectId();
        Long id = moduleService.searchByProjectId(projectId).get(0).getModuleId();

        // when
        CommonResponse response = moduleService.delete(id);
        List<ModuleDto.Result> results = moduleService.searchByProjectId(projectId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

}