package org.doif.projectv.business.vcs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class VcsDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Log {
        private String revision;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime date;
        private String author;
        private String message;
    }

    @Getter
    @Setter
    public static class SearchLog {
        private Long moduleId;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate startDate;
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        private LocalDate endDate;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response<T> {
        private List<T> content;
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    public static class Tag {
        private String revision;
        private String tag;
    }
}
