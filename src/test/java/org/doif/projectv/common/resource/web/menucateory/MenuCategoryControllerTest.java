package org.doif.projectv.common.resource.web.menucateory;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
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
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class MenuCategoryControllerTest extends ApiDocumentTest {

    @Test
    public void 메뉴_카테고리_조회_API_테스트() throws Exception {
        // given
        MenuCategoryDto.Result content = new MenuCategoryDto.Result(
                2L,
                "버전-이슈",
                "버전-이슈 메뉴 카테고리",
                EnableStatus.ENABLE,
                "MENU_CATEGORY_1",
                1L,
                1,
                "edit"
        );

        List<MenuCategoryDto.Result> results = Arrays.asList(content);
        MenuCategoryDto.Response response = new MenuCategoryDto.Response(results);

        given(menuCategoryService.select())
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/menu-categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("menu-category/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("메뉴-카테고리 ID"),
                                fieldWithPath("name").type(STRING).description("메뉴-카테고리명"),
                                fieldWithPath("description").type(STRING).description("메뉴-카테고리 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("메뉴-카테고리 코드"),
                                fieldWithPath("parentId").type(NUMBER).description("부모 메뉴-카테고리 ID"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("icon").type(STRING).description("아이콘"),
                                fieldWithPath("childrenItems").type(ARRAY).description("하위 카테고리")
                        )
                ));
    }

    @Test
    public void 메뉴_카테고리_추가_API_테스트() throws Exception {
        // given
        MenuCategoryDto.Insert insert = new MenuCategoryDto.Insert();
        insert.setParentId(1L);
        insert.setName("버전관리시스템");
        insert.setDescription("버전관리시스템 메뉴-카테고리");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setSort(2);
        insert.setIcon("heart");
        insert.setCode("MENU_CATEGORY_2");

        given(menuCategoryService.insert(any(MenuCategoryDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/menu-categories")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("menu-category/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("메뉴-카테고리명"),
                                fieldWithPath("description").optional().type(STRING).description("메뉴-카테고리 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("메뉴-카테고리 코드"),
                                fieldWithPath("parentId").type(NUMBER).description("부모 메뉴-카테고리 ID"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("icon").type(STRING).description("아이콘")
                        )
                ));
    }

    @Test
    public void 메뉴_카테고리_수정_API_테스트() throws Exception {
        // given
        MenuCategoryDto.Update update = new MenuCategoryDto.Update();
        update.setName("버전관리시스템");
        update.setDescription("버전관리시스템 메뉴-카테고리");
        update.setStatus(EnableStatus.ENABLE);
        update.setSort(2);
        update.setIcon("heart");

        given(menuCategoryService.update(eq(1L), any(MenuCategoryDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/menu-categories/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("menu-category/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("메뉴-카테고리 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("메뉴-카테고리명"),
                                fieldWithPath("description").optional().type(STRING).description("메뉴-카테고리 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("icon").type(STRING).description("아이콘")
                        )
                ));
    }

    @Test
    public void 메뉴_카테고리_삭제_API_테스트() throws Exception {
        // given
        given(menuCategoryService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/menu-categories/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("menu-category/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("메뉴-카테고리 ID")
                        )
                ));
    }

}