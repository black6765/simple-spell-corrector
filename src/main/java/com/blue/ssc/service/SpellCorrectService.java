package com.blue.ssc.service;

import com.blue.ssc.util.KeywordResolver;
import io.github.mightguy.spellcheck.symspell.api.SpellChecker;
import io.github.mightguy.spellcheck.symspell.common.SuggestionItem;
import io.github.mightguy.spellcheck.symspell.exception.SpellCheckException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpellCorrectService {

    private final SpellChecker symSpellCheck;
    private static final double MAX_EDIT_DISTANCE = 2.0;

    public String spellCorrect(final String originKeyword) {

        log.info("origin = [{}]", originKeyword);

        final String processedKeyword = KeywordResolver.convertSyllableToJamo(originKeyword);

        List<SuggestionItem> suggestionItems;
        try {
            suggestionItems = symSpellCheck.lookupCompound(processedKeyword);
        } catch (SpellCheckException e) {
            throw new RuntimeException(e);
        }

        final String result = suggestionItems.get(0).getDistance() > MAX_EDIT_DISTANCE
                ? originKeyword : KeywordResolver.convertJamoToSyllable(suggestionItems.get(0).getTerm());

        log.info("corrected = [{}]", result);
        return result;
    }
}
