package com.blue.ssc.util;

import org.springframework.stereotype.Component;

public class JamoSeparator {

    // 유니코드 한글 초성, 중성, 종성의 시작값과 각 구성의 수를 정의
    private static final int CHOSUNG_SIZE = 19;
    private static final int JUNGSUNG_SIZE = 21;
    private static final int JONGSUNG_SIZE = 28;
    private static final char HANGUL_BASE = 0xAC00;
    private static final char HANGUL_LAST = (char) (HANGUL_BASE + CHOSUNG_SIZE * JUNGSUNG_SIZE * JONGSUNG_SIZE - 1);

    private static final char[] CHOSUNG = {
            'ㄱ', 'ㄲ', 'ㄴ', 'ㄷ', 'ㄸ', 'ㄹ', 'ㅁ', 'ㅂ', 'ㅃ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅉ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };
    private static final char[] JUNGSUNG = {
            'ㅏ', 'ㅐ', 'ㅑ', 'ㅒ', 'ㅓ', 'ㅔ', 'ㅕ', 'ㅖ', 'ㅗ', 'ㅘ', 'ㅙ', 'ㅚ', 'ㅛ', 'ㅜ', 'ㅝ', 'ㅞ', 'ㅟ', 'ㅠ', 'ㅡ', 'ㅢ', 'ㅣ'
    };
    private static final char[] JONGSUNG = {
            ' ', 'ㄱ', 'ㄲ', 'ㄳ', 'ㄴ', 'ㄵ', 'ㄶ', 'ㄷ', 'ㄹ', 'ㄺ', 'ㄻ', 'ㄼ', 'ㄽ', 'ㄾ', 'ㄿ', 'ㅀ', 'ㅁ', 'ㅂ', 'ㅄ', 'ㅅ', 'ㅆ', 'ㅇ', 'ㅈ', 'ㅊ', 'ㅋ', 'ㅌ', 'ㅍ', 'ㅎ'
    };

    public static String separateJamo(final String keyword) {
        StringBuilder result = new StringBuilder();

        for (char syllable : keyword.toCharArray()) {
            if (syllable >= HANGUL_BASE && syllable <= HANGUL_LAST) {  // 한글 음절인지 확인
                int syllableIndex = syllable - HANGUL_BASE;

                // 초성, 중성, 종성을 분리
                int chosungIndex = syllableIndex / (JUNGSUNG_SIZE * JONGSUNG_SIZE);
                int jungsungIndex = (syllableIndex % (JUNGSUNG_SIZE * JONGSUNG_SIZE)) / JONGSUNG_SIZE;
                int jongsungIndex = syllableIndex % JONGSUNG_SIZE;

                // 분리된 자모를 결과에 추가
                result.append(CHOSUNG[chosungIndex]).append(JUNGSUNG[jungsungIndex]);
                if (jongsungIndex != 0) { // 종성이 있을 때만 추가
                    result.append(JONGSUNG[jongsungIndex]);
                }
            } else {
                // 한글이 아닌 문자는 그대로 추가
                result.append(syllable);
            }
        }
        return result.toString();
    }
}