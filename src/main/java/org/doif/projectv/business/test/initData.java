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
import org.doif.projectv.common.resource.entity.*;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.system.constant.PropertyGroupType;
import org.doif.projectv.common.system.entity.SystemProperty;
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
            Role role = new Role("관리자 ROLE", "관리자 ROLE입니다.", EnableStatus.ENABLE);
            UserRole userRole = new UserRole(user, role);

            MenuCategory menuCategory1 = new MenuCategory("메뉴-카테고리 1", "메뉴-카테고리 1", EnableStatus.ENABLE, 1, "", null);
            MenuCategory menuCategory1_1 = new MenuCategory("메뉴-카테고리 1-1", "메뉴-카테고리 1-1", EnableStatus.ENABLE, 1, "", menuCategory1);
            MenuCategory menuCategory1_1_1 = new MenuCategory("메뉴-카테고리 1-1-1", "메뉴-카테고리 1-1-1", EnableStatus.ENABLE, 1, "", menuCategory1_1);
            MenuCategory menuCategory1_1_2 = new MenuCategory("메뉴-카테고리 1-1-2", "메뉴-카테고리 1-1-2", EnableStatus.ENABLE, 2, "", menuCategory1_1);
            MenuCategory menuCategory1_1_3 = new MenuCategory("메뉴-카테고리 1-1-3", "메뉴-카테고리 1-1-3", EnableStatus.ENABLE, 3, "", menuCategory1_1);

            MenuCategory menuCategory2 = new MenuCategory("메뉴-카테고리 2", "메뉴-카테고리 2", EnableStatus.ENABLE, 2, "", null);
            MenuCategory menuCategory2_1 = new MenuCategory("메뉴-카테고리 2-1", "메뉴-카테고리 2-1", EnableStatus.ENABLE, 1, "", menuCategory2);
            MenuCategory menuCategory2_1_1 = new MenuCategory("메뉴-카테고리 2-1-1", "메뉴-카테고리 2-1-1", EnableStatus.ENABLE, 1, "edit", menuCategory2_1);
            MenuCategory menuCategory2_1_2 = new MenuCategory("메뉴-카테고리 2-1-2", "메뉴-카테고리 2-1-2", EnableStatus.ENABLE, 2, "", menuCategory2_1);

            MenuCategory menuCategory2_2 = new MenuCategory("메뉴-카테고리 2-2", "메뉴-카테고리 2-2", EnableStatus.ENABLE, 2, "", menuCategory2);
            MenuCategory menuCategory2_2_1 = new MenuCategory("메뉴-카테고리 2-2-1", "메뉴-카테고리 2-2-1", EnableStatus.ENABLE, 1, "", menuCategory2_2);
            MenuCategory menuCategory2_2_2 = new MenuCategory("메뉴-카테고리 2-2-2", "메뉴-카테고리 2-2-2", EnableStatus.ENABLE, 2, "", menuCategory2_2);

            MenuCategory menuCategory3 = new MenuCategory("메뉴-카테고리 3", "메뉴-카테고리 3", EnableStatus.ENABLE, 3, "heart", null);
            MenuCategory menuCategory3_1 = new MenuCategory("메뉴-카테고리 3-1", "메뉴-카테고리 3-1", EnableStatus.ENABLE, 1, "iconconc", menuCategory3);
            MenuCategory menuCategory3_1_1 = new MenuCategory("메뉴-카테고리 3-1-1", "메뉴-카테고리 3-1-1", EnableStatus.ENABLE, 1, "exit", menuCategory3_1);

            Menu menu1 = new Menu("메뉴 1", "메뉴 1", EnableStatus.ENABLE, menuCategory1_1_1, 1, "", "");
            Menu menu2 = new Menu("메뉴 2", "메뉴 2", EnableStatus.ENABLE, menuCategory1_1_1, 2, "/menus/2", "right-arrow");
            Menu menu3 = new Menu("메뉴 3", "메뉴 3", EnableStatus.ENABLE, menuCategory2_1_1, 1, "", "");
            Menu menu4 = new Menu("메뉴 4", "메뉴 4", EnableStatus.ENABLE, menuCategory2_1_1, 2, "", "");
            Menu menu5 = new Menu("메뉴 5", "메뉴 5", EnableStatus.ENABLE, menuCategory2_1_2, 1, "", "");
            Menu menu6 = new Menu("메뉴 6", "메뉴 6", EnableStatus.ENABLE, menuCategory2_2_1, 1, "", "");
            Menu menu7 = new Menu("메뉴 7", "메뉴 7", EnableStatus.ENABLE, menuCategory2_2_2, 1, "", "");
            Menu menu8 = new Menu("메뉴 8", "메뉴 8", EnableStatus.ENABLE, menuCategory2_1, 3, "", "");

            Page page = new Page("페이지1", "페이지1 입니다.", EnableStatus.ENABLE, "/api/pages/project", GET);
            Button button1 = new Button("버튼1", "", EnableStatus.ENABLE, "/api/project", GET, page, "");
            Button button2 = new Button("버튼2", "", EnableStatus.ENABLE, "/api/user", POST, page, "");
            Button button3 = new Button("버튼3", "", EnableStatus.ENABLE, "/api/project/{id}", PUT, page, "");
            Button button4 = new Button("버튼4", "", EnableStatus.ENABLE, "/api/task", GET, page, "");
            Button button5 = new Button("버튼5", "", EnableStatus.ENABLE, "/api/svn", GET, page, "");
            Button button6 = new Button("버튼6", "", EnableStatus.ENABLE, "/api/version/release/{id}", PUT, page, "");
            Button button7 = new Button("버튼7", "", EnableStatus.ENABLE, "/api/version", POST, page, "");
            Button button8 = new Button("버튼8", "", EnableStatus.ENABLE, "/api/version", GET, page, "");

            RoleResource roleResource3 = new RoleResource(role, page);
            RoleResource roleResource4 = new RoleResource(role, button1);
            RoleResource roleResource5 = new RoleResource(role, button2);
            RoleResource roleResource6 = new RoleResource(role, button3);
            RoleResource roleResource7 = new RoleResource(role, button4);
            RoleResource roleResource8 = new RoleResource(role, button5);
            RoleResource roleResource9 = new RoleResource(role, button6);
            RoleResource roleResource10 = new RoleResource(role, button7);
            RoleResource roleResource11 = new RoleResource(role, button8);

            SystemProperty systemProperty = new SystemProperty(PropertyGroupType.COMMON, "TEST", "테스트", "테스트 속성", true);

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
//            em.persist(menu1);
//            em.persist(menu2);
            em.persist(page);
            em.persist(button1);
            em.persist(button2);
            em.persist(button3);
            em.persist(button4);
            em.persist(button5);
            em.persist(button6);
            em.persist(button7);
            em.persist(button8);

//            em.persist(roleResource2);
//            em.persist(roleResource2_1);
            em.persist(roleResource3);
            em.persist(roleResource4);
            em.persist(roleResource5);
            em.persist(roleResource6);
            em.persist(roleResource7);
            em.persist(roleResource8);
            em.persist(roleResource9);
            em.persist(roleResource10);
            em.persist(roleResource11);

            em.persist(systemProperty);

            // 메뉴 테스트
            em.persist(menuCategory3);
            em.persist(menuCategory3_1);
            em.persist(menuCategory3_1_1);

            em.persist(menuCategory1);
            em.persist(menuCategory1_1);
            em.persist(menuCategory1_1_3);
            em.persist(menuCategory1_1_1);
            em.persist(menuCategory1_1_2);

            em.persist(menuCategory2);
            em.persist(menuCategory2_1);
            em.persist(menuCategory2_1_1);
            em.persist(menuCategory2_1_2);

            em.persist(menuCategory2_2);
            em.persist(menuCategory2_2_1);
            em.persist(menuCategory2_2_2);

            em.persist(menu8);
            em.persist(menu1);
            em.persist(menu2);
            em.persist(menu3);
            em.persist(menu4);
            em.persist(menu5);
            em.persist(menu6);
            em.persist(menu7);
        }
    }
}
