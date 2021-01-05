package org.doif.projectv.common.resource.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.service.ResourceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceWeb {

    private final ResourceService resourceService;

    @GetMapping("/api/pages/**")
    public ResponseEntity<PageDto.Child> select(HttpServletRequest request) {
        PageDto.Child child = resourceService.searchPageChildResource(request.getRequestURI());

        return ResponseEntity.ok(child);
    }
}
