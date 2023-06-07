package com.kenzie.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.kenzie.bank.exceptions.InvalidInputException;

class BankTest {

    private Bank bank = new Bank();

    @Test
    public void transfer_validInput_returnsTrue() throws InvalidInputException {

        // GIVEN
        CheckingAccount account1 = new CheckingAccount("account1", new BigDecimal("500.00"));
        CheckingAccount account2 = new CheckingAccount("account2", new BigDecimal("1000.00"));

        // WHEN
        boolean result = bank.transfer(account1, account2, new BigDecimal("100.00"));


        // THEN
        assertTrue(result);
        assertEquals(new BigDecimal("400.00"), account1.getBalance());
        assertEquals(new BigDecimal("1100.00"), account2.getBalance());

    }

    @Test
    public void transfer_insufficientFunds_returnsFalse() throws InvalidInputException {

        // GIVEN
        CheckingAccount account1 = new CheckingAccount("account1", new BigDecimal("500.00"));
        CheckingAccount account2 = new CheckingAccount("account2", new BigDecimal("1000.00"));

        // WHEN
        boolean result = bank.transfer(account1, account2, new BigDecimal("600.00"));

        // THEN
        assertFalse(result);
        assertEquals(new BigDecimal("500.00"), account1.getBalance());
        assertEquals(new BigDecimal("1000.00"), account2.getBalance());

    }

    @Test
    public void transfer_nullInput_InvalidInputExceptionThrown() {

        // GIVEN
        CheckingAccount account1 = new CheckingAccount("account1", new BigDecimal("500.00"));
        CheckingAccount account2 = new CheckingAccount("account2", new BigDecimal("1000.00"));

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            bank.transfer(account1, account2, null);
        });

    }

    @Test
    public void transfer_negativeInput_InvalidInputExceptionThrown() {

        // GIVEN
        CheckingAccount account1 = new CheckingAccount("account1", new BigDecimal("500.00"));
        CheckingAccount account2 = new CheckingAccount("account2", new BigDecimal("1000.00"));

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            bank.transfer(account1, account2, new BigDecimal("-100.00"));
        });

    }

}