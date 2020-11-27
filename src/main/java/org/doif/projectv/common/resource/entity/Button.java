package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Entity
@DiscriminatorValue("BUTTON")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_BUTTON")
public class Button extends ResourceAuthority {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    @Column(name = "icon", length = 50)
    private String icon;

    public Button(String name, String description, EnableStatus status, String url, HttpMethod httpMethod, Page page, String icon) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.page = page;
        this.url = url;
        this.httpMethod = httpMethod;
        this.icon = icon;

        page.getButtons().add(this);
    }
}
