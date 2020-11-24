package org.doif.projectv.common.resource.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.resource.constant.ModifyStatus;

import javax.persistence.*;

@Entity
@DiscriminatorValue("GROUP_CODE")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "RESOURCE_GROUP_CODE")
public class GroupCode extends Resource {

    private String groupCode;

    @Enumerated(EnumType.STRING)
    private ModifyStatus modifyModifyStatus;
}
