package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import java.util.List;

public class PageDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private String url;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, String url) {
            super(resourceId, name, description, status, code);
            this.url = url;
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
        private String url;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        private String url;
    }
}
