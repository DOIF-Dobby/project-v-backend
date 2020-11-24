package org.doif.projectv.business.task.repository;

import org.doif.projectv.business.task.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long>, TaskQueryRepository {
}
