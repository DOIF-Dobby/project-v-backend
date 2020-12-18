package org.doif.projectv.business.module.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.project.entity.Project;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Module extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "module_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "module_name", length = 50, nullable = false)
    private String moduleName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @Column(name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "vcs_type", length = 20, nullable = false)
    private VcsType vcsType;

    @Column(name = "vcs_repository")
    private String vcsRepository;

    @Enumerated(EnumType.STRING)
    @Column(name = "build_tool", length = 20)
    private BuildTool buildTool;

    public Module(String moduleName, Project project, String description, VcsType vcsType, String vcsRepository, BuildTool buildTool) {
        this.moduleName = moduleName;
        this.project = project;
        this.description = description;
        this.vcsType = vcsType;
        this.vcsRepository = vcsRepository;
        this.buildTool = buildTool;
    }

    public void changeModule(String moduleName, String description, VcsType vcsType, String vcsRepository, BuildTool buildTool) {
        this.moduleName = moduleName;
        this.description = description;
        this.vcsType = vcsType;
        this.vcsRepository = vcsRepository;
        this.buildTool = buildTool;
    }

}
