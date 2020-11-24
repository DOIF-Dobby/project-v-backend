package org.doif.projectv.business.project.repository;

import org.doif.projectv.business.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {

}
