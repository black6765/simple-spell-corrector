package com.blue.ssc.service;

import com.blue.ssc.domain.request.RequestDTO;
import com.blue.ssc.domain.response.ResponseDTO;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpellCorrectorTest {

    @Autowired
    private SpellCorrectService spellCorrectService;

    @Test
    void spellCorrect() {
        // given
        RequestDTO request = new RequestDTO("용화", 2.0);

        // when
        ResponseDTO response = spellCorrectService.spellCorrect(request);

        // then
        Assertions.assertThat(response.isCorrected()).isTrue();
        Assertions.assertThat(response.getCorrectedKeyword()).isEqualTo("영화");
    }

}