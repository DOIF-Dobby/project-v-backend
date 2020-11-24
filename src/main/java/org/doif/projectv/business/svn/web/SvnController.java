package org.doif.projectv.business.svn.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.svn.dto.SvnDto;
import org.doif.projectv.business.svn.dto.SvnSearchCondition;
import org.doif.projectv.business.svn.service.SvnService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/svn")
@RequiredArgsConstructor
public class SvnController {

    private final SvnService svnService;

    @GetMapping
    public List<SvnDto> getSvnApi() {
        SvnSearchCondition condition = new SvnSearchCondition();
        condition.setModuleId(1L);
        condition.setStartDate(LocalDate.of(2019, 10, 10));
        condition.setEndDate(LocalDate.now());
        List<SvnDto> list = svnService.search(condition);
        return list;
    }
}
