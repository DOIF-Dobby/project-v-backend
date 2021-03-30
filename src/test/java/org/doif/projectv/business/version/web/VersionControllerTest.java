package org.doif.projectv.business.version.web;

import org.doif.projectv.business.version.constant.VersionStatus;
import org.doif.projectv.business.version.dto.VersionDto;
import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.util.MultiValueMapConverter;
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

import java.util.ArrayList;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.*;
import static org.doif.projectv.common.api.DocumentLinkGenerator.*;
import static org.doif.projectv.common.api.DocumentLinkGenerator.generateText;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class VersionControllerTest extends ApiDocumentTest {

    @Test
    public void 버전_조회_API_테스트() throws Exception {
        // given
        List<VersionDto.Result> versionResults = new ArrayList<>();
        VersionDto.Result versionResult = new VersionDto.Result(1L, "1.0.1", "버전 설명", 1L, VersionStatus.DEVELOP, "14000", "svn 복사된 태그 정보");

        versionResults.add(versionResult);

        PageRequest pageRequest = PageRequest.of(0, 100);
        Page<VersionDto.Result> pages = new PageImpl<>(versionResults, pageRequest, 100);

        VersionDto.Search search = new VersionDto.Search();
        search.setVersionName("1.0.1");
        search.setDescription("뭐지");
        search.setVersionStatus(VersionStatus.DEVELOP);

        given(versionService.searchByCondition(eq(1L), any(VersionDto.Search.class), any(Pageable.class)))
                .willReturn(pages);

        VersionDto.Response response = new VersionDto.Response(pages);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/modules/{id}/versions", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .params(MultiValueMapConverter.convert(objectMapper, search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("version/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("모듈 ID")
                        ),
                        requestParameters(
                                parameterWithName("versionName").description("버전명"),
                                parameterWithName("description").description("버전 설명"),
                                parameterWithName("versionStatus").description(generateLinkCode(CodeEnum.VERSION_STATUS))
                        ),
                        responseFields(subsectionWithPath("pageInfo").type(JsonFieldType.OBJECT).description(generateLinkPageInfo())),
                        responseFields(
                                beneathPath("pageInfo.content").withSubsectionId("pageInfo.content"),
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).description("버전 ID"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("버전 설명"),
                                fieldWithPath("moduleId").type(JsonFieldType.NUMBER).description("모듈 ID"),
                                fieldWithPath("versionStatus").type(JsonFieldType.STRING).description(generateLinkCode(CodeEnum.VERSION_STATUS)),
                                fieldWithPath("versionStatusName").type(JsonFieldType.STRING).description(generateText(CodeEnum.VERSION_STATUS)),
                                fieldWithPath("revision").type(JsonFieldType.STRING).description("리비전"),
                                fieldWithPath("tag").type(JsonFieldType.STRING).description("태그 경로")
                        )
                ));
    }

    @Test
    public void 버전_추가_API_테스트() throws Exception {
        // given
        VersionDto.Insert insert = new VersionDto.Insert();
        insert.setModuleId(1L);
        insert.setVersionName("1.0.2");
        insert.setDescription("버전 설명");

        given(versionService.insert(any(VersionDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/versions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(insert))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("moduleId").type(JsonFieldType.NUMBER).description("모듈 ID"),
                                fieldWithPath("versionName").type(JsonFieldType.STRING).description("버전명"),
                                fieldWithPath("description").type(JsonFieldType.STRING).description("버전 설명")
                        )
                ));
    }

    @Test
    public void 버전_수정_API_테스트() throws Exception {
        // given
        VersionDto.Update update = new VersionDto.Update();
        update.setDescription("버전 설명");

        given(versionService.update(eq(1L), any(VersionDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/versions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(update))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버전 ID")
                        ),
                        requestFields(
                                fieldWithPath("description").type(JsonFieldType.STRING).description("버전 설명")
                        )
                ));
    }

    @Test
    public void 버전_삭제_API_테스트() throws Exception {
        // given
        given(versionService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/versions/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("버전 ID")
                        )
                ));
    }
    
    @Test
    public void 버전_배포_API_테스트() throws Exception {
        // given
        VersionDto.Release release = new VersionDto.Release();
        release.setVersionId(1L);

        given(versionService.release(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/versions/release")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(release))
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("version/release",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("versionId").type(JsonFieldType.NUMBER).description("버전 ID")
                        )
                ));
    }
}