package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.status.EnableStatus;

import javax.persistence.*;

@Entity
@DiscriminatorValue("LABEL")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_LABEL")
public class Label extends Resource {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "page_id", nullable = false)
    private Page page;

    public Label(String name, String description, EnableStatus status, String code, Page page) {
        super(name, description, status, code);
        this.page = page;

        page.getLabels().add(this);
    }

    public void changeLabel(String name, String description, EnableStatus status) {
        changeResource(name, description, status);
    }
}
