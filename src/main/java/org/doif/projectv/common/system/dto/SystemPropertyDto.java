package org.doif.projectv.common.system.dto;

import lombok.*;
import org.doif.projectv.common.system.constant.PropertyGroupType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public class SystemPropertyDto {
    @Getter
    @Setter
    @ToString
    public static class Result {
        private Long systemPropertyId;
        private PropertyGroupType propertyGroup;
        private String propertyGroupName;
        private String property;
        private String value;
        private String description;
        private boolean updatable;
        private String updatableName;

        public Result(Long systemPropertyId, PropertyGroupType propertyGroup, String property, String value, String description, boolean updatable) {
            this.systemPropertyId = systemPropertyId;
            this.propertyGroup = propertyGroup;
            this.propertyGroupName = propertyGroup.getMessage();
            this.property = property;
            this.value = value;
            this.description = description;
            this.updatable = updatable;
            this.updatableName = updatable ? "예" : "아니오";
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    public static class Search {
        private PropertyGroupType propertyGroup;
    }

    @Getter
    @Setter
    public static class Insert {
        @NotNull
        private PropertyGroupType propertyGroup;
        @NotEmpty
        private String property;
        @NotEmpty
        private String value;
        private String description;
        @NotNull
        private boolean updatable;
    }

    @Getter
    @Setter
    public static class Update {
        @NotEmpty
        private String value;
        private String description;
        @NotNull
        private boolean updatable;
    }
}
