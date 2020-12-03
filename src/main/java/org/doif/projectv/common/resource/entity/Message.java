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

    @Column(name = "message", length = 50, nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", length = 20, nullable = false)
    private MessageType type;

    public Message(String name, String description, EnableStatus status, String message, MessageType type) {
        super(name, description, status);
        this.message = message;
        this.type = type;
    }

    public void changeMessage(String name, String description, EnableStatus status, MessageType type) {
        changeResource(name, description, status);
        this.type = type;
    }
}
