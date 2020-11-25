package org.doif.projectv.business.patchlog.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.version.entity.Version;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PatchLog extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "patch_log_id", length = 10, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "version_id", nullable = false)
    private Version version;

    @Enumerated(EnumType.STRING)
    @Column(name = "target", length = 10, nullable = false)
    private PatchTarget target;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private PatchStatus status;

    @Column(name = "patch_schedule_date", nullable = false)
    private LocalDate patchScheduleDate;

    @Column(name = "patch_date")
    private LocalDate patchDate;

    @Column(name = "worker", length = 50)
    private String worker;

    @Column(name = "remark")
    private String remark;

    public PatchLog(Version version, PatchTarget target, PatchStatus status, LocalDate patchScheduleDate, String worker, String remark) {
        this.version = version;
        this.target = target;
        this.status = status;
        this.patchScheduleDate = patchScheduleDate;
        this.worker = worker;
        this.remark = remark;

        version.getPatchLogs().add(this);
    }

    public PatchLog(Version version, PatchTarget target, PatchStatus status, LocalDate patchScheduleDate, LocalDate patchDate, String worker, String remark) {
        this.version = version;
        this.target = target;
        this.status = status;
        this.patchScheduleDate = patchScheduleDate;
        this.patchDate = patchDate;
        this.worker = worker;
        this.remark = remark;

        version.getPatchLogs().add(this);
    }

    public void changePatchLog(PatchTarget target, PatchStatus status, LocalDate patchScheduleDate, LocalDate patchDate, String worker, String remark) {
        this.target = target;
        this.status = status;
        this.patchScheduleDate = patchScheduleDate;
        this.patchDate = patchDate;
        this.worker = worker;
        this.remark = remark;

        version.getPatchLogs().add(this);
    }
}
