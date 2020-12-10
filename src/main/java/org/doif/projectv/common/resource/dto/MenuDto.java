package org.doif.projectv.common.resource.dto;

import lombok.*;
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
        private String type;
        private String icon;
        private String url;

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
