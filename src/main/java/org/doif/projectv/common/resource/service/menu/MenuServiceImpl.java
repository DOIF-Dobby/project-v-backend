package org.doif.projectv.common.resource.service.menu;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.repository.menu.MenuRepository;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.resource.util.ResourceUtil;
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
                    MenuDto.Result result = new MenuDto.Result(
                            menuCategory.getId(),
                            menuCategory.getName(),
                            menuCategory.getDescription(),
                            menuCategory.getStatus(),
                            menuCategory.getCode(),
                            menuCategory.getParent() != null ? menuCategory.getParent().getId() : null,
                            menuCategory.getSort(),
                            MenuType.CATEGORY,
                            menuCategory.getIcon(),
                            null
                    );

                    result.setDepthAndPath(menuCategory);
                    result.setPaddingName(menuCategory.getName());

                    return result;
                })
                .collect(Collectors.toList());

        List<MenuDto.Result> menuResults = menus.stream()
                .map(menu -> {
                    MenuDto.Result result = new MenuDto.Result(
                            menu.getId(),
                            menu.getName(),
                            menu.getDescription(),
                            menu.getStatus(),
                            menu.getCode(),
                            menu.getMenuCategory().getId(),
                            menu.getSort(),
                            MenuType.MENU,
                            menu.getIcon(),
                            menu.getUrl()
                    );

                    result.setDepthAndPath(menu);
                    result.setPaddingName(menu.getName());

                    return result;
                })
                .collect(Collectors.toList());

        categoryResults.addAll(menuResults);

        List<MenuDto.Result> collect = categoryResults.stream()
                .sorted(Comparator.comparing(MenuDto.Result::getPath))
                .collect(Collectors.toList());

        List<MenuDto.Result> subRowsList2 = ResourceUtil.getSubRowsList(collect);

        return subRowsList2;
    }

    @Override
    public CommonResponse insert(MenuDto.Insert dto) {
        Optional<MenuCategory> optionalMenuCategory = menuCategoryRepository.findById(dto.getMenuCategoryId());
        MenuCategory menuCategory = optionalMenuCategory.orElseThrow(() -> new IllegalArgumentException("카테고리를 찾을 수 없음"));
        Menu menu = new Menu(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getCode(), menuCategory, dto.getSort(), dto.getUrl(), dto.getIcon());
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
