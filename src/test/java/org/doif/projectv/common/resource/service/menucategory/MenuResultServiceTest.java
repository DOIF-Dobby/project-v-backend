package org.doif.projectv.common.resource.service.menucategory;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.entity.Label;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpMethod;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class MenuCategoryServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MenuCategoryService menuCategoryService;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    @BeforeEach
    public void init() {
        MenuCategory parent = new MenuCategory("부모", "부모", EnableStatus.ENABLE, 1, "heart", null);
        MenuCategory child = new MenuCategory("자식", "자식", EnableStatus.ENABLE, 1, "heart", parent);

        em.persist(parent);
        em.persist(child);
    }

    @Test
    public void MenuCategory_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<MenuCategoryDto.Result> results = menuCategoryService.select();

        // then
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(0).getName()).isEqualTo("부모");
        assertThat(results.get(1).getName()).isEqualTo("자식");
        assertThat(results.get(1).getParentId()).isEqualTo(results.get(0).getResourceId());
    }

    @Test
    public void MenuCategory_추가_서비스_테스트() throws Exception {
        // given
        Long parentId = menuCategoryRepository.findAll().get(0).getId();

        MenuCategoryDto.Insert insert = new MenuCategoryDto.Insert();
        insert.setName("자식2");
        insert.setDescription("자식2");
        insert.setIcon("edit");
        insert.setSort(2);
        insert.setStatus(EnableStatus.ENABLE);
        insert.setParentId(parentId);

        // when
        CommonResponse response = menuCategoryService.insert(insert);
        List<MenuCategoryDto.Result> results = menuCategoryService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(3);
        assertThat(results).extracting("name").containsExactly("부모", "자식", "자식2");
        assertThat(results.get(2).getParentId()).isEqualTo(results.get(0).getResourceId());
    }

    @Test
    public void MenuCategory_수정_서비스_테스트() throws Exception {
        // given
        Long menuCategoryId = menuCategoryRepository.findAll().get(1).getId();

        MenuCategoryDto.Update update = new MenuCategoryDto.Update();
        update.setName("호로자식");
        update.setDescription("호로자식");
        update.setIcon("edit");
        update.setSort(1);
        update.setStatus(EnableStatus.DISABLE);

        // when
        CommonResponse response = menuCategoryService.update(menuCategoryId, update);
        List<MenuCategoryDto.Result> results = menuCategoryService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(2);
        assertThat(results.get(1).getName()).isEqualTo("호로자식");
    }

    @Test
    public void MenuCategory_삭제_서비스_테스트() throws Exception {
        // given
        Long menuCategoryId = menuCategoryRepository.findAll().get(1).getId();

        // when
        CommonResponse response = menuCategoryService.delete(menuCategoryId);
        List<MenuCategoryDto.Result> results = menuCategoryService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(1);
        assertThat(results.get(0).getName()).isEqualTo("부모");
    }

    @Test
    public void MenuCategory_자식이_있는_부모_삭제시_Exception_테스트() throws Exception {
        // given
        Long menuCategoryId = menuCategoryRepository.findAll().get(0).getId();

        // when
        assertThrows(DataIntegrityViolationException.class, () -> {
            menuCategoryService.delete(menuCategoryId);
            menuCategoryService.select();
        });
    }
}