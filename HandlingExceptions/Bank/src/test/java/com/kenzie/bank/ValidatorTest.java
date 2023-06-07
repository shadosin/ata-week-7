package com.kenzie.bank;

import com.kenzie.bank.exceptions.InvalidInputException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;


import java.math.BigDecimal;

import org.junit.jupiter.api.Test;


public class ValidatorTest {

    private Validator validator = new Validator();
    private BigDecimal amount;

    @Test
    public void validate_nullInput_throwsInvalidInputException() {
        // GIVEN
        amount = null;

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            validator.validate(amount);
        });
    }

    @Test
    public void validate_negativeInput_throwsInvalidInputException() {
        // GIVEN
        amount = new BigDecimal("-100.00");

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            validator.validate(amount);
        });
    }

    @Test
    public void validate_validInput_success() {
        // GIVEN
        amount = new BigDecimal("100.00");

        // WHEN && THEN
        try {
            validator.validate(amount);
        } catch (Exception e) {
            fail("Unexpected exception caught in validate method");
        }
    }
}