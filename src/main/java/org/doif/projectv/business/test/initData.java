package org.doif.projectv.business.test;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.entity.Issue;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.task.entity.Task;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.resource.constant.ResourceStatus;
import org.doif.projectv.common.resource.entity.*;
import org.doif.projectv.common.role.constant.RoleStatus;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;

import static org.springframework.http.HttpMethod.*;

@Profile("local")
@Component
@RequiredArgsConstructor
public class initData {

    private final InitDataService initDataService;

    @PostConstruct
    public void init() {
        initDataService.init();
    }

    @Component
    static class InitDataService {
        @PersistenceContext
        private EntityManager em;

        @Autowired
        BCryptPasswordEncoder bCryptPasswordEncoder;

        @Transactional
        public void init() {
            Project project = new Project("금융결제원 PG");
            Module module = new Module("금융결제원 PG WEB/ADMIN", project);
            Issue issue1 = new Issue("이슈1", "이슈1 입니다.", IssueStatus.OPEN, IssueCategory.NEW_DEVELOP);
            Issue issue2 = new Issue("이슈2", "이슈2 입니다.", IssueStatus.OPEN, IssueCategory.NEW_DEVELOP);

            Version version1 = new Version("v1.0.1", "버전 1.0.1 입니다.", module);

            VersionIssue versionIssue1 = new VersionIssue(version1, issue1, "202001", VersionIssueProgress.COMPLETE, "kjpmj", "");
            VersionIssue versionIssue2 = new VersionIssue(version1, issue1, "202002", VersionIssueProgress.COMPLETE, "kjpmj", "");
            VersionIssue versionIssue3 = new VersionIssue(version1, issue2, "202003", VersionIssueProgress.COMPLETE, "kjpmj", "");
            VersionIssue versionIssue4 = new VersionIssue(version1, issue2, "202004", VersionIssueProgress.COMPLETE, "kjpmj", "");

            Task task1 = new Task(versionIssue1, LocalDate.of(2020, 10, 10), LocalDate.of(2020, 10, 12), "이슈1 해결함", TaskType.DEVELOP, 0.5, "kjpmj", "remark1");
            Task task2 = new Task(versionIssue1, LocalDate.of(2020, 10, 11), LocalDate.of(2020, 10, 13), "이슈1 추가 수정", TaskType.DEVELOP, 1.0, "kjpmj", "remark1");
            Task task3 = new Task(versionIssue3, LocalDate.of(2020, 10, 12), LocalDate.of(2020, 10, 14), "이슈2 해결", TaskType.DEVELOP, 0.5, "kjpmj", "remark2");

            PatchLog patchLog1 = new PatchLog(version1, PatchTarget.DEV, PatchStatus.COMPLETE, LocalDate.of(2020,10,19), "kjpmj", "");
            PatchLog patchLog2 = new PatchLog(version1, PatchTarget.PROD, PatchStatus.COMPLETE, LocalDate.of(2020,10,20), "kjpmj", "");

            User user = new User("kjpmj", bCryptPasswordEncoder.encode("1234"), "김명진씨", UserStatus.VALID, "", "");
            Role role = new Role("관리자 ROLE", "관리자 ROLE입니다.", RoleStatus.ENABLE);
            UserRole userRole = new UserRole(user, role);
            MenuCategory menuCategory = new MenuCategory("메뉴카테고리1", "메뉴카테고리1 입니다.", ResourceStatus.ENABLE,1, "heart");
            Menu menu1 = new Menu("메뉴1", "메뉴1입니다.", ResourceStatus.ENABLE, menuCategory, 1, "/project", "");
            Menu menu2 = new Menu("메뉴2", "메뉴2입니다.", ResourceStatus.ENABLE, menuCategory, 2, "/module", "");
            Page page = new Page("페이지1", "페이지1 입니다.", ResourceStatus.ENABLE, "/api/pages/project", GET);
            Button button1 = new Button("버튼1", "", ResourceStatus.ENABLE, "/api/project", GET, page, "");
            Button button2 = new Button("버튼2", "", ResourceStatus.ENABLE, "/api/user", POST, page, "");
            Button button3 = new Button("버튼3", "", ResourceStatus.ENABLE, "/api/project/{id}", PUT, page, "");
            Button button4 = new Button("버튼4", "", ResourceStatus.ENABLE, "/api/task", GET, page, "");
            Button button5 = new Button("버튼5", "", ResourceStatus.ENABLE, "/api/svn", GET, page, "");
            Button button6 = new Button("버튼6", "", ResourceStatus.ENABLE, "/api/version/release/{id}", PUT, page, "");
            Button button7 = new Button("버튼7", "", ResourceStatus.ENABLE, "/api/version", POST, page, "");
            Button button8 = new Button("버튼8", "", ResourceStatus.ENABLE, "/api/version", GET, page, "");

            RoleResource roleResource1 = new RoleResource(role, menuCategory);
            RoleResource roleResource2 = new RoleResource(role, menu1);
            RoleResource roleResource2_1 = new RoleResource(role, menu2);
            RoleResource roleResource3 = new RoleResource(role, page);
            RoleResource roleResource4 = new RoleResource(role, button1);
            RoleResource roleResource5 = new RoleResource(role, button2);
            RoleResource roleResource6 = new RoleResource(role, button3);
            RoleResource roleResource7 = new RoleResource(role, button4);
            RoleResource roleResource8 = new RoleResource(role, button5);
            RoleResource roleResource9 = new RoleResource(role, button6);
            RoleResource roleResource10 = new RoleResource(role, button7);
            RoleResource roleResource11 = new RoleResource(role, button8);

            em.persist(project);
            em.persist(module);
            em.persist(version1);
            em.persist(issue1);
            em.persist(issue2);
            em.persist(versionIssue1);
            em.persist(versionIssue2);
            em.persist(versionIssue3);
            em.persist(versionIssue4);
            em.persist(task1);
            em.persist(task2);
            em.persist(task3);
            em.persist(patchLog1);
            em.persist(patchLog2);

            em.persist(user);
            em.persist(role);
            em.persist(userRole);
            em.persist(menuCategory);
            em.persist(menu1);
            em.persist(menu2);
            em.persist(page);
            em.persist(button1);
            em.persist(button2);
            em.persist(button3);
            em.persist(button4);
            em.persist(button5);
            em.persist(button6);
            em.persist(button7);
            em.persist(button8);

            em.persist(roleResource1);
            em.persist(roleResource2);
            em.persist(roleResource2_1);
            em.persist(roleResource3);
            em.persist(roleResource4);
            em.persist(roleResource5);
            em.persist(roleResource6);
            em.persist(roleResource7);
            em.persist(roleResource8);
            em.persist(roleResource9);
            em.persist(roleResource10);
            em.persist(roleResource11);
        }
    }
}
