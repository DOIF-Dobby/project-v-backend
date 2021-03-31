package org.doif.projectv.business.patchlog.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchLogVersion extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patch_log_version_id", length = 10, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patch_log_id", nullable = false)
    private PatchLog patchLog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private Version version;

    public PatchLogVersion(PatchLog patchLog, Version version) {
        this.patchLog = patchLog;
        this.version = version;

        patchLog.getPatchLogVersions().add(this);
        version.getPatchLogVersions().add(this);
    }
}
