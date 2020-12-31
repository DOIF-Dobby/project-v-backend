package org.doif.projectv.common.resource.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceWeb {

    private final ResourceService resourceService;

    // TODO: Page 요청시 해당 페이지의 자식 리소스들 (Button, Tab, Label) 담아서 응답하는 것 추가하기
    @GetMapping("/api/pages/{path}")
    public ResponseEntity<PageDto.Child> select(@PathVariable String path) {
        PageDto.Child child = new PageDto.Child();

        return ResponseEntity.ok(child);
    }
}
