package org.doif.projectv.common.user.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.dto.LabelDto;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.TabDto;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.user.dto.UserRoleDto;
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
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserRoleControllerTest extends ApiDocumentTest {

    @Test
    public void UserRole_조회_API_테스트() throws Exception {
        // given
        UserRoleDto.Search search = new UserRoleDto.Search();
        search.setUserId("kjpmj");

        UserRoleDto.ResultRole content = new UserRoleDto.ResultRole(
                1L,
                "관리자 Role",
                "관리자 Role 입니다.",
                EnableStatus.ENABLE,
                true
        );

        List<UserRoleDto.ResultRole> results = Arrays.asList(content);
        UserRoleDto.Response response = new UserRoleDto.Response(results);

        given(userRoleService.selectRole(any(UserRoleDto.Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/user-role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("user-role/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("userId").type(STRING).description("유저 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("roleId").type(NUMBER).description("Role ID"),
                                fieldWithPath("name").type(STRING).description("Role명"),
                                fieldWithPath("description").type(STRING).description("Role 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("checked").type(BOOLEAN).description("할당 여부")
                        )
                ));
    }

    @Test
    public void UserRole_할당_API_테스트() throws Exception {
        // given
        List<Long> roleIds = Arrays.asList(1L, 2L);

        UserRoleDto.Allocate allocate = new UserRoleDto.Allocate();
        allocate.setUserId("kjpmj");
        allocate.setRoleIds(roleIds);

        given(userRoleService.allocate(any(UserRoleDto.Allocate.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/user-role")
                        .content(objectMapper.writeValueAsBytes(allocate))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user-role/allocate",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("userId").type(STRING).description("유저 ID"),
                                fieldWithPath("roleIds").type(ARRAY).description("할당할 Role ID 배열")
                        )
                ));
    }

}