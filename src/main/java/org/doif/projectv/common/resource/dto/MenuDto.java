package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.codehaus.plexus.util.StringUtils;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.status.EnableStatus;

import java.util.List;

public class MenuDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private Long parentId;
        private String paddingName;
        private Integer sort;
        private Integer depth;
        private String path;
        private MenuType type;
        private String icon;
        private String url;

        public void setDepthAndPath(MenuCategory menuCategory) {
            this.setParentId(menuCategory.getParent() != null ? menuCategory.getParent().getId() : null);
            this.depth = menuCategory.getDepth();
            this.path = menuCategory.getPath();
        }

        public void setDepthAndPath(Menu menu) {
            this.setParentId(menu.getMenuCategory() != null ? menu.getMenuCategory().getId() : null);
            this.depth = menu.getDepth();
            this.path = menu.getPath();
        }

        public void setPaddingName(String name) {
            this.paddingName = StringUtils.leftPad(name, name.length() + this.depth, " ");
        }

    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceDto.Insert {
        private Long menuCategoryId;
        private String url;
        private String icon;
        private Integer sort;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        private String url;
        private String icon;
        private Integer sort;
    }
}