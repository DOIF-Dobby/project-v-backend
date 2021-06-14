package org.doif.projectv.common.role.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class RoleResourceServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    RoleResourceService roleResourceService;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ButtonRepository buttonRepository;

    @Autowired
    TabRepository tabRepository;

    @Autowired
    RoleRepository roleRepository;

    @BeforeEach
    public void init() {
        Role role1 = new Role("개발자 Role", "개발자 롤입니다.", EnableStatus.ENABLE);
        Role role2 = new Role("사용자 Role", "사용자 롤입니다.", EnableStatus.ENABLE);
        em.persist(role1);
        em.persist(role2);

        Page page1 = new Page("페이지1", "페이지1", EnableStatus.ENABLE, "PAGE_1", "/api/pages/page1");
        Page page2 = new Page("페이지2", "페이지2", EnableStatus.ENABLE, "PAGE_2", "/api/pages/page2");
        Page page3 = new Page("페이지3", "페이지3", EnableStatus.ENABLE, "PAGE_3", "/api/pages/page3");
        em.persist(page1);
        em.persist(page2);
        em.persist(page3);

        Button button1 = new Button("버튼1", "", EnableStatus.ENABLE, "BUTTON_1",  "/api/button1", HttpMethod.GET, page1, "");
        Button button2 = new Button("버튼2", "", EnableStatus.ENABLE, "BUTTON_2",  "/api/button2", HttpMethod.GET, page1, "");
        Button button3 = new Button("버튼3", "", EnableStatus.ENABLE, "BUTTON_3",  "/api/button3", HttpMethod.GET, page1, "");
        em.persist(button1);
        em.persist(button2);
        em.persist(button3);

        Tab tab1 = new Tab("탭1", "", EnableStatus.ENABLE, "TAB_1", "/api/tab1", HttpMethod.GET, page1, "TABGROUP1", 1);
        Tab tab2 = new Tab("탭2", "", EnableStatus.ENABLE, "TAB_2", "/api/tab2", HttpMethod.GET, page1, "TABGROUP1", 2);
        Tab tab3 = new Tab("탭3", "", EnableStatus.ENABLE, "TAB_3", "/api/tab3", HttpMethod.GET, page1, "TABGROUP1", 3);
        em.persist(tab1);
        em.persist(tab2);
        em.persist(tab3);

        em.persist(new RoleResource(role1, page1));
        em.persist(new RoleResource(role1, page2));
        em.persist(new RoleResource(role1, page3));

        em.persist(new RoleResource(role1, button1));
        em.persist(new RoleResource(role1, button2));
        em.persist(new RoleResource(role1, button3));

        em.persist(new RoleResource(role1, tab1));
        em.persist(new RoleResource(role1, tab2));
        em.persist(new RoleResource(role1, tab3));


        em.persist(new RoleResource(role2, page1));
        em.persist(new RoleResource(role2, button1));
        em.persist(new RoleResource(role2, tab1));

    }

    @Test
    public void ROLE_RESOURCE_폐이지_조회_서비스_테스트() throws Exception {
        // given
        Role devRole = roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("개발자 Role"))
                .findFirst().get();

        Role userRole = roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("사용자 Role"))
                .findFirst().get();

        RoleResourceDto.SearchPage search = new RoleResourceDto.SearchPage();
        search.setRoleId(userRole.getId());

        // when
        List<RoleResourceDto.ResultPage> results = roleResourceService.selectPage(search);

        //then
        assertThat(results.size()).isEqualTo(3);
        assertThat(results).extracting("checked").containsExactly(true, false, false);

        // given
        search.setRoleId(devRole.getId());

        // when
        List<RoleResourceDto.ResultPage> results2 = roleResourceService.selectPage(search);

        // then
        assertThat(results2.size()).isEqualTo(3);
        assertThat(results2).extracting("checked").containsExactly(true, true, true);

    }

    @Test
    public void ROLE_RESOURCE_할당_서비스_테스트() throws Exception {
        // given
        List<Long> pageIds = new ArrayList<>();
        List<Long> buttonIds = new ArrayList<>();
        List<Long> tabIds = new ArrayList<>();

        List<Page> pages = pageRepository.findAll();

        for (Page page : pages) {
            pageIds.add(page.getId());

            List<Button> buttons = buttonRepository.findAllByPage(page);
            for (Button button : buttons) {
                buttonIds.add(button.getId());
            }

            List<Tab> tabs = tabRepository.findAllByPage(page);
            for (Tab tab : tabs) {
                tabIds.add(tab.getId());
            }
        }

        pageIds.remove(pageIds.size() - 1);
        buttonIds.remove(buttonIds.size() - 1);
        tabIds.remove(tabIds.size() - 1);


        Role userRole = roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("사용자 Role"))
                .findFirst().get();

        Page page1 = pageRepository.findAll()
                .stream()
                .filter(page -> page.getName().equals("페이지1"))
                .findFirst().get();

        RoleResourceDto.SearchPage searchPage = new RoleResourceDto.SearchPage();
        searchPage.setRoleId(userRole.getId());

        RoleResourceDto.Search search = new RoleResourceDto.Search();
        search.setRoleId(userRole.getId());
        search.setPageId(page1.getId());

        RoleResourceDto.Allocate allocate = new RoleResourceDto.Allocate();
        allocate.setPages(pageIds);
        allocate.setButtons(buttonIds);
        allocate.setTabs(tabIds);
        allocate.setRoleId(userRole.getId());

        // when
        CommonResponse response = roleResourceService.allocate(allocate);
        List<RoleResourceDto.ResultPage> resultPages = roleResourceService.selectPage(searchPage);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(resultPages.size()).isEqualTo(3);
        assertThat(resultPages).extracting("checked").containsExactly(true, true, false);
    }

    @Test
    public void ROLE_RESOURCE_잘못된_할당_테스트() throws Exception {
        // given
        Role devRole = roleRepository.findAll()
                .stream()
                .filter(role -> role.getName().equals("개발자 Role"))
                .findFirst().get();

        Page page = pageRepository.findAll().get(0);

        Button button = new Button("버튼1", "버튼1", EnableStatus.ENABLE, "BUTTON_TEST", "/api/test", HttpMethod.GET, page, "");
        Label label = new Label("라벨1", "라벨1", EnableStatus.ENABLE, "LABEL_TEST", page);

        // when
        RoleResource roleResource = new RoleResource(devRole, button);
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> new RoleResource(devRole, label));

        // then
        assertThat(e.getMessage()).isEqualTo("Role-Resource 에는 MenuCategory, Menu, Page 또는 ResourceAuthority 만이 할당될 수 있습니다.");
    }
}