package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.MessageType;

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
}
