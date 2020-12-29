package org.doif.projectv.business.version.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.issue.entity.VersionIssue;
import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.patchlog.entity.PatchLog;
import org.doif.projectv.business.version.constant.VersionStatus;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Version extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "version_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "name", length = 50, nullable = false)
    private String name;

    @Lob
    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "module_id", nullable = false)
    private Module module;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 20, nullable = false)
    private VersionStatus status;

    @Column(name = "revision")
    private String revision;

    @Column(name = "tag")
    private String tag;

    @OneToMany(mappedBy = "version")
    private List<VersionIssue> versionIssues = new ArrayList<>();

    @OneToMany(mappedBy = "version")
    private List<PatchLog> patchLogs = new ArrayList<>();

    public Version(String name, String description, Module module) {
        this.name = name;
        this.description = description;
        this.module = module;
        this.status = VersionStatus.DEVELOP;
    }

    /**
     * 버전 설명 변경
     * @param description
     */
    public void changeDescription(String description) {
        this.description = description;
    }

    /**
     * 버전을 배포상태로 변경
     */
    public void release(String revision, String tag) {
        this.status = VersionStatus.RELEASE;
        this.revision = revision;
        this.tag = tag;
    }
}
