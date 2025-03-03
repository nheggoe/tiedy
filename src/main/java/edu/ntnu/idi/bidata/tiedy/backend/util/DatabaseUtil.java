package edu.ntnu.idi.bidata.tiedy.backend.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Utility class that help with database connection and create tables
 *
 * @author Nick Heggø
 * @version 2025.02.28
 */
public class DatabaseUtil {

  private static final String JDBC_URL = "jdbc:h2:./data/userdb";
  private static final String USER = "sa";
  private static final String PASSWORD = "";

  private DatabaseUtil() {}

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
  }

  /**
   * Creates the user database if it does not exist yet.
   * <p>
   * Users table contains:
   * <pre>
   * int id PK "AUTO_INCREMENT"
   * varchar(50) username "NOT NULL, UNIQUE"
   * varchar(100) password "NOT NULL"
   * varchar(100) email "NOT NULL"
   * timestamp created_at "DEFAULT CURRENT_TIMESTAMP"
   * </pre>
   */
  public static void initDatabase() {
    try (Connection conn = getConnection();
         Statement stmt = conn.createStatement()) {

      String sql = "CREATE TABLE IF NOT EXISTS users (" +
          "id INT AUTO_INCREMENT PRIMARY KEY, " +
          "username VARCHAR(50) NOT NULL UNIQUE, " +
          "password VARCHAR(100) NOT NULL, " +
          "email VARCHAR(100) NOT NULL, " +
          "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
          ")";
      stmt.execute(sql);

    } catch (SQLException e) {
      // TODO add logger
      e.printStackTrace();
    }
  }
}
