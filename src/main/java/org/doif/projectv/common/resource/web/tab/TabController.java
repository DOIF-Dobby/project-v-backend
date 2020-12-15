package org.doif.projectv.common.resource.web.tab;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.resource.service.tab.TabService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/tab")
@RequiredArgsConstructor
public class TabController {

    private final TabService tabService;

    @GetMapping
    public ResponseEntity<TabDto.Response> selectByPage(@RequestBody TabDto.Search search) {
        List<TabDto.Result> result = tabService.selectByPage(search);
        TabDto.Response response = new TabDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody TabDto.Insert dto) {
        CommonResponse response = tabService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody TabDto.Update dto) {
        CommonResponse response = tabService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = tabService.delete(id);
        return ResponseEntity.ok(response);
    }
}
