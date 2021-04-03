package org.doif.projectv.business.client.dto;

import lombok.*;

import java.util.List;

public class ClientDto {

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Result {
        private Long clientId;
        private String name;
        private String description;
        private String tel;
        private String bizRegNo;
        private String zipCode;
        private String basicAddr;
        private String detailAddr;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Response {
        private List<Result> content;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Insert {
        private String name;
        private String description;
        private String tel;
        private String bizRegNo;
        private String zipCode;
        private String basicAddr;
        private String detailAddr;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Update {
        private String name;
        private String description;
        private String tel;
        private String bizRegNo;
        private String zipCode;
        private String basicAddr;
        private String detailAddr;
    }
}
