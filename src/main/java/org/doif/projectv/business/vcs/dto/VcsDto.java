package org.doif.projectv.business.vcs.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @ToString
    @NoArgsConstructor
    public static class Tag {
        private String revision;
        private String tag;
    }
}
