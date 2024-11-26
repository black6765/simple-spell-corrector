package com.blue.ssc.service;

import com.blue.ssc.domain.request.RequestDTO;
import com.blue.ssc.domain.response.ResponseDTO;
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

    public ResponseDTO spellCorrect(RequestDTO request) {

        log.info("origin keyword = [{}]", request.getKeyword());

        final String processedKeyword = KeywordResolver.convertSyllableToJamo(request.getKeyword());
        List<SuggestionItem> suggestionItems;

        try {
            suggestionItems = symSpellCheck.lookupCompound(processedKeyword);
        } catch (SpellCheckException e) {
            throw new RuntimeException(e);
        }

        ResponseDTO responseDTO = new ResponseDTO(request.getKeyword());

        if (suggestionItems.size() == 0) {
            return responseDTO;
        }

        final String correctedKeyword = suggestionItems.get(0).getDistance() > request.getDistance()
                ? request.getKeyword() : KeywordResolver.convertJamoToSyllable(suggestionItems.get(0).getTerm());

        if (!request.getKeyword().equals(correctedKeyword)) {
            log.info("corrected keyword = [{}]", correctedKeyword);
            responseDTO.setCorrectedKeyword(correctedKeyword);
            responseDTO.setCorrected(true);
            responseDTO.setDistance(suggestionItems.get(0).getDistance());
        }

        return responseDTO;
    }
}
