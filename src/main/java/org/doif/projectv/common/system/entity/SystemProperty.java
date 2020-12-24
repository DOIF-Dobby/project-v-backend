package org.doif.projectv.common.system.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.doif.projectv.common.jpa.converter.BooleanToYNConverter;
import org.doif.projectv.common.jpa.entity.BaseEntity;
import org.doif.projectv.common.system.constant.PropertyGroupType;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SystemProperty extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "system_property_id", length = 10, nullable = false)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "property_group", length = 50, nullable = false)
    private PropertyGroupType propertyGroup;

    @Column(name = "property", length = 50, nullable = false)
    private String property;

    @Column(name = "value", nullable = false)
    private String value;

    @Column(name = "description", nullable = false)
    private String description;

    @Convert(converter = BooleanToYNConverter.class)
    @Column(name = "updatable", length = 1, nullable = false)
    private Boolean updatable;

    public SystemProperty(PropertyGroupType propertyGroup, String property, String value, String description, Boolean updatable) {
        this.propertyGroup = propertyGroup;
        this.property = property;
        this.value = value;
        this.description = description;
        this.updatable = updatable;
    }

    public void changeSystemProperty(String value, String description, Boolean updatable) {
        this.value = value;
        this.description = description;
        this.updatable = updatable;
    }
}
