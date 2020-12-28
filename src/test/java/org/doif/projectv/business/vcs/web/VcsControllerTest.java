package org.doif.projectv.business.vcs.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.business.vcs.dto.VcsDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VcsControllerTest extends ApiDocumentTest {

    @Test
    public void 버전관리시스템_로그_조회_API_테스트() throws Exception {
        // given
        VcsDto.SearchLog searchLog = new VcsDto.SearchLog();
        searchLog.setModuleId(1L);
        searchLog.setStartDate(LocalDate.of(2020, 12, 28));
        searchLog.setEndDate(LocalDate.of(2020, 12, 31));

        VcsDto.Log log = new VcsDto.Log("15000",
                LocalDateTime.of(2020, 12, 28, 12, 1, 54),
                "kmj",
                "블라 블라 커밋메시지를 잘 남기자"
        );

        List<VcsDto.Log> logs = Arrays.asList(log);
        VcsDto.Response<VcsDto.Log> response = new VcsDto.Response<>(logs);

        given(vcsService.searchLogByCondition(any(VcsDto.SearchLog.class)))
                .willReturn(logs);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/vcs/logs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(searchLog))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("vcs/logs",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("moduleId").type(NUMBER).description("모듈 ID"),
                                fieldWithPath("startDate").type(STRING).description("조회 시작일"),
                                fieldWithPath("endDate").type(STRING).description("조회 종료일")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("revision").type(STRING).description("Commit 번호"),
                                fieldWithPath("date").type(STRING).description("날짜"),
                                fieldWithPath("author").type(STRING).description("작성자"),
                                fieldWithPath("message").type(STRING).description("커밋 메시지")
                        )
                ));
    }

}