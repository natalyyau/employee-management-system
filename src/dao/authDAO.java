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
    public boolean validateUser(String email, String password) throws SQLException {
        String sql = "SELECT password FROM employees WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String dbPassword = rs.getString("password");
                return password.equals(dbPassword);
            } else {
                return false; // email not found
            }
        }
    }

    @Override
    public String getUserRole(String email) throws SQLException {
        String sql = "SELECT role FROM employees WHERE email = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getString("role"); // Returns HR_ADMIN or EMPLOYEE
            } else {
                return null; // email not found
            }
        }
    }
}
