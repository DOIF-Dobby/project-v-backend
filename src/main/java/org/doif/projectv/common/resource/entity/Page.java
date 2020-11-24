package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.ResourceStatus;
import org.springframework.http.HttpMethod;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@DiscriminatorValue("PAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_PAGE")
public class Page extends ResourceAuthority {

    @OneToMany(mappedBy = "page")
    List<Button> buttons = new ArrayList<>();

    @OneToMany(mappedBy = "page")
    List<Tab> tabs = new ArrayList<>();

    @OneToMany(mappedBy = "page")
    List<Label> labels = new ArrayList<>();

    public Page(ResourceStatus status, String url, HttpMethod httpMethod) {
        this.status = status;
        this.url = url;
        this.httpMethod = httpMethod;
    }
}
