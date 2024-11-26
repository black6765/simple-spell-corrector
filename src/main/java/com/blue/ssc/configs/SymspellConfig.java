package com.blue.ssc.configs;

import com.blue.ssc.util.KeywordResolver;
import io.github.mightguy.spellcheck.symspell.api.DataHolder;
import io.github.mightguy.spellcheck.symspell.api.SpellChecker;
import io.github.mightguy.spellcheck.symspell.common.*;
import io.github.mightguy.spellcheck.symspell.exception.SpellCheckException;
import io.github.mightguy.spellcheck.symspell.impl.InMemoryDataHolder;
import io.github.mightguy.spellcheck.symspell.impl.SymSpellCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.function.Consumer;

@Configuration
public class SymspellConfig {

    @Bean
    public SpellCheckSettings spellCheckSettings() {
        return SpellCheckSettings.builder()
                .prefixLength(25)
                .deletionWeight(1.0f)
                .insertionWeight(1.0f)
                .replaceWeight(1.2f)
                .transpositionWeight(0.7f)
                .verbosity(Verbosity.ALL)
                .build();
    }

    @Bean
    public DataHolder dataHolder(SpellCheckSettings spellCheckSettings) throws IOException, SpellCheckException {
        DataHolder dataHolder = new InMemoryDataHolder(spellCheckSettings, new Murmur3HashFunction());
        readKorDict(dataHolder);
        readEngDict(dataHolder);
        return dataHolder;
    }

    @Value("${dictionary.kor.path}")
    String korDictPath;
    @Value("${dictionary.eng.path}")
    String engDictPath;

    private void readKorDict(DataHolder dataHolder) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(korDictPath))) {
            readFileDictAndAddItems(dataHolder, br);
        }
    }

    private void readEngDict(DataHolder dataHolder) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(engDictPath))) {
            readFileDictAndAddItems(dataHolder, br);
        }
    }

    private void readFileDictAndAddItems(DataHolder dataHolder, BufferedReader br) {
        br.lines()
                .filter(line -> !line.isEmpty())
                .map(line -> line.split("\\t+"))
                .filter(arr -> arr.length == 2 && KeywordResolver.isKoreanOrEnglish(arr[0]))
                .forEach(getDictionaryItemAdder(dataHolder));
    }

    private Consumer<String[]> getDictionaryItemAdder(DataHolder dataHolder) {
        return arr -> {
            try {
                dataHolder.addItem(new DictionaryItem(arr[0], Double.parseDouble(arr[1]), -1.0));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    @Bean
    public WeightedDamerauLevenshteinDistance weightedDamerauLevenshteinDistance(SpellCheckSettings spellCheckSettings) {
        return new WeightedDamerauLevenshteinDistance(
                spellCheckSettings.getDeletionWeight(),
                spellCheckSettings.getInsertionWeight(),
                spellCheckSettings.getReplaceWeight(),
                spellCheckSettings.getTranspositionWeight(),
                null);
    }


    @Bean
    public SpellChecker spellChecker(DataHolder dataHolder,
                                     WeightedDamerauLevenshteinDistance weightedDamerauLevenshteinDistance,
                                     SpellCheckSettings spellCheckSettings) {
        return new SymSpellCheck(dataHolder, weightedDamerauLevenshteinDistance,
                spellCheckSettings);
    }
}
