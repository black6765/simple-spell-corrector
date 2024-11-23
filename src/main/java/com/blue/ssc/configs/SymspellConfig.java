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

import java.io.*;

@Configuration
public class SymspellConfig {

    @Bean
    public SpellCheckSettings spellCheckSettings() {
        return SpellCheckSettings.builder()
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

    private void readKorDict(DataHolder dataHolder) throws IOException, SpellCheckException {
        BufferedReader br = new BufferedReader(new FileReader(korDictPath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split("\\t+");
            if (KeywordResolver.isKoreanOrEnglish(arr[0])) {
                dataHolder.addItem(new DictionaryItem(arr[0], Double.parseDouble(arr[1]), -1.0));
            }
        }
    }

    private void readEngDict(DataHolder dataHolder) throws IOException, SpellCheckException {
        BufferedReader br = new BufferedReader(new FileReader(engDictPath));
        String line;
        while ((line = br.readLine()) != null) {
            String[] arr = line.split("\\t+");
            if (KeywordResolver.isKoreanOrEnglish(arr[1])) {
                dataHolder.addItem(new DictionaryItem(arr[1], Double.parseDouble(arr[2]), -1.0));
            }
        }
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
