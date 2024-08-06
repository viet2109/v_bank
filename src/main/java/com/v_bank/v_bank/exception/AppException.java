package com.v_bank.v_bank.exception;

import com.v_bank.v_bank.enums.Error;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AppException extends RuntimeException {
    private Error error;
    public AppException(Error error) {
        super(error.getMessage());
        this.error = error;
    }
}
