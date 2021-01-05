package org.doif.projectv.common.resource.repository.page;

import org.doif.projectv.common.resource.entity.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PageRepository extends JpaRepository<Page, Long> {

    Optional<Page> findByUrl(String url);
}
