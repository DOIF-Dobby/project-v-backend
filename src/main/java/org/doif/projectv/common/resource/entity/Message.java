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

    private String message;

    @Enumerated(EnumType.STRING)
    private MessageType type;
}
