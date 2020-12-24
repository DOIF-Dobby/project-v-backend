package org.doif.projectv.business.vcs.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.vcs.dto.VcsDto;
import org.doif.projectv.business.vcs.dto.VcsDto.Response;
import org.doif.projectv.business.vcs.service.VcsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.doif.projectv.business.vcs.dto.VcsDto.*;
import static org.doif.projectv.business.vcs.dto.VcsDto.Log;

@RestController
@RequestMapping("/api/vcs")
@RequiredArgsConstructor
public class VcsController {

    private final VcsService vcsService;

    @GetMapping("/logs")
    public ResponseEntity<Response<Log>> searchLogByCondition(@RequestBody SearchLog search) {
        List<Log> result = vcsService.searchLogByCondition(search);
        Response<Log> response = new Response<>(result);
        return ResponseEntity.ok(response);
    }
}
