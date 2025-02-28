package edu.ntnu.idi.bidata.tiedy.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Nick Heggø
 * @version 2025.02.28
 */
public class DatabaseUtil {

  private static final String JDBC_URL = "jdbc:h:./data/userdb";
  private static final String USER = "sa";
  private static final String PASSWORD = "";

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
  }

  public static void initDatabase() {


  }
}
