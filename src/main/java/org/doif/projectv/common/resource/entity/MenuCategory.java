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
    private int sort;

    @Column(name = "icon", length = 50)
    private String icon;

    public void addChildMenuCategory(MenuCategory child) {
        this.children.add(child);
        child.parent = this;
    }

    public MenuCategory(String name, String description, EnableStatus status, int sort, String icon) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.sort = sort;
        this.icon = icon;
    }
}
