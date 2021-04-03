package org.doif.projectv.common.api;

import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

public interface ApiDocumentUtils {

    static OperationRequestPreprocessor getDocumentRequest() {
        return preprocessRequest(
                modifyUris()    // 문서상 URI를 기본값인 http://localhost:8080에서 다른 걸로 변경하기 위해 사용
                        .scheme("http")
                        .host("3.35.143.205")
                        .removePort(),
                prettyPrint()); // 문서의 request를 예쁘게 출력하기 위해 사용
    }

    static OperationResponsePreprocessor getDocumentResponse() {
        return preprocessResponse(prettyPrint());   // 문서의 response를 예쁘게 출력하기 위해 사용
    }
}
