package com.blue.ssc.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpellCorrectionController {

    @GetMapping
    public ResponseEntity<String> correctKeyword(@RequestParam("keyword") String keyword) {


        return null;
    }

}
