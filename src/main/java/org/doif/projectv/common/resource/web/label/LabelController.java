package org.doif.projectv.common.resource.web.label;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.service.label.LabelService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/label")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @GetMapping("/page/{pageId}")
    public ResponseEntity<LabelDto.Response> selectByPage(@PathVariable Long pageId) {
        List<LabelDto.Result> result = labelService.selectByPage(pageId);
        LabelDto.Response response = new LabelDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody LabelDto.Insert dto) {
        CommonResponse response = labelService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody LabelDto.Update dto) {
        CommonResponse response = labelService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = labelService.delete(id);
        return ResponseEntity.ok(response);
    }
}
