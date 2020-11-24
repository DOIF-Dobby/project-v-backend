package org.doif.projectv.business.project.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.entity.BaseEntity;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Project extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "project_id", length = 10, nullable = false)
    private Long id;

    @Column(name = "project_name", length = 50, nullable = false)
    private String projectName;

    @Column(name = "description")
    private String description;

    public Project(String projectName) {
        this.projectName = projectName;
    }

    public Project(String projectName, String description) {
        this.projectName = projectName;
        this.description = description;
    }

    public void changeProject(String projectName, String description) {
        this.projectName = projectName;
        this.description = description;
    }
}
