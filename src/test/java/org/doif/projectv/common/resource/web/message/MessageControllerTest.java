package org.doif.projectv.common.resource.web.message;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.constant.MessageType;
import org.doif.projectv.common.resource.dto.MenuCategoryDto;
import org.doif.projectv.common.resource.dto.MessageDto;
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

class MessageControllerTest extends ApiDocumentTest {

    @Test
    public void 메시지_조회_API_테스트() throws Exception {
        // given
        MessageDto.Result content = new MessageDto.Result(
                1L,
                "정말 삭제하시겠습니까?",
                "정말 삭제하시겠습니까?",
                EnableStatus.ENABLE,
                "MSG_WARN_001",
                MessageType.WARN
        );

        List<MessageDto.Result> results = Arrays.asList(content);
        MessageDto.Response response = new MessageDto.Response(results);

        given(messageService.select())
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("message/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("메시지 ID"),
                                fieldWithPath("name").type(STRING).description("메시지명"),
                                fieldWithPath("description").type(STRING).description("메시지 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("메시지 코드"),
                                fieldWithPath("type").type(STRING).description(generateLinkCode(CodeEnum.MESSAGE_TYPE)),
                                fieldWithPath("typeName").type(STRING).description(generateText(CodeEnum.MESSAGE_TYPE))
                        )
                ));
    }

    @Test
    public void 메시지_추가_API_테스트() throws Exception {
        // given
        MessageDto.Insert insert = new MessageDto.Insert();
        insert.setName("하위 이슈가 존재하여 삭제할 수 없습니다.");
        insert.setDescription("하위 이슈가 존재하여 삭제할 수 없습니다.");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setCode("MSG_WARN_002");
        insert.setType(MessageType.WARN);

        given(messageService.insert(any(MessageDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/messages")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("message/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("메시지명"),
                                fieldWithPath("description").type(STRING).optional().description("메시지 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("메시지 코드"),
                                fieldWithPath("type").type(STRING).description(generateLinkCode(CodeEnum.MESSAGE_TYPE))
                        )
                ));
    }

    @Test
    public void 메시지_수정_API_테스트() throws Exception {
        // given
        MessageDto.Update update = new MessageDto.Update();
        update.setName("하위 이슈가 존재하여 삭제할 수 없습니다.");
        update.setDescription("하위 이슈가 존재하여 삭제할 수 없습니다.");
        update.setStatus(EnableStatus.ENABLE);
        update.setType(MessageType.WARN);

        given(messageService.update(eq(1L), any(MessageDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/messages/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("message/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("메시지 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("메시지명"),
                                fieldWithPath("description").type(STRING).optional().description("메시지 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("type").type(STRING).description(generateLinkCode(CodeEnum.MESSAGE_TYPE))
                        )
                ));
    }

    @Test
    public void 메시지_삭제_API_테스트() throws Exception {
        // given
        given(messageService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/messages/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("message/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("메시지 ID")
                        )
                ));
    }

}