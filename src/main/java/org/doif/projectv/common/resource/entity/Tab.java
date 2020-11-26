package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.ResourceStatus;
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
    private int sort;

    public Tab(String name, String description, ResourceStatus status, String url, HttpMethod httpMethod, Page page, String tabGroup, int sort) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.url = url;
        this.httpMethod = httpMethod;
        this.page = page;
        this.tabGroup = tabGroup;
        this.sort = sort;

        page.getTabs().add(this);
    }
}
