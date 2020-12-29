package org.doif.projectv.common.resource.web.label;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.Test;
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

class LabelControllerTest extends ApiDocumentTest {

    @Test
    public void 라벨_카테고리_조회_API_테스트() throws Exception {
        // given
        LabelDto.Search search = new LabelDto.Search();
        search.setPageId(1L);

        LabelDto.Result content = new LabelDto.Result(
                2L,
                "버전관리시스템 유형",
                "버전관리시스템 유형",
                EnableStatus.ENABLE,
                1L,
                "LABEL_VCS_TYPE"
        );

        List<LabelDto.Result> results = Arrays.asList(content);
        LabelDto.Response response = new LabelDto.Response(results);

        given(labelService.selectByPage(any(LabelDto.Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/label")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("label/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("라벨 ID"),
                                fieldWithPath("name").type(STRING).description("라벨명"),
                                fieldWithPath("description").type(STRING).description("라벨 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("label").type(STRING).description("라벨 코드")
                        )
                ));
    }

    @Test
    public void 라벨_카테고리_추가_API_테스트() throws Exception {
        // given
        LabelDto.Insert insert = new LabelDto.Insert();
        insert.setName("메뉴명");
        insert.setDescription("메뉴명");
        insert.setLabel("LABEL_MENU_NAME");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setPageId(1L);

        given(labelService.insert(any(LabelDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/label")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("label/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("라벨명"),
                                fieldWithPath("description").optional().type(STRING).description("라벨 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("label").type(STRING).description("라벨 코드")
                        )
                ));
    }

    @Test
    public void 라벨_카테고리_수정_API_테스트() throws Exception {
        // given
        LabelDto.Update update = new LabelDto.Update();
        update.setName("메뉴명");
        update.setDescription("메뉴명");
        update.setStatus(EnableStatus.ENABLE);

        given(labelService.update(eq(1L), any(LabelDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/label/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("label/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("라벨 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("라벨명"),
                                fieldWithPath("description").optional().type(STRING).description("라벨 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void 라벨_카테고리_삭제_API_테스트() throws Exception {
        // given
        given(labelService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/label/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("label/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("라벨 ID")
                        )
                ));
    }

}