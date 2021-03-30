package org.doif.projectv.business.module.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.*;
import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.vcs.constant.VcsType;
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
        private VcsType vcsType;
        private String vcsTypeName;
        private String vcsRepository;
        private BuildTool buildTool;
        private String buildToolName;

        @QueryProjection
        public Result(Long moduleId, String moduleName, String description, VcsType vcsType, String vcsRepository, BuildTool buildTool) {
            this.moduleId = moduleId;
            this.moduleName = moduleName;
            this.description = description;
            this.vcsType = vcsType;
            this.vcsTypeName = vcsType.getMessage();
            this.vcsRepository = vcsRepository;
            this.buildTool = buildTool;
            this.buildToolName = buildTool.getMessage();
        }
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
        private VcsType vcsType;
        private String vcsRepository;
        private BuildTool buildTool;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String moduleName;
        private String description;
        private VcsType vcsType;
        private String vcsRepository;
        private BuildTool buildTool;
    }
}
