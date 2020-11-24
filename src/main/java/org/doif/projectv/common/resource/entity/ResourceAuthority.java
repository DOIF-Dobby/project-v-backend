package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Entity
@DiscriminatorValue("Authority")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class ResourceAuthority extends Resource {

    protected String url;

    @Enumerated(EnumType.STRING)
    protected HttpMethod httpMethod;
}
