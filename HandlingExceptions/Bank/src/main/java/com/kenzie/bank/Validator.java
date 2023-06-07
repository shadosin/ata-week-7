package com.kenzie.bank;

import com.kenzie.bank.exceptions.InvalidInputException;

import java.math.BigDecimal;

/**
 * Validator helper class that provides methods to help validate inputs.
 */
public class Validator {

    /**
     * Validates whether amount passed is valid (not null or negative).
     * @param amount input to validate.
     * @throws InvalidInputException is not valid.
     */
    public void validate(BigDecimal amount) {
        // check if input is null or negative
        if (amount == null || amount.signum() == -1) {
            throw new InvalidInputException("Amount cannot be null or negative");
        }
    }

}