package org.doif.projectv.common.resource.web.page;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.ButtonDto;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.resource.service.button.ButtonService;
import org.doif.projectv.common.resource.service.label.LabelService;
import org.doif.projectv.common.resource.service.page.PageService;
import org.doif.projectv.common.resource.service.tab.TabService;
import org.doif.projectv.common.response.CommonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources/pages")
@RequiredArgsConstructor
public class PageController {

    private final PageService pageService;
    private final LabelService labelService;
    private final TabService tabService;
    private final ButtonService buttonService;

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

    @GetMapping("/{id}/labels")
    public ResponseEntity<LabelDto.Response> selectLabelsByPage(@PathVariable Long id) {
        List<LabelDto.Result> result = labelService.selectByPage(id);
        LabelDto.Response response = new LabelDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/tabs")
    public ResponseEntity<TabDto.Response> selectTabsByPage(@PathVariable Long id) {
        List<TabDto.Result> result = tabService.selectByPage(id);
        TabDto.Response response = new TabDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}/buttons")
    public ResponseEntity<ButtonDto.Response> selectButtonsByPage(@PathVariable Long id) {
        List<ButtonDto.Result> result = buttonService.selectByPage(id);
        ButtonDto.Response response = new ButtonDto.Response(result);
        return ResponseEntity.ok(response);
    }
}
