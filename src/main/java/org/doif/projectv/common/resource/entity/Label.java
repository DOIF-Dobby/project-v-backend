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

    @Column(name = "label", length = 50, nullable = false)
    private String label;

    public Label(String name, String description, EnableStatus status, Page page, String label) {
        super(name, description, status);
        this.page = page;
        this.label = label;

        page.getLabels().add(this);
    }

    public void changeLabel(String name, String description, EnableStatus status) {
        changeResource(name, description, status);
    }
}
