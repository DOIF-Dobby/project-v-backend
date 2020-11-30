package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import javax.persistence.*;

@Entity
@DiscriminatorValue("TAB")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_TAB")
public class Tab extends ResourceAuthority {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id")
    private Page page;

    @Column(name = "tab_group", length = 50, nullable = false)
    private String tabGroup;

    @Column(name = "sort", length = 5, nullable = false)
    private Integer sort;

    public Tab(String name, String description, EnableStatus status, String url, HttpMethod httpMethod, Page page, String tabGroup, Integer sort) {
        super(name, description, status, url, httpMethod);
        this.page = page;
        this.tabGroup = tabGroup;
        this.sort = sort;

        page.getTabs().add(this);
    }

    public void changeTab(String name, String description, EnableStatus status, String url, HttpMethod httpMethod, String tabGroup, Integer sort) {
        changeResourceAuthority(name, description, status, url, httpMethod);
        this.tabGroup = tabGroup;
        this.sort = sort;
    }
}
