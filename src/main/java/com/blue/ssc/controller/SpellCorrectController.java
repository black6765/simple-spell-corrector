package com.blue.ssc.controller;

import com.blue.ssc.domain.request.RequestDTO;
import com.blue.ssc.domain.response.ResponseDTO;
import com.blue.ssc.service.SpellCorrectService;
import io.github.mightguy.spellcheck.symspell.exception.SpellCheckException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class SpellCorrectController {

    @Autowired
    SpellCorrectService spellCorrectService;

    @GetMapping
    public ResponseEntity<ResponseDTO> correctKeyword(@ModelAttribute RequestDTO request) throws SpellCheckException {
        log.info("request = [{}]", request);
        return ResponseEntity.ok(spellCorrectService.spellCorrect(request));
    }

}
