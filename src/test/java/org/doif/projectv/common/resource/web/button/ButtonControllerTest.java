package org.doif.projectv.common.resource.web.button;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.dto.ButtonDto;
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

class ButtonControllerTest extends ApiDocumentTest {

    @Test
    public void 버튼_조회_API_테스트() throws Exception {
        // given
        ButtonDto.Search search = new ButtonDto.Search();
        search.setPageId(1L);

        ButtonDto.Result content = new ButtonDto.Result(
                2L,
                "버전 수정",
                "버전 수정 버튼",
                EnableStatus.ENABLE,
                "BUTTON_1",
                "/api/version/{id}",
                HttpMethod.PUT,
                1L,
                "edit"
        );

        List<ButtonDto.Result> results = Arrays.asList(content);
        ButtonDto.Response response = new ButtonDto.Response(results);

        given(buttonService.selectByPage(any(ButtonDto.Search.class)))
                .willReturn(results);
        
        // when
        ResultActions result = mockMvc.perform(
                get("/api/resources/button")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("button/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("resourceId").type(NUMBER).description("버튼 ID"),
                                fieldWithPath("name").type(STRING).description("버튼명"),
                                fieldWithPath("description").type(STRING).description("버튼 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("버튼 코드"),
                                fieldWithPath("url").type(STRING).description("버튼 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("icon").type(STRING).description("아이콘")
                        )
                ));
    }

    @Test
    public void 버튼_추가_API_테스트() throws Exception {
        // given
        ButtonDto.Insert insert = new ButtonDto.Insert();
        insert.setName("버전 삭제");
        insert.setDescription("버전 삭제 버튼");
        insert.setStatus(EnableStatus.ENABLE);
        insert.setPageId(1L);
        insert.setIcon("delete");
        insert.setUrl("/api/version/{id}");
        insert.setHttpMethod(HttpMethod.DELETE);
        insert.setCode("BUTTON_1");

        given(buttonService.insert(any(ButtonDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/resources/button")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("button/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("버튼명"),
                                fieldWithPath("description").type(STRING).optional().description("버튼 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("code").type(STRING).description("버튼 코드"),
                                fieldWithPath("url").type(STRING).description("버튼 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("icon").type(STRING).description("아이콘")
                        )
                ));
    }

    @Test
    public void 버튼_수정_API_테스트() throws Exception {
        // given
        ButtonDto.Update update = new ButtonDto.Update();
        update.setName("버전 삭제");
        update.setDescription("버전 삭제 버튼");
        update.setStatus(EnableStatus.ENABLE);
        update.setIcon("delete");
        update.setUrl("/api/version/{id}");
        update.setHttpMethod(HttpMethod.DELETE);

        given(buttonService.update(eq(1L), any(ButtonDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/resources/button/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("button/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버튼 ID")
                        ),
                        requestFields(
                                fieldWithPath("name").type(STRING).description("버튼명"),
                                fieldWithPath("description").type(STRING).optional().description("버튼 설명"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("url").type(STRING).description("버튼 URL"),
                                fieldWithPath("httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("icon").type(STRING).description("아이콘")
                        )
                ));
    }
    
    @Test
    public void 버튼_삭제_API_테스트() throws Exception {
        // given
        given(buttonService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/resources/button/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("button/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버튼 ID")
                        )
                ));
    }
    
}