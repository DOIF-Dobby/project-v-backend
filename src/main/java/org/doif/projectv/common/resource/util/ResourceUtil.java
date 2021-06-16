package org.doif.projectv.common.resource.util;

import org.doif.projectv.common.enumeration.dto.CodeDto;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.role.dto.RoleResourceDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResourceUtil {

//    public static List<MenuDto.Result> getHierarchicalList(final List<MenuDto.Result> originalList) {
//        final List<MenuDto.Result> copyList = new ArrayList<>(originalList);
//
//        copyList.forEach(element -> {
//            originalList
//                    .stream()
//                    .filter(parent -> parent.getResourceId().equals(element.getParentId()))
//                    .findAny()
//                    .ifPresent(parent -> {
//                        if (parent.getChildrenItems() == null) {
//                            parent.setChildrenItems(new ArrayList<>());
//                        }
//                        parent.getChildrenItems().add(element);
//                        parent.getSubRows().sort(Comparator.comparing(MenuDto.Result::getSort));
//                        /* originalList.remove(element); don't remove the content before completing the recursive */
//                    });
//        });
//        originalList.subList(1, originalList.size()).clear();
//        return originalList;
//    }

    public static List<MenuDto.Result> getHierarchicalList(final List<MenuDto.Result> originalList) {
        final List<MenuDto.Result> copyList = new ArrayList<>(originalList);
        final List<MenuDto.Result> removeList = new ArrayList<>();

        for (MenuDto.Result childDto : copyList) {
            for(MenuDto.Result parentDto : originalList) {
                if(childDto.getParentId() != null && childDto.getParentId().equals(parentDto.getResourceId())) {
                    removeList.add(childDto);
                    parentDto.getChildrenItems().add(childDto);
                    parentDto.getChildrenItems().sort(Comparator.comparing(MenuDto.Result::getSort));
                }
            }
        }

        originalList.removeAll(removeList);

        return originalList;
    }

    public static List<MenuDto.Result> getSubRowsList(final List<MenuDto.Result> originalList) {
        final List<MenuDto.Result> copyList = new ArrayList<>(originalList);
        final List<MenuDto.Result> removeList = new ArrayList<>();

        for (MenuDto.Result childDto : copyList) {
            for(MenuDto.Result parentDto : originalList) {
                if(childDto.getParentId() != null && childDto.getParentId().equals(parentDto.getResourceId())) {
                    removeList.add(childDto);
                    parentDto.getSubRows().add(childDto);
                    parentDto.getSubRows().sort(Comparator.comparing(MenuDto.Result::getSort));
                }
            }
        }

        originalList.removeAll(removeList);

        return originalList;
    }

    public static List<RoleResourceDto.ResultMenu> getRoleResourceMenuSubRowsList(final List<RoleResourceDto.ResultMenu> originalList) {
        final List<RoleResourceDto.ResultMenu> copyList = new ArrayList<>(originalList);
        final List<RoleResourceDto.ResultMenu> removeList = new ArrayList<>();

        for (RoleResourceDto.ResultMenu childDto : copyList) {
            for(RoleResourceDto.ResultMenu parentDto : originalList) {
                if(childDto.getParentId() != null && childDto.getParentId().equals(parentDto.getMenuId())) {
                    removeList.add(childDto);
                    parentDto.getSubRows().add(childDto);
                    parentDto.getSubRows().sort(Comparator.comparing(RoleResourceDto.ResultMenu::getSort));
                }
            }
        }

        originalList.removeAll(removeList);

        return originalList;
    }

    public static List<CodeDto> MenuCategoryToCode(final List<MenuDto.Result> menuCategoryList) {
        List<CodeDto> codeDtoList = new ArrayList<>();

        for (MenuDto.Result category : menuCategoryList) {
            CodeDto codeDto = new CodeDto(String.valueOf(category.getResourceId()), category.getName(), String.valueOf(category.getDepth()));
            codeDtoList.add(codeDto);
        }

        return codeDtoList;
    }


}
