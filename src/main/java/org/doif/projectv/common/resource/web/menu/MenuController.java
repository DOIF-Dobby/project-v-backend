package org.doif.projectv.common.resource.web.menu;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.service.menu.MenuService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/resources/menus")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;

    @GetMapping
    public ResponseEntity<MenuDto.Response> select() {
        List<MenuDto.Result> result = menuService.select();
        MenuDto.Response response = new MenuDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody @Valid MenuDto.Insert dto) {
        CommonResponse response = menuService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody MenuDto.Update dto) {
        CommonResponse response = menuService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = menuService.delete(id);
        return ResponseEntity.ok(response);
    }
}
