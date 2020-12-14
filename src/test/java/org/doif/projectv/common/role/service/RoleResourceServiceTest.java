package org.doif.projectv.common.role.service;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

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
        Role role = new Role("개발자 Role", "개발자 롤입니다.", EnableStatus.ENABLE);
        em.persist(role);

        for (int i = 0; i < 10; i++) {
            Page page = new Page("페이지" + i, "", EnableStatus.ENABLE, "/api/page" + i, HttpMethod.GET);
            em.persist(page);

            for (int j = 0; j < 2; j++) {
                Button button = new Button("버튼" + j, "", EnableStatus.ENABLE, "/api/button" + j, HttpMethod.GET, page, "");
                em.persist(button);
            }

            for (int j = 0; j < 1; j++) {
                Tab tab = new Tab("탭" + j, "", EnableStatus.ENABLE, "/api/tab" + j, HttpMethod.GET, page, "TABGROUP" + j, i + 1);
                em.persist(tab);
            }
        }
    }

    @Test
    public void ROLE_RESOURCE_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<RoleResourceDto.ResultPage> resultPages = roleResourceService.selectPage();
        Long pageId = resultPages.get(0).getPageId();
        List<RoleResourceDto.ResultButton> resultButtons = roleResourceService.selectButtonByPageId(pageId);
        List<RoleResourceDto.ResultTab> resultTabs = roleResourceService.selectTabByPageId(pageId);

        // then
        assertThat(resultPages.size()).isEqualTo(10);
        assertThat(resultButtons.size()).isEqualTo(2);
        assertThat(resultTabs.size()).isEqualTo(1);
    }

    @Test
    public void ROLE_RESOURCE_할당_서비스_테스트() throws Exception {
        // given
        List<RoleResourceDto.ResultPage> resultPages = new ArrayList<>();
        List<RoleResourceDto.ResultButton> resultButtons = new ArrayList<>();
        List<RoleResourceDto.ResultTab> resultTabs = new ArrayList<>();

        List<Page> pages = pageRepository.findAll();

        for (Page page : pages) {
            RoleResourceDto.ResultPage resultPage = new RoleResourceDto.ResultPage(
                    page.getId(),
                    page.getName(),
                    page.getStatus()
            );
            resultPages.add(resultPage);

            List<Button> buttons = buttonRepository.findAllByPage(page);
            for (Button button : buttons) {
                RoleResourceDto.ResultButton resultButton = new RoleResourceDto.ResultButton(
                        button.getId(),
                        page.getId(),
                        button.getName(),
                        button.getDescription(),
                        button.getStatus()
                );
                resultButtons.add(resultButton);
            }

            List<Tab> tabs = tabRepository.findAllByPage(page);
            for (Tab tab : tabs) {
                RoleResourceDto.ResultTab resultTab = new RoleResourceDto.ResultTab(
                        tab.getId(),
                        page.getId(),
                        tab.getName(),
                        tab.getDescription(),
                        tab.getStatus()
                );
                resultTabs.add(resultTab);
            }
        }

        Long roleId = roleRepository.findAll().get(0).getId();

        RoleResourceDto.Allocate allocate = new RoleResourceDto.Allocate();
        allocate.setPages(resultPages);
        allocate.setButtons(resultButtons);
        allocate.setTabs(resultTabs);
        allocate.setRoleId(roleId);

        // when
        CommonResponse response = roleResourceService.allocate(allocate);
        List<RoleResourceDto.ResultPage> resultPages1 = roleResourceService.selectPage();

        // then
        for (RoleResourceDto.ResultPage resultPage : resultPages1) {
            System.out.println("resultPage = " + resultPage);
        }
    }
}