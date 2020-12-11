package org.doif.projectv.common.role.dto;


import lombok.*;
import org.doif.projectv.common.status.EnableStatus;

import java.util.List;

public class RoleResourceDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultPage {
        private Long pageId;
        private String name;
        private EnableStatus status;
        private String statusName;

        public ResultPage(Long pageId, String name, EnableStatus status) {
            this.pageId = pageId;
            this.name = name;
            this.status = status;
            this.statusName = status.getMessage();
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultButton {
        private Long buttonId;
        private Long pageId;
        private String name;
        private String description;
        private EnableStatus status;
        private String statusName;

        public ResultButton(Long buttonId, Long pageId, String name, String description, EnableStatus status) {
            this.buttonId = buttonId;
            this.pageId = pageId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
        }
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class ResultTab {
        private Long tabId;
        private Long pageId;
        private String name;
        private String description;
        private EnableStatus status;
        private String statusName;

        public ResultTab(Long tabId, Long pageId, String name, String description, EnableStatus status) {
            this.tabId = tabId;
            this.pageId = pageId;
            this.name = name;
            this.description = description;
            this.status = status;
            this.statusName = status.getMessage();
        }
    }

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response<T> {
        List<T> content;
    }

    @Getter
    @Setter
    @ToString
    public static class Allocate {
        private Long roleId;
        private List<ResultPage> pages;
        private List<ResultButton> buttons;
        private List<ResultTab> tabs;
    }
}
