package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import java.util.List;

public class LabelDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private Long pageId;
        private String label;

        public Result(Long resourceId, String name, String description, EnableStatus status, Long pageId, String label) {
            this.resourceId = resourceId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
            this.pageId = pageId;
            this.label = label;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceDto.Insert {
        private Long pageId;
        private String label;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {

    }
}
