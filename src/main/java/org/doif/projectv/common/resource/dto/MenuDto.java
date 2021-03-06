package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.codehaus.plexus.util.StringUtils;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.entity.Menu;
import org.doif.projectv.common.resource.entity.MenuCategory;
import org.doif.projectv.common.status.EnableStatus;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
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
        private String typeName;
        private String icon;
        private String url;
        private List<Result> childrenItems = new ArrayList<>();
        private List<Result> subRows = new ArrayList<>();

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, Long parentId, Integer sort, MenuType type, String icon, String url) {
            super(resourceId, name, description, status, code);
            this.parentId = parentId;
            this.sort = sort;
            this.type = type;
            this.typeName = type.getMessage();
            this.icon = icon;
            this.url = url;
        }

        public void setDepthAndPath(MenuCategory menuCategory) {
            this.depth = menuCategory.getDepth();
            this.path = menuCategory.getPath();
        }

        public void setDepthAndPath(Menu menu) {
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
        @NotNull
        private Long menuCategoryId;
        @NotEmpty
        private String url;
        private String icon;
        @NotNull
        private Integer sort;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        @NotEmpty
        private String url;
        private String icon;
        @NotNull
        private Integer sort;
    }

    @Getter
    @Setter
    @ToString
    public static class AccessibleMenu {
        private String code;
        private String name;
        private String menuPath;
        private String url;
    }
}
