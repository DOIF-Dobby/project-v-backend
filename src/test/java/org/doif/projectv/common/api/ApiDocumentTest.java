package org.doif.projectv.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.doif.projectv.business.issue.service.IssueService;
import org.doif.projectv.business.issue.service.VersionIssueService;
import org.doif.projectv.business.issue.web.IssueController;
import org.doif.projectv.business.issue.web.VersionIssueController;
import org.doif.projectv.business.module.service.ModuleService;
import org.doif.projectv.business.module.web.ModuleController;
import org.doif.projectv.business.patchlog.service.PatchLogService;
import org.doif.projectv.business.patchlog.web.PatchLogController;
import org.doif.projectv.business.project.service.ProjectService;
import org.doif.projectv.business.project.web.ProjectController;
import org.doif.projectv.business.task.service.TaskService;
import org.doif.projectv.business.task.web.TaskController;
import org.doif.projectv.business.vcs.service.VcsAuthInfoService;
import org.doif.projectv.business.vcs.service.VcsService;
import org.doif.projectv.business.version.service.VersionService;
import org.doif.projectv.business.version.web.VersionController;
import org.doif.projectv.common.api.web.ApiCommonController;
import org.doif.projectv.common.enumeration.EnumMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = {
        EnumMapper.class,
        ApiCommonController.class,
        ProjectController.class,
        ModuleController.class,
        IssueController.class,
        TaskController.class,
        VersionController.class,
        PatchLogController.class,
        VersionIssueController.class
},
        useDefaultFilters = false
)
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureRestDocs
@ComponentScan(basePackages = "org.doif.projectv.common.enumeration")
@ComponentScan(basePackages = "org.doif.projectv.common.api.web")
@ComponentScan(basePackages = "org.doif.projectv.business.project.web")
@ComponentScan(basePackages = "org.doif.projectv.business.module.web")
@ComponentScan(basePackages = "org.doif.projectv.business.issue.web")
@ComponentScan(basePackages = "org.doif.projectv.business.task.web")
@ComponentScan(basePackages = "org.doif.projectv.business.version.web")
@ComponentScan(basePackages = "org.doif.projectv.business.patchlog.web")
@ComponentScan(basePackages = "org.doif.projectv.business.vcs.web")
public abstract class ApiDocumentTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    protected EnumMapper enumMapper;

    @MockBean
    protected ProjectService projectService;

    @MockBean
    protected ModuleService moduleService;

    @MockBean
    protected IssueService issueService;

    @MockBean
    protected VersionIssueService versionIssueService;

    @MockBean
    protected TaskService taskService;

    @MockBean
    protected VersionService versionService;

    @MockBean
    protected PatchLogService patchLogService;

    @MockBean
    protected VcsService vcsService;

    @MockBean
    protected VcsAuthInfoService vcsAuthInfoService;
}
