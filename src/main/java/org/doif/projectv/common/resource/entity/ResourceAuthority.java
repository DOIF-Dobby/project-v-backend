package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
}
