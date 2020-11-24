package org.doif.projectv.business.module.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.business.project.entity.Project;
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

    @Column(name = "svn_url")
    private String svnUrl;

    public Module(String moduleName, Project project) {
        this.moduleName = moduleName;
        this.project = project;
    }

    public Module(String moduleName, Project project, String svnUrl) {
        this.moduleName = moduleName;
        this.project = project;
        this.svnUrl = svnUrl;
    }

    public Module(String moduleName, Project project, String description, String svnUrl) {
        this.moduleName = moduleName;
        this.project = project;
        this.description = description;
        this.svnUrl = svnUrl;
    }

    public void changeModule(String moduleName, String description, String svnUrl) {
        this.moduleName = moduleName;
        this.description = description;
        this.svnUrl = svnUrl;
    }
}
