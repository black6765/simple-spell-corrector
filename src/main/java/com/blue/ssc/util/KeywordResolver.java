package com.blue.ssc.util;

public class KeywordResolver {

    public static String convertSyllableToJamo(final String originKeyword) {
        return HangulUtils.separateJamo(originKeyword.toLowerCase());
    }

    public static String convertJamoToSyllable(final String originKeyword) {
        return HangulUtils.combineJamo(originKeyword);
    }

    public static boolean isKoreanOrEnglish(String input) {
        for (char ch : input.toCharArray()) {
            if ((ch >= '가' && ch <= '힣') ||
                    (ch >= 'ㄱ' && ch <= 'ㅎ') ||
                    (ch >= 'ㅏ' && ch <= 'ㅣ')) {
                continue;
            }
            if ((ch >= 'A' && ch <= 'Z') || (ch >= 'a' && ch <= 'z')) {
                continue;
            }
            return false;
        }
        return true;
    }

    public static String removeSpecialCharacters(String input) {
        // 한글, 영어, 숫자, 공백만 허용
        return input.replaceAll("[^가-힣a-zA-Z0-9\\s]", "");
    }
}
