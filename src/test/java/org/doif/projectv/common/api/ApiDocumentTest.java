package org.doif.projectv.common.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.doif.projectv.business.issue.service.IssueService;
import org.doif.projectv.business.issue.service.VersionIssueService;
import org.doif.projectv.business.issue.web.IssueController;
import org.doif.projectv.business.module.service.ModuleService;
import org.doif.projectv.business.module.web.ModuleController;
import org.doif.projectv.business.project.service.ProjectService;
import org.doif.projectv.business.project.web.ProjectController;
import org.doif.projectv.business.task.service.TaskService;
import org.doif.projectv.business.task.web.TaskController;
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
        TaskController.class
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
}
