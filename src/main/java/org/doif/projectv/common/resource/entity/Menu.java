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

    @Column(name = "sort", length = 5, nullable = false)
    private int sort;

    @Column(name = "icon", length = 50)
    private String icon;

    public Menu(String name, String description, ResourceStatus status, String url, HttpMethod httpMethod, MenuCategory menuCategory, int sort, String icon) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.url = url;
        this.httpMethod = httpMethod;
        this.menuCategory = menuCategory;
        this.sort = sort;
        this.icon = icon;
    }
}
