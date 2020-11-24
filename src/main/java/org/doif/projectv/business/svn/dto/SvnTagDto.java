package org.doif.projectv.business.svn.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SvnTagDto {
    private Long revision;
    private String tag;
}
