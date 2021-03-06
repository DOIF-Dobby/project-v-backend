package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

public class MenuCategoryDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private Long parentId;
        private Integer sort;
        private String icon;
        private List<Result> childrenItems = new ArrayList<>();

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, Long parentId, Integer sort, String icon) {
            super(resourceId, name, description, status, code);
            this.parentId = parentId;
            this.sort = sort;
            this.icon = icon;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceDto.Insert {
        private Long parentId;
        @NotNull
        private Integer sort;
        private String icon;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        @NotNull
        private Integer sort;
        private String icon;
    }
}
