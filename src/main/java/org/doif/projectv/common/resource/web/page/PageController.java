package org.doif.projectv.common.resource.web.page;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.service.page.PageService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/page")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;

    @GetMapping
    public ResponseEntity<PageDto.Response> select() {
        List<PageDto.Result> result = pageService.select();
        PageDto.Response response = new PageDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CommonResponse> insert(@RequestBody PageDto.Insert dto) {
        CommonResponse response = pageService.insert(dto);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CommonResponse> update(@PathVariable Long id, @RequestBody PageDto.Update dto) {
        CommonResponse response = pageService.update(id, dto);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CommonResponse> delete(@PathVariable Long id) {
        CommonResponse response = pageService.delete(id);
        return ResponseEntity.ok(response);
    }
}
