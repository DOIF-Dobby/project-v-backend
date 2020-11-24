package org.doif.projectv.common.resource.repository;

import org.doif.projectv.common.resource.entity.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<Resource, Long>, ResourceQueryRepository {

}
