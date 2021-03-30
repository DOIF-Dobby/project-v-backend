package org.doif.projectv.business.vcs.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.business.vcs.service.VcsAuthInfoService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vcs-auth-infos")
@RequiredArgsConstructor
public class VcsAuthInfoController {

    private final VcsAuthInfoService vcsAuthInfoService;

    @GetMapping
    public ResponseEntity<VcsAuthInfoDto.Response> searchByCondition(VcsAuthInfoDto.Search search) {
        List<VcsAuthInfoDto.Result> result = vcsAuthInfoService.searchByCondition(search);
        VcsAuthInfoDto.Response response = new VcsAuthInfoDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody VcsAuthInfoDto.Insert dto) {
        CommonResponse response = vcsAuthInfoService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody VcsAuthInfoDto.Update dto) {
        CommonResponse response = vcsAuthInfoService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = vcsAuthInfoService.delete(id);
        return ResponseEntity.ok(response);
    }
    
}
