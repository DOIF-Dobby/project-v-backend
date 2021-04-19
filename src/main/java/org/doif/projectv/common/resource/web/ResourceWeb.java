package org.doif.projectv.common.resource.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.service.ResourceService;
import org.doif.projectv.common.resource.service.menu.MenuService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceWeb {

    private final ResourceService resourceService;
    private final MenuService menuService;

    @GetMapping("/api/pages/main")
    public ResponseEntity<MenuDto.Response> selectMain(HttpServletRequest request) {
        List<MenuDto.Result> result = menuService.select();
        List<MenuDto.Result> hierarchicalList = ResourceWeb.getHierarchicalList(result);

        MenuDto.Response response = new MenuDto.Response(hierarchicalList);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/pages/**")
    public ResponseEntity<PageDto.Child> select(HttpServletRequest request) {
        PageDto.Child child = resourceService.searchPageChildResource(request.getRequestURI());

        return ResponseEntity.ok(child);
    }

    public static List<MenuDto.Result> getHierarchicalList(final List<MenuDto.Result> originalList) {
        final List<MenuDto.Result> copyList = new ArrayList<>(originalList);

        copyList.forEach(element -> {
            originalList
                    .stream()
                    .filter(parent -> parent.getResourceId().equals(element.getParentId()))
                    .findAny()
                    .ifPresent(parent -> {
                        if (parent.getChildrenItems() == null) {
                            parent.setChildrenItems(new ArrayList<>());
                        }
                        parent.getChildrenItems().add(element);
                        /* originalList.remove(element); don't remove the content before completing the recursive */
                    });
        });
        originalList.subList(1, originalList.size()).clear();
        return originalList;
    }

}
