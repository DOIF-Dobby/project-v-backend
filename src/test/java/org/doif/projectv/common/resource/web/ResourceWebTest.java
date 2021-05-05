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
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ResourceWebTest extends ApiDocumentTest {

    @Test
    public void 페이지_자식_리소스_조회_API_테스트() throws Exception {
        // given
        PageDto.Child child = new PageDto.Child();
        child.setPageId(1L);
        ButtonDto.Result button1 = new ButtonDto.Result(2L, "이슈 조회", "이슈 조회 버튼", EnableStatus.ENABLE, "BTN_ISSUE_SEARCH", "/api/issue", HttpMethod.GET, 1L, "find");
//        ButtonDto.Result button2 = new ButtonDto.Result(3L, "이슈 추가", "이슈 추가 버튼", EnableStatus.ENABLE, "BTN_ISSUE_ADD", "/api/issue", HttpMethod.POST, 1L, "add");

        TabDto.Result tab1 = new TabDto.Result(4L, "탭 1", "탭 1", EnableStatus.ENABLE, "TAB_1", "/api/tabs/tab1", HttpMethod.GET, 1L, "TAB_GROUP1", 1);
//        TabDto.Result tab2 = new TabDto.Result(5L, "탭 2", "탭 2", EnableStatus.ENABLE, "TAB_2", "/api/tabs/tab2", HttpMethod.GET, 1L, "TAB_GROUP1", 2);

        LabelDto.Result label1 = new LabelDto.Result(6L, "이슈명", "이슈명", EnableStatus.ENABLE, "LABEL_ISSUE_NAME", 1L);
//        LabelDto.Result label2 = new LabelDto.Result(7L, "이슈 상태", "이슈 상태", EnableStatus.ENABLE, "LABEL_ISSUE_STATUS", 1L);

        Map<String, ButtonDto.Result> buttonMap = Stream.of(button1).collect(Collectors.toMap(ResourceDto.Result::getCode, o -> o));
        Map<String, TabDto.Result> tabMap = Stream.of(tab1).collect(Collectors.toMap(ResourceDto.Result::getCode, o -> o));
        Map<String, LabelDto.Result> labelMap = Stream.of(label1).collect(Collectors.toMap(ResourceDto.Result::getCode, o -> o));

        child.setButtonMap(buttonMap);
        child.setTabMap(tabMap);
        child.setLabelMap(labelMap);
        child.setMenuName("메뉴명");
        child.setMenuList(Arrays.asList("메뉴 카테고리", "메뉴"));

        given(resourceService.searchPageChildResource(eq("/api/pages/issue"), eq("/menu")))
                .willReturn(child);

        // when
        ResultActions result = mockMvc.perform(
                get("/api/pages/issue")
                        .param("menuPath", "/menu")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        // then
        result.andExpect(status().isOk())
//                .andExpect(content().json(objectMapper.writeValueAsString(child)))
                .andDo(print())
                .andDo(document("page-child/select",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParameters(
                                parameterWithName("menuPath").description("메뉴 URL")
                        ),
                        responseFields(
                                fieldWithPath("pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("menuName").type(STRING).description("메뉴명"),
                                fieldWithPath("menuList").type(ARRAY).description("메뉴 경로 리스트"),
                                fieldWithPath("buttonMap").type(OBJECT).description("버튼 리소스 맵"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.resourceId").type(NUMBER).description("버튼 ID"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.name").type(STRING).description("버튼명"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.description").type(STRING).description("버튼 설명"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.code").type(STRING).description("버튼 코드"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.url").type(STRING).description("버튼 URL"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("buttonMap.BTN_ISSUE_SEARCH.icon").type(STRING).description("아이콘"),
                                fieldWithPath("tabMap").type(OBJECT).description("탭 리소스 맵"),
                                fieldWithPath("tabMap.TAB_1.resourceId").type(NUMBER).description("탭 ID"),
                                fieldWithPath("tabMap.TAB_1.name").type(STRING).description("탭명"),
                                fieldWithPath("tabMap.TAB_1.description").type(STRING).description("탭 설명"),
                                fieldWithPath("tabMap.TAB_1.status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("tabMap.TAB_1.statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("tabMap.TAB_1.code").type(STRING).description("탭 코드"),
                                fieldWithPath("tabMap.TAB_1.url").type(STRING).description("탭 URL"),
                                fieldWithPath("tabMap.TAB_1.httpMethod").type(STRING).description("http 메서드"),
                                fieldWithPath("tabMap.TAB_1.pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("tabMap.TAB_1.tabGroup").type(STRING).description("탭 그룹"),
                                fieldWithPath("tabMap.TAB_1.sort").type(NUMBER).description("정렬 순서"),
                                fieldWithPath("labelMap").type(OBJECT).description("라벨 리소스 맵"),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.resourceId").type(NUMBER).description("라벨 ID"),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.name").type(STRING).description("라벨명"),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.description").type(STRING).description("라벨 설명"),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.status").type(STRING).description(generateLinkCode(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.statusName").type(STRING).description(generateText(CodeEnum.ENABLE_STATUS)),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.pageId").type(NUMBER).description("페이지 ID"),
                                fieldWithPath("labelMap.LABEL_ISSUE_NAME.code").type(STRING).description("라벨 코드")
                        )
                ));
    }
}