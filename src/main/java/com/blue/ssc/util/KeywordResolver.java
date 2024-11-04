package com.blue.ssc.util;

import org.springframework.util.StringUtils;

public class KeywordResolver {

    public static String resolveKeyword(final String originKeyword) {
        return JamoSeparator.separateJamo(StringUtils.trimAllWhitespace(originKeyword)
                .toLowerCase());
    }
}
