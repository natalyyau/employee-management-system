package dao;

import java.sql.*;

public class AuthDAO implements AuthInterface {

    private Connection conn;

    public AuthDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public boolean validateUser(String email, String password) throws SQLException {
        String sql = "SELECT COUNT(*) FROM employees WHERE email=? AND password=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

    @Override
    public String getUserRole(String email) throws SQLException {
        String sql = "SELECT role FROM employees WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("role");
                }
            }
        }
        return null;
    }

    @Override
    public int getEmpIdByEmail(String email) throws SQLException {
        String sql = "SELECT empid FROM employees WHERE email=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("empid");
                }
            }
        }
        return -1; // not found
    }
}
