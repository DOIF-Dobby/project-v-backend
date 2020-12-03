package org.doif.projectv.common.resource.web.menucateory;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.service.menucategory.MenuCategoryService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/menu-category")
@RequiredArgsConstructor
public class MenuCategoryController {

    private final MenuCategoryService menuCategoryService;

    @GetMapping
    public ResponseEntity<MenuCategoryDto.Response> select() {
        List<MenuCategoryDto.Result> result = menuCategoryService.select();
        MenuCategoryDto.Response response = new MenuCategoryDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody MenuCategoryDto.Insert dto) {
        CommonResponse response = menuCategoryService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody MenuCategoryDto.Update dto) {
        CommonResponse response = menuCategoryService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = menuCategoryService.delete(id);
        return ResponseEntity.ok(response);
    }
}
