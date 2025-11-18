package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.User;
import util.DatabaseConnection;

public class authDAO {
    
    public User authenticate(String username, String password) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT user_id, username, password_hash, role, empid FROM users WHERE username = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String storedPassword = rs.getString("password_hash");
                // Simple password check (in production, use proper hashing like BCrypt)
                if (storedPassword.equals(password)) {
                    User user = new User();
                    user.setUserId(rs.getInt("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setPasswordHash(rs.getString("password_hash"));
                    user.setRole(rs.getString("role"));
                    int empid = rs.getInt("empid");
                    if (!rs.wasNull()) {
                        user.setEmpid(empid);
                    }
                    return user;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    public User getUserById(int userId) {
        Connection conn = DatabaseConnection.getConnection();
        String sql = "SELECT user_id, username, password_hash, role, empid FROM users WHERE user_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPasswordHash(rs.getString("password_hash"));
                user.setRole(rs.getString("role"));
                int empid = rs.getInt("empid");
                if (!rs.wasNull()) {
                    user.setEmpid(empid);
                }
                return user;
            }
        } catch (SQLException e) {
            System.err.println("Error getting user: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
}
