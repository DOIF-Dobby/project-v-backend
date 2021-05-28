package org.doif.projectv.common.resource.web.label;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.service.label.LabelService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/resources/labels")
@RequiredArgsConstructor
public class LabelController {

    private final LabelService labelService;

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody @Valid LabelDto.Insert dto) {
        CommonResponse response = labelService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody @Valid LabelDto.Update dto) {
        CommonResponse response = labelService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = labelService.delete(id);
        return ResponseEntity.ok(response);
    }
}
