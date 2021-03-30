package org.doif.projectv.common.resource.service.label;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.entity.Button;
import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.label.LabelRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
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
class LabelServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    LabelService labelService;

    @Autowired
    PageRepository pageRepository;

    @Autowired
    LabelRepository labelRepository;

    @BeforeEach
    public void init() {
        Page page = new Page("이슈 관리 페이지", "이슈 관리 페이지 입니다.", EnableStatus.ENABLE, "PAGE_1", "/api/pages/issue");
        Label label = new Label("안녕하세요", "안녕하세요", EnableStatus.ENABLE, "LABEL_1", page);

        em.persist(page);
        em.persist(label);
    }
    
    @Test
    public void Label_조회_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        // when
        List<LabelDto.Result> results = labelService.selectByPage(pageId);

        // then
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("안녕하세요");
    }

    @Test
    public void Label_추가_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        LabelDto.Insert insert = new LabelDto.Insert();
        insert.setPageId(pageId);
        insert.setCode("LABEL_BYE");
        insert.setName("안녕히 계세요");
        insert.setDescription("안녕히 계세요");
        insert.setStatus(EnableStatus.ENABLE);

        // when
        CommonResponse response = labelService.insert(insert);
        List<LabelDto.Result> results = labelService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results).extracting("name").containsExactly("안녕하세요", "안녕히 계세요");
    }

    @Test
    public void Label_수정_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        Long labelId = labelRepository.findAll().get(0).getId();

        LabelDto.Update update = new LabelDto.Update();
        update.setName("안녕히 계시렵니까");
        update.setDescription("호호호");
        update.setStatus(EnableStatus.ENABLE);

        // when
        CommonResponse response = labelService.update(labelId, update);
        List<LabelDto.Result> results = labelService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("안녕히 계시렵니까");
    }

    @Test
    public void Label_삭제_서비스_테스트() throws Exception {
        // given
        Long pageId = pageRepository.findAll().get(0).getId();

        Long labelId = labelRepository.findAll().get(0).getId();

        // when
        CommonResponse response = labelService.delete(labelId);
        List<LabelDto.Result> results = labelService.selectByPage(pageId);

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results).isEmpty();
    }
}