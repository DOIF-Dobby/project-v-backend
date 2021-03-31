package org.doif.projectv.business.client.repository;

import org.doif.projectv.business.client.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
