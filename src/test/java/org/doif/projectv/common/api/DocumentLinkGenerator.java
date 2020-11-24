package org.doif.projectv.common.api;

import org.doif.projectv.common.enumeration.CodeEnum;

public interface DocumentLinkGenerator {

    static String generateLinkCode(CodeEnum codeEnum) {
        return String.format("link:code/%s.html[%s %s,role=\"popup\"]", codeEnum.getKey(), codeEnum.getValue(), "코드");
    }

    static String generateLinkPageInfo() {
        return "link:page-info/page-info.html[페이지 정보 ,role=\"popup\"]";
    }

    static String generateText(CodeEnum codeEnum) {
        return String.format("%s %s", codeEnum.getValue(), "코드명");
    }
}
