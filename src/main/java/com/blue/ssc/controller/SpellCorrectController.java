package com.blue.ssc.controller;

import com.blue.ssc.service.SpellCorrectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpellCorrectController {

    @Autowired
    SpellCorrectService spellCorrectService;

    @GetMapping
    public ResponseEntity<String> correctKeyword(@RequestParam("keyword") String keyword) {
        String result = spellCorrectService.spellCorrect(keyword);
        return ResponseEntity.ok(result);
    }

}
