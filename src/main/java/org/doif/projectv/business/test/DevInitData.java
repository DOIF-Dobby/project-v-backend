package org.doif.projectv.business.test;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Profile("none")
@Component
@RequiredArgsConstructor
public class DevInitData {

    private final DevInitDataService devInitDataService;

    @PostConstruct
    public void init() {
        devInitDataService.init();
    }

    @Component
    static class DevInitDataService {
        @Autowired
        PasswordEncoder passwordEncoder;

        @Autowired
        BytesEncryptor bytesEncryptor;

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            User user1 = new User("", passwordEncoder.encode(""), "김명진", UserStatus.VALID);
            Role devRole = new Role("개발자 ROLE", "개발자 ROLE 입니다.", EnableStatus.ENABLE);
            UserRole userRole1 = new UserRole(user1, devRole);

            Page commonPage = new Page("공통 페이지", "애플리케이션 전반적으로 사용하는 권한을 등록할 때 사용하는 페이지", EnableStatus.ENABLE, "PAGE_COMMON", "/api/pages/common");
            Page buttonPage = new Page("버튼 자원 관리", "버튼 자원 관리 페이지", EnableStatus.ENABLE, "PAGE_RESOURCE_BUTTON", "/api/pages/resources/button");
            Page menuPage = new Page("메뉴 자원 관리", "메뉴 자원 관리 페이지", EnableStatus.ENABLE, "PAGE_RESOURCE_MENU", "/api/pages/resources/menu");
            Page messagePage = new Page("메세지 자원 관리", "메세지 자원 관리 페이지", EnableStatus.ENABLE, "PAGE_RESOURCE_MESSAGE", "/api/pages/resources/message");
            Page labelPage = new Page("라벨 자원 관리", "라벨 자원 관리 페이지", EnableStatus.ENABLE, "PAGE_RESOURCE_LABEL", "/api/pages/resources/label");
            Page pagePage = new Page("페이지 자원 관리", "페이지 자원 관리 페이지", EnableStatus.ENABLE, "PAGE_RESOURCE_PAGE", "/api/pages/resources/page");
            Page tabPage = new Page("탭 자원 관리", "탭 자원 관리 페이지", EnableStatus.ENABLE, "PAGE_RESOURCE_TAB", "/api/pages/resources/tab");
            Page rolePage = new Page("Role 관리", "Role 관리 페이지", EnableStatus.ENABLE, "PAGE_ROLE", "/api/pages/role");
            Page systemPropertyPage = new Page("시스템 속성 관리", "시스템 속성 관리 페이지", EnableStatus.ENABLE, "PAGE_SYSTEM_PROPERTY", "/api/pages/system-property");
            Page userPage = new Page("사용자 관리", "사용자 관리 페이지", EnableStatus.ENABLE, "PAGE_USER", "/api/pages/user");
            Page roleResourcePage = new Page("Role-Resource 관리", "Role-Resource 관리 페이지", EnableStatus.ENABLE, "PAGE_ROLE_RESOURCE", "/api/pages/role-resource");
            Page userRolePage = new Page("User-Role 관리", "User-Role 관리 페이지", EnableStatus.ENABLE, "PAGE_USER_ROLE", "/api/pages/user-role");

            Button buttonFindButton = new Button("조회", "버튼 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_BUTTON_FIND", "/api/resources/button", HttpMethod.GET, buttonPage, "find");
            Button buttonAddButton = new Button("등록", "버튼 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_BUTTON_ADD", "/api/resources/button", HttpMethod.POST, buttonPage, "add");
            Button buttonModifyButton = new Button("수정", "버튼 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_BUTTON_MODIFY", "/api/resources/button/{id}", HttpMethod.PUT, buttonPage, "modify");
            Button buttonDeleteButton = new Button("삭제", "버튼 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_BUTTON_DELETE", "/api/resources/button/{id}", HttpMethod.DELETE, buttonPage, "delete");

            Button menuCategoryFindButton = new Button("조회", "메뉴 카테고리 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_CATEGORY_FIND", "/api/resources/menu-category", HttpMethod.GET, menuPage, "find");
            Button menuCategoryAddButton = new Button("등록", "메뉴 카테고리 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_CATEGORY_ADD", "/api/resources/menu-category", HttpMethod.POST, menuPage, "add");
            Button menuCategoryModifyButton = new Button("수정", "메뉴 카테고리 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_CATEGORY_MODIFY", "/api/resources/menu-category/{id}", HttpMethod.PUT, menuPage, "modify");
            Button menuCategoryDeleteButton = new Button("삭제", "메뉴 카테고리 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_CATEGORY_DELETE", "/api/resources/menu-category/{id}", HttpMethod.DELETE, menuPage, "delete");

            Button menuFindButton = new Button("조회", "메뉴 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_FIND", "/api/resources/menu", HttpMethod.GET, menuPage, "find");
            Button menuAddButton = new Button("등록", "메뉴 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_ADD", "/api/resources/menu", HttpMethod.POST, menuPage, "add");
            Button menuModifyButton = new Button("수정", "메뉴 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_MODIFY", "/api/resources/menu/{id}", HttpMethod.PUT, menuPage, "modify");
            Button menuDeleteButton = new Button("삭제", "메뉴 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MENU_DELETE", "/api/resources/menu/{id}", HttpMethod.DELETE, menuPage, "delete");

            Button messageFindButton = new Button("조회", "메세지 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MESSAGE_FIND", "/api/resources/message", HttpMethod.GET, messagePage, "find");
            Button messageAddButton = new Button("등록", "메세지 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MESSAGE_ADD", "/api/resources/message", HttpMethod.POST, messagePage, "add");
            Button messageModifyButton = new Button("수정", "메세지 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MESSAGE_MODIFY", "/api/resources/message/{id}", HttpMethod.PUT, messagePage, "modify");
            Button messageDeleteButton = new Button("삭제", "메세지 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_MESSAGE_DELETE", "/api/resources/message/{id}", HttpMethod.DELETE, messagePage, "delete");

            Button labelFindButton = new Button("조회", "라벨 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_LABEL_FIND", "/api/resources/label", HttpMethod.GET, labelPage, "find");
            Button labelAddButton = new Button("등록", "라벨 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_LABEL_ADD", "/api/resources/label", HttpMethod.POST, labelPage, "add");
            Button labelModifyButton = new Button("수정", "라벨 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_LABEL_MODIFY", "/api/resources/label/{id}", HttpMethod.PUT, labelPage, "modify");
            Button labelDeleteButton = new Button("삭제", "라벨 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_LABEL_DELETE", "/api/resources/label/{id}", HttpMethod.DELETE, labelPage, "delete");

            Button pageFindButton = new Button("조회", "페이지 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_PAGE_FIND", "/api/resources/page", HttpMethod.GET, pagePage, "find");
            Button pageAddButton = new Button("등록", "페이지 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_PAGE_ADD", "/api/resources/page", HttpMethod.POST, pagePage, "add");
            Button pageModifyButton = new Button("수정", "페이지 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_PAGE_MODIFY", "/api/resources/page/{id}", HttpMethod.PUT, pagePage, "modify");
            Button pageDeleteButton = new Button("삭제", "페이지 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_PAGE_DELETE", "/api/resources/page/{id}", HttpMethod.DELETE, pagePage, "delete");

            Button tabFindButton = new Button("조회", "페이지 자원 조회 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_TAB_FIND", "/api/resources/tab", HttpMethod.GET, tabPage, "find");
            Button tabAddButton = new Button("등록", "페이지 자원 등록 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_TAB_ADD", "/api/resources/tab", HttpMethod.POST, tabPage, "add");
            Button tabModifyButton = new Button("수정", "페이지 자원 수정 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_TAB_MODIFY", "/api/resources/tab/{id}", HttpMethod.PUT, tabPage, "modify");
            Button tabDeleteButton = new Button("삭제", "페이지 자원 삭제 버튼", EnableStatus.ENABLE, "BTN_RESOURCE_TAB_DELETE", "/api/resources/tab/{id}", HttpMethod.DELETE, tabPage, "delete");

            Button roleFindButton = new Button("조회", "Role 조회 버튼", EnableStatus.ENABLE, "BTN_ROLE_FIND", "/api/role", HttpMethod.GET, rolePage, "find");
            Button roleAddButton = new Button("등록", "Role 등록 버튼", EnableStatus.ENABLE, "BTN_ROLE_ADD", "/api/role", HttpMethod.POST, rolePage, "add");
            Button roleModifyButton = new Button("수정", "Role 수정 버튼", EnableStatus.ENABLE, "BTN_ROLE_MODIFY", "/api/role/{id}", HttpMethod.PUT, rolePage, "modify");
            Button roleDeleteButton = new Button("삭제", "Role 삭제 버튼", EnableStatus.ENABLE, "BTN_ROLE_DELETE", "/api/role/{id}", HttpMethod.DELETE, rolePage, "delete");

            Button systemPropertyFindButton = new Button("조회", "시스템 속성 조회 버튼", EnableStatus.ENABLE, "BTN_SYSTEM_PROPERTY_FIND", "/api/system-property", HttpMethod.GET, systemPropertyPage, "find");
            Button systemPropertyAddButton = new Button("등록", "시스템 속성 등록 버튼", EnableStatus.ENABLE, "BTN_SYSTEM_PROPERTY_ADD", "/api/system-property", HttpMethod.POST, systemPropertyPage, "add");
            Button systemPropertyModifyButton = new Button("수정", "시스템 속성 수정 버튼", EnableStatus.ENABLE, "BTN_SYSTEM_PROPERTY_MODIFY", "/api/system-property/{id}", HttpMethod.PUT, systemPropertyPage, "modify");
            Button systemPropertyDeleteButton = new Button("삭제", "시스템 속성 삭제 버튼", EnableStatus.ENABLE, "BTN_SYSTEM_PROPERTY_DELETE", "/api/system-property/{id}", HttpMethod.DELETE, systemPropertyPage, "delete");

            Button userFindButton = new Button("조회", "사용자 조회 버튼", EnableStatus.ENABLE, "BTN_USER_FIND", "/api/user", HttpMethod.GET, systemPropertyPage, "find");
            Button userAddButton = new Button("등록", "사용자 등록 버튼", EnableStatus.ENABLE, "BTN_USER_ADD", "/api/user", HttpMethod.POST, systemPropertyPage, "add");
            Button userModifyButton = new Button("수정", "사용자 수정 버튼", EnableStatus.ENABLE, "BTN_USER_MODIFY", "/api/user/{id}", HttpMethod.PUT, systemPropertyPage, "modify");
            Button userDeleteButton = new Button("삭제", "사용자 삭제 버튼", EnableStatus.ENABLE, "BTN_USER_DELETE", "/api/user/{id}", HttpMethod.DELETE, systemPropertyPage, "delete");

            Button roleResourcePageFindButton = new Button("조회", "Role-Resource 페이지 조회 버튼", EnableStatus.ENABLE, "BTN_ROLE_RESOURCE_PAGE_FIND", "/api/role-resource/page", HttpMethod.GET, roleResourcePage, "find");
            Button roleResourceButtonFindButton = new Button("조회", "Role-Resource 버튼 조회 버튼", EnableStatus.ENABLE, "BTN_ROLE_RESOURCE_BUTTON_FIND", "/api/role-resource/button", HttpMethod.GET, roleResourcePage, "find");
            Button roleResourceTabFindButton = new Button("조회", "Role-Resource 탭 조회 버튼", EnableStatus.ENABLE, "BTN_ROLE_RESOURCE_TAB_FIND", "/api/role-resource/tab", HttpMethod.GET, roleResourcePage, "find");
            Button roleResourceAllocateButton = new Button("할당", "Role-Resource 할당 버튼", EnableStatus.ENABLE, "BTN_ROLE_RESOURCE_ALLOCATE", "/api/role-resource", HttpMethod.POST, roleResourcePage, "allocate");

            Button userRoleFindButton = new Button("조회", "User-Role 조회 버튼", EnableStatus.ENABLE, "BTN_USER_ROLE_FIND", "/api/user-role", HttpMethod.GET, userRolePage, "find");
            Button userRoleAllocateButton = new Button("할당", "User-Role 할당 버튼", EnableStatus.ENABLE, "BTN_USER_ROLE_ALLOCATE", "/api/user-role", HttpMethod.POST, userRolePage, "allocate");

            Button commonPageChildResourceFindButton = new Button("조회", "페이지 자식 자원 조회", EnableStatus.ENABLE, "BTN_COMMON_PAGE_CHILD_RESOURCE_FIND", "/api/pages/{path}", HttpMethod.GET, commonPage, "find");

            MenuCategory devMenuCategory = new MenuCategory("개발자", "개발자 메뉴 카테고리", EnableStatus.ENABLE, "CATEGORY_DEV", 99, "dev", null);
            Menu devResourceMenu = new Menu("메뉴 관리", "메뉴 리소스 관리 메뉴", EnableStatus.ENABLE, "MENU_DEV_RESOURCE_MENU", devMenuCategory, 1, "/dev/menu", null);
            Menu devResourcePage = new Menu("페이지 관리", "페이지 리소스 관리 메뉴", EnableStatus.ENABLE, "MENU_DEV_RESOURCE_PAGE", devMenuCategory, 2, "/dev/page", null);
            Menu devResourceTab = new Menu("탭 관리", "탭 리소스 관리 메뉴", EnableStatus.ENABLE, "MENU_DEV_RESOURCE_TAB", devMenuCategory, 3, "/dev/page", null);
            Menu devResourceButton = new Menu("버튼 관리", "버튼 리소스 관리 메뉴", EnableStatus.ENABLE, "MENU_DEV_RESOURCE_BUTTON", devMenuCategory, 4, "/dev/button", null);
            Menu devResourceLabel = new Menu("라벨 관리", "라벨 리소스 관리 메뉴", EnableStatus.ENABLE, "MENU_DEV_RESOURCE_LABEL", devMenuCategory, 5, "/dev/label", null);
            Menu devResourceMessage = new Menu("메세지 관리", "메세지 리소스 관리 메뉴", EnableStatus.ENABLE, "MENU_DEV_RESOURCE_MESSAGE", devMenuCategory, 6, "/dev/message", null);

            // 유저, Role 등록
            em.persist(user1);
            em.persist(devRole);
            em.persist(userRole1);

            // 페이지 등록
            em.persist(commonPage);
            em.persist(buttonPage);
            em.persist(menuPage);
            em.persist(messagePage);
            em.persist(labelPage);
            em.persist(pagePage);
            em.persist(tabPage);
            em.persist(rolePage);
            em.persist(systemPropertyPage);
            em.persist(userPage);
            em.persist(roleResourcePage);
            em.persist(userRolePage);

            // 페이지 RoleResource 등록
            em.persist(new RoleResource(devRole, commonPage));
            em.persist(new RoleResource(devRole, buttonPage));
            em.persist(new RoleResource(devRole, menuPage));
            em.persist(new RoleResource(devRole, messagePage));
            em.persist(new RoleResource(devRole, labelPage));
            em.persist(new RoleResource(devRole, pagePage));
            em.persist(new RoleResource(devRole, tabPage));
            em.persist(new RoleResource(devRole, rolePage));
            em.persist(new RoleResource(devRole, systemPropertyPage));
            em.persist(new RoleResource(devRole, userPage));
            em.persist(new RoleResource(devRole, roleResourcePage));
            em.persist(new RoleResource(devRole, userRolePage));


            // 버튼 등록 및 RoleResource 등록
            em.persist(buttonFindButton);
            em.persist(buttonAddButton);
            em.persist(buttonModifyButton);
            em.persist(buttonDeleteButton);
            em.persist(new RoleResource(devRole, buttonFindButton));
            em.persist(new RoleResource(devRole, buttonAddButton));
            em.persist(new RoleResource(devRole, buttonModifyButton));
            em.persist(new RoleResource(devRole, buttonDeleteButton));

            em.persist(menuCategoryFindButton);
            em.persist(menuCategoryAddButton);
            em.persist(menuCategoryModifyButton);
            em.persist(menuCategoryDeleteButton);
            em.persist(new RoleResource(devRole, menuCategoryFindButton));
            em.persist(new RoleResource(devRole, menuCategoryAddButton));
            em.persist(new RoleResource(devRole, menuCategoryModifyButton));
            em.persist(new RoleResource(devRole, menuCategoryDeleteButton));

            em.persist(menuFindButton);
            em.persist(menuAddButton);
            em.persist(menuModifyButton);
            em.persist(menuDeleteButton);
            em.persist(new RoleResource(devRole, menuFindButton));
            em.persist(new RoleResource(devRole, menuAddButton));
            em.persist(new RoleResource(devRole, menuModifyButton));
            em.persist(new RoleResource(devRole, menuDeleteButton));

            em.persist(messageFindButton);
            em.persist(messageAddButton);
            em.persist(messageModifyButton);
            em.persist(messageDeleteButton);
            em.persist(new RoleResource(devRole, messageFindButton));
            em.persist(new RoleResource(devRole, messageAddButton));
            em.persist(new RoleResource(devRole, messageModifyButton));
            em.persist(new RoleResource(devRole, messageDeleteButton));

            em.persist(labelFindButton);
            em.persist(labelAddButton);
            em.persist(labelModifyButton);
            em.persist(labelDeleteButton);
            em.persist(new RoleResource(devRole, labelFindButton));
            em.persist(new RoleResource(devRole, labelAddButton));
            em.persist(new RoleResource(devRole, labelModifyButton));
            em.persist(new RoleResource(devRole, labelDeleteButton));

            em.persist(pageFindButton);
            em.persist(pageAddButton);
            em.persist(pageModifyButton);
            em.persist(pageDeleteButton);
            em.persist(new RoleResource(devRole, pageFindButton));
            em.persist(new RoleResource(devRole, pageAddButton));
            em.persist(new RoleResource(devRole, pageModifyButton));
            em.persist(new RoleResource(devRole, pageDeleteButton));

            em.persist(tabFindButton);
            em.persist(tabAddButton);
            em.persist(tabModifyButton);
            em.persist(tabDeleteButton);
            em.persist(new RoleResource(devRole, tabFindButton));
            em.persist(new RoleResource(devRole, tabAddButton));
            em.persist(new RoleResource(devRole, tabModifyButton));
            em.persist(new RoleResource(devRole, tabDeleteButton));

            em.persist(roleFindButton);
            em.persist(roleAddButton);
            em.persist(roleModifyButton);
            em.persist(roleDeleteButton);
            em.persist(new RoleResource(devRole, roleFindButton));
            em.persist(new RoleResource(devRole, roleAddButton));
            em.persist(new RoleResource(devRole, roleModifyButton));
            em.persist(new RoleResource(devRole, roleDeleteButton));

            em.persist(systemPropertyFindButton);
            em.persist(systemPropertyAddButton);
            em.persist(systemPropertyModifyButton);
            em.persist(systemPropertyDeleteButton);
            em.persist(new RoleResource(devRole, systemPropertyFindButton));
            em.persist(new RoleResource(devRole, systemPropertyAddButton));
            em.persist(new RoleResource(devRole, systemPropertyModifyButton));
            em.persist(new RoleResource(devRole, systemPropertyDeleteButton));

            em.persist(userFindButton);
            em.persist(userAddButton);
            em.persist(userModifyButton);
            em.persist(userDeleteButton);
            em.persist(new RoleResource(devRole, userFindButton));
            em.persist(new RoleResource(devRole, userAddButton));
            em.persist(new RoleResource(devRole, userModifyButton));
            em.persist(new RoleResource(devRole, userDeleteButton));

            em.persist(roleResourcePageFindButton);
            em.persist(roleResourceButtonFindButton);
            em.persist(roleResourceTabFindButton);
            em.persist(roleResourceAllocateButton);
            em.persist(new RoleResource(devRole, roleResourcePageFindButton));
            em.persist(new RoleResource(devRole, roleResourceButtonFindButton));
            em.persist(new RoleResource(devRole, roleResourceTabFindButton));
            em.persist(new RoleResource(devRole, roleResourceAllocateButton));

            em.persist(userRoleFindButton);
            em.persist(userRoleAllocateButton);
            em.persist(new RoleResource(devRole, userRoleFindButton));
            em.persist(new RoleResource(devRole, userRoleAllocateButton));

            em.persist(commonPageChildResourceFindButton);
            em.persist(new RoleResource(devRole, commonPageChildResourceFindButton));

            // 메뉴 카테고리, 메뉴 등록
            em.persist(devMenuCategory);
            em.persist(devResourceMenu);
            em.persist(devResourcePage);
            em.persist(devResourceTab);
            em.persist(devResourceButton);
            em.persist(devResourceLabel);
            em.persist(devResourceMessage);

        }
    }
}
