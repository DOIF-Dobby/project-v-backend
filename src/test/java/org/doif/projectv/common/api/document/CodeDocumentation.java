package org.doif.projectv.common.api.document;

import org.doif.projectv.common.enumeration.EnumModel;
import org.springframework.http.MediaType;
import org.springframework.restdocs.operation.Operation;
import org.springframework.restdocs.payload.*;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.restdocs.payload.PayloadDocumentation.*;

public class CodeDocumentation {

    public static List<FieldDescriptor> enumToFieldDescriptor(EnumModel[] enumModels) {
        return Arrays.stream(enumModels)
                .map(enumModel -> fieldWithPath(enumModel.getCode()).type(JsonFieldType.STRING).optional().description(enumModel.getMessage()))
                .collect(Collectors.toList());
    }

    public static CodeResponseFieldsSnippet codeResponseFields(List<FieldDescriptor> descriptors, Map<String, Object> attributes, PayloadSubsectionExtractor<?> subsectionExtractor) {
        return new CodeResponseFieldsSnippet("code-response", descriptors, attributes, true, subsectionExtractor);
    }

    public static class CodeResponseFieldsSnippet extends AbstractFieldsSnippet {

        protected CodeResponseFieldsSnippet(String type, List<FieldDescriptor> descriptors, Map<String, Object> attributes, boolean ignoreUndocumentedFields, PayloadSubsectionExtractor<?> subsectionExtractor) {
            super(type, descriptors, attributes, ignoreUndocumentedFields, subsectionExtractor);
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
