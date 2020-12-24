package org.doif.projectv.common.system.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.system.dto.SystemPropertyDto;
import org.doif.projectv.common.system.service.SystemPropertyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/system-property")
@RequiredArgsConstructor
public class SystemPropertyController {

    private final SystemPropertyService systemPropertyService;

    @GetMapping
    public ResponseEntity<SystemPropertyDto.Response> searchByCondition(@RequestBody SystemPropertyDto.Search search) {
        List<SystemPropertyDto.Result> result = systemPropertyService.searchByCondition(search);
        SystemPropertyDto.Response response = new SystemPropertyDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody SystemPropertyDto.Insert dto) {
        CommonResponse response = systemPropertyService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody SystemPropertyDto.Update dto) {
        CommonResponse response = systemPropertyService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = systemPropertyService.delete(id);
        return ResponseEntity.ok(response);
    }
}
