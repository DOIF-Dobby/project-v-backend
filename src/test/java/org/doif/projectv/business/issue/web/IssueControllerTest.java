package org.doif.projectv.business.issue.web;

import org.doif.projectv.business.issue.constant.IssueCategory;
import org.doif.projectv.business.issue.constant.IssueStatus;
import org.doif.projectv.business.issue.dto.IssueDto;
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
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentRequest;
import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentResponse;
import static org.doif.projectv.common.api.DocumentFormatGenerator.getDateTimeFormat;
import static org.doif.projectv.common.api.DocumentLinkGenerator.*;
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateText;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class IssueControllerTest extends ApiDocumentTest {

    @Test
    public void 이슈_조회_API_테스트() throws Exception {
        // given
        IssueDto.Search search = new IssueDto.Search();
        search.setCategory(IssueCategory.NEW_DEVELOP);
        search.setStatus(IssueStatus.OPEN);
        search.setContents("임진성은 왜 늦는가");

        IssueDto.Result content = new IssueDto.Result();
        content.setIssueId(1L);
        content.setCategory(IssueCategory.NEW_DEVELOP);
        content.setCategoryName(IssueCategory.NEW_DEVELOP.getMessage());
        content.setContents("임진성은 왜 늦는가");
        content.setIssueName("임진성 바보");
        content.setStatus(IssueStatus.OPEN);
        content.setStatusName(IssueStatus.OPEN.getMessage());
        content.setCreatedDate(LocalDateTime.now());
        content.setCreatedBy("kjpmj");
        content.setLastModifiedDate(LocalDateTime.now());
        content.setLastModifiedBy("kjpmj");

        List<IssueDto.Result> results = Arrays.asList(content);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<IssueDto.Result> pages = new PageImpl<>(results, pageRequest, 100);

        IssueDto.Response response = new IssueDto.Response(pages);

        given(issueService.searchByCondition(any(IssueDto.Search.class), any(Pageable.class)))
                .willReturn(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/issues")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("issue/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("category").description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                parameterWithName("status").description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                parameterWithName("contents").description("이슈 내용")
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("issueId").type(NUMBER).description("이슈 ID"),
                                fieldWithPath("issueName").type(STRING).description("이슈명"),
                                fieldWithPath("category").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("categoryName").type(STRING).description(generateText(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("contents").type(STRING).description("이슈 내용"),
                                fieldWithPath("createdDate").type(STRING).attributes(getDateTimeFormat()).description("생성일"),
                                fieldWithPath("lastModifiedDate").type(STRING).attributes(getDateTimeFormat()).description("수정일"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("lastModifiedBy").type(STRING).description("수정자")
                        )
                ));
    }

    @Test
    public void 이슈_추가_API_테스트() throws Exception {
        // given
        IssueDto.Insert insert = new IssueDto.Insert();
        insert.setIssueName("진성씨는 왜 정보처리기사 시험에 합격하지 못 하였는가");
        insert.setCategory(IssueCategory.ERROR_MODIFY);
        insert.setStatus(IssueStatus.OPEN);
        insert.setContents("진성씨는 바보인가?");

        given(issueService.insert(any(IssueDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/issues")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("issue/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("issueName").type(STRING).description("이슈명"),
                                fieldWithPath("category").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("contents").type(STRING).description("이슈 내용")
                        )
                ));
    }

    @Test
    public void 이슈_수정_API_테스트() throws Exception {
        // given
        IssueDto.Update update = new IssueDto.Update();
        update.setIssueName("진성씨는 왜 정보처리기사 시험에 합격하지 못 하였는가");
        update.setCategory(IssueCategory.ERROR_MODIFY);
        update.setStatus(IssueStatus.OPEN);
        update.setContents("진성씨는 바보인가?");

        given(issueService.update(eq(1L), any(IssueDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/issues/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("issue/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("이슈 ID")
                        ),
                        requestFields(
                                fieldWithPath("issueName").type(STRING).description("이슈명"),
                                fieldWithPath("category").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("contents").type(STRING).description("이슈 내용")
                        )
                ));
    }

    @Test
    public void 이슈_삭제_API_테스트() throws Exception {
        // given
        given(issueService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/issues/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("issue/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("이슈 ID")
                        )
                ));
    }

    @Test
    public void 버전ID를_받아서_해당_버전과_맵핑_되지_않은_이슈_조회_API_테스트() throws Exception {
        // given
        IssueDto.Result content = new IssueDto.Result();
        content.setIssueId(1L);
        content.setCategory(IssueCategory.NEW_DEVELOP);
        content.setCategoryName(IssueCategory.NEW_DEVELOP.getMessage());
        content.setContents("임진성은 왜 늦는가");
        content.setIssueName("임진성 바보");
        content.setStatus(IssueStatus.OPEN);
        content.setStatusName(IssueStatus.OPEN.getMessage());
        content.setCreatedDate(LocalDateTime.now());
        content.setCreatedBy("kjpmj");
        content.setLastModifiedDate(LocalDateTime.now());
        content.setLastModifiedBy("kjpmj");

        List<IssueDto.Result> results = Arrays.asList(content);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<IssueDto.Result> pages = new PageImpl<>(results, pageRequest, 100);

        IssueDto.Response response = new IssueDto.Response(pages);

        given(issueService.searchIssuesNotMappingVersion(eq(1L), any(Pageable.class)))
                .willReturn(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/issues/not-mapping-version")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("versionId", "1")
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("issue/select-not-mapping-version",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("versionId").description("버전 Id")
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("issueId").type(NUMBER).description("이슈 ID"),
                                fieldWithPath("issueName").type(STRING).description("이슈명"),
                                fieldWithPath("category").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("categoryName").type(STRING).description(generateText(CodeEnum.ISSUE_CATEGORY)),
                                fieldWithPath("status").type(STRING).description(generateLinkCode(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("statusName").type(STRING).description(generateText(CodeEnum.ISSUE_STATUS)),
                                fieldWithPath("contents").type(STRING).description("이슈 내용"),
                                fieldWithPath("createdDate").type(STRING).attributes(getDateTimeFormat()).description("생성일"),
                                fieldWithPath("lastModifiedDate").type(STRING).attributes(getDateTimeFormat()).description("수정일"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("lastModifiedBy").type(STRING).description("수정자")
                        )
                ));
    }
}