package org.doif.projectv.common.user.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.user.constant.UserStatus;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity implements Persistable<String> {

    @Id
    @Column(name = "user_id", length = 50, nullable = false)
    private String id;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Column(name = "status", length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private UserStatus status;

    @Column(name = "svn_id", length = 50)
    private String svnId;

    @Column(name = "svn_password")
    private String svnPassword;

    @OneToMany(mappedBy = "user")
    private List<UserRole> userRoles = new ArrayList<>();

    public User(String id) {
        this.id = id;
    }

    public User(String id, String password, String name, UserStatus status, String svnId, String svnPassword) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.status = status;
        this.svnId = svnId;
        this.svnPassword = svnPassword;
    }

    @Override
    public boolean isNew() {
        return getCreatedDate() == null;
    }
}
