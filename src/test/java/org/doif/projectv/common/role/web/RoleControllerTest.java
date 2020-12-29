package org.doif.projectv.common.role.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.MessageDto;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.role.dto.RoleDto;
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

class RoleControllerTest extends ApiDocumentTest {

    @Test
    public void Role_조회_API_테스트() throws Exception {
        // given
        RoleDto.Result content = new RoleDto.Result(
                1L,
                "개발자 Role",
                "개발자 Role 입니다.",
                EnableStatus.ENABLE
        );

        List<RoleDto.Result> results = Arrays.asList(content);
        RoleDto.Response response = new RoleDto.Response(results);

        given(roleService.select())
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/role")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("role/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("roleId").type(NUMBER).description("Role ID"),
                                fieldWithPath("name").type(STRING).description("Role명"),
                                fieldWithPath("description").type(STRING).description("Role 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void Role_추가_API_테스트() throws Exception {
        // given
        RoleDto.Insert insert = new RoleDto.Insert();
        insert.setName("관리자 Role");
        insert.setDescription("관리자 Role 입니다.");
        insert.setStatus(EnableStatus.ENABLE);

        given(roleService.insert(any(RoleDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/role")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("role/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("Role명"),
                                fieldWithPath("description").type(STRING).optional().description("Role 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void Role_수정_API_테스트() throws Exception {
        // given
        RoleDto.Update update = new RoleDto.Update();
        update.setName("관리자 Role");
        update.setDescription("관리자 Role 입니다.");
        update.setStatus(EnableStatus.ENABLE);

        given(roleService.update(eq(1L), any(RoleDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/role/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("role/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("Role ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("Role명"),
                                fieldWithPath("description").type(STRING).optional().description("Role 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void Role_삭제_API_테스트() throws Exception {
        // given
        given(roleService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/role/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("role/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("Role ID")
                        )
                ));
    }
}