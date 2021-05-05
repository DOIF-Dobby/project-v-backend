package org.doif.projectv.common.resource.web.menu;

import static org.junit.jupiter.api.Assertions.*;

import org.codehaus.plexus.util.StringUtils;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.constant.MenuType;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.MenuDto;
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

class MenuControllerTest extends ApiDocumentTest {


    @Test
    public void 메뉴_조회_API_테스트() throws Exception {
        // given
        MenuDto.Result content = new MenuDto.Result(
                2L,
                "버전-이슈 관리",
                "버전-이슈 관리 메뉴입니다.",
                EnableStatus.ENABLE,
                "MENU_1",
                1L,
                1,
                MenuType.MENU,
                "heart",
                "/version-issue"
        );

        content.setDepth(2);
        content.setPaddingName("버전-이슈 관리");
        content.setPath("1-1");

        List<MenuDto.Result> results = Arrays.asList(content);
        MenuDto.Response response = new MenuDto.Response(results);

        given(menuService.select())
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/menus")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("menu/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("메뉴 ID"),
                                fieldWithPath("name").type(STRING).description("메뉴명"),
                                fieldWithPath("description").type(STRING).description("메뉴 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("메뉴 코드"),
                                fieldWithPath("parentId").type(NUMBER).description("부모 메뉴-카테고리 ID"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("icon").type(STRING).description("아이콘"),
                                fieldWithPath("paddingName").type(STRING).description("패딩된 이름"),
                                fieldWithPath("depth").type(NUMBER).description("메뉴 깊이"),
                                fieldWithPath("path").type(STRING).description("정렬을 위한 항목"),
                                fieldWithPath("type").type(STRING).description(generateLinkCode(CodeEnum.MENU_TYPE)),
                                fieldWithPath("typeName").type(STRING).description(generateText(CodeEnum.MENU_TYPE)),
                                fieldWithPath("url").type(STRING).description("메뉴 URL"),
                                fieldWithPath("childrenItems").type(ARRAY).description("하위 메뉴/카테고리"),
                                fieldWithPath("subRows").type(ARRAY).description("하위 메뉴/카테고리")
                        )
                ));
    }

    @Test
    public void 메뉴_추가_API_테스트() throws Exception {
        // given
        MenuDto.Insert insert = new MenuDto.Insert();
        insert.setMenuCategoryId(1L);
        insert.setName("버전관리시스템");
        insert.setDescription("버전관리시스템 메뉴-카테고리");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setSort(1);
        insert.setIcon("heart");
        insert.setUrl("/vcs");
        insert.setCode("MENU_1");

        given(menuService.insert(any(MenuDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/menus")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("menu/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("메뉴"),
                                fieldWithPath("description").optional().type(STRING).description("메뉴 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("메뉴 코드"),
                                fieldWithPath("menuCategoryId").type(NUMBER).description("부모 메뉴-카테고리 ID"),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("icon").type(STRING).description("아이콘"),
                                fieldWithPath("url").type(STRING).description("메뉴 URL")
                        )
                ));
    }

    @Test
    public void 메뉴_수정_API_테스트() throws Exception {
        // given
        MenuDto.Update update = new MenuDto.Update();
        update.setName("버전관리시스템 관리");
        update.setDescription("버전관리시스템 관리 메뉴입니다.");
        update.setStatus(EnableStatus.ENABLE);
        update.setSort(1);
        update.setIcon("heart");
        update.setUrl("/vcs");

        given(menuService.update(eq(1L), any(MenuDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/menus/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("menu/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("메뉴 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("메뉴"),
                                fieldWithPath("description").optional().type(STRING).description("메뉴 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("icon").type(STRING).description("아이콘"),
                                fieldWithPath("url").type(STRING).description("메뉴 URL")
                        )
                ));
    }

    @Test
    public void 메뉴_삭제_API_테스트() throws Exception {
        // given
        given(menuService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/menus/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("menu/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("메뉴 ID")
                        )
                ));
    }

}