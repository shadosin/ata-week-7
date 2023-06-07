package com.kenzie.bank;

import com.kenzie.bank.exceptions.TransactionException;

import java.math.BigDecimal;

/**
 * Interface for a bank account, which provides deposit and withdraw methods.
 */
public interface BankAccount {

    /**
     * Deposit amount to account.
     *
     * @param amount of money to deposit
     * @return value of account after the deposit
     * @throws TransactionException thrown if an error occurred during the transaction
     */
    BigDecimal deposit(BigDecimal amount) throws TransactionException;

    /**
     * Withdraw amount from account.
     *
     * @param amount of money to withdraw
     * @return value of account after the withdraw
     * @throws TransactionException thrown if an error occurred during the transaction
     */
    BigDecimal withdraw(BigDecimal amount) throws TransactionException;

    /**
     * Retrieves the balance of the account.
     * @return the balance of the account
     */
    BigDecimal getBalance();
}