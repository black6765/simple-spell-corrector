package com.blue.ssc.service;

import com.blue.ssc.util.KeywordResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SpellCorrector {

    private final KeywordResolver keywordResolver;

    public String spellCorrect(final String keyword) {

        String processedKeyword = keywordResolver.resolveKeyword(keyword);

        return null;
    }
}
