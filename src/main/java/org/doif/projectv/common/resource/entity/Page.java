package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;
import org.springframework.http.HttpMethod;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_PAGE")
public class Page extends Resource {

    @Column(name = "url", nullable = false)
    private String url;

//    @OneToMany(mappedBy = "page")
//    List<Button> buttons = new ArrayList<>();
//
//    @OneToMany(mappedBy = "page")
//    List<Tab> tabs = new ArrayList<>();

    @OneToMany(mappedBy = "page")
    List<ResourceAuthority> resourceAuthorities = new ArrayList<>();

    @OneToMany(mappedBy = "page")
    List<Label> labels = new ArrayList<>();

    public Page(String name, String description, EnableStatus status, String code, String url) {
        super(name, description, status, code);
        this.url = url;

        urlCheck();
    }

    public void changePage(String name, String description, EnableStatus status, String url) {
        changeResource(name, description, status);
        this.url = url;

        urlCheck();
    }

    /**
     * Url이 /api/ 로 시작하는지 체크한다.
     */
    private void urlCheck() {
        if(!getUrl().startsWith("/api/pages/")) {
            throw new IllegalArgumentException("페이지 리소스의 url은 /api/pages/ 로 시작해야 합니다.");
        }
    }
}
