package com.kenzie.bank;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import com.kenzie.bank.exceptions.InsufficientFundsException;
import com.kenzie.bank.exceptions.InvalidInputException;

public class CheckingAccountTest {

    private CheckingAccount account;

    /*
     * Note: you'll see that we're declaring exceptions in some of these these test methods.
     * That's because we're calling methods in these tests that throw checked exceptions, so
     * we need to handle them.
     */
    @Test
    public void deposit_validAmount_successfulDeposit() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN
        BigDecimal balance = account.deposit(new BigDecimal("150.00"));

        // THEN
        assertEquals(new BigDecimal("650.00"), balance);
    }

    @Test
    public void withdraw_validAmount_successfulWithdraw() throws InsufficientFundsException {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN
        BigDecimal balance = account.withdraw(new BigDecimal("150.00"));

        // THEN
        assertEquals(new BigDecimal("350.00"), balance);
    }


    @Test
    public void deposit_nullInputAmount_invalidInputExceptionThrown() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            account.deposit(null);
        });
    }

    @Test
    public void withdraw_nullInputAmount_invalidInputExceptionThrown() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            account.withdraw(null);
        });

    }

    @Test
    public void deposit_negativeInputAmount_invalidInputExceptionThrown() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            account.deposit(new BigDecimal("-100.00"));
        });
    }

    @Test
    public void withdraw_negativeInputAmount_invalidInputExceptionThrown() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN && THEN
        assertThrows(InvalidInputException.class, () -> {
            account.withdraw(new BigDecimal("-100.00"));
        });

    }

    @Test
    public void withdraw_amountGreaterThanBalance_insufficientFundsExceptionThrown() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN && THEN
        assertThrows(InsufficientFundsException.class, () -> {
            account.withdraw(new BigDecimal("700.00"));
        });
    }

    @Test
    public void getBalance_afterConstruction_returnsBalance() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));

        // WHEN
        BigDecimal balance = account.getBalance();

        // THEN
        assertEquals(new BigDecimal("500.00"), balance);
    }

    @Test
    public void getBalance_afterDeposit_returnsBalance() {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));
        account.deposit(new BigDecimal("150.00"));

        // WHEN
        BigDecimal balance = account.getBalance();

        // THEN
        assertEquals(new BigDecimal("650.00"), balance);
    }

    @Test
    public void getBalance_afterWithdraw_returnsBalance() throws InsufficientFundsException {

        // GIVEN
        account =  new CheckingAccount("account1", new BigDecimal("500.00"));
        account.withdraw(new BigDecimal("150.00"));

        // WHEN
        BigDecimal balance = account.getBalance();

        // THEN
        assertEquals(new BigDecimal("350.00"), balance);
    }
}