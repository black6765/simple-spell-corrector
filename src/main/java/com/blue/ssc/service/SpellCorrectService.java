package com.blue.ssc.service;

import com.blue.ssc.util.KeywordResolver;
import io.github.mightguy.spellcheck.symspell.api.SpellChecker;
import io.github.mightguy.spellcheck.symspell.common.SuggestionItem;
import io.github.mightguy.spellcheck.symspell.exception.SpellCheckException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpellCorrectService {

    private final SpellChecker symSpellCheck;

    public String spellCorrect(final String keyword) {

        final String processedKeyword = KeywordResolver.resolveKeyword(keyword);

        List<SuggestionItem> suggestionItems = null;
        try {
            suggestionItems = symSpellCheck
                    .lookupCompound(processedKeyword);
        } catch (SpellCheckException e) {
            throw new RuntimeException(e);
        }

        final String correctedKeyword = suggestionItems.get(0).getTerm();

        System.out.println(correctedKeyword);

        return correctedKeyword;
    }
}
