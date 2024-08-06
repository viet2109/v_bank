package com.v_bank.v_bank.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public enum Error {
    ALREADY_USER("already user", HttpStatus.CONFLICT),
    USER_NOT_FOUND("user not found", HttpStatus.NOT_FOUND),
    LOGIN_INFO_WRONG("The email/phone or password is wrong", HttpStatus.UNPROCESSABLE_ENTITY)
    ;
    private final String message;
    private final HttpStatus status;
}
