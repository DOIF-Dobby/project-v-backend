package org.doif.projectv.business.vcs.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.status.EnableStatus;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class VcsAuthInfo extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vcs_auth_info_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "user_id", length = 50, nullable = false)
    private String userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "vcs_type", length = 20, nullable = false)
    private VcsType vcsType;

    @Column(name = "vcs_auth_id", nullable = false)
    private String vcsAuthId;

    @Column(name = "vcs_auth_password", nullable = false)
    private String vcsAuthPassword;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnableStatus status;

    public VcsAuthInfo(String userId, VcsType vcsType, String vcsAuthId, String vcsAuthPassword, EnableStatus status) {
        this.userId = userId;
        this.vcsType = vcsType;
        this.vcsAuthId = vcsAuthId;
        this.vcsAuthPassword = vcsAuthPassword;
        this.status = status;
    }

    public void changeVcsAuthInfo(String vcsAuthId, String vcsAuthPassword, EnableStatus status) {
        this.vcsAuthId = vcsAuthId;
        this.vcsAuthPassword = vcsAuthPassword;
        this.status = status;
    }
}
