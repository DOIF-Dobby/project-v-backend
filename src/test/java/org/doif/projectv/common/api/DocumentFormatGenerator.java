package org.doif.projectv.common.api;

import org.springframework.restdocs.snippet.Attributes;

import static org.springframework.restdocs.snippet.Attributes.*;

public interface DocumentFormatGenerator {

    static Attribute getDateFormat() {
        return key("format").value("yyyy-MM-dd");
    }

    static Attribute getDateTimeFormat() {
        return key("format").value("yyyy-MM-dd HH:mm:ss");
    }
}
