package com.blue.ssc.domain.request;

import lombok.*;

@Getter
@Setter
@ToString
public class RequestDTO {
    private String keyword;
    private Double distance = 2.0;

    public RequestDTO() {
    }

    public RequestDTO(String keyword, Double distance) {
        this.keyword = keyword;
        this.distance = distance;
    }
}
