package org.doif.projectv.common.api.document;

import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.AbstractFieldsSnippet;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.subsectionWithPath;
import static org.springframework.restdocs.snippet.Attributes.attributes;
import static org.springframework.restdocs.snippet.Attributes.key;

public class PageDocumentation {

    public static List<FieldDescriptor> pageResponseFieldDescriptor() {
        return Arrays.asList(
                fieldWithPath("pageInfo").type(JsonFieldType.OBJECT).description("페이지 정보"),
                subsectionWithPath("pageInfo.content").type(JsonFieldType.ARRAY).description("컨텐츠 정보"),
                fieldWithPath("pageInfo.pageable").type(JsonFieldType.OBJECT).description("페이저블"),
                fieldWithPath("pageInfo.pageable.sort").type(JsonFieldType.OBJECT).description("정렬"),
                fieldWithPath("pageInfo.pageable.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬?"),
                fieldWithPath("pageInfo.pageable.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬?"),
                fieldWithPath("pageInfo.pageable.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬이 비어있니?"),
                fieldWithPath("pageInfo.pageable.offset").type(JsonFieldType.NUMBER).description("오프셋"),
                fieldWithPath("pageInfo.pageable.pageNumber").type(JsonFieldType.NUMBER).description("페이지 번호"),
                fieldWithPath("pageInfo.pageable.pageSize").type(JsonFieldType.NUMBER).description("페이지 사이즈"),
                fieldWithPath("pageInfo.pageable.unpaged").type(JsonFieldType.BOOLEAN).description("이건 뭐냐"),
                fieldWithPath("pageInfo.pageable.paged").type(JsonFieldType.BOOLEAN).description("이건 뭐냐"),
                fieldWithPath("pageInfo.sort.unsorted").type(JsonFieldType.BOOLEAN).description("미정렬?"),
                fieldWithPath("pageInfo.sort.sorted").type(JsonFieldType.BOOLEAN).description("정렬?"),
                fieldWithPath("pageInfo.sort.empty").type(JsonFieldType.BOOLEAN).description("정렬이 비어있니?"),
                fieldWithPath("pageInfo.last").type(JsonFieldType.BOOLEAN).description("마지막 여부"),
                fieldWithPath("pageInfo.totalElements").type(JsonFieldType.NUMBER).description("총 요소 수"),
                fieldWithPath("pageInfo.totalPages").type(JsonFieldType.NUMBER).description("총 몇 페이지인지"),
                fieldWithPath("pageInfo.numberOfElements").type(JsonFieldType.NUMBER).description("가져온 요소 수"),
                fieldWithPath("pageInfo.number").type(JsonFieldType.NUMBER).description("뭔지 모르겠다"),
                fieldWithPath("pageInfo.size").type(JsonFieldType.NUMBER).description("몇개 가져올지"),
                fieldWithPath("pageInfo.first").type(JsonFieldType.BOOLEAN).description("첫번째 페이지인지?"),
                fieldWithPath("pageInfo.empty").type(JsonFieldType.BOOLEAN).description("요소가 없는지?")
        );
    }

    public static PageResponseFieldsSnippet pageResponseFields() {
        return new PageResponseFieldsSnippet("page-response", pageResponseFieldDescriptor(), attributes(key("field").value("")), false);
    }

    static class PageResponseFieldsSnippet extends AbstractFieldsSnippet {
        public PageResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors, Map<String, Object> attributes, boolean ignoreUndocumentedFields) {
            super(type, descriptors, attributes, ignoreUndocumentedFields);
        }

        @Override
        protected MediaType getContentType(Operation operation) {
            return operation.getResponse().getHeaders().getContentType();
        }

        @Override
        protected byte[] getContent(Operation operation) throws IOException {
            return operation.getResponse().getContent();
        }
    }


}
