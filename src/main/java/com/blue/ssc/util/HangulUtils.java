package com.blue.ssc.util;

import java.util.HashSet;
import java.util.Set;

public class HangulUtils {

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
            // 한글 음절인지 확인
            if (syllable >= HANGUL_BASE && syllable <= HANGUL_LAST) {
                int syllableIndex = syllable - HANGUL_BASE;

                // 초성, 중성, 종성을 분리
                int chosungIndex = syllableIndex / (JUNGSUNG_SIZE * JONGSUNG_SIZE);
                int jungsungIndex = (syllableIndex % (JUNGSUNG_SIZE * JONGSUNG_SIZE)) / JONGSUNG_SIZE;
                int jongsungIndex = syllableIndex % JONGSUNG_SIZE;

                // 분리된 자모를 결과에 추가
                result.append(CHOSUNG[chosungIndex]).append(JUNGSUNG[jungsungIndex]);

                // 종성이 있을 때만 추가
                if (jongsungIndex != 0) {
                    result.append(JONGSUNG[jongsungIndex]);
                }
            } else {
                // 한글이 아닌 문자는 그대로 추가
                result.append(syllable);
            }
        }
        return result.toString();
    }

    // 분리된 자모를 합치는 메소드
    public static String combineJamo(final String jamoString) {
        StringBuilder result = new StringBuilder();
        int chosung = -1, jungsung = -1, jongsung = -1;

        // charArray의 특정 자모가 결합되었는지 확인을 위한 Set
        // 시작 이전 인덱스인 -1은 결합된 인덱스로 간주한다.
        Set<Integer> combinedIndex = new HashSet<>(Set.of(-1));

        char[] charArray = jamoString.toCharArray();
        for (int i = 0; i < charArray.length; i++) {
            char ch = charArray[i];
            int chosungIndex = findIndex(CHOSUNG, ch);
            int jungsungIndex = findIndex(JUNGSUNG, ch);
            int jongsungIndex = findIndex(JONGSUNG, ch);

            if (chosungIndex != -1) {
                // 이전 음절이 있을 경우 합성하여 추가
                if (chosung != -1 && jungsung != -1) {
                    if (i == charArray.length - 1 || findIndex(CHOSUNG, charArray[i + 1]) != -1 || charArray[i + 1] == ' ') {
                        jongsung = jongsungIndex;
                    }
                    result.append(combineSyllable(chosung, jungsung, jongsung));
                    combinedIndex.add(i);
                }

                // 이전에 초성만 존재했을 때 해당 초성을 그대로 추가한다.
                if (!combinedIndex.contains(i - 1) && findIndex(CHOSUNG, charArray[i - 1]) != -1) {
                    result.append(CHOSUNG[findIndex(CHOSUNG, charArray[i - 1])]);
                }

                // 새로운 초성을 시작
                chosung = chosungIndex;
                jungsung = -1;
                jongsung = -1;
            } else if (jungsungIndex != -1 && jungsung == -1) {
                jungsung = jungsungIndex;
            } else if (jongsungIndex != -1) {
                jongsung = jongsungIndex;
            } else {
                // 한글 외 문자는 그대로 추가
                if (chosung != -1 && jungsung != -1) {
                    result.append(combineSyllable(chosung, jungsung, jongsung));
                    combinedIndex.add(i);
                }
                result.append(ch);
                chosung = -1;
                jungsung = -1;
                jongsung = -1;
            }
        }

        // 마지막 남은 음절 처리
        if (chosung != -1 && jungsung != -1) {
            result.append(combineSyllable(chosung, jungsung, jongsung));
        } else if (chosung != -1 && combinedIndex.contains(charArray.length - 2)) {
            // 이전 자모가 결합되었고, 마지막 자모가 자음일 때 그대로 추가
            result.append(CHOSUNG[chosung]);
        }

        return result.toString();
    }

    // 초성, 중성, 종성을 조합하여 한글 음절 생성
    private static char combineSyllable(int chosung, int jungsung, int jongsung) {
        // 종성이 0이면 종성 없다고 간주하고 처리
        if (jongsung == -1) {
            jongsung = 0;
        }
        int code = HANGUL_BASE + (chosung * JUNGSUNG_SIZE * JONGSUNG_SIZE) + (jungsung * JONGSUNG_SIZE) + jongsung;
        return (char) code;
    }

    // 배열에서 문자 위치를 찾는 메소드
    private static int findIndex(char[] array, char target) {

        // 공백은 종성으로 취급하지 않는다
        if (target == ' ') {
            return -1;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

}