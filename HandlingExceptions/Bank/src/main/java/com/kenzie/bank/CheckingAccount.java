package com.kenzie.bank;

import com.kenzie.bank.exceptions.InsufficientFundsException;
import com.kenzie.bank.exceptions.InvalidInputException;
import com.kenzie.bank.exceptions.TransactionException;

import java.math.BigDecimal;

/**
 * This class represents a checking bank account, which includes methods that
 * allow money to be deposited or withdrawn from the account.
 */
public class CheckingAccount implements BankAccount {

    /**
     * Identification number associated with the account.
     */
    private String accountId;

    /**
     * Amount of money in the account.
     */
    private BigDecimal balance;

    /*
     * Validator helper class for validating input.
     */
    private Validator validator = new Validator();

    public CheckingAccount(String accountId, BigDecimal balance) {
        this.accountId = accountId;
        this.balance = balance;
    }

    /**
     * Deposit amount to account.
     *
     * @param amount of money to deposit
     * @return value of account after the deposit
     */
    @Override
    public BigDecimal deposit(BigDecimal amount) {
        // TODO: Implement
        validator.validate(amount);
        balance = balance.add(amount);
        return balance;
    }

    /**
     * Withdraw amount from account.
     *
     * @param amount of money to withdraw
     * @return value of account after the withdraw
     */
    @Override
    public BigDecimal withdraw(BigDecimal amount) throws InsufficientFundsException {
        // TODO: implement
      validator.validate(amount);
      if(amount.compareTo(balance) > 0){
          throw new InsufficientFundsException("Insufficient funds");
      }
      balance = balance.subtract(amount);
      return balance;
    }

    @Override
    public BigDecimal getBalance() {
        // TODO: implement
        return balance;    }
}