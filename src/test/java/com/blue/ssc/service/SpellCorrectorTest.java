package com.blue.ssc.service;

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

        // when
        String result = spellCorrectService.spellCorrect("hello worls");

        // then
        Assertions.assertThat(result).isEqualTo("hello world");
    }

}