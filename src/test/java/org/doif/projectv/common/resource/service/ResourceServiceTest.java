package org.doif.projectv.common.resource.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.dto.AuthCheckDto;
import org.doif.projectv.common.resource.dto.ButtonDto;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.entity.User;
import org.doif.projectv.common.user.entity.UserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ResourceServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ResourceService resourceService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @BeforeEach
    public void init() {
        String limPassword = passwordEncoder.encode("limPassword");
        User user = new User("lim", limPassword, "임진성", UserStatus.VALID);
        Role devRole = new Role("개발자 ROLE", "개발자 Role 입니다.", EnableStatus.ENABLE);
        UserRole userRole1 = new UserRole(user, devRole);

        Page page1 = new Page("맥북 관리 페이지", "맥북 오너 임진성", EnableStatus.ENABLE, "MAC_BOOK_OWNER", "/api/pages/macbook");
        Page page2 = new Page("삼전 관리 페이지", "삼전 주식 오너 전혜수", EnableStatus.DISABLE, "SAMSUNG_OWNER", "/api/pages/samsung");
        Page page3 = new Page("애플 관리 페이지", "사과 인간 박태용", EnableStatus.ENABLE, "APPLE_OWNER", "/api/pages/apple");

        Button button1 = new Button("버튼1", "버튼1", EnableStatus.ENABLE, "BUTTON_1", "/api/macbook", HttpMethod.GET, page1, "find");
        Button button2 = new Button("버튼2", "버튼2", EnableStatus.ENABLE, "BUTTON_2", "/api/macbook", HttpMethod.POST, page1, "add");
        Button button3 = new Button("버튼3", "버튼3", EnableStatus.ENABLE, "BUTTON_3", "/api/macbook/{id}", HttpMethod.PUT, page1, "edit");

        Button button4 = new Button("버튼4", "버튼4", EnableStatus.DISABLE, "BUTTON_4", "/api/samsung", HttpMethod.GET, page2, "find");
        Button button5 = new Button("버튼5", "버튼5", EnableStatus.DISABLE, "BUTTON_5", "/api/samsung", HttpMethod.POST, page2, "add");
        Button button6 = new Button("버튼6", "버튼6", EnableStatus.DISABLE, "BUTTON_6", "/api/samsung/{id}", HttpMethod.PUT, page2, "edit");

        Button button7 = new Button("버튼7", "버튼7", EnableStatus.ENABLE, "BUTTON_7", "/api/apple", HttpMethod.GET, page3, "find");
        Button button8 = new Button("버튼8", "버튼8", EnableStatus.ENABLE, "BUTTON_8", "/api/apple", HttpMethod.POST, page3, "add");
        Button button9 = new Button("버튼9", "버튼9", EnableStatus.ENABLE, "BUTTON_9", "/api/apple/{id}", HttpMethod.PUT, page3, "edit");

        Tab tab1 = new Tab("탭1", "탭1", EnableStatus.ENABLE, "TAB_1", "/api/tabs/tab1", HttpMethod.GET, page1, "TAB_GROUP_1", 1);
        Tab tab2 = new Tab("탭2", "탭2", EnableStatus.ENABLE, "TAB_2", "/api/tabs/tab2", HttpMethod.GET, page1, "TAB_GROUP_2", 2);

        Tab tab3 = new Tab("탭3", "탭3", EnableStatus.DISABLE, "TAB_3", "/api/tabs/tab3", HttpMethod.GET, page2, "TAB_GROUP_3", 1);
        Tab tab4 = new Tab("탭4", "탭4", EnableStatus.DISABLE, "TAB_4", "/api/tabs/tab4", HttpMethod.GET, page2, "TAB_GROUP_4", 2);

        Tab tab5 = new Tab("탭5", "탭5", EnableStatus.ENABLE, "TAB_5", "/api/tabs/tab5", HttpMethod.GET, page3, "TAB_GROUP_5", 1);
        Tab tab6 = new Tab("탭6", "탭6", EnableStatus.ENABLE, "TAB_6", "/api/tabs/tab6", HttpMethod.GET, page3, "TAB_GROUP_6", 2);

        RoleResource roleResource1 = new RoleResource(devRole, page1);
        RoleResource roleResource2 = new RoleResource(devRole, page2);
        RoleResource roleResource3 = new RoleResource(devRole, page3);
        
        RoleResource roleResource4 = new RoleResource(devRole, button1);
        RoleResource roleResource5 = new RoleResource(devRole, button2);
        RoleResource roleResource6 = new RoleResource(devRole, button3);
        RoleResource roleResource7 = new RoleResource(devRole, button4);
        RoleResource roleResource8 = new RoleResource(devRole, button5);
        RoleResource roleResource9 = new RoleResource(devRole, button6);
        RoleResource roleResource10 = new RoleResource(devRole, button7);
        RoleResource roleResource11 = new RoleResource(devRole, button8);
        RoleResource roleResource12 = new RoleResource(devRole, button9);

        RoleResource roleResource13 = new RoleResource(devRole, tab1);
        RoleResource roleResource14 = new RoleResource(devRole, tab2);
        RoleResource roleResource15 = new RoleResource(devRole, tab3);
        RoleResource roleResource16 = new RoleResource(devRole, tab4);
        RoleResource roleResource17 = new RoleResource(devRole, tab5);
        RoleResource roleResource18 = new RoleResource(devRole, tab6);


        em.persist(user);
        em.persist(devRole);
        em.persist(userRole1);
        em.persist(page1);
        em.persist(page2);
        em.persist(page3);

        em.persist(button1);
        em.persist(button2);
        em.persist(button3);
        em.persist(button4);
        em.persist(button5);
        em.persist(button6);
        em.persist(button7);
        em.persist(button8);
        em.persist(button9);

        em.persist(tab1);
        em.persist(tab2);
        em.persist(tab3);
        em.persist(tab4);
        em.persist(tab5);
        em.persist(tab6);
        
        em.persist(roleResource1);
        em.persist(roleResource2);
        em.persist(roleResource3);
        em.persist(roleResource4);
        em.persist(roleResource5);
        em.persist(roleResource6);
        em.persist(roleResource7);
        em.persist(roleResource8);
        em.persist(roleResource9);
        em.persist(roleResource10);
        em.persist(roleResource11);
        em.persist(roleResource12);
        em.persist(roleResource13);
        em.persist(roleResource14);
        em.persist(roleResource15);
        em.persist(roleResource16);
        em.persist(roleResource17);
        em.persist(roleResource18);
        
    }

    @Test
    public void 권한_리소스_체크_서비스_테스트() throws Exception {
        // given

        // when
        resourceService.searchAuthorityResource("lim");
        List<AuthCheckDto.ResourceAuthorityCheck> results = resourceService.searchAuthorityResource("lim");

        // then
        assertThat(results.size()).isEqualTo(10);
    }

    @Test
    public void 페이지_리소스_체크_서비스_테스트() throws Exception {
        // given

        // when
        resourceService.searchAuthorityResource("lim");
        List<AuthCheckDto.ResourcePageCheck> results = resourceService.searchPageResource("lim");

        // then
        assertThat(results.size()).isEqualTo(2);
    }


    @Test
    public void 페이지_자식_리소스_체크_서비스_테스트() throws Exception {
        // given

        // when
        PageDto.Child macbookChild = resourceService.searchPageChildResource("/api/pages/macbook");
        Map<String, ButtonDto.Result> macbookChildButtonMap = macbookChild.getButtonMap();

        PageDto.Child samsungChild = resourceService.searchPageChildResource("/api/pages/samsung");
        Map<String, ButtonDto.Result> samsungChildButtonMap = samsungChild.getButtonMap();

        // then
        assertThat(macbookChildButtonMap.get("BUTTON_1").getName()).isEqualTo("버튼1");
        assertThat(samsungChildButtonMap).isEmpty();

    }
}