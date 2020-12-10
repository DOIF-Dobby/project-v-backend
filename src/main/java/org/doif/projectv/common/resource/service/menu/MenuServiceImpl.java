package org.doif.projectv.common.resource.service.menu;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.repository.menu.MenuRepository;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public List<MenuDto.Result> select() {

        List<MenuDto.Result> list = menuRepository.findAllRecursiveCategoryAndMenu();

        for (MenuDto.Result result : list) {
            result.setStatusName(result.getStatus().getMessage());
        }

        return list;
    }

    @Override
    public CommonResponse insert(MenuDto.Insert dto) {
        Optional<MenuCategory> optionalMenuCategory = menuCategoryRepository.findById(dto.getMenuCategoryId());
        MenuCategory menuCategory = optionalMenuCategory.orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없음"));
        Menu menu = new Menu(dto.getName(), dto.getDescription(), dto.getStatus(), menuCategory, dto.getSort(), dto.getUrl(), dto.getIcon());
        menuRepository.save(menu);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, MenuDto.Update dto) {
        Optional<Menu> optionalMenu = menuRepository.findById(id);
        Menu menu = optionalMenu.orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없음"));
        menu.changeMenu(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getSort(), dto.getUrl(), dto.getIcon());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<Menu> optionalMenu = menuRepository.findById(id);
        Menu menu = optionalMenu.orElseThrow(() -> new IllegalArgumentException("메뉴를 찾을 수 없음"));
        menuRepository.delete(menu);

        return ResponseUtil.ok();
    }
}
