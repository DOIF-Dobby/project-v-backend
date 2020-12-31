package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.status.EnableStatus;

import java.util.List;

public class MessageDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private MessageType type;
        private String typeName;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, MessageType type) {
            super(resourceId, name, description, status, code);
            this.type = type;
            this.typeName = type.getMessage();
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
        private MessageType type;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        private MessageType type;
    }
}
