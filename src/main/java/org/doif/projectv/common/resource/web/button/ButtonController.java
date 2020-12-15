package org.doif.projectv.common.resource.web.button;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.ButtonDto;
import org.doif.projectv.common.resource.service.button.ButtonService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/button")
@RequiredArgsConstructor
public class ButtonController {

    private final ButtonService buttonService;

    @GetMapping
    public ResponseEntity<ButtonDto.Response> selectByPage(@RequestBody ButtonDto.Search search) {
        List<ButtonDto.Result> result = buttonService.selectByPage(search);
        ButtonDto.Response response = new ButtonDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody ButtonDto.Insert dto) {
        CommonResponse response = buttonService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody ButtonDto.Update dto) {
        CommonResponse response = buttonService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = buttonService.delete(id);
        return ResponseEntity.ok(response);
    }
}
