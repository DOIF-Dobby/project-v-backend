package org.doif.projectv.common.resource.service.menu;

import org.assertj.core.api.Assertions;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.repository.menu.MenuRepository;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseCode;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class MenuServiceTest {

    @Autowired
    EntityManager em;

    @Autowired
    MenuService menuService;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    MenuCategoryRepository menuCategoryRepository;

    @BeforeEach
    public void init() {
        MenuCategory menuCategory1 = new MenuCategory("메뉴-카테고리 1", "메뉴-카테고리 1", EnableStatus.ENABLE, "MENU_CATEGORY_1", 1, "", null);
        MenuCategory menuCategory1_1 = new MenuCategory("메뉴-카테고리 1-1", "메뉴-카테고리 1-1", EnableStatus.ENABLE, "MENU_CATEGORY_1_1", 1, "", menuCategory1);
        MenuCategory menuCategory1_1_1 = new MenuCategory("메뉴-카테고리 1-1-1", "메뉴-카테고리 1-1-1", EnableStatus.ENABLE, "MENU_CATEGORY_1_1_1", 1, "", menuCategory1_1);
        MenuCategory menuCategory1_1_2 = new MenuCategory("메뉴-카테고리 1-1-2", "메뉴-카테고리 1-1-2", EnableStatus.ENABLE, "MENU_CATEGORY_1_1_2", 2, "", menuCategory1_1);

        MenuCategory menuCategory2 = new MenuCategory("메뉴-카테고리 2", "메뉴-카테고리 2", EnableStatus.ENABLE, "MENU_CATEGORY_2", 2, "", null);
        MenuCategory menuCategory2_1 = new MenuCategory("메뉴-카테고리 2-1", "메뉴-카테고리 2-1", EnableStatus.ENABLE, "MENU_CATEGORY_2_1", 1, "", menuCategory2);
        MenuCategory menuCategory2_1_1 = new MenuCategory("메뉴-카테고리 2-1-1", "메뉴-카테고리 2-1-1", EnableStatus.ENABLE, "MENU_CATEGORY_2_1_1", 1, "", menuCategory2_1);
        MenuCategory menuCategory2_1_2 = new MenuCategory("메뉴-카테고리 2-1-2", "메뉴-카테고리 2-1-2", EnableStatus.ENABLE, "MENU_CATEGORY_2_1_2",2, "", menuCategory2_1);

        MenuCategory menuCategory2_2 = new MenuCategory("메뉴-카테고리 2-2", "메뉴-카테고리 2-2", EnableStatus.ENABLE, "MENU_CATEGORY_2_2", 2, "", menuCategory2);
        MenuCategory menuCategory2_2_1 = new MenuCategory("메뉴-카테고리 2-2-1", "메뉴-카테고리 2-2-1", EnableStatus.ENABLE, "MENU_CATEGORY_2_2_1", 1, "", menuCategory2_2);
        MenuCategory menuCategory2_2_2 = new MenuCategory("메뉴-카테고리 2-2-2", "메뉴-카테고리 2-2-2", EnableStatus.ENABLE, "MENU_CATEGORY_2_2_2", 2, "", menuCategory2_2);

        MenuCategory menuCategory3 = new MenuCategory("메뉴-카테고리 3", "메뉴-카테고리 3", EnableStatus.ENABLE, "MENU_CATEGORY_3", 3, "", null);
        MenuCategory menuCategory3_1 = new MenuCategory("메뉴-카테고리 3-1", "메뉴-카테고리 3-1", EnableStatus.ENABLE, "MENU_CATEGORY_3_1", 1, "", menuCategory3);
        MenuCategory menuCategory3_1_1 = new MenuCategory("메뉴-카테고리 3-1-1", "메뉴-카테고리 3-1-1", EnableStatus.ENABLE, "MENU_CATEGORY_3_3_1", 1, "", menuCategory3_1);

        Menu menu1 = new Menu("메뉴 1", "메뉴 1", EnableStatus.ENABLE, "MENU_1", menuCategory1_1_1, 1, "", "");
        Menu menu2 = new Menu("메뉴 2", "메뉴 2", EnableStatus.ENABLE, "MENU_2", menuCategory1_1_2, 1, "", "");
        Menu menu3 = new Menu("메뉴 3", "메뉴 3", EnableStatus.ENABLE, "MENU_3", menuCategory2_1_1, 1, "", "");
        Menu menu4 = new Menu("메뉴 4", "메뉴 4", EnableStatus.ENABLE, "MENU_4", menuCategory2_1_1, 2, "", "");
        Menu menu5 = new Menu("메뉴 5", "메뉴 5", EnableStatus.ENABLE, "MENU_5", menuCategory2_1_2, 1, "", "");
        Menu menu6 = new Menu("메뉴 6", "메뉴 6", EnableStatus.ENABLE, "MENU_6", menuCategory2_2_1, 1, "", "");
        Menu menu7 = new Menu("메뉴 7", "메뉴 7", EnableStatus.ENABLE, "MENU_7", menuCategory2_2_2, 1, "", "");

        em.persist(menuCategory3);
        em.persist(menuCategory3_1);
        em.persist(menuCategory3_1_1);

        em.persist(menuCategory1);
        em.persist(menuCategory1_1);
        em.persist(menuCategory1_1_1);
        em.persist(menuCategory1_1_2);

        em.persist(menuCategory2);
        em.persist(menuCategory2_1);
        em.persist(menuCategory2_1_1);
        em.persist(menuCategory2_1_2);

        em.persist(menuCategory2_2);
        em.persist(menuCategory2_2_1);
        em.persist(menuCategory2_2_2);

        em.persist(menu1);
        em.persist(menu2);
        em.persist(menu3);
        em.persist(menu4);
        em.persist(menu5);
        em.persist(menu6);
        em.persist(menu7);
    }

    @Test
    public void Menu_조회_서비스_테스트() throws Exception {
        // given

        // when
        List<MenuDto.Result> results = menuService.select();

        // then
        Assertions.assertThat(results.size()).isEqualTo(3);
    }

    @Test
    public void Menu_추가_서비스_테스트() throws Exception {
        // given
        List<MenuDto.Result> menuCategories = menuService.select();
        MenuDto.Result menuCategory = menuCategories.get(menuCategories.size() - 1);

        MenuDto.Insert insert = new MenuDto.Insert();
        insert.setMenuCategoryId(menuCategory.getResourceId());
        insert.setIcon("heart");
        insert.setSort(1);
        insert.setUrl("/menu/1");
        insert.setName("메뉴메뉴1");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setCode("MENU_10");

        // when
        CommonResponse response = menuService.insert(insert);
        List<MenuDto.Result> results = menuService.select();

        // then
        MenuDto.Result menu = results.get(results.size() - 1);

        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(3);
//        assertThat(menu.getIcon()).isEqualTo("heart");
    }

    @Test
    public void Menu_수정_서비스_테스트() throws Exception {
        // given
        Menu menu = menuRepository.findAll().get(0);

        MenuDto.Update update = new MenuDto.Update();
        update.setIcon("heart");
        update.setSort(1);
        update.setUrl("/menu/1");
        update.setName("메뉴메뉴1");
        update.setStatus(EnableStatus.ENABLE);

        // when
        CommonResponse response = menuService.update(menu.getId(), update);
        Menu findMenu = menuRepository.findById(menu.getId()).get();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(findMenu.getIcon()).isEqualTo("heart");
    }

    @Test
    public void Menu_삭제_서비스_테스트() throws Exception {
        // given
        Long menuId = menuRepository.findAll().get(0).getId();

        // when
        CommonResponse response = menuService.delete(menuId);
        List<MenuDto.Result> results = menuService.select();

        // then
        assertThat(response.getCode()).isEqualTo(ResponseCode.OK.getCode());
        assertThat(results.size()).isEqualTo(3);
    }

}