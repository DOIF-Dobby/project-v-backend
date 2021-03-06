package org.doif.projectv.business.module.web;

import org.doif.projectv.business.buildtool.constant.BuildTool;
import org.doif.projectv.business.module.dto.ModuleDto;
import org.doif.projectv.business.vcs.constant.VcsType;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
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
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ModuleControllerTest extends ApiDocumentTest {

    @Test
    public void 모듈_조회_API_테스트() throws Exception {
        // given
        ModuleDto.Result content = new ModuleDto.Result(1L, "금결원 PG 어드민 WEB", "금윰결제원 PG 관리자 WEB 모듈입니다", VcsType.SVN, "repository", BuildTool.MAVEN);
        content.setModuleName("금결원 PG 어드민 WEB");
        content.setDescription("금윰결제원 PG 관리자 WEB 모듈입니다.");
        content.setCreatedDate(LocalDateTime.now());
        content.setCreatedBy("kjpmj");
        content.setLastModifiedDate(LocalDateTime.now());
        content.setLastModifiedBy("kjpmj");

        List<ModuleDto.Result> results = Arrays.asList(content);

        ModuleDto.Response response = new ModuleDto.Response(results);

        given(moduleService.searchByProjectId(eq(1L)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/projects/{id}/modules", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("module/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("모듈 ID")
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("moduleId").type(NUMBER).description("모듈 ID"),
                                fieldWithPath("moduleName").type(STRING).description("모듈명"),
                                fieldWithPath("description").type(STRING).description("모듈 설명"),
                                fieldWithPath("vcsType").type(STRING).description(generateLinkCode(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsTypeName").type(STRING).description(generateText(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsRepository").type(STRING).description("버전관리 저장소"),
                                fieldWithPath("buildTool").type(STRING).description(generateLinkCode(CodeEnum.BUILD_TOOL)),
                                fieldWithPath("buildToolName").type(STRING).description(generateText(CodeEnum.BUILD_TOOL)),
                                fieldWithPath("createdDate").type(STRING).attributes(getDateTimeFormat()).description("생성일"),
                                fieldWithPath("lastModifiedDate").type(STRING).attributes(getDateTimeFormat()).description("수정일"),
                                fieldWithPath("createdBy").type(STRING).description("생성자"),
                                fieldWithPath("lastModifiedBy").type(STRING).description("수정자")
                        )
                ));
    }

    @Test
    public void 모듈_추가_API_테스트() throws Exception {
        // given
        ModuleDto.Insert insert = new ModuleDto.Insert();
        insert.setModuleName("금결원 PG 어드민 WEB");
        insert.setDescription("금윰결제원 PG 관리자 WEB 모듈입니다.");
        insert.setProjectId(1L);
        insert.setVcsType(VcsType.SVN);

        given(moduleService.insert(any(ModuleDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/modules")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("module/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("moduleName").type(STRING).description("모듈명"),
                                fieldWithPath("projectId").type(NUMBER).description("프로젝트 ID"),
                                fieldWithPath("description").type(STRING).optional().description("모듈 설명"),
                                fieldWithPath("vcsType").type(STRING).description(generateLinkCode(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsRepository").type(STRING).optional().description("버전관리 저장소"),
                                fieldWithPath("buildTool").type(STRING).optional().description(generateLinkCode(CodeEnum.BUILD_TOOL))
                        )
                ));
    }

    @Test
    public void 모듈_수정_API_테스트() throws Exception {
        // given
        ModuleDto.Update update = new ModuleDto.Update();
        update.setModuleName("금결원 PG 어드민 WEB");
        update.setDescription("금윰결제원 PG 관리자 WEB 모듈입니다.");
        update.setVcsType(VcsType.SVN);

        given(moduleService.update(eq(1L), any(ModuleDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/modules/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("module/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("모듈 ID")
                        ),
                        requestFields(
                                fieldWithPath("moduleName").type(STRING).description("모듈명"),
                                fieldWithPath("description").type(STRING).optional().description("모듈 설명"),
                                fieldWithPath("vcsType").type(STRING).description(generateLinkCode(CodeEnum.VCS_TYPE)),
                                fieldWithPath("vcsRepository").type(STRING).optional().description("버전관리 저장소"),
                                fieldWithPath("buildTool").type(STRING).optional().description(generateLinkCode(CodeEnum.BUILD_TOOL))
                        )
                ));
    }

    @Test
    public void 모듈_삭제_API_테스트() throws Exception {
        // given
        given(moduleService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/modules/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("module/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("모듈 ID")
                        )
                ));
    }
}