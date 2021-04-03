package org.doif.projectv.business.version.service;

import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.client.entity.Client;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.module.repository.ModuleRepository;
import org.doif.projectv.business.module.service.ModuleService;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.patchlog.entity.PatchLogVersion;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.project.repository.ProjectRepository;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.version.constant.VersionStatus;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.business.version.repository.VersionRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.junit.jupiter.api.BeforeAll;
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

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class VersionServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    VersionService versionService;

    @Autowired
    ModuleRepository moduleRepository;


    @BeforeEach
    public void init() {
        Project project = new Project("금융결제원 PG");
        Module module = new Module("금융결제원 PG WEB/ADMIN", project, "", VcsType.SVN, "repo", BuildTool.MAVEN);
        Version version = new Version("v1.0.1", "버전 1.0.1 입니다.", module);

        em.persist(project);
        em.persist(module);
        em.persist(version);
    }

    @Test
    public void 버전_조회_서비스_테스트() throws Exception {
        // given
        VersionDto.Search search = new VersionDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long moduleId = moduleRepository.findAll().get(0).getId();

        // when
        Page<VersionDto.Result> results = versionService.searchByCondition(moduleId, search, pageRequest);
        List<VersionDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getVersionName()).isEqualTo("v1.0.1");
        assertThat(content.get(0).getVersionStatus().getCode()).isEqualTo(VersionStatus.DEVELOP.getCode());
    }

    /**
     * 버전을 추가 하면 실제 pom.xml의 version을 바꾸고 commit 하기 때문에 테스트를 하지 않는다.
     * @throws Exception
     */
//    @Test
    public void 버전_추가_서비스_테스트() throws Exception {
        // given
        Long moduleId = moduleRepository.findAll().get(0).getId();

        VersionDto.Insert insert = new VersionDto.Insert();
        insert.setVersionName("v1.0.2");
        insert.setDescription("버전 1.0.2");
        insert.setModuleId(moduleId);

        VersionDto.Search search = new VersionDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        search.setVersionName("v1.0.2");
        // when
        CommonResponse response = versionService.insert(insert);
        Page<VersionDto.Result> results = versionService.searchByCondition(1L, search, pageRequest);
        List<VersionDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(content).extracting("versionName").containsExactly("v1.0.2");
    }

    @Test
    public void 버전_수정_서비스_테스트() throws Exception {
        // given
        VersionDto.Search search = new VersionDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long moduleId = moduleRepository.findAll().get(0).getId();
        Long versionId = versionService.searchByCondition(moduleId, search, pageRequest).getContent().get(0).getVersionId();

        VersionDto.Update update = new VersionDto.Update();
        update.setDescription("버전 1.0.2");

        // when
        CommonResponse response = versionService.update(versionId, update);
        Page<VersionDto.Result> results = versionService.searchByCondition(moduleId, search, pageRequest);
        List<VersionDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content.size()).isEqualTo(1);
        assertThat(content.get(0).getDescription()).isEqualTo("버전 1.0.2");
    }

    @Test
    public void 버전_삭제_서비스_테스트() throws Exception {
        // given
        VersionDto.Search search = new VersionDto.Search();
        PageRequest pageRequest = PageRequest.of(0, 100);
        Long moduleId = moduleRepository.findAll().get(0).getId();
        Long versionId = versionService.searchByCondition(moduleId, search, pageRequest).getContent().get(0).getVersionId();

        // when
        CommonResponse response = versionService.delete(versionId);
        Page<VersionDto.Result> results = versionService.searchByCondition(moduleId, search, pageRequest);
        List<VersionDto.Result> content = results.getContent();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(content).isEmpty();
    }

    @Test
    public void 이슈와_맵핑_되지_않은_버전_조회_테스트() throws Exception {
        // given
        Project project = new Project("프로젝트1");
        Module module = new Module("모듈1", project, "모듈1 입니다", VcsType.SVN, "", BuildTool.MAVEN);
        Version version1 = new Version("1.0.0", "1.0.0", module);
        Version version2 = new Version("2.1.1", "2.1.1", module);
        Version version3 = new Version("3.1.1", "3.1.1", module);
        Issue issue1 = new Issue("이슈1", "이슈1 입니다.", IssueStatus.OPEN, IssueCategory.ERROR_MODIFY);
        VersionIssue versionIssue1 = new VersionIssue(version1, issue1, "202104", VersionIssueProgress.PROGRESSING, "kjpmj", "안녕");
        VersionIssue versionIssue2 = new VersionIssue(version2, issue1, "202104", VersionIssueProgress.PROGRESSING, "kjpmj", "안녕");

        em.persist(project);
        em.persist(module);
        em.persist(version1);
        em.persist(version2);
        em.persist(version3);
        em.persist(issue1);
        em.persist(versionIssue1);
        em.persist(versionIssue2);

        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<VersionDto.Result> results = versionService.searchVersionsNotMappingIssue(issue1.getId(), pageRequest);
        List<VersionDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(2);
        assertThat(content).extracting("versionName").containsExactly("v1.0.1", "3.1.1");
    }

    @Test
    public void 패치로그와_맵핑_되지_않은_버전_조회_테스트() throws Exception {
        // given
        Project project = new Project("프로젝트1");
        Module module = new Module("모듈1", project, "모듈1 입니다", VcsType.SVN, "", BuildTool.MAVEN);
        Version version1 = new Version("1.0.0", "1.0.0", module, VersionStatus.RELEASE);
        Version version2 = new Version("2.1.1", "2.1.1", module, VersionStatus.RELEASE);
        Version version3 = new Version("3.1.1", "3.1.1", module, VersionStatus.RELEASE);
        Client client = new Client("금융결제원", "VVVVVIP", "01012345678", "1112233333", "12345", "서울", "강남");
        PatchLog patchLog = new PatchLog(client, PatchTarget.DEV, PatchStatus.SCHEDULE, LocalDate.of(2021, 4, 1), "kjpmj", "안녕");
        PatchLogVersion patchLogVersion1 = new PatchLogVersion(patchLog, version1);
        PatchLogVersion patchLogVersion2 = new PatchLogVersion(patchLog, version2);

        em.persist(project);
        em.persist(module);
        em.persist(version1);
        em.persist(version2);
        em.persist(version3);
        em.persist(client);
        em.persist(patchLog);
        em.persist(patchLogVersion1);
        em.persist(patchLogVersion2);

        PageRequest pageRequest = PageRequest.of(0, 100);

        // when
        Page<VersionDto.Result> results = versionService.searchVersionsNotMappingPatchLog(patchLog.getId(), pageRequest);
        List<VersionDto.Result> content = results.getContent();

        // then
        assertThat(content.size()).isEqualTo(1);
        assertThat(content).extracting("versionName").containsExactly("3.1.1");
    }
}