package org.doif.projectv.common.api.dto;

import org.springframework.data.domain.Page;

import java.util.Map;

public class PageResponseDto {
    private Page<Map<String, Object>> pageInfo;

    public Page<Map<String, Object>> getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(Page<Map<String, Object>> pageInfo) {
        this.pageInfo = pageInfo;
    }
}
