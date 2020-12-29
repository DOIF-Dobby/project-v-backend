package org.doif.projectv.common.resource.web.tab;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
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
import static org.springframework.restdocs.payload.JsonFieldType.NUMBER;
import static org.springframework.restdocs.payload.JsonFieldType.STRING;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TabControllerTest extends ApiDocumentTest {

    @Test
    public void 탭_카테고리_조회_API_테스트() throws Exception {
        // given
        TabDto.Search search = new TabDto.Search();
        search.setPageId(1L);

        TabDto.Result content = new TabDto.Result(
                2L,
                "탭1",
                "탭1 입니다.",
                EnableStatus.ENABLE,
                "/api/tabs/tab1",
                HttpMethod.GET,
                "TAB_GROUP1",
                1,
                1L
        );

        List<TabDto.Result> results = Arrays.asList(content);
        TabDto.Response response = new TabDto.Response(results);

        given(tabService.selectByPage(any(TabDto.Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/tab")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("tab/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("탭 ID"),
                                fieldWithPath("name").type(STRING).description("탭명"),
                                fieldWithPath("description").type(STRING).description("탭 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("탭 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("tabGroup").type(STRING).description("탭 그룹"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서")
                        )
                ));
    }

    @Test
    public void 탭_카테고리_추가_API_테스트() throws Exception {
        // given
        TabDto.Insert insert = new TabDto.Insert();
        insert.setName("탭2");
        insert.setDescription("탭2 입니다.");
        insert.setPageId(1L);
        insert.setTabGroup("TAB_GROUP1");
        insert.setUrl("/api/tabs/tab2");
        insert.setHttpMethod(HttpMethod.GET);
        insert.setStatus(EnableStatus.ENABLE);
        insert.setSort(2);

        given(tabService.insert(any(TabDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/tab")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("tab/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("탭명"),
                                fieldWithPath("description").type(STRING).optional().description("탭 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("탭 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("tabGroup").type(STRING).description("탭 그룹"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서")
                        )
                ));
    }

    @Test
    public void 탭_카테고리_수정_API_테스트() throws Exception {
        // given
        TabDto.Update update = new TabDto.Update();
        update.setName("탭2");
        update.setDescription("탭2 입니다.");
        update.setTabGroup("TAB_GROUP1");
        update.setUrl("/api/tabs/tab2");
        update.setHttpMethod(HttpMethod.GET);
        update.setStatus(EnableStatus.ENABLE);
        update.setSort(2);

        given(tabService.update(eq(1L), any(TabDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/tab/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("tab/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("탭 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("탭명"),
                                fieldWithPath("description").type(STRING).optional().description("탭 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("탭 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("tabGroup").type(STRING).description("탭 그룹"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서")
                        )
                ));
    }

    @Test
    public void 탭_카테고리_삭제_API_테스트() throws Exception {
        // given
        given(tabService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/tab/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("tab/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("탭 ID")
                        )
                ));
    }
}