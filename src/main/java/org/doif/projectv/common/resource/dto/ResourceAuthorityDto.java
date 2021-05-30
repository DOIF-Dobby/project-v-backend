package org.doif.projectv.common.resource.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ResourceAuthorityDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Result extends ResourceDto.Result {
        protected String url;
        protected HttpMethod httpMethod;
        protected Long pageId;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, String url, HttpMethod httpMethod, Long pageId) {
            super(resourceId, name, description, status, code);
            this.url = url;
            this.httpMethod = httpMethod;
            this.pageId = pageId;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceDto.Insert {
        @NotEmpty
        protected String url;
        @NotNull
        protected HttpMethod httpMethod;
        @NotNull
        protected Long pageId;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        @NotEmpty
        protected String url;
        @NotNull
        protected HttpMethod httpMethod;
    }
}
