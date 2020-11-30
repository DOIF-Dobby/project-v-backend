package org.doif.projectv.common.resource.service.button;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.dto.ButtonDto;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.service.page.PageService;
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
class ButtonServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    ButtonService buttonService;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    ButtonRepository buttonRepository;

    @BeforeEach
    public void init() {
        Page page = new Page("이슈 관리 페이지", "이슈 관리 페이지 입니다.", EnableStatus.ENABLE, "/api/pages/issue", HttpMethod.GET);
        Button button = new Button("조회", "이슈 조회 버튼", EnableStatus.ENABLE, "/api/issue", HttpMethod.GET, page, "heart");

        em.persist(page);
        em.persist(button);
    }

    @Test
    public void Button_조회_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        // when
        List<ButtonDto.Result> results = buttonService.selectByPage(pageId);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getDescription()).isEqualTo("이슈 조회 버튼");
    }

    @Test
    public void Button_추가_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        ButtonDto.Insert insert = new ButtonDto.Insert();
        insert.setPageId(pageId);
        insert.setName("추가");
        insert.setDescription("이슈 추가 버튼");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/api/issue");
        insert.setHttpMethod(HttpMethod.POST);
        insert.setIcon("add");

        // when
        CommonResponse response = buttonService.insert(insert);
        List<ButtonDto.Result> results = buttonService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("조회", "추가");
    }

    @Test
    public void Button_수정_서비스_테스트() throws Exception {
        // given
        Long buttonId = buttonRepository.findAll().get(0).getId();
        Long pageId = pageRepository.findAll().get(0).getId();

        ButtonDto.Update update = new ButtonDto.Update();
        update.setName("이슈 조회");
        update.setDescription("[이슈 관리] 이슈 조회 버튼");
        update.setStatus(EnableStatus.ENABLE);
        update.setUrl("/api/issue");
        update.setHttpMethod(HttpMethod.GET);
        update.setIcon("search");

        // when
        CommonResponse response = buttonService.update(buttonId, update);
        List<ButtonDto.Result> results = buttonService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("이슈 조회");
    }

    @Test
    public void Button_삭제_서비스_테스트() throws Exception {
        // given
        Long buttonId = buttonRepository.findAll().get(0).getId();
        Long pageId = pageRepository.findAll().get(0).getId();

        // when
        CommonResponse response = buttonService.delete(buttonId);
        List<ButtonDto.Result> results = buttonService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

    @Test
    public void Button_추가시_URL_체크_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        ButtonDto.Insert insert = new ButtonDto.Insert();
        insert.setPageId(pageId);
        insert.setName("추가");
        insert.setDescription("이슈 추가 버튼");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/issue");
        insert.setHttpMethod(HttpMethod.POST);
        insert.setIcon("add");

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> buttonService.insert(insert));

        // then
        assertThat(e.getMessage()).isEqualTo("권한 리소스의 url은 /api/ 로 시작해야 합니다.");
    }

    @Test
    public void Button_수정시_URL_체크_테스트() throws Exception {
        // given
        Long buttonId = buttonRepository.findAll().get(0).getId();
        Long pageId = pageRepository.findAll().get(0).getId();

        ButtonDto.Update update = new ButtonDto.Update();
        update.setName("이슈 조회");
        update.setDescription("[이슈 관리] 이슈 조회 버튼");
        update.setStatus(EnableStatus.ENABLE);
        update.setUrl("/issue");
        update.setHttpMethod(HttpMethod.GET);
        update.setIcon("search");

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> buttonService.update(buttonId, update));

        // then
        assertThat(e.getMessage()).isEqualTo("권한 리소스의 url은 /api/ 로 시작해야 합니다.");
    }
}