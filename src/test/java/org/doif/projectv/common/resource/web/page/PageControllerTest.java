package org.doif.projectv.common.resource.web.page;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.PageDto;
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

class PageControllerTest extends ApiDocumentTest {

    @Test
    public void 페이지_카테고리_조회_API_테스트() throws Exception {
        // given
        PageDto.Result content = new PageDto.Result(
                1L,
                "버전관리",
                "버전관리 페이지",
                EnableStatus.ENABLE,
                "/api/pages/version",
                HttpMethod.GET
        );

        List<PageDto.Result> results = Arrays.asList(content);
        PageDto.Response response = new PageDto.Response(results);

        given(pageService.select())
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/page")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("page/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("name").type(STRING).description("페이지명"),
                                fieldWithPath("description").type(STRING).description("페이지 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("페이지 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드")
                        )
                ));
    }

    @Test
    public void 페이지_카테고리_추가_API_테스트() throws Exception {
        // given
        PageDto.Insert insert = new PageDto.Insert();
        insert.setName("이슈 관리");
        insert.setDescription("이슈 관리 페이지");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setUrl("/api/pages/issue");
        insert.setHttpMethod(HttpMethod.GET);

        given(pageService.insert(any(PageDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/page")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("page/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("페이지명"),
                                fieldWithPath("description").optional().type(STRING).description("페이지 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("페이지 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드")
                        )
                ));
    }

    @Test
    public void 페이지_카테고리_수정_API_테스트() throws Exception {
        // given
        PageDto.Update update = new PageDto.Update();
        update.setName("이슈 관리");
        update.setDescription("이슈 관리 페이지");
        update.setStatus(EnableStatus.ENABLE);
        update.setUrl("/api/pages/issue");
        update.setHttpMethod(HttpMethod.GET);

        given(pageService.update(eq(1L), any(PageDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/page/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("page/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("페이지 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("페이지명"),
                                fieldWithPath("description").optional().type(STRING).description("페이지 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("페이지 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드")
                        )
                ));
    }

    @Test
    public void 페이지_카테고리_삭제_API_테스트() throws Exception {
        // given
        given(pageService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/page/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("page/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("페이지 ID")
                        )
                ));
    }
}