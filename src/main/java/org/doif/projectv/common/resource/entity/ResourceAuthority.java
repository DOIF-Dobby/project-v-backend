package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Entity
@DiscriminatorValue("Authority")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ResourceAuthority extends Resource {

    @Column(name = "url", nullable = false)
    protected String url;

    @Enumerated(EnumType.STRING)
    @Column(name = "http_method", length = 10, nullable = false)
    protected HttpMethod httpMethod;

    protected ResourceAuthority(String name, String description, EnableStatus status, String url, HttpMethod httpMethod) {
        super(name, description, status);
        this.url = url;
        this.httpMethod = httpMethod;

        urlCheck();
    }

    protected void changeResourceAuthority(String name, String description, EnableStatus status, String url, HttpMethod httpMethod) {
        changeResource(name, description, status);
        this.url = url;
        this.httpMethod = httpMethod;

        urlCheck();
    }

    /**
     * Url이 /api/ 로 시작하는지 체크한다.
     */
    private void urlCheck() {
        if(!getUrl().startsWith("/api/")) {
            throw new IllegalArgumentException("권한 리소스의 url은 /api/ 로 시작해야 합니다.");
        }
    }
}
