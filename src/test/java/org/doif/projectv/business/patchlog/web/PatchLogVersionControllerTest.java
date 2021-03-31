package org.doif.projectv.business.patchlog.web;

import org.doif.projectv.business.patchlog.dto.PatchLogVersionDto;
import org.doif.projectv.business.patchlog.entity.PatchLogVersion;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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
import static org.junit.jupiter.api.Assertions.*;

class PatchLogVersionControllerTest extends ApiDocumentTest {

    @Test
    public void 패치에_포함된_버전_조회_API_테스트() throws Exception {
        // given
        PatchLogVersionDto.Result content = new PatchLogVersionDto.Result(
                1L,
                1L,
                1L,
                "v1.0.1",
                "버전설명",
                "정산 모듈"
        );

        List<PatchLogVersionDto.Result> results = Arrays.asList(content);
        PatchLogVersionDto.Response response = new PatchLogVersionDto.Response(results);
        
        given(patchLogVersionService.searchPatchLogVersionsByPatchLogId(eq(1L)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/patch-logs/{id}/patch-log-versions", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );
        
        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("patch-log-version/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("패치로그 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("patchLogVersionId").type(NUMBER).description("패치로그-버전 ID"),
                                fieldWithPath("patchLogId").type(NUMBER).description("패치로그 ID"),
                                fieldWithPath("versionId").type(NUMBER).description("버전 ID"),
                                fieldWithPath("versionName").type(STRING).description("버전명"),
                                fieldWithPath("versionDescription").type(STRING).description("버전설명"),
                                fieldWithPath("moduleName").type(STRING).description("모듈명")
                        )
                ));
    }

    @Test
    public void 패치에_버전_추가_API_테스트() throws Exception {
        // given
        PatchLogVersionDto.Insert insert = new PatchLogVersionDto.Insert();
        insert.setVersionId(1L);
        insert.setPatchLogId(1L);

        given(patchLogVersionService.insert(any(PatchLogVersionDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/patch-log-versions")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-log-version/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("patchLogId").type(NUMBER).description("패치로그 ID"),
                                fieldWithPath("versionId").type(NUMBER).description("버전 ID")
                        )
                ));
    }
}