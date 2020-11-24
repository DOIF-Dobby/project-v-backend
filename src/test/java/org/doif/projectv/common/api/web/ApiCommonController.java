package org.doif.projectv.common.api.web;

import org.doif.projectv.common.api.dto.PageResponseDto;
import org.doif.projectv.common.enumeration.EnumMapper;
import org.doif.projectv.common.enumeration.EnumValue;
import org.doif.projectv.common.response.CommonResponse;
import org.doif.projectv.common.response.ResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ApiCommonController {

    private final EnumMapper enumMapper;

    public ApiCommonController(EnumMapper enumMapper) {
        this.enumMapper = enumMapper;
    }

    @GetMapping("/common-response")
    public CommonResponse commonResponse() {
        return ResponseUtil.ok();
    }

    @GetMapping("/code-response")
    public Map<String, List<EnumValue>> codeResponse() {
        return enumMapper.getAll();
    }

    @GetMapping("/page-response")
    public ResponseEntity<PageResponseDto> pageResponse() {
        PageResponseDto pageResponseDto = new PageResponseDto();
        Map<String, Object> map = new HashMap<>();
        PageRequest pageRequest = PageRequest.of(0, 100);


        Page<Map<String, Object>> pages = new PageImpl<>(Arrays.asList(map), pageRequest, 100);
        pageResponseDto.setPageInfo(pages);

        return ResponseEntity.ok(pageResponseDto);
    }
}
