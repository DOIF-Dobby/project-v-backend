package org.doif.projectv.common.resource.dto;

import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

public class PageDto {

    @Getter
    @Setter
    @ToString
    public static class Result extends ResourceDto.Result {
        private String url;

        public Result(Long resourceId, String name, String description, EnableStatus status, String code, String url) {
            super(resourceId, name, description, status, code);
            this.url = url;
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
        @NotEmpty
        private String url;
    }

    @Getter
    @Setter
    @ToString
    public static class Update extends ResourceDto.Update {
        @NotEmpty
        private String url;
    }

    @Getter
    @Setter
    @ToString
    public static class Child {
        private Long pageId;
        private String menuName;
        private List<String> menuList;
        private Map<String, ButtonDto.Result> buttonMap;
        private Map<String, TabDto.Result> tabMap;
        private Map<String, LabelDto.Result> labelMap;
    }
}
