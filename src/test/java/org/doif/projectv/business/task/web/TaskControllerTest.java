package org.doif.projectv.business.task.web;

import org.doif.projectv.business.task.constant.TaskType;
import org.doif.projectv.business.task.dto.TaskDto;
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

class TaskControllerTest extends ApiDocumentTest {

    @Test
    public void 작업_조회_API_테스트() throws Exception {

        //given
        List<TaskDto.Result> taskDtos = new ArrayList<>();
        TaskDto.Result taskDto = new TaskDto.Result(1L,
                "암호화 이슈",
                "v1.0.1",
                TaskType.DEVELOP,
                LocalDate.of(2020, 11, 2),
                LocalDate.of(2020, 11, 3),
                1.5,
                "이메일 암호화 항목 암호화 해야됨",
                "kjpmj",
                "비고비고");

        taskDtos.add(taskDto);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<TaskDto.Result> pages = new PageImpl<>(taskDtos, pageRequest, 100);

        TaskDto.Search search = new TaskDto.Search();
        search.setModuleId(1L);
        search.setVersionId(1L);
        search.setVersionIssueId(1L);
        search.setType(TaskType.DEVELOP);
        search.setStartDate(LocalDate.of(2020, 11, 2));
        search.setEndDate(LocalDate.of(2020, 11, 3));
        search.setWorker("kjpmj");

        given(taskService.searchByCondition(any(TaskDto.Search.class), any(Pageable.class)))
                .willReturn(pages);

        TaskDto.Response response = new TaskDto.Response(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("task/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("moduleId").type(JsonFieldType.NUMBER).optional().description("모듈 ID"),
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).optional().description("버전 ID"),
                                fieldWithPath("versionIssueId").type(JsonFieldType.NUMBER).optional().description("버전-이슈 ID"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("작업 시작일"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).optional().attributes(getDateFormat()).description("작업 종료일"),
                                fieldWithPath("type").type(JsonFieldType.STRING).optional().description(generateLinkCode(CodeEnum.TASK_TYPE)),
                                fieldWithPath("worker").type(JsonFieldType.STRING).optional().description("작업자")
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(JsonFieldType.OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("taskId").type(JsonFieldType.NUMBER).description("작업 ID"),
                                fieldWithPath("issueName").type(JsonFieldType.STRING).description("이슈명"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.TASK_TYPE)),
                                fieldWithPath("typeName").type(JsonFieldType.STRING).description(generateText(CodeEnum.TASK_TYPE)),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 시작일"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 종료일"),
                                fieldWithPath("manDay").type(JsonFieldType.NUMBER).description("공수"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("작업 내용"),
                                fieldWithPath("worker").type(JsonFieldType.STRING).description("작업자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).description("비고")
                        )
                ));
    }

    @Test
    public void 작업_추가_API_테스트() throws Exception {
        // given
        TaskDto.Insert insert = TaskDto.Insert.builder()
                .contents("정산 관련 신규 페이지 작성")
                .versionIssueId(1L)
                .startDate(LocalDate.of(2020, 11, 11))
                .endDate(LocalDate.of(2020, 11, 11))
                .manDay(1.0)
                .type(TaskType.DEVELOP)
                .worker("kjpmj")
                .remark("파이팅!")
                .build();

        given(taskService.insert(any(TaskDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/task")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("task/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("versionIssueId").type(JsonFieldType.NUMBER).description("모듈-이슈 ID"),
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("작업 내용"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 시작일"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 종료일"),
                                fieldWithPath("manDay").type(JsonFieldType.NUMBER).description("작업 공수"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.TASK_TYPE)),
                                fieldWithPath("worker").type(JsonFieldType.STRING).description("작업자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).optional().description("비고")
                        )
                ));
    }

    @Test
    public void 작업_수정_API_테스트() throws Exception {
        // given
        TaskDto.Update update = TaskDto.Update.builder()
                .contents("정산 관련 신규 페이지 작성")
                .startDate(LocalDate.of(2020, 11, 11))
                .endDate(LocalDate.of(2020, 11, 13))
                .manDay(2.5)
                .type(TaskType.DEVELOP)
                .worker("kjpmj")
                .remark("더 진행하였음...")
                .build();

        given(taskService.update(eq(1L), any(TaskDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/task/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("task/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("작업 ID")
                        ),
                        requestFields(
                                fieldWithPath("contents").type(JsonFieldType.STRING).description("작업 내용"),
                                fieldWithPath("startDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 시작일"),
                                fieldWithPath("endDate").type(JsonFieldType.STRING).attributes(getDateFormat()).description("작업 종료일"),
                                fieldWithPath("manDay").type(JsonFieldType.NUMBER).description("작업 공수"),
                                fieldWithPath("type").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.TASK_TYPE)),
                                fieldWithPath("worker").type(JsonFieldType.STRING).description("작업자"),
                                fieldWithPath("remark").type(JsonFieldType.STRING).optional().description("비고")
                        )
                ));
    }

    @Test
    public void 작업_삭제_API_테스트() throws Exception {
        // given
        given(taskService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/task/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("task/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("작업 ID")
                        )
                ));
    }
}