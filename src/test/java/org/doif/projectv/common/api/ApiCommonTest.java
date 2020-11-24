package org.doif.projectv.common.api;

import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.enumeration.EnumModel;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.ResultActions;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.List;

import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentRequest;
import static org.doif.projectv.common.api.ApiDocumentUtils.getDocumentResponse;
import static org.doif.projectv.common.api.document.CodeDocumentation.*;
import static org.doif.projectv.common.api.document.PageDocumentation.pageResponseFields;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiCommonTest extends ApiDocumentTest {

    @Test
    public void 페이지_응답_API () throws Exception {
        ResultActions result = mockMvc.perform(
                get("/page-response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("common/page-response",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pageResponseFields()
                ));
    }

    @Test
    public void 공통_응답_API() throws Exception {
        ResultActions result = mockMvc.perform(
                get("/common-response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(fieldWithPath("code").description("응답 코드"));
        fieldDescriptors.add(fieldWithPath("message").description("응답 메세지"));

        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("common/common-response",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        codeResponseFields(
                                fieldDescriptors,
                                attributes(key("title").value("공통 응답")),
                                null
                        )
                ));
    }

    @Test
    public void 코드_응답_API () throws Exception {
        List<CodeResponseFieldsSnippet> codeResponseFieldsSnippets = new ArrayList<>();
        CodeEnum[] codeEnums = CodeEnum.values();

        ResultActions result = mockMvc.perform(
                get("/code-response")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        for (CodeEnum codeEnum : codeEnums) {
            Class<? extends EnumModel> type = codeEnum.getType();

            CodeResponseFieldsSnippet snippet = codeResponseFields(
                                                    enumToFieldDescriptor(type.getEnumConstants()),
                                                    attributes(key("title").value(codeEnum.getValue())),
                                                    beneathPath(codeEnum.getKey()).withSubsectionId(codeEnum.getKey())
                                                );

            codeResponseFieldsSnippets.add(snippet);
        }

        result.andExpect(status().isOk())
                .andDo(print())
                .andDo(document("common/code-response",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        codeResponseFieldsSnippets.toArray(new CodeResponseFieldsSnippet[0])
                ));
    }
}
