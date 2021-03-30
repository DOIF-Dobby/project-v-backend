package org.doif.projectv.common.resource.service.tab;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Tab;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
import org.doif.projectv.common.resource.service.button.ButtonService;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class TabServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    TabService tabService;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    TabRepository tabRepository;

    @BeforeEach
    public void init() {
        Page page = new Page("이슈 관리 페이지", "이슈 관리 페이지 입니다.", EnableStatus.ENABLE, "PAGE_1", "/api/pages/issue");
        Tab tab = new Tab("탭탭", "탭입니다.", EnableStatus.ENABLE, "TAB_1", "/api/tabs/tab1", HttpMethod.GET, page, "TAB_GROUP1", 1);

        em.persist(page);
        em.persist(tab);
    }
    
    @Test
    public void Tab_조회_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        // when
        List<TabDto.Result> results = tabService.selectByPage(pageId);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getDescription()).isEqualTo("탭입니다.");
    }

    @Test
    public void Tab_추가_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        TabDto.Insert insert = new TabDto.Insert();
        insert.setPageId(pageId);
        insert.setName("탭탭탭");
        insert.setDescription("탭입니다요");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/api/tabs/tab2");
        insert.setHttpMethod(HttpMethod.GET);
        insert.setTabGroup("TAB_GROUP1");
        insert.setSort(2);
        insert.setCode("TAB_2");

        // when
        CommonResponse response = tabService.insert(insert);
        List<TabDto.Result> results = tabService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("탭탭", "탭탭탭");
    }

    @Test
    public void Tab_수정_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        Long tabId = tabRepository.findAll().get(0).getId();

        TabDto.Update update = new TabDto.Update();
        update.setName("탭");
        update.setDescription("태태태탭");
        update.setStatus(EnableStatus.ENABLE);
        update.setUrl("/api/tabs/tab2");
        update.setHttpMethod(HttpMethod.GET);
        update.setTabGroup("TAB_GROUP1");
        update.setSort(1);

        // when
        CommonResponse response = tabService.update(tabId, update);
        List<TabDto.Result> results = tabService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("탭");
    }

    @Test
    public void Tab_삭제_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        Long tabId = tabRepository.findAll().get(0).getId();

        // when
        CommonResponse response = tabService.delete(tabId);
        List<TabDto.Result> results = tabService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

    @Test
    public void Tab_추가시_URL_체크_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        TabDto.Insert insert = new TabDto.Insert();
        insert.setPageId(pageId);
        insert.setName("탭탭탭");
        insert.setDescription("탭입니다요");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/tabs/tab2");
        insert.setHttpMethod(HttpMethod.GET);
        insert.setTabGroup("TAB_GROUP1");
        insert.setSort(2);
        insert.setCode("TAB_2");

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> tabService.insert(insert));

        // then
        assertThat(e.getMessage()).isEqualTo("권한 리소스의 url은 /api/ 로 시작해야 합니다.");
    }

    @Test
    public void Tab_수정시_URL_체크_테스트() throws Exception {
        // given
        Long tabId = tabRepository.findAll().get(0).getId();

        TabDto.Update update = new TabDto.Update();
        update.setName("탭");
        update.setDescription("태태태탭");
        update.setStatus(EnableStatus.ENABLE);
        update.setUrl("/tabs/tab2");
        update.setHttpMethod(HttpMethod.GET);
        update.setTabGroup("TAB_GROUP1");
        update.setSort(1);

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> tabService.update(tabId, update));

        // then
        assertThat(e.getMessage()).isEqualTo("권한 리소스의 url은 /api/ 로 시작해야 합니다.");
    }
}