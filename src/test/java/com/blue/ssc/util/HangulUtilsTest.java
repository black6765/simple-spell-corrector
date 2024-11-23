package com.blue.ssc.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class HangulUtilsTest {
    @Test
    @DisplayName("한글 키워드가 정상적으로 자모 단위로 분리되어야 한다")
    void separateJamo() {
        String keyword = "안녕";

        // when
        String result = HangulUtils.separateJamo(keyword);

        // then
        Assertions.assertThat(result).isEqualTo("ㅇㅏㄴㄴㅕㅇ");
    }

    @Test
    @DisplayName("자모가 정상적으로 한글로 결합되어야 한다")
    void combineJamo() {
        // given
        String keyword = "ㅇㅏㄴㄴㅕㅇ";

        // when
        String result = HangulUtils.combineJamo(keyword);

        // then
        Assertions.assertThat(result).isEqualTo("안녕");
    }

    @Test
    @DisplayName("공백이 존재할 때 자모가 정상적으로 한글로 결합되어야 한다")
    void combineJamoContainsWhitespace() {
        // given
        String keyword = "ㅇㅏㄴㄴㅕㅇ ㅎㅏㅅㅔㅇㅛ";

        // when
        String result = HangulUtils.combineJamo(keyword);

        // then
        Assertions.assertThat(result).isEqualTo("안녕 하세요");
    }

    @Test
    @DisplayName("결합할 수 있는 자모는 모두 결합되고, 그렇지 않은 자모는 그대로 남아야 한다.")
    void combineJamoContainsNotCombinable() {
        // given
        String keyword = "ㅇㅏㅏㄴㄴㅕㅕ";

        // when
        String result = HangulUtils.combineJamo(keyword);

        // then
        Assertions.assertThat(result).isEqualTo("아ㅏㄴ녀ㅕ");
    }

    @Test
    void combineJamoFinalChosung() {
        // given
        String keyword = "ㅇㅏㄴㄴㅕㅇㅇ";

        // when
        String result = HangulUtils.combineJamo(keyword);

        // then
        Assertions.assertThat(result).isEqualTo("안녕ㅇ");
    }

}