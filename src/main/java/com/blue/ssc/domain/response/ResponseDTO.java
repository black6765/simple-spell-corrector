package com.blue.ssc.domain.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDTO {
    private String originKeyword;
    private String correctedKeyword;
    private boolean isCorrected;
    private Double distance;

    public ResponseDTO(String originKeyword, String correctedKeyword, boolean isCorrected, Double distance) {
        this.originKeyword = originKeyword;
        this.correctedKeyword = correctedKeyword;
        this.isCorrected = isCorrected;
        this.distance = distance;
    }

    public ResponseDTO(String originKeyword) {
        this.originKeyword = originKeyword;
    }

}
