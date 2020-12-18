package org.doif.projectv.business.module.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.common.jpa.dto.BaseDto;

import java.util.List;

public class ModuleDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Result extends BaseDto {
        private Long moduleId;
        private String moduleName;
        private String description;

        @QueryProjection
        public Result(Long moduleId, String moduleName, String description) {
            this.moduleId = moduleId;
            this.moduleName = moduleName;
            this.description = description;
        }
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Search {
        private Long projectId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private List<ModuleDto.Result> content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Insert {
        private String moduleName;
        private String description;
        private Long projectId;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String moduleName;
        private String description;
    }
}
