package org.doif.projectv.common.role.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.entity.UserRole;

import javax.management.relation.RoleStatus;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Role extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private EnableStatus status;

    public Role(String name, String description, EnableStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }

    @OneToMany(mappedBy = "role")
    private List<UserRole> userRoles = new ArrayList<>();

    public void changeRole(String name, String description, EnableStatus status) {
        this.name = name;
        this.description = description;
        this.status = status;
    }
}
