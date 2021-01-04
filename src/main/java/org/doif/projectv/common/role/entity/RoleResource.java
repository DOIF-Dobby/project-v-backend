package org.doif.projectv.common.role.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.resource.entity.Page;
import org.doif.projectv.common.resource.entity.Resource;
import org.doif.projectv.common.resource.entity.ResourceAuthority;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleResource extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_resource_id", length = 10, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_id", nullable = false)
    private Resource resource;

    public RoleResource(Role role, Resource resource) {
        if( (resource instanceof Page) || (resource instanceof ResourceAuthority) ) {
            this.role = role;
            this.resource = resource;

            resource.getRoleResources().add(this);
        } else {
            throw new IllegalArgumentException("Role-Resource 에는 Page 또는 ResourceAuthority 만이 할당될 수 있습니다.");
        }
    }
}
