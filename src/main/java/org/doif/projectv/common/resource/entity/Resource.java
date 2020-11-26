package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.resource.constant.ResourceStatus;
import org.doif.projectv.common.role.entity.RoleResource;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    protected ResourceStatus status;

    @Column(name = "name", length = 50, nullable = false)
    protected String name;

    @Column(name = "description")
    protected String description;

    @OneToMany(mappedBy = "resource")
    protected List<RoleResource> roleResources = new ArrayList<>();

}
