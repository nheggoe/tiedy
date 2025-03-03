package edu.ntnu.idi.bidata.tiedy.backend.dao;

import edu.ntnu.idi.bidata.tiedy.backend.user.User;
import edu.ntnu.idi.bidata.tiedy.backend.util.DatabaseUtil;
import edu.ntnu.idi.bidata.tiedy.backend.util.PasswordUtil;

import java.sql.*;

/**
 * Data Access Object (DAO) for interacting with the user database.
 * Provides methods to register a new user and authenticate existing users.
 *
 * @author Nick Heggø
 * @version 2025.02.28
 */
public class UserDAO {

  /**
   * Adds the User object to the database for storing.
   *
   * @param user object used to add to the {@code users} database schema.
   * @return true if the user was successfully added, false otherwise
   */
  public boolean registerUser(User user) {
    String sql = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, user.getUsername());

      String hashedPassword = PasswordUtil.hashPassword(user.getPassword());
      pstmt.setString(2, hashedPassword);

      pstmt.setString(3, user.getEmail());

      int rowsAffected = pstmt.executeUpdate();
      return rowsAffected > 0;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }

  /**
   * Used to check if the login credential matches the stored pair.
   * If matches, login successfully, else fails
   *
   * @param username provided username to authenticate
   * @param password provided password to verify authentication
   * @return User object, which is used by the application to know "who" was logged in.
   */
  public User authenticateUser(String username, String password) {
    String sql = "SELECT * FROM users WHERE username = ? AND password = ?";

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

      pstmt.setString(1, username);
      pstmt.setString(2, password);

      try (ResultSet rs = pstmt.executeQuery()) {
        if (rs.next()) {
          String storedHash = rs.getString("password");
          if (PasswordUtil.checkPassword(password, storedHash)) {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));

            Timestamp timestamp = rs.getTimestamp("created_at");
            if (timestamp != null) {
              user.setCreatedAt(timestamp.toLocalDateTime());
            }

            return user;
          }
        }
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }
    return null;
  }

}
