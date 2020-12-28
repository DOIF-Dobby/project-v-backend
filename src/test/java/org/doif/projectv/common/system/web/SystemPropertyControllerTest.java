package org.doif.projectv.common.system.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.system.constant.PropertyGroupType;
import org.doif.projectv.common.system.dto.SystemPropertyDto;
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
import static org.springframework.restdocs.payload.JsonFieldType.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class SystemPropertyControllerTest extends ApiDocumentTest {

    @Test
    public void SystemProperty_조회_API_테스트() throws Exception {
        // given
        SystemPropertyDto.Search search = new SystemPropertyDto.Search();
        search.setPropertyGroup(PropertyGroupType.COMMON);

        SystemPropertyDto.Result content = new SystemPropertyDto.Result(
                1L,
                PropertyGroupType.COMMON,
                "TEST",
                "테스트 값",
                "테스트 값입니다.",
                true
        );

        List<SystemPropertyDto.Result> results = Arrays.asList(content);
        SystemPropertyDto.Response response = new SystemPropertyDto.Response(results);

        given(systemPropertyService.searchByCondition(any(SystemPropertyDto.Search.class)))
                .willReturn(results);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/system-property")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(search))
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(response)))
                .andDo(print())
                .andDo(document("system-property/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("propertyGroup").type(STRING).optional().description(generateLinkCode(CodeEnum.PROPERTY_GROUP_TYPE))
                        ),
                        responseFields(
                                beneathPath("content").withSubsectionId("content"),
                                fieldWithPath("systemPropertyId").type(NUMBER).description("시스템 속성 ID"),
                                fieldWithPath("propertyGroup").type(STRING).description(generateLinkCode(CodeEnum.PROPERTY_GROUP_TYPE)),
                                fieldWithPath("propertyGroupName").type(STRING).description(generateText(CodeEnum.PROPERTY_GROUP_TYPE)),
                                fieldWithPath("property").type(STRING).description("속성"),
                                fieldWithPath("value").type(STRING).description("속성 값"),
                                fieldWithPath("description").type(STRING).description("속성 설명"),
                                fieldWithPath("updatable").type(BOOLEAN).description("변경 가능 여부"),
                                fieldWithPath("updatableName").type(STRING).description("변경 가능 여부 이름")
                        )
                ));
    }

    @Test
    public void SystemProperty_추가_API_테스트() throws Exception {
        // given
        SystemPropertyDto.Insert insert = new SystemPropertyDto.Insert();
        insert.setPropertyGroup(PropertyGroupType.COMMON);
        insert.setProperty("TEST_PROPERTY");
        insert.setValue("테스트 속성");
        insert.setDescription("테스트 속성입니다.");
        insert.setUpdatable(true);

        given(systemPropertyService.insert(any(SystemPropertyDto.Insert.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                post("/api/system-property")
                        .content(objectMapper.writeValueAsBytes(insert))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("system-property/insert",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestFields(
                                fieldWithPath("propertyGroup").type(STRING).description(generateLinkCode(CodeEnum.PROPERTY_GROUP_TYPE)),
                                fieldWithPath("property").type(STRING).description("속성"),
                                fieldWithPath("value").type(STRING).description("속성 값"),
                                fieldWithPath("description").optional().type(STRING).description("속성 설명"),
                                fieldWithPath("updatable").type(BOOLEAN).description("변경 가능 여부")
                        )
                ));
    }

    @Test
    public void SystemProperty_수정_API_테스트() throws Exception {
        // given
        SystemPropertyDto.Update update = new SystemPropertyDto.Update();
        update.setValue("테스트 속성");
        update.setDescription("테스트 속성입니다.");
        update.setUpdatable(true);

        given(systemPropertyService.update(eq(1L), any(SystemPropertyDto.Update.class)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                put("/api/system-property/{id}", 1L)
                        .content(objectMapper.writeValueAsBytes(update))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("system-property/update",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("시스템 속성 ID")
                        ),
                        requestFields(
                                fieldWithPath("value").type(STRING).description("속성 값"),
                                fieldWithPath("description").optional().type(STRING).description("속성 설명"),
                                fieldWithPath("updatable").type(BOOLEAN).description("변경 가능 여부")
                        )
                ));
    }

    @Test
    public void SystemProperty_삭제_API_테스트() throws Exception {
        // given
        given(systemPropertyService.delete(eq(1L)))
                .willReturn(ResponseUtil.ok());

        // when
        ResultActions result = mockMvc.perform(
                delete("/api/system-property/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("system-property/delete",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("id").description("시스템 속성 ID")
                        )
                ));
    }
  

}