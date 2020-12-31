package org.doif.projectv.common.resource.service.page;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.entity.Page;
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
class PageServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    PageService pageService;

    @BeforeEach
    public void init() {
        Page page = new Page("이슈 관리 페이지", "이슈 관리 페이지 입니다.", EnableStatus.ENABLE, "PAGE_1", "/api/pages/issue");

        em.persist(page);
    }

    @Test
    public void Page_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<PageDto.Result> results = pageService.select();

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getUrl()).isEqualTo("/api/pages/issue");
    }

    @Test
    public void Page_추가_서비스_테스트() throws Exception {
        // given
        PageDto.Insert insert = new PageDto.Insert();
        insert.setName("작업 관리 페이지");
        insert.setDescription("작업 관리 페이지 입니다.");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/api/pages/task");
        insert.setCode("PAGE_2");

        // when
        CommonResponse response = pageService.insert(insert);
        List<PageDto.Result> results = pageService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("이슈 관리 페이지", "작업 관리 페이지");
    }

    @Test
    public void Page_수정_서비스_테스트() throws Exception {
        // given
        PageDto.Update update = new PageDto.Update();
        update.setName("작업 관리 페이지");
        update.setDescription("작업 관리 페이지 입니다.");
        update.setStatus(EnableStatus.DISABLE);
        update.setUrl("/api/pages/task");

        Long resourceId = pageService.select().get(0).getResourceId();

        // when
        CommonResponse response = pageService.update(resourceId, update);
        List<PageDto.Result> results = pageService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getStatusName()).isEqualTo(EnableStatus.DISABLE.getMessage());
    }

    @Test
    public void Page_삭제_서비스_테스트() throws Exception {
        // given
        Long resourceId = pageService.select().get(0).getResourceId();

        // when
        CommonResponse response = pageService.delete(resourceId);
        List<PageDto.Result> results = pageService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }

    @Test
    public void Page_추가시_URL_체크_테스트() throws Exception {
        // given
        PageDto.Insert insert = new PageDto.Insert();
        insert.setName("작업 관리 페이지");
        insert.setDescription("작업 관리 페이지 입니다.");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/pages/task");
        insert.setCode("PAGE_2");

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> pageService.insert(insert));

        // then
        assertThat(e.getMessage()).isEqualTo("페이지 리소스의 url은 /api/pages/ 로 시작해야 합니다.");
    }

    @Test
    public void Page_수정시_URL_체크_테스트() throws Exception {
        // given
        PageDto.Update update = new PageDto.Update();
        update.setName("작업 관리 페이지");
        update.setDescription("작업 관리 페이지 입니다.");
        update.setStatus(EnableStatus.DISABLE);
        update.setUrl("/pages/task");

        Long resourceId = pageService.select().get(0).getResourceId();

        // when
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> pageService.update(resourceId, update));

        // then
        assertThat(e.getMessage()).isEqualTo("페이지 리소스의 url은 /api/pages/ 로 시작해야 합니다.");

    }

}