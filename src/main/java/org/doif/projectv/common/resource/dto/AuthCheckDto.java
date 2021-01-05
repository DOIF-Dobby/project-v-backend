package org.doif.projectv.common.resource.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpMethod;

public class AuthCheckDto {

    @Getter
    @Setter
    @ToString
    public static class ResourceAuthorityCheck {
        private String url;
        private HttpMethod httpMethod;
        private Long pageId;

        @QueryProjection
        public ResourceAuthorityCheck(String url, HttpMethod httpMethod, Long pageId) {
            this.url = url;
            this.httpMethod = httpMethod;
            this.pageId = pageId;
        }
    }

    @Getter
    @Setter
    @ToString
    public static class ResourcePageCheck {
        private String url;

        @QueryProjection
        public ResourcePageCheck(String url) {
            this.url = url;
        }
    }
}
