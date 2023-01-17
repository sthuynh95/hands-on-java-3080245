package bank;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSource {
  // connecting to database
  // reading and writing from database

  // import Connection from java.sql
  public static Connection connect() {
    // declare path to database file
    String db_file = "jdbc:sqlite:resources/bank.db";
    Connection connection = null;

    try {
      connection = DriverManager.getConnection(db_file);
      // System.out.println("We are connected");
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return connection;

  }

  public static Customer getCustomer(String username) {
    String sql = "select * from customers where username = ?"; // ? is a placeholder
    // never send raw user input as a sql query -- safer to use place holder
    Customer customer = null;

    // try with resources -- Java will close the resource automatically after the
    // try block has finished
    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {

      statement.setString(1, username);
      try (ResultSet resultSet = statement.executeQuery()) {
        customer = new Customer(
            resultSet.getInt("id"),
            resultSet.getString("name"),
            resultSet.getString("username"),
            resultSet.getString("password"),
            resultSet.getInt("account_id"));
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return customer;
  }

  public static Account getAccount(int accountId) {
    String sql = "select * from accounts where id = ?";
    Account account = null;

    try (Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {
      statement.setInt(1, accountId);
      try (ResultSet resultSet = statement.executeQuery()) {
        account = new Account(
            resultSet.getInt("id"),
            resultSet.getString("type"),
            resultSet.getDouble("balance"));
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return account;
  }

  public static void updateAccountBalance(int accountId, double balance) {
    String sql = "update accounts set balance = ? where id = ?";
    try (
        Connection connection = connect();
        PreparedStatement statement = connection.prepareStatement(sql);) {

          statement.setDouble(1, balance);
          statement.setInt(2, accountId);

          statement.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  /* public static void main(String[] args) {
    // obtain data from a data-base and store it in an object
    Customer customer = getCustomer("twest8o@friendfeed.com");
    // System.out.println(customer.getAccountID());
    Account account = getAccount(customer.getAccountID());
    System.out.println(account.getBalance());
  }*/

}
