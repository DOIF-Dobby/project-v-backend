package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.status.EnableStatus;

import javax.persistence.*;

@Entity
@DiscriminatorValue("MESSAGE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_MESSAGE")
public class Message extends Resource {

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private MessageType type;

    public Message(String name, String description, EnableStatus status, String code, MessageType type) {
        super(name, description, status, code);
        this.type = type;
    }

    public void changeMessage(String name, String description, EnableStatus status, MessageType type) {
        changeResource(name, description, status);
        this.type = type;
    }
}
