package bank;

import bank.exceptions.AmountException;

public class Account {

  // encapsulation -- hiding data with private
  private int accountId;
  private String type;
  private double balance;

  // constructor
  public Account(int accountId, String type, double balance) {
    setAccountId(accountId);
    setType(type);
    setBalance(balance);
  }

  public void deposit(double amount) throws AmountException {
    // check that the input is valid
    if (amount < 1) {
      throw new AmountException("The minimum deposit is 1.00"); // create an home-made Exception
    } else {
      double newBalance = balance + amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(accountId, newBalance);
    }
  }

  public void withdraw(double amount) throws AmountException {
    if (amount < 0) {
      throw new AmountException("The withdraw amount must be greater than 0.00"); // create an home-made Exception
    } else if (amount > getBalance()) {
      throw new AmountException("You do not have enough funds for this withdraw.");
    } else {
      double newBalance = balance - amount;
      setBalance(newBalance);
      DataSource.updateAccountBalance(accountId, newBalance);
    }
  }

  public int getAccountId() {
    return this.accountId;
  }

  public void setAccountId(int accountId) {
    this.accountId = accountId;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public double getBalance() {
    return this.balance;
  }

  public void setBalance(double balance) {
    this.balance = balance;
  }

}
