package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.ResourceStatus;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Entity
@DiscriminatorValue("MENU")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_MENU")
public class Menu extends ResourceAuthority {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    private int sort;

    private String icon;

    public Menu(ResourceStatus status, String url, HttpMethod httpMethod, MenuCategory menuCategory, int sort) {
        this.status = status;
        this.url = url;
        this.httpMethod = httpMethod;
        this.menuCategory = menuCategory;
        this.sort = sort;
    }
}
