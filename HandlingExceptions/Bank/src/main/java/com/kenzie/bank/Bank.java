package com.kenzie.bank;

import com.kenzie.bank.exceptions.TransactionException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.math.BigDecimal;

/**
 * This class represents a bank, which includes the functionality to transfer from
 * one BankAccount to another.
 */
public class Bank {
    private Logger log = LogManager.getLogger(Bank.class);

    /**
     * Transfer money from one account to another.
     *
     * @param fromAccount BankAccount to withdraw amount from
     * @param toAccount BankAccount to deposit amount into
     * @param amount of money to transfer.
     * @return true if transfer was successful, false if transfer fails due to insufficient funds
     */
    public boolean transfer(BankAccount fromAccount, BankAccount toAccount, BigDecimal amount) {
        // TODO: implement
        try{
            fromAccount.withdraw(amount);
            toAccount.deposit(amount);
            return true;
        }catch (TransactionException e){
            log.error("TransactionException occurred during transfer,", e);
            return false;
        }
    }
}