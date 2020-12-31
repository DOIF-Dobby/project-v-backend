package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.role.entity.RoleResource;
import org.doif.projectv.common.status.EnableStatus;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "dtype")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class Resource extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "resource_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    protected String name;

    @Column(name = "description")
    protected String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    protected EnableStatus status;

    @Column(name = "code", nullable = false)
    protected String code;

    @OneToMany(mappedBy = "resource")
    protected List<RoleResource> roleResources = new ArrayList<>();

    protected Resource(String name, String description, EnableStatus status, String code) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.code = code;
    }

    protected void changeResource(String name, String description, EnableStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

}
