package org.doif.projectv.business.vcs.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.business.vcs.dto.VcsAuthInfoDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.status.EnableStatus;
import org.doif.projectv.common.util.MultiValueMapConverter;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VcsAuthInfoControllerTest extends ApiDocumentTest {

    @Test
    public void 버전관리시스템_인증정보_조회_API_테스트() throws Exception {
        // given
        VcsAuthInfoDto.Search search = new VcsAuthInfoDto.Search();
        search.setUserId("kjpmj");

        VcsAuthInfoDto.Result content = new VcsAuthInfoDto.Result(
                1L,
                "kjpmj",
                VcsType.SVN,
                "kmj_svn_id",
                "kmj_svn_pw",
                EnableStatus.ENABLE
        );

        List<VcsAuthInfoDto.Result> results = Arrays.asList(content);
        VcsAuthInfoDto.Response response = new VcsAuthInfoDto.Response(results);

        given(vcsAuthInfoService.searchByCondition(any(VcsAuthInfoDto.Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/vcs-auth-infos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("vcs-auth-info/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("userId").description("유저 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("vcsAuthInfoId").type(NUMBER).description("버전관리 시스템 인증정보 ID"),
                                fieldWithPath("userId").type(STRING).description("유저 ID"),
                                fieldWithPath("vcsType").type(STRING).description(generateLinkCode(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsTypeName").type(STRING).description(generateText(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsAuthId").type(STRING).description("버전관리 시스템 인증 ID"),
                                fieldWithPath("vcsAuthPassword").type(STRING).description("버전관리 시스템 인증 PW"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void 버전관리시스템_인증정보_추가_API_테스트() throws Exception {
        // given
        VcsAuthInfoDto.Insert insert = new VcsAuthInfoDto.Insert();
        insert.setVcsAuthId("kmj_svn_id");
        insert.setVcsAuthPassword("kmj_svn_pw");
        insert.setUserId("kjpmj");
        insert.setVcsType(VcsType.SVN);
        insert.setStatus(EnableStatus.ENABLE);

        given(vcsAuthInfoService.insert(any(VcsAuthInfoDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/vcs-auth-infos")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("vcs-auth-info/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("userId").type(STRING).description("유저 ID"),
                                fieldWithPath("vcsType").type(STRING).description(generateLinkCode(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsAuthId").type(STRING).description("버전관리 시스템 인증 ID"),
                                fieldWithPath("vcsAuthPassword").type(STRING).description("버전관리 시스템 인증 PW"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void 버전관리시스템_인증정보_수정_API_테스트() throws Exception {
        // given
        VcsAuthInfoDto.Update update = new VcsAuthInfoDto.Update();
        update.setVcsAuthId("kmj_svn_id");
        update.setVcsAuthPassword("kmj_svn_pw");
        update.setStatus(EnableStatus.DISABLE);

        given(vcsAuthInfoService.update(eq(1L), any(VcsAuthInfoDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/vcs-auth-infos/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("vcs-auth-info/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버전관리 시스템 인증정보 ID")
                        ),
                        requestFields(
                                fieldWithPath("vcsAuthId").type(STRING).description("버전관리 시스템 인증 ID"),
                                fieldWithPath("vcsAuthPassword").type(STRING).description("버전관리 시스템 인증 PW"),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS))
                        )
                ));
    }

    @Test
    public void 버전관리시스템_인증정보_삭제_API_테스트() throws Exception {
        // given
        given(vcsAuthInfoService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/vcs-auth-infos/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("vcs-auth-info/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버전 ID")
                        )
                ));
    }
}