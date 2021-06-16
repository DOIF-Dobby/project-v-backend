package org.doif.projectv.common.role.service;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.entity.*;
import org.doif.projectv.common.resource.repository.button.ButtonRepository;
import org.doif.projectv.common.resource.repository.menu.MenuRepository;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.resource.repository.page.PageRepository;
import org.doif.projectv.common.resource.repository.tab.TabRepository;
import org.doif.projectv.common.resource.util.ResourceUtil;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.role.entity.Role;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.role.repository.RoleRepository;
import org.doif.projectv.common.role.repository.RoleResourceRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RoleResourceServiceImpl implements RoleResourceService {

    private final RoleResourceRepository roleResourceRepository;
    private final RoleRepository roleRepository;
    private final PageRepository pageRepository;
    private final ButtonRepository buttonRepository;
    private final TabRepository tabRepository;
    private final MenuRepository menuRepository;
    private final MenuCategoryRepository menuCategoryRepository;

    @Override
    public List<RoleResourceDto.ResultPage> selectPage(RoleResourceDto.SearchPage search) {
        List<RoleResourceDto.ResultPage> resultPages = roleResourceRepository.selectPage(search);
        for (RoleResourceDto.ResultPage resultPage : resultPages) {
            List<RoleResourceDto.ResultButton> resultButtons = roleResourceRepository.selectButton(new RoleResourceDto.Search(search.getRoleId(), resultPage.getPageId()));
            List<RoleResourceDto.ResultTab> resultTabs = roleResourceRepository.selectTab(new RoleResourceDto.Search(search.getRoleId(), resultPage.getPageId()));
            resultPage.setButtons(resultButtons);
            resultPage.setTabs(resultTabs);
        }

        return resultPages;
    }

    @Override
    public List<RoleResourceDto.ResultMenu> selectMenu(RoleResourceDto.SearchMenu search) {
        List<MenuCategory> menuCategories = menuCategoryRepository.findAll();
        List<Menu> menus = menuRepository.findAll();
        List<RoleResource> menuRoleResources = roleResourceRepository.selectMenuRoleResources(search.getRoleId());
        List<RoleResource> menuCategoryRoleResources = roleResourceRepository.selectMenuCategoryRoleResources(search.getRoleId());

        List<RoleResourceDto.ResultMenu> categoryResults = menuCategories.stream()
                .map(menuCategory -> {
                    boolean checked = menuCategoryRoleResources.stream()
                            .anyMatch(roleResource -> roleResource.getResource().getId().equals(menuCategory.getId()));

                    RoleResourceDto.ResultMenu result = new RoleResourceDto.ResultMenu(
                            menuCategory.getId(),
                            menuCategory.getParent() != null ? menuCategory.getParent().getId() : null,
                            menuCategory.getName(),
                            menuCategory.getDescription(),
                            menuCategory.getStatus(),
                            MenuType.CATEGORY,
                            menuCategory.getSort(),
                            checked
                    );

                    result.setDepthAndPath(menuCategory);
                    return result;
                })
                .collect(Collectors.toList());

        List<RoleResourceDto.ResultMenu> menuResults = menus.stream()
                .map(menu -> {
                    boolean checked = menuRoleResources.stream()
                            .anyMatch(roleResource -> roleResource.getResource().getId().equals(menu.getId()));

                    RoleResourceDto.ResultMenu result = new RoleResourceDto.ResultMenu(
                            menu.getId(),
                            menu.getMenuCategory().getId(),
                            menu.getName(),
                            menu.getDescription(),
                            menu.getStatus(),
                            MenuType.MENU,
                            menu.getSort(),
                            checked
                    );

                    result.setDepthAndPath(menu);
                    return result;
                })
                .collect(Collectors.toList());

        categoryResults.addAll(menuResults);

        List<RoleResourceDto.ResultMenu> collect = categoryResults.stream()
                .sorted(Comparator.comparing(RoleResourceDto.ResultMenu::getPath))
                .collect(Collectors.toList());


        return ResourceUtil.getRoleResourceMenuSubRowsList(collect);
    }

    @Transactional
    @Override
    public CommonResponse allocate(RoleResourceDto.Allocate dto) {
        Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
        Role role = optionalRole.orElseThrow(() -> new IllegalArgumentException("Role를 찾을 수 없음"));

        // TODO: deleteBy ... 했는데 select문이 됨 좀 더 찾아볼까?
        roleResourceRepository.deleteByRoleId(role.getId());

        // TODO: roleResource를 한 방에 지우고 다시 Insert 하는 것이 맞는가? 맞다면 아래처럼 findById로 찾는 것이 아니라, insert into select로 찾아야 하는 것이 아닐까?
        // 일단 쓰다가 성능상 이슈가 발생하면 한 번 고민해보자

        dto.getPages()
                .forEach(pageId -> {
                    Optional<Page> optionalPage = pageRepository.findById(pageId);
                    Page page = optionalPage.orElseThrow(() -> new IllegalArgumentException("페이지를 찾을 수 없음"));
                    RoleResource roleResource = new RoleResource(role, page);
                    roleResourceRepository.save(roleResource);
                });

        dto.getButtons()
                .forEach(buttonId -> {
                    Optional<Button> optionalButton = buttonRepository.findById(buttonId);
                    Button button = optionalButton.orElseThrow(() -> new IllegalArgumentException("버튼을 찾을 수 없음"));
                    RoleResource roleResource = new RoleResource(role, button);
                    roleResourceRepository.save(roleResource);
                });

        dto.getTabs()
                .forEach(tabId -> {
                    Optional<Tab> optionalTab = tabRepository.findById(tabId);
                    Tab tab = optionalTab.orElseThrow(() -> new IllegalArgumentException("탭을 찾을 수 없음"));
                    RoleResource roleResource = new RoleResource(role, tab);
                    roleResourceRepository.save(roleResource);
                });

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse allocateMenu(RoleResourceDto.AllocateMenu dto) {
        Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
        Role role = optionalRole.orElseThrow(() -> new IllegalArgumentException("Role를 찾을 수 없음"));
        roleResourceRepository.deleteMenuByRoleId(role.getId());

        dto.getMenus()
                .forEach(menuId -> {
                    Optional<Menu> optionalMenu = menuRepository.findById(menuId);
                    if(optionalMenu.isPresent()) {
                        Menu menu = optionalMenu.get();
                        RoleResource roleResource = new RoleResource(role, menu);
                        roleResourceRepository.save(roleResource);
                    } else{
                        Optional<MenuCategory> optionalMenuCategory = menuCategoryRepository.findById(menuId);
                        MenuCategory menuCategory = optionalMenuCategory.orElseThrow(() -> new IllegalArgumentException("메뉴 혹은 메뉴 카테고리를 찾을 수 없음"));
                        RoleResource roleResource = new RoleResource(role, menuCategory);
                        roleResourceRepository.save(roleResource);
                    }
                });

        return ResponseUtil.ok();
    }

}
