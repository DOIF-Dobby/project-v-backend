package org.doif.projectv.common.resource.repository.menu;

import jdk.nashorn.internal.runtime.options.Option;
import org.doif.projectv.common.resource.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MenuRepository extends JpaRepository<Menu, Long> {

    Optional<Menu> findByUrl(String url);
}
