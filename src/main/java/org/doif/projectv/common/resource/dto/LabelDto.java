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

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, Long pageId) {
            super(resourceId, name, description, status, code);
            this.pageId = pageId;
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        List<Result> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Insert extends ResourceDto.Insert {
        private Long pageId;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {

    }
}
