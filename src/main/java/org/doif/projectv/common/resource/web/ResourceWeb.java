package org.doif.projectv.common.resource.web;

import lombok.RequiredArgsConstructor;
import org.doif.projectv.common.resource.dto.MenuDto;
import org.doif.projectv.common.resource.dto.PageDto;
import org.doif.projectv.common.resource.service.ResourceService;
import org.doif.projectv.common.resource.service.menu.MenuService;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.security.util.SecurityUtil;
import org.doif.projectv.common.user.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class ResourceWeb {

    private final ResourceService resourceService;

    @GetMapping("/api/side-menu")
    public ResponseEntity<MenuDto.Response> selectSideMenu() {
        Optional<User> userByContext = SecurityUtil.getUserByContext();
        User user = userByContext.orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없음"));
        List<MenuDto.Result> result = resourceService.selectSideMenu(user.getId());

        MenuDto.Response response = new MenuDto.Response(result);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/api/pages/**")
    public ResponseEntity<PageDto.Child> select(HttpServletRequest request, @RequestParam String menuPath ) {
        PageDto.Child child = resourceService.searchPageChildResource(request.getRequestURI(), menuPath);

        return ResponseEntity.ok(child);
    }

    @PostMapping("/api/cache/refresh/all")
    public ResponseEntity<CommonResponse> update() {
        CommonResponse response = resourceService.refreshCacheAll();
        return ResponseEntity.ok(response);
    }

}
