package org.doif.projectv.common.resource.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpMethod;

import java.io.Serializable;

@Getter
@ToString
public class AuthorityResourceDto {

    private String url;
    private HttpMethod httpMethod;

    @QueryProjection
    public AuthorityResourceDto(String url, HttpMethod httpMethod) {
        this.url = url;
        this.httpMethod = httpMethod;
    }
}
