package org.doif.projectv.business.issue.web;

import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.business.patchlog.constant.PatchTarget;
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
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateLinkCode;
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

class VersionIssueControllerTest extends ApiDocumentTest {

    @Test
    public void 버전_이슈_이슈ID로_조회_API_테스트() throws Exception {
        // given
        List<VersionIssueDto.Result> versionIssueResults = new ArrayList<>();

        VersionIssueDto.Result versionIssueResult = new VersionIssueDto.Result();
        versionIssueResult.setVersionIssueId(1L);
        versionIssueResult.setModuleName("금결원 PG WEB/ADMIN");
        versionIssueResult.setVersionName("1.0.1");
        versionIssueResult.setIssueName("임진성씨는 왜 국밥성애자가 됬는가");
        versionIssueResult.setIssueStatus(IssueStatus.OPEN);
        versionIssueResult.setIssueContents("임진성씨는 뚝배기에 들어가 있는 음식이면 다 좋아하는 것인가 아닌가");
        versionIssueResult.setIssueYm("202011");
        versionIssueResult.setProgress(VersionIssueProgress.PROGRESSING);
        versionIssueResult.setAssignee("kjpmj");
        versionIssueResult.setRemark("비고 입니다.");

        versionIssueResults.add(versionIssueResult);

        VersionIssueDto.Response response = new VersionIssueDto.Response(versionIssueResults);

        given(versionIssueService.searchByIssueId(eq(1L)))
                .willReturn(versionIssueResults);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/version-issue/issue/{issueId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("version-issue/select-by-issue-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("issueId").description("이슈 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("versionIssueId").type(JsonFieldType.NUMBER).description("버전-이슈 ID"),
                                fieldWithPath("moduleName").type(JsonFieldType.STRING).description("모듈명"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("issueName").type(JsonFieldType.STRING).description("이슈명"),
                                fieldWithPath("issueStatus").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("issueContents").type(JsonFieldType.STRING).description("이슈 내용"),
                                fieldWithPath("issueYm").type(JsonFieldType.STRING).description("이슈 년월"),
                                fieldWithPath("progress").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.VERSION_ISSUE_PROGRESS)),
                                fieldWithPath("assignee").type(JsonFieldType.STRING).description("작업 예정자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).description("비고")
                        )
                ));
    }

    @Test
    public void 버전_이슈_버전ID로_조회_API_테스트() throws Exception {
        // given
        List<VersionIssueDto.Result> versionIssueResults = new ArrayList<>();

        VersionIssueDto.Result versionIssueResult = new VersionIssueDto.Result();
        versionIssueResult.setVersionIssueId(1L);
        versionIssueResult.setModuleName("금결원 PG WEB/ADMIN");
        versionIssueResult.setVersionName("1.0.1");
        versionIssueResult.setIssueName("오늘의 저녁은 뭘 먹어야 하나");
        versionIssueResult.setIssueStatus(IssueStatus.OPEN);
        versionIssueResult.setIssueContents("뭘 먹어야 잘 먹었다고 소문이 날까");
        versionIssueResult.setIssueYm("202011");
        versionIssueResult.setProgress(VersionIssueProgress.PROGRESSING);
        versionIssueResult.setAssignee("kjpmj");
        versionIssueResult.setRemark("비고 입니다.");

        versionIssueResults.add(versionIssueResult);

        VersionIssueDto.Response response = new VersionIssueDto.Response(versionIssueResults);

        given(versionIssueService.searchByVersionId(eq(1L)))
                .willReturn(versionIssueResults);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/version-issue/version/{versionId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("version-issue/select-by-version-id",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("versionId").description("버전 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("versionIssueId").type(JsonFieldType.NUMBER).description("버전-이슈 ID"),
                                fieldWithPath("moduleName").type(JsonFieldType.STRING).description("모듈명"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("issueName").type(JsonFieldType.STRING).description("이슈명"),
                                fieldWithPath("issueStatus").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("issueContents").type(JsonFieldType.STRING).description("이슈 내용"),
                                fieldWithPath("issueYm").type(JsonFieldType.STRING).description("이슈 년월"),
                                fieldWithPath("progress").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.VERSION_ISSUE_PROGRESS)),
                                fieldWithPath("assignee").type(JsonFieldType.STRING).description("작업 예정자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).description("비고")
                        )
                ));
    }

    @Test
    public void 버전_이슈_추가_API_테스트() throws Exception {
        // given
        VersionIssueDto.Insert insert = new VersionIssueDto.Insert();
        insert.setVersionId(1L);
        insert.setIssueId(1L);
        insert.setIssueYm("202011");
        insert.setProgress(VersionIssueProgress.TODO);
        insert.setAssignee("kjpmj");
        insert.setRemark("비고 입니다.");

        given(versionIssueService.insert(any(VersionIssueDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/version-issue")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version-issue/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).description("버전 ID"),
                                fieldWithPath("issueId").type(JsonFieldType.NUMBER).description("이슈 ID"),
                                fieldWithPath("issueYm").type(JsonFieldType.STRING).description("이슈 년월"),
                                fieldWithPath("progress").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.VERSION_ISSUE_PROGRESS)),
                                fieldWithPath("assignee").type(JsonFieldType.STRING).optional().description("작업 예정자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).optional().description("비고")
                        )
                ));
    }

    @Test
    public void 버전_이슈_수정_API_테스트() throws Exception {
        // given
        VersionIssueDto.Update update = new VersionIssueDto.Update();
        update.setIssueYm("202012");
        update.setProgress(VersionIssueProgress.COMPLETE);
        update.setAssignee("kjpmj");
        update.setRemark("비고 입니다.");

        given(versionIssueService.update(eq(1L), any(VersionIssueDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/version-issue/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version-issue/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버전-이슈 ID")
                        ),
                        requestFields(
                                fieldWithPath("issueYm").type(JsonFieldType.STRING).description("이슈 년월"),
                                fieldWithPath("progress").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.VERSION_ISSUE_PROGRESS)),
                                fieldWithPath("assignee").type(JsonFieldType.STRING).optional().description("작업 예정자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).optional().description("비고")
                        )
                ));
    }

    @Test
    public void 버전_이슈_삭제_API_테스트() throws Exception {
        // given
        given(versionIssueService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/version-issue/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version-issue/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버전-이슈 ID")
                        )
                ));
    }

    @Test
    public void 버전_이슈_현황_조회_API_테스트() throws Exception {
        // given
        List<VersionIssueDto.ResultOverview> resultOverviews = new ArrayList<>();
        VersionIssueDto.ResultOverview resultOverview = new VersionIssueDto.ResultOverview(
                1L,
                "금융결제원 PG",
                "금결원 PG WEB/ADMIN",
                "1.0.2",
                "임진성씨는 왜 집에 갔는가",
                IssueStatus.OPEN,
                IssueCategory.ERROR_MODIFY,
                VersionIssueProgress.TODO,
                "202011",
                "kjpmj",
                LocalDate.of(2020, 11, 25),
                LocalDate.of(2020, 11, 25),
                1.5,
                LocalDate.of(2020, 11, 26),
                LocalDate.of(2020, 11, 26)
        );

        resultOverviews.add(resultOverview);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<VersionIssueDto.ResultOverview> pages = new PageImpl<>(resultOverviews, pageRequest, 100);

        VersionIssueDto.ResponseOverview response = new VersionIssueDto.ResponseOverview(pages);

        VersionIssueDto.Search search = new VersionIssueDto.Search();
        search.setProjectId(1L);
        search.setModuleId(1L);
        search.setVersionId(1L);
        search.setIssueId(1L);
        search.setStatus(IssueStatus.OPEN);
        search.setCategory(IssueCategory.ERROR_MODIFY);
        search.setIssueYm("202011");
        search.setProgress(VersionIssueProgress.TODO);
        search.setAssignee("kjpmj");
        search.setPatchTarget(PatchTarget.DEV);

        given(versionIssueService.searchOverview(any(VersionIssueDto.Search.class), any(Pageable.class)))
                .willReturn(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/version-issue/overview")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("version-issue/overview",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("projectId").type(JsonFieldType.NUMBER).optional().description("프로젝트 ID"),
                                fieldWithPath("moduleId").type(JsonFieldType.NUMBER).optional().description("모듈 ID"),
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).optional().description("버전 ID"),
                                fieldWithPath("issueId").type(JsonFieldType.NUMBER).optional().description("이슈 ID"),
                                fieldWithPath("status").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("category").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("issueYm").type(JsonFieldType.STRING).optional().description("이슈 년월"),
                                fieldWithPath("progress").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.VERSION_ISSUE_PROGRESS)),
                                fieldWithPath("assignee").type(JsonFieldType.STRING).optional().description("작업 예정자"),
                                fieldWithPath("patchTarget").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.PATCH_TARGET))
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(JsonFieldType.OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("versionIssueId").type(JsonFieldType.NUMBER).description("버전-이슈 ID"),
                                fieldWithPath("projectName").type(JsonFieldType.STRING).description("프로젝트명"),
                                fieldWithPath("moduleName").type(JsonFieldType.STRING).description("모듈명"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("issueName").type(JsonFieldType.STRING).description("이슈명"),
                                fieldWithPath("status").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("statusName").type(JsonFieldType.STRING).description("이슈 상태 코드명"),
                                fieldWithPath("category").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("categoryName").type(JsonFieldType.STRING).description("이슈 분류 코드명"),
                                fieldWithPath("progress").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.VERSION_ISSUE_PROGRESS)),
                                fieldWithPath("progressName").type(JsonFieldType.STRING).description("이슈 진행 상황 코드명"),
                                fieldWithPath("issueYm").type(JsonFieldType.STRING).description("이슈 년월"),
                                fieldWithPath("assignee").type(JsonFieldType.STRING).description("작업 예정자"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 시작일"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 종료일"),
                                fieldWithPath("manDay").type(JsonFieldType.NUMBER).description("공수"),
                                fieldWithPath("patchDateDev").type(JsonFieldType.STRING).attributes(getDateFormat()).description("개발기 패치일"),
                                fieldWithPath("patchDateProd").type(JsonFieldType.STRING).attributes(getDateFormat()).description("상용기 패치일")
                        )
                ));
    }
}