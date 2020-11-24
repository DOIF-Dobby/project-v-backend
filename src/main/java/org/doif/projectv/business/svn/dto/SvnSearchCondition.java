package org.doif.projectv.business.svn.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class SvnSearchCondition {

    private Long moduleId;
    private LocalDate startDate;
    private LocalDate endDate;
}
