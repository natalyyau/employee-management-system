package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AuthDAO implements AuthInterface {

    private Connection conn;

    // Constructor receives a JDBC connection
    public AuthDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean validateUser(String username, String password) throws SQLException {
        String sql = "SELECT password FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                return password.equals(dbPassword);
            } else {
                return false; // Username not found
            }
        }
    }

    @Override
    public String getUserRole(String username) throws SQLException {
        String sql = "SELECT role FROM users WHERE username = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role"); // Returns HR_ADMIN or EMPLOYEE
            } else {
                return null; // Username not found
            }
        }
    }
}
