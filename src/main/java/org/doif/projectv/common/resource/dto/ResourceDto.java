package org.doif.projectv.common.resource.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.doif.projectv.common.status.EnableStatus;

public class ResourceDto {

    @Getter
    @Setter
    @ToString
    public static class Result {
        protected Long resourceId;
        protected String name;
        protected String description;
        protected EnableStatus status;
        protected String statusName;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert {
        protected String name;
        protected String description;
        protected EnableStatus status;
    }

    @Getter
    @Setter
    @ToString
    public static class Update {
        protected String name;
        protected String description;
        protected EnableStatus status;
    }

}
