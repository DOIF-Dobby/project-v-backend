package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.ResourceStatus;

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

    private int sort;

    private String icon;

    public void addChildMenuCategory(MenuCategory child) {
        this.children.add(child);
        child.parent = this;
    }

    public MenuCategory(ResourceStatus status, int sort, String icon) {
        this.status = status;
        this.sort = sort;
        this.icon = icon;
    }
}
