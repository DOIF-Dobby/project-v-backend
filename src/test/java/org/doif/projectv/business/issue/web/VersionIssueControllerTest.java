package org.doif.projectv.business.issue.web;

import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.constant.VersionIssueProgress;
import org.doif.projectv.business.issue.dto.VersionIssueDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;

class VersionIssueControllerTest extends ApiDocumentTest {

    @Test
    public void 모듈_이슈_조회_API_테스트() throws Exception {
        // given
        VersionIssueDto.Search search = new VersionIssueDto.Search();
        search.setIssueId(1L);

        VersionIssueDto.Result content = new VersionIssueDto.Result();
        content.setVersionIssueId(1L);
        content.setIssueName("클래스 이름 짓기 너무 어렵다.");
        content.setIssueContents("클래스 이름 뭐로 해야 되냐");
        content.setAssignee("kjpmj");
        content.setIssueStatus(IssueStatus.OPEN);
        content.setModuleName("금결원 어드민 WEB");
        content.setIssueYm("202011");
        content.setProgress(VersionIssueProgress.PROGRESSING);
        content.setRemark("비고입니다.");

        List<VersionIssueDto.Result> results = Arrays.asList(content);

        VersionIssueDto.Response response = new VersionIssueDto.Response(results);

        given(versionIssueService.searchByIssueId(any(VersionIssueDto.Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/module-issue")
                        .content(objectMapper.writeValueAsString(search))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
//        result.andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(response)))
//                .andDo(print())
//                .andDo(document("module-issue/select",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        requestFields(
//                                fieldWithPath("issueId").type(STRING).description("이슈 ID")
//                        ),
//                        responseFields(
//                                beneathPath("content").withSubsectionId("content"),
//                                fieldWithPath("moduleIssueId").type(NUMBER).description("모듈-이슈 ID"),
//                                fieldWithPath("moduleName").type(STRING).description("모듈명"),
//                                fieldWithPath("issueName").type(STRING).description("이슈명"),
//                                fieldWithPath("issueContents").type(STRING).description("이슈 내용"),
//                                fieldWithPath("assignee").type(STRING).description("작업 예정자"),
//                                fieldWithPath("issueStatus").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
//                                fieldWithPath("progress").type(STRING).description(generateLinkCode(CodeEnum.MODULE_ISSUE_PROGRESS)),
//                                fieldWithPath("issueYm").type(STRING).description("작업 년월"),
//                                fieldWithPath("remark").type(STRING).description("비고")
//                        )
//                ));
    }
}