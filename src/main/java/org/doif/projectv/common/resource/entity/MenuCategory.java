package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("MENU_CATEGORY")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_MENU_CATEGORY")
public class MenuCategory extends Resource {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private MenuCategory parent;

    @OneToMany(mappedBy = "parent")
    private List<MenuCategory> children = new ArrayList<>();

    @Column(name = "sort", length = 5, nullable = false)
    private Integer sort;

    @Column(name = "icon", length = 50)
    private String icon;

    public void addChildMenuCategory(MenuCategory child) {
        this.children.add(child);
    }

    public MenuCategory(String name, String description, EnableStatus status, Integer sort, String icon, MenuCategory parent) {
        super(name, description, status);
        this.sort = sort;
        this.icon = icon;
        this.parent = parent;

        if(parent != null) {
            parent.addChildMenuCategory(this);
        }
    }

    public void changeMenuCategory(String name, String description, EnableStatus status, Integer sort, String icon) {
        changeResource(name, description, status);
        this.sort = sort;
        this.icon = icon;
    }
}
