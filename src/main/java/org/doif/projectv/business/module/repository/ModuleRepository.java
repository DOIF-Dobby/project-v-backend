package org.doif.projectv.business.module.repository;

import org.doif.projectv.business.module.entity.Module;
import org.doif.projectv.business.project.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModuleRepository extends JpaRepository<Module, Long>, ModuleQueryRepository {

    /**
     * Project로 Module 조회
     * @param project
     * @return
     */
    List<Module> findByProject(Project project);
}
