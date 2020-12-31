package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import java.util.List;

public class ButtonDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Result extends ResourceAuthorityDto.Result {
        private String icon;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, String url, HttpMethod httpMethod, Long pageId, String icon) {
            super(resourceId, name, description, status, code, url, httpMethod, pageId);
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
    public static class Search {
        private Long pageId;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Insert extends ResourceAuthorityDto.Insert {
        private String icon;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Update extends ResourceAuthorityDto.Update {
        private String icon;
    }
}
