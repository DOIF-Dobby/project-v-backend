package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import java.util.List;

public class MenuCategoryDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private Long parentId;
        private Integer sort;
        private String icon;

        public Result(Long resourceId, String name, String description, EnableStatus status, Long parentId, Integer sort, String icon) {
            this.resourceId = resourceId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.parentId = parentId;
            this.sort = sort;
            this.icon = icon;
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
        private Long parentId;
        private Integer sort;
        private String icon;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        private Integer sort;
        private String icon;
    }
}
