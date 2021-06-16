package org.doif.projectv.common.role.dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.status.EnableStatus;

import java.util.ArrayList;
import java.util.List;

public class RoleResourceDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultPage {
        private Long pageId;
        private String name;
        private EnableStatus status;
        private String statusName;
        private boolean checked;
        private List<ResultButton> buttons = new ArrayList<>();
        private List<ResultTab> tabs = new ArrayList<>();

        @QueryProjection
        public ResultPage(Long pageId, String name, EnableStatus status, boolean checked) {
            this.pageId = pageId;
            this.name = name;
            this.status = status;
            this.statusName = status.getMessage();
            this.checked = checked;
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultButton {
        private Long buttonId;
        private Long pageId;
        private String name;
        private String description;
        private EnableStatus status;
        private String statusName;
        private boolean checked;

        @QueryProjection
        public ResultButton(Long buttonId, Long pageId, String name, String description, EnableStatus status, boolean checked) {
            this.buttonId = buttonId;
            this.pageId = pageId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.checked = checked;
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultTab {
        private Long tabId;
        private Long pageId;
        private String name;
        private String description;
        private EnableStatus status;
        private String statusName;
        private boolean checked;

        @QueryProjection
        public ResultTab(Long tabId, Long pageId, String name, String description, EnableStatus status, boolean checked) {
            this.tabId = tabId;
            this.pageId = pageId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.checked = checked;
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultMenu {
        private Long menuId;
        private Long parentId;
        private String name;
        private String description;
        private Integer depth;
        private String path;
        private Integer sort;
        private EnableStatus status;
        private String statusName;
        private MenuType type;
        private String typeName;
        private boolean checked;
        private List<ResultMenu> subRows = new ArrayList<>();

        public ResultMenu(Long menuId, Long parentId, String name, String description, EnableStatus status, MenuType type, Integer sort, boolean checked) {
            this.menuId = menuId;
            this.parentId = parentId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.type = type;
            this.typeName = type.getMessage();
            this.sort = sort;
            this.checked = checked;
        }

        public void setDepthAndPath(MenuCategory menuCategory) {
            this.depth = menuCategory.getDepth();
            this.path = menuCategory.getPath();
        }

        public void setDepthAndPath(Menu menu) {
            this.depth = menu.getDepth();
            this.path = menu.getPath();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response<T> {
        List<T> content;
    }

    @Getter
    @Setter
    public static class SearchPage {
        private Long roleId;
    }

    @Getter
    @Setter
    public static class SearchMenu {
        private Long roleId;
    }


        @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Search {
        private Long roleId;
        private Long pageId;
    }

    @Getter
    @Setter
    @ToString
    public static class Allocate {
        private Long roleId;
        private List<Long> pages;
        private List<Long> buttons;
        private List<Long> tabs;
    }

    @Getter
    @Setter
    @ToString
    public static class AllocateMenu {
        private Long roleId;
        private List<Long> menus;
    }
}
