package org.doif.projectv.business.svn.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class SvnDto {

    private Long revision;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime date;
    private String author;
    private String message;

    public SvnDto(Long revision, LocalDateTime date, String author, String message) {
        this.revision = revision;
        this.date = date;
        this.author = author;
        this.message = message;
    }
}
