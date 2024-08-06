package com.v_bank.v_bank.utils;

import org.springframework.stereotype.Component;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class CardProvider {
    public String generatedCardNumberByEmail(String email, int length) {
        String hash = hashEmail(email);
        StringBuilder cardNumber = new StringBuilder();

        for (int i = 0; i < hash.length() && cardNumber.length() < length - 1; i++) {
            char c = hash.charAt(i);
            if (Character.isDigit(c)) {
                cardNumber.append(c);
            }
        }

        while (cardNumber.length() < length - 1) {
            cardNumber.append(new Random().nextInt(10));
        }

        cardNumber.append(getLuhnCheckDigit(cardNumber.toString()));

        return cardNumber.toString();
    }

    private String hashEmail(String email) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] encodedHash = digest.digest(email.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte b : encodedHash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private int getLuhnCheckDigit(String number) {
        int[] digits = new int[number.length()];

        for (int i = 0; i < number.length(); i++) {
            digits[i] = Character.getNumericValue(number.charAt(i));
        }

        for (int i = digits.length - 2; i >= 0; i -= 2) {
            int j = digits[i];
            j *= 2;
            if (j > 9) {
                j -= 9;
            }
            digits[i] = j;
        }

        int sum = 0;
        for (int digit : digits) {
            sum += digit;
        }

        return (10 - (sum % 10)) % 10;
    }
}
