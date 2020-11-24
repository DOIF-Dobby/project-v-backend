package org.doif.projectv.business.project.web;

import org.doif.projectv.business.project.dto.ProjectDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.response.ResponseUtil;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentRequest;
import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentResponse;
import static org.doif.projectv.common.api.DocumentFormatGenerator.getDateTimeFormat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
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

class ProjectControllerTest extends ApiDocumentTest {

    @Test
    void 프로젝트_조회_API_테스트() throws Exception{
        List<ProjectDto.Result> projectDtos = new ArrayList<>();
        ProjectDto.Result projectDto = new ProjectDto.Result(1L, "금융결제원 PG", "금융결제원 PG 프로젝트");
        projectDto.setCreatedDate(LocalDateTime.now());
        projectDto.setCreatedBy("kjpmj");
        projectDto.setLastModifiedDate(LocalDateTime.now());
        projectDto.setLastModifiedBy("kjpmj");
        projectDtos.add(projectDto);

        ProjectDto.Result projectDto2 = new ProjectDto.Result(2L, "트렁크 PG", "베이스 PG 프로젝트");
        projectDto2.setCreatedDate(LocalDateTime.now());
        projectDto2.setCreatedBy("kjpmj");
        projectDto2.setLastModifiedDate(LocalDateTime.now());
        projectDto2.setLastModifiedBy("kjpmj");
        projectDtos.add(projectDto2);

        given(projectService.select())
                .willReturn(projectDtos);

        ProjectDto.Response response = ProjectDto.Response.builder()
                .content(projectDtos)
                .build();

        String content = objectMapper.writeValueAsString(response);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/project")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(content))
                .andDo(print())
                .andDo(document("project/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        responseFields(
                                beneathPath("content"),
                                fieldWithPath("projectId").type(NUMBER).description("프로젝트 ID"),
                                fieldWithPath("projectName").type(STRING).description("프로젝트명"),
                                fieldWithPath("description").type(STRING).description("프로젝트 설명"),
                                fieldWithPath("createdDate").type(STRING).attributes(getDateTimeFormat()).description("생성일"),
                                fieldWithPath("lastModifiedDate").type(STRING).attributes(getDateTimeFormat()).description("수정일"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("lastModifiedBy").type(STRING).description("수정자")
                        )

                ));

    }

    @Test
    public void 프로젝트_추가_API_테스트() throws Exception {
        // given
        ProjectDto.Insert insert = new ProjectDto.Insert();
        insert.setProjectName("금융결제원 PG");
        insert.setDescription("금융결제원 PG 프로젝트");

        given(projectService.insert(any(ProjectDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/project")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("projectName").type(STRING).description("프로젝트 명"),
                                fieldWithPath("description").type(STRING).optional().description("프로젝트 설명")
                        )
                ));
    }

    @Test
    public void 프로젝트_변경_API_테스트() throws Exception {
        // given
        ProjectDto.Update update = new ProjectDto.Update();
        update.setProjectName("프로젝트명 변경");
        update.setDescription("프로젝트 설명 변경");

        given(projectService.update(eq(1L), any(ProjectDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/project/{id}", 1L)
                .content(objectMapper.writeValueAsBytes(update))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("프로젝트 ID")
                        ),
                        requestFields(
                                fieldWithPath("projectName").type(STRING).description("프로젝트 명"),
                                fieldWithPath("description").type(STRING).optional().description("프로젝트 설명")
                        )
                ));

    }

    @Test
    public void 프로젝트_삭제_API_테스트() throws Exception {
        // given
        given(projectService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/project/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("project/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("프로젝트 ID")
                        )
                ));
    }
}