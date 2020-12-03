package org.doif.projectv.common.resource.repository.message;

import org.doif.projectv.common.resource.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
