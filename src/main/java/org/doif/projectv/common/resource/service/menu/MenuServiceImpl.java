package org.doif.projectv.common.resource.service.menu;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.repository.menu.MenuRepository;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MenuDto.Result> select() {
        List<MenuCategory> menuCategories = menuCategoryRepository.findAll();
        List<Menu> menus = menuRepository.findAll();

        List<MenuDto.Result> categoryResults = menuCategories.stream()
                .map(menuCategory -> {
                    MenuDto.Result result = new MenuDto.Result();
                    result.setResourceId(menuCategory.getId());
                    result.setName(menuCategory.getName());
                    result.setDescription(menuCategory.getDescription());
                    result.setSort(menuCategory.getSort());
                    result.setIcon(menuCategory.getIcon());
                    result.setStatus(menuCategory.getStatus());
                    result.setStatusName(menuCategory.getStatus().getMessage());

                    result.setDepthAndPath(menuCategory);
                    result.setType(MenuType.CATEGORY);
                    result.setTypeName(MenuType.CATEGORY.getMessage());
                    result.setPaddingName(menuCategory.getName());

                    return result;
                })
                .collect(Collectors.toList());

        List<MenuDto.Result> menuResults = menus.stream()
                .map(menu -> {
                    MenuDto.Result result = new MenuDto.Result();
                    result.setResourceId(menu.getId());
                    result.setName(menu.getName());
                    result.setDescription(menu.getDescription());
                    result.setSort(menu.getSort());
                    result.setIcon(menu.getIcon());
                    result.setStatus(menu.getStatus());
                    result.setStatusName(menu.getStatus().getMessage());
                    result.setUrl(menu.getUrl());

                    result.setDepthAndPath(menu);
                    result.setType(MenuType.MENU);
                    result.setTypeName(MenuType.MENU.getMessage());
                    result.setPaddingName(menu.getName());

                    return result;
                })
                .collect(Collectors.toList());

        categoryResults.addAll(menuResults);

        return categoryResults.stream()
                .sorted(Comparator.comparing(MenuDto.Result::getPath))
                .collect(Collectors.toList());
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
