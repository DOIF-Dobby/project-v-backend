package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import java.util.List;

public class TabDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceAuthorityDto.Result {
        private String tabGroup;
        private Integer sort;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, String url, HttpMethod httpMethod, Long pageId, String tabGroup, Integer sort) {
            super(resourceId, name, description, status, code, url, httpMethod, pageId);
            this.tabGroup = tabGroup;
            this.sort = sort;
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
    public static class Search {
        private Long pageId;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceAuthorityDto.Insert {
        private String tabGroup;
        private Integer sort;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceAuthorityDto.Update {
        private String tabGroup;
        private Integer sort;
    }
}
