package com.v_bank.v_bank.dto;

import com.v_bank.v_bank.enums.CardType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Long id;
    private String cardNumber;
    private CardType cardType;
    private LocalDateTime expiryDate;
}
