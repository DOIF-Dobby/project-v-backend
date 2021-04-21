package org.doif.projectv.common.resource.util;

import org.doif.projectv.common.resource.dto.MenuDto;

import java.util.ArrayList;
import java.util.List;

public class ResourceUtil {

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

    public static List<MenuDto.Result> getSubRowsList(final List<MenuDto.Result> originalList) {
        final List<MenuDto.Result> copyList = new ArrayList<>(originalList);

        copyList.forEach(element -> {
            originalList
                    .stream()
                    .filter(parent -> parent.getResourceId().equals(element.getParentId()))
                    .findAny()
                    .ifPresent(parent -> {
                        if (parent.getSubRows() == null) {
                            parent.setSubRows(new ArrayList<>());
                        }
                        parent.getSubRows().add(element);
                        /* originalList.remove(element); don't remove the content before completing the recursive */
                    });
        });
        originalList.subList(1, originalList.size()).clear();
        return originalList;
    }
}
