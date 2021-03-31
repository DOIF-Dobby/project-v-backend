package org.doif.projectv.business.patchlog.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.business.patchlog.service.PatchLogVersionService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patch-log-versions")
@RequiredArgsConstructor
public class PatchLogVersionController {

    private final PatchLogVersionService patchLogVersionService;

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody PatchLogVersionDto.Insert dto) {
        CommonResponse response = patchLogVersionService.insert(dto);
        return ResponseEntity.ok(response);
    }

}
