package org.doif.projectv.business.client.web;

import static org.doif.projectv.common.api.DocumentLinkGenerator.generateLinkCode;
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateText;
import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.business.client.dto.ClientDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentRequest;
import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentResponse;
import static org.doif.projectv.common.api.DocumentFormatGenerator.getDateTimeFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ClientControllerTest extends ApiDocumentTest {

    @Test
    void 고객사_조회_API_테스트() throws Exception {
        // given
        ClientDto.Result content = new ClientDto.Result(1L, "금융결제원", "VVVVVIP", "01012345678", "1112233333", "12345", "서울", "강남");
        List<ClientDto.Result> results = Arrays.asList(content);

        ClientDto.Response response = new ClientDto.Response(results);

        given(clientService.select())
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("client/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("clientId").type(NUMBER).description("고객사 ID"),
                                fieldWithPath("name").type(STRING).description("고객사명"),
                                fieldWithPath("description").type(STRING).description("고객사 설명"),
                                fieldWithPath("tel").type(STRING).description("고객사 전화번호"),
                                fieldWithPath("bizRegNo").type(STRING).description("고객사 사업자 번호"),
                                fieldWithPath("zipCode").type(STRING).description("우편번호"),
                                fieldWithPath("basicAddr").type(STRING).description("기본주소"),
                                fieldWithPath("detailAddr").type(STRING).description("상세주소")
                        )
                ));
    }

    @Test
    public void 고객사_추가_API_테스트() throws Exception {
        // given
        ClientDto.Insert insert = new ClientDto.Insert("금융결제원", "VVVVVIP", "01012345678", "1112233333", "12345", "서울", "강남");

        given(clientService.insert(any(ClientDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/clients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(insert))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("client/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("고객사명"),
                                fieldWithPath("description").type(STRING).description("고객사 설명"),
                                fieldWithPath("tel").type(STRING).description("고객사 전화번호"),
                                fieldWithPath("bizRegNo").type(STRING).description("고객사 사업자 번호"),
                                fieldWithPath("zipCode").type(STRING).description("우편번호"),
                                fieldWithPath("basicAddr").type(STRING).description("기본주소"),
                                fieldWithPath("detailAddr").type(STRING).description("상세주소")
                        )
                ));
    }

    @Test
    public void 고객사_수정_API_테스트() throws Exception {
        // given
        ClientDto.Update update = new ClientDto.Update("금융결제원", "VVVVVIP", "01012345678", "1112233333", "12345", "서울", "강남");

        given(clientService.update(eq(1L), any(ClientDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/clients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(update))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("client/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("고객사 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("고객사명"),
                                fieldWithPath("description").type(STRING).description("고객사 설명"),
                                fieldWithPath("tel").type(STRING).description("고객사 전화번호"),
                                fieldWithPath("bizRegNo").type(STRING).description("고객사 사업자 번호"),
                                fieldWithPath("zipCode").type(STRING).description("우편번호"),
                                fieldWithPath("basicAddr").type(STRING).description("기본주소"),
                                fieldWithPath("detailAddr").type(STRING).description("상세주소")
                        )
                ));
    }

    @Test
    public void 고객사_삭제_API_테스트() throws Exception {
        // given
        given(clientService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/clients/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("client/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("고객사 ID")
                        )
                ));
    }
}