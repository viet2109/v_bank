package com.v_bank.v_bank.controller;

import com.v_bank.v_bank.mapper.CardMapper;
import com.v_bank.v_bank.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<?> getListCard(@RequestParam("user_id") Long userId) {
        return ResponseEntity.ok(cardService.getListCardByUserId(userId));
    }
}
