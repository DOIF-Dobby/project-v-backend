package org.doif.projectv.common.resource.service.menucategory;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.resource.repository.menucategory.MenuCategoryRepository;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class MenuCategoryServiceImpl implements MenuCategoryService {

    private final MenuCategoryRepository menuCategoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<MenuCategoryDto.Result> select() {
        return menuCategoryRepository.findAll()
                .stream()
                .map(menuCategory -> new MenuCategoryDto.Result(
                        menuCategory.getId(),
                        menuCategory.getName(),
                        menuCategory.getDescription(),
                        menuCategory.getStatus(),
                        menuCategory.getParent() == null ? null : menuCategory.getParent().getId(),
                        menuCategory.getSort(),
                        menuCategory.getIcon()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public CommonResponse insert(MenuCategoryDto.Insert dto) {
        Optional<MenuCategory> optionalParent = menuCategoryRepository.findById(dto.getParentId());
        MenuCategory parent = optionalParent.orElseThrow(() -> new IllegalArgumentException("부모 메뉴카테고리를 찾을 수 없음"));
        MenuCategory menuCategory = new MenuCategory(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getSort(), dto.getIcon(), parent);
        menuCategoryRepository.save(menuCategory);

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse update(Long id, MenuCategoryDto.Update dto) {
        Optional<MenuCategory> optionalMenuCategory = menuCategoryRepository.findById(id);
        MenuCategory menuCategory = optionalMenuCategory.orElseThrow(() -> new IllegalArgumentException("메뉴카테고리를 찾을 수 없음"));
        menuCategory.changeMenuCategory(dto.getName(), dto.getDescription(), dto.getStatus(), dto.getSort(), dto.getIcon());

        return ResponseUtil.ok();
    }

    @Override
    public CommonResponse delete(Long id) {
        Optional<MenuCategory> optionalMenuCategory = menuCategoryRepository.findById(id);
        MenuCategory menuCategory = optionalMenuCategory.orElseThrow(() -> new IllegalArgumentException("메뉴카테고리를 찾을 수 없음"));
        menuCategoryRepository.delete(menuCategory);

        return ResponseUtil.ok();
    }
}
