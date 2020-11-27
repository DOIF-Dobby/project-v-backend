package org.doif.projectv.common.resource.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

public class ResourceAuthorityDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class Result extends ResourceDto.Result {
        protected String url;
        protected HttpMethod httpMethod;

        @QueryProjection
        public Result(String url, HttpMethod httpMethod) {
            this.url = url;
            this.httpMethod = httpMethod;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceDto.Inert {
        protected String url;
        protected HttpMethod httpMethod;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        protected String url;
        protected HttpMethod httpMethod;
    }
}
