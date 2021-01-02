package org.doif.projectv.common.resource.web;

import static org.junit.jupiter.api.Assertions.*;

import org.doif.projectv.common.api.ApiDocumentTest;
import org.doif.projectv.common.enumeration.CodeEnum;
import org.doif.projectv.common.resource.dto.*;
import org.doif.projectv.common.response.ResponseUtil;
import org.doif.projectv.common.status.EnableStatus;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
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
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceWebTest extends ApiDocumentTest {

    @Test
    public void 페이지_자식_리소스_조회_API_테스트() throws Exception {
        // given
        PageDto.Child child = new PageDto.Child();
        ButtonDto.Result button1 = new ButtonDto.Result(2L, "이슈 조회", "이슈 조회 버튼", EnableStatus.ENABLE, "BTN_ISSUE_SEARCH", "/api/issue", HttpMethod.GET, 1L, "find");
        ButtonDto.Result button2 = new ButtonDto.Result(3L, "이슈 추가", "이슈 추가 버튼", EnableStatus.ENABLE, "BTN_ISSUE_ADD", "/api/issue", HttpMethod.POST, 1L, "add");

        TabDto.Result tab1 = new TabDto.Result(4L, "탭 1", "탭 1", EnableStatus.ENABLE, "TAB_1", "/api/tabs/tab1", HttpMethod.GET, 1L, "TAB_GROUP1", 1);
        TabDto.Result tab2 = new TabDto.Result(5L, "탭 2", "탭 2", EnableStatus.ENABLE, "TAB_2", "/api/tabs/tab2", HttpMethod.GET, 1L, "TAB_GROUP1", 2);

        LabelDto.Result label1 = new LabelDto.Result(6L, "이슈명", "이슈명", EnableStatus.ENABLE, "LABEL_ISSUE_NAME", 1L);
        LabelDto.Result label2 = new LabelDto.Result(7L, "이슈 상태", "이슈 상태", EnableStatus.ENABLE, "LABEL_ISSUE_STATUS", 1L);

        List<ButtonDto.Result> buttons = Arrays.asList(button1, button2);
        List<TabDto.Result> tabs = Arrays.asList(tab1, tab2);
        List<LabelDto.Result> labels = Arrays.asList(label1, label2);

        child.setButtons(buttons);
        child.setTabs(tabs);
        child.setLabels(labels);

        given(resourceService.searchPageChildResource(eq("/api/pages/issue")))
                .willReturn(child);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/pages/{path}", "issue")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(child)))
                .andDo(print())
                .andDo(document("page-child/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("path").description("페이지 URL")
                        ),
                        responseFields(
                                fieldWithPath("buttons").type(ARRAY).description("버튼 리소스 배열"),
                                fieldWithPath("buttons[].resourceId").type(NUMBER).description("버튼 ID"),
                                fieldWithPath("buttons[].name").type(STRING).description("버튼명"),
                                fieldWithPath("buttons[].description").type(STRING).description("버튼 설명"),
                                fieldWithPath("buttons[].status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("buttons[].statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("buttons[].code").type(STRING).description("버튼 코드"),
                                fieldWithPath("buttons[].url").type(STRING).description("버튼 URL"),
                                fieldWithPath("buttons[].httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("buttons[].pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("buttons[].icon").type(STRING).description("아이콘"),
                                fieldWithPath("tabs").type(ARRAY).description("탭 리소스 배열"),
                                fieldWithPath("tabs[].resourceId").type(NUMBER).description("탭 ID"),
                                fieldWithPath("tabs[].name").type(STRING).description("탭명"),
                                fieldWithPath("tabs[].description").type(STRING).description("탭 설명"),
                                fieldWithPath("tabs[].status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("tabs[].statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("tabs[].code").type(STRING).description("탭 코드"),
                                fieldWithPath("tabs[].url").type(STRING).description("탭 URL"),
                                fieldWithPath("tabs[].httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("tabs[].pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("tabs[].tabGroup").type(STRING).description("탭 그룹"),
                                fieldWithPath("tabs[].sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("labels").type(ARRAY).description("라벨 리소스 배열"),
                                fieldWithPath("labels[].resourceId").type(NUMBER).description("라벨 ID"),
                                fieldWithPath("labels[].name").type(STRING).description("라벨명"),
                                fieldWithPath("labels[].description").type(STRING).description("라벨 설명"),
                                fieldWithPath("labels[].status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("labels[].statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("labels[].pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("labels[].code").type(STRING).description("라벨 코드")
                        )
                ));
    }
}