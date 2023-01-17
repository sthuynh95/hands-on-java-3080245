package bank;

import java.util.Scanner;

import javax.security.auth.login.LoginException;

import bank.exceptions.AmountException;

public class Menu {

  private Scanner scanner; // obtain user input

  public static void main(String[] args) {
    System.out.println("Welcome to Not-Fake-At-All International Bank!");

    Menu menu = new Menu();
    menu.scanner = new Scanner(System.in);

    Customer customer = menu.authenticateUser();

    if (customer != null) {
      Account account = DataSource.getAccount(customer.getAccountID());
      menu.showMenu(customer, account);
    }

    menu.scanner.close();
  }

  private Customer authenticateUser() {
    System.out.println("Please enter your username");
    String username = scanner.next();

    System.out.println("Please enter your password");
    String password = scanner.next();

    Customer customer = null;
    try {
      customer = Authenticator.login(username, password);
    } catch (LoginException e) {
      System.out.println("There was an error: " + e.getMessage());
    }

    return customer;

  }

  private void showMenu(Customer customer, Account account) {
    int selection = 0;

    while (selection != 4 && customer.isAuthenticated()) {
      // print the option
      System.out.println("===================================");
      System.out.println("Select one the following options: ");
      System.out.println("1: Deposit");
      System.out.println("2: Withdraw");
      System.out.println("3: Check balance");
      System.out.println("4: Exit");
      System.out.println("===================================");

      // read selection as input
      selection = scanner.nextInt();

      // use switch decision structure to execute the job
      double amount = 0;
      switch (selection) {

        case 1:
          System.out.println("How much would you like to deposite?");
          amount = scanner.nextDouble();
          try {
            account.deposit(amount);
          } catch (AmountException e) {
            e.getMessage();
            System.out.println("Please try again.");
          }
          break; // not continue after finish the case

        case 2:
          System.out.println("How much would you like to withdraw?");
          amount = scanner.nextDouble();
          try {
            account.withdraw(amount);
          } catch (AmountException e) {
            e.getMessage();
            System.out.println("Please try again.");
          }
          break;

        case 3:
          System.out.println("Current balance: " + account.getBalance());
          break;

        case 4:
          Authenticator.logout(customer);
          System.out.println("Thank you for banking with us!");
          break;

        default:
          System.out.println("Invalid option. Please try again");
          break;
      }
    }
  }
}
