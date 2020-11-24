package org.doif.projectv.business.project.dto;

import lombok.*;
import org.doif.projectv.common.jpa.dto.BaseDto;

import java.util.List;

public class ProjectDto {

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Result extends BaseDto{
        private Long projectId;
        private String projectName;
        private String description;
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    public static class Response {
        List<ProjectDto.Result> content;
    }

    @Getter
    @Setter
    public static class Insert {
        private String projectName;
        private String description;
    }

    @Getter
    @Setter
    public static class Update {
        private String projectName;
        private String description;
    }
}
