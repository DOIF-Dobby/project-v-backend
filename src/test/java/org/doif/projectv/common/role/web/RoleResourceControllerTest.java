package org.doif.projectv.common.role.web;

import static org.doif.projectv.common.role.dto.RoleResourceDto.*;
import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.MessageDto;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.role.dto.RoleResourceDto;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.util.MultiValueMapConverter;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentRequest;
import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentResponse;
import static org.doif.projectv.common.api.DocumentFormatGenerator.getDateTimeFormat;
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateLinkCode;
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateText;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RoleResourceControllerTest extends ApiDocumentTest {

    @Test
    public void RoleResource_페이지_조회_API_테스트() throws Exception {
        // given
        SearchPage searchPage = new SearchPage();
        searchPage.setRoleId(1L);

        ResultPage content = new ResultPage(
                1L,
                "버전 관리",
                EnableStatus.ENABLE,
                true
        );

        List<ResultPage> results = Arrays.asList(content);
        Response<ResultPage> response = new Response<>(results);

        given(roleResourceService.selectPage(any(SearchPage.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/role-resources/pages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, searchPage))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("role-resource/select-page",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("roleId").description("Role ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("name").type(STRING).description("페이지명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("checked").type(BOOLEAN).description("할당 여부")
                        )
                ));
    }

    @Test
    public void RoleResource_버튼_조회_API_테스트() throws Exception {
        // given
        Search search = new Search();
        search.setRoleId(1L);
        search.setPageId(2L);

        ResultButton content = new ResultButton(1L, 2L, "버전 수정", "버전 수정 버튼", EnableStatus.ENABLE, true);

        List<ResultButton> results = Arrays.asList(content);
        Response<ResultButton> response = new Response<>(results);

        given(roleResourceService.selectButton(any(Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/role-resources/buttons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("role-resource/select-button",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("roleId").description("Role ID"),
                                parameterWithName("pageId").description("페이지 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("buttonId").type(NUMBER).description("버튼 ID"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("name").type(STRING).description("버튼명"),
                                fieldWithPath("description").type(STRING).description("버튼 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("checked").type(BOOLEAN).description("할당 여부")
                        )
                ));
    }

    @Test
    public void RoleResource_탭_버튼_조회_API_테스트() throws Exception {
        // given
        Search search = new Search();
        search.setRoleId(1L);
        search.setPageId(2L);

        ResultTab content = new ResultTab(1L, 2L, "탭1", "탭1 입니다.", EnableStatus.ENABLE, true);

        List<ResultTab> results = Arrays.asList(content);
        Response<ResultTab> response = new Response<>(results);

        given(roleResourceService.selectTab(any(Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/role-resources/tabs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("role-resource/select-tab",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("roleId").description("Role ID"),
                                parameterWithName("pageId").description("페이지 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("tabId").type(NUMBER).description("탭 ID"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("name").type(STRING).description("탭명"),
                                fieldWithPath("description").type(STRING).description("탭 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("checked").type(BOOLEAN).description("할당 여부")
                        )
                ));
    }

    @Test
    public void RoleResource_할당_API_테스트() throws Exception {
        // given
        List<Long> pages = Arrays.asList(1L, 2L);
        List<Long> buttons = Arrays.asList(1L, 2L);
        List<Long> tabs = Arrays.asList(1L, 2L);

        Allocate allocate = new Allocate();
        allocate.setRoleId(1L);
        allocate.setPages(pages);
        allocate.setButtons(buttons);
        allocate.setTabs(tabs);

        given(roleResourceService.allocate(any(Allocate.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/role-resources")
                        .content(objectMapper.writeValueAsBytes(allocate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("role-resource/allocate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("roleId").type(NUMBER).description("Role ID"),
                                fieldWithPath("pages").type(ARRAY).description("할당할 페이지 ID 배열"),
                                fieldWithPath("buttons").type(ARRAY).description("할당할 버튼 ID 배열"),
                                fieldWithPath("tabs").type(ARRAY).description("할당할 탭 ID 배열")
                        )
                ));
    }

}