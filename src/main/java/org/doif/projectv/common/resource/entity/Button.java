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

    @Column(name = "icon", length = 50)
    private String icon;

    public Button(String name, String description, EnableStatus status, String code, String url, HttpMethod httpMethod, Page page, String icon) {
        super(name, description, status, code, url, httpMethod, page);
        this.icon = icon;

//        page.getButtons().add(this);
    }

    public void changeButton(String name, String description, EnableStatus status, String url, HttpMethod httpMethod, String icon) {
        changeResourceAuthority(name, description, status, url, httpMethod);
        this.icon = icon;
    }
}
