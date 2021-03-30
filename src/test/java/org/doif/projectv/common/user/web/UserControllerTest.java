package org.doif.projectv.common.user.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.user.constant.UserStatus;
import org.doif.projectv.common.user.dto.UserDto;
import org.doif.projectv.common.util.MultiValueMapConverter;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentRequest;
import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentResponse;
import static org.doif.projectv.common.api.DocumentFormatGenerator.getDateFormat;
import static org.doif.projectv.common.api.DocumentLinkGenerator.*;
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateLinkCode;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class UserControllerTest extends ApiDocumentTest {

    @Test
    public void 유저_조회_API_테스트() throws Exception {
        // given
        UserDto.Search search = new UserDto.Search();
        search.setName("김씨");
        search.setId("kjpmj");
        search.setStatus(UserStatus.VALID);

        UserDto.Result content = new UserDto.Result("kjpmj", "김씨", UserStatus.VALID);
        List<UserDto.Result> results = Arrays.asList(content);
        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<UserDto.Result> pages = new PageImpl<>(results, pageRequest, 100);
        UserDto.Response response = new UserDto.Response(pages);

        given(userService.selectByCondition(any(UserDto.Search.class), any(Pageable.class)))
                .willReturn(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("user/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("id").description("유저 ID"),
                                parameterWithName("name").description("유저 이름"),
                                parameterWithName("status").description("유저 상태")
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("id").type(STRING).description("작업 ID"),
                                fieldWithPath("name").type(STRING).description("버전명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.USER_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.USER_STATUS))
                        )
                ));
    }

    @Test
    public void 유저_추가_API_테스트() throws Exception {
        // given
        UserDto.Insert insert = new UserDto.Insert();
        insert.setId("kjpmj");
        insert.setName("김씨");
        insert.setPassword("password는 어렵게");
        insert.setStatus(UserStatus.VALID);

        given(userService.insert(any(UserDto.Insert.class)))
                .willReturn(ResponseUtil.ok());
        
        // when
        ResultActions result = mockMvc.perform(
                post("/api/users")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("id").type(STRING).description("유저 ID"),
                                fieldWithPath("name").type(STRING).description("유저명"),
                                fieldWithPath("password").type(STRING).description("패스워드"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.USER_STATUS))
                        )
                ));
    }

    @Test
    public void 유저_수정_API_테스트() throws Exception {
        // given
        UserDto.Update update = new UserDto.Update();
        update.setName("김씨");
        update.setStatus(UserStatus.INVALID);

        given(userService.update(eq("kjpmj"), any(UserDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/users/{id}", "kjpmj")
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("유저 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("유저명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.USER_STATUS))
                        )
                ));
    }

    @Test
    public void 유저_삭제_API_테스트() throws Exception {
        // given
        given(userService.delete(eq("kjpmj")))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/users/{id}", "kjpmj")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("user/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("유저 ID")
                        )
                ));
    }
}