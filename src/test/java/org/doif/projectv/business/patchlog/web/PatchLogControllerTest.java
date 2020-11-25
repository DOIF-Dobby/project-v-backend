package org.doif.projectv.business.patchlog.web;

import org.doif.projectv.business.patchlog.constant.PatchStatus;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
import org.doif.projectv.business.patchlog.dto.PatchLogDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.jupiter.api.Assertions.*;

class PatchLogControllerTest extends ApiDocumentTest {

    @Test
    public void 패치로그_조회_API_테스트() throws Exception {
        // given
        List<PatchLogDto.Result> patchLogResults = new ArrayList<>();
        PatchLogDto.Result patchLogResult = new PatchLogDto.Result(
                1L,
                "금결원 PG WEB/ADMIN",
                "1.0.2",
                PatchTarget.DEV,
                PatchStatus.SCHEDULE,
                LocalDate.of(2020, 11, 25),
                LocalDate.of(2020, 11, 25),
                "kjpmj",
                "비고입니다."
        );

        patchLogResults.add(patchLogResult);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<PatchLogDto.Result> pages = new PageImpl<>(patchLogResults, pageRequest, 100);

        PatchLogDto.Search search = new PatchLogDto.Search();
        search.setModuleId(1L);
        search.setVersionId(1L);
        search.setTarget(PatchTarget.DEV);
        search.setStatus(PatchStatus.SCHEDULE);
        search.setPatchScheduleDateGoe(LocalDate.of(2020, 11, 25));
        search.setPatchScheduleDateLt(LocalDate.of(2020, 11, 25));
        search.setPatchDateGoe(LocalDate.of(2020, 11, 25));
        search.setPatchDateLt(LocalDate.of(2020, 11, 25));
        search.setWorker("kjpmj");

        PatchLogDto.Response response = new PatchLogDto.Response(pages);

        given(patchLogService.searchByCondition(any(PatchLogDto.Search.class), any(Pageable.class)))
                .willReturn(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/patch-log")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("patch-log/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("moduleId").type(JsonFieldType.NUMBER).optional().description("모듈 ID"),
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).optional().description("버전 ID"),
                                fieldWithPath("target").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.PATCH_TARGET)),
                                fieldWithPath("status").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.PATCH_STATUS)),
                                fieldWithPath("patchScheduleDateGoe").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("패치 예정일 From"),
                                fieldWithPath("patchScheduleDateLt").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("패치 예정일 To"),
                                fieldWithPath("patchDateGoe").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("패치일 From"),
                                fieldWithPath("patchDateLt").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("패치일 To"),
                                fieldWithPath("worker").type(JsonFieldType.STRING).optional().description("작업자")
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(JsonFieldType.OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("patchLogId").type(JsonFieldType.NUMBER).description("패치로그 ID"),
                                fieldWithPath("moduleName").type(JsonFieldType.STRING).description("모듈명"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("target").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.PATCH_TARGET)),
                                fieldWithPath("targetName").type(JsonFieldType.STRING).description(generateText(CodeEnum.PATCH_TARGET)),
                                fieldWithPath("status").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.PATCH_STATUS)),
                                fieldWithPath("statusName").type(JsonFieldType.STRING).description(generateText(CodeEnum.PATCH_STATUS)),
                                fieldWithPath("patchScheduleDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("패치 예정일"),
                                fieldWithPath("patchDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("패치일"),
                                fieldWithPath("worker").type(JsonFieldType.STRING).description("작업자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).description("비고")
                        )
                ));
    }

    @Test
    public void 패치로그_추가_API_테스트() throws Exception {
        // given
        PatchLogDto.Insert insert = new PatchLogDto.Insert();
        insert.setVersionId(1L);
        insert.setTarget(PatchTarget.DEV);
        insert.setStatus(PatchStatus.SCHEDULE);
        insert.setPatchScheduleDate(LocalDate.of(2020, 11, 25));
        insert.setWorker("kjpmj");
        insert.setRemark("비고 입니다.");

        given(patchLogService.insert(any(PatchLogDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/patch-log")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-log/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).description("버전 ID"),
                                fieldWithPath("target").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.PATCH_TARGET)),
                                fieldWithPath("status").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.PATCH_STATUS)),
                                fieldWithPath("patchScheduleDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("패치 예정일"),
                                fieldWithPath("worker").type(JsonFieldType.STRING).optional().description("작업자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).optional().description("비고")
                        )
                ));
    }

    @Test
    public void 패치로그_수정_API_테스트() throws Exception {
        // given
        PatchLogDto.Update update = new PatchLogDto.Update();
        update.setTarget(PatchTarget.PROD);
        update.setStatus(PatchStatus.COMPLETE);
        update.setPatchScheduleDate(LocalDate.of(2020, 11, 26));
        update.setPatchDate(LocalDate.of(2020, 11, 26));
        update.setWorker("kjpmj");
        update.setRemark("비고입니다.");

        given(patchLogService.update(eq(1L), any(PatchLogDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/patch-log/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-log/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("패치로그 ID")
                        ),
                        requestFields(
                                fieldWithPath("target").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.PATCH_TARGET)),
                                fieldWithPath("status").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.PATCH_STATUS)),
                                fieldWithPath("patchScheduleDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("패치 예정일"),
                                fieldWithPath("patchDate").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("패치일"),
                                fieldWithPath("worker").type(JsonFieldType.STRING).optional().description("작업자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).optional().description("비고")
                        )
                ));
    }

    @Test
    public void 패치로그_삭제_API_테스트() throws Exception {
        // given
        given(patchLogService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/patch-log/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("patch-log/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("패치로그 ID")
                        )
                ));
    }
}