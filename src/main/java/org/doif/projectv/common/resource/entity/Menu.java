package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;

import javax.persistence.*;

@Entity
@DiscriminatorValue("MENU")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_MENU")
public class Menu extends Resource {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "menu_category_id")
    private MenuCategory menuCategory;

    @Column(name = "sort", length = 5, nullable = false)
    private Integer sort;

    @Column(name = "url", nullable = false)
    private String url;

    @Column(name = "icon", length = 50)
    private String icon;

    public Menu(String name, String description, EnableStatus status, MenuCategory menuCategory, Integer sort, String url, String icon) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.menuCategory = menuCategory;
        this.sort = sort;
        this.url = url;
        this.icon = icon;

        menuCategory.getMenus().add(this);
    }

    public void changeMenu(String name, String description, EnableStatus status, Integer sort, String url, String icon) {
        changeResource(name, description, status);
        this.sort = sort;
        this.url = url;
        this.icon = icon;
    }

    public Integer getDepth() {
        return getDepth(1);
    }

    private Integer getDepth(Integer depth) {
        if(menuCategory != null) {
            return menuCategory.getDepth(depth + 1);
        }

        return depth;
    }

    public String getPath() {
        return getPath(String.valueOf(this.sort));
    }

    private String getPath(String path) {
        if(menuCategory != null) {
            return menuCategory.getPath(menuCategory.getSort() + "-" + path);
        }

        return path;
    }
}
