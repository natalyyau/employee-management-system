package controller;

import dao.AuthInterface;
import java.sql.SQLException;

public class authController {

    private AuthInterface authDAO;

    // Constructor receives DAO (dependency injection)
    public authController(AuthInterface authDAO) {
        this.authDAO = authDAO;
    }

    // Handles login logic
    public boolean login(String email, String password) {
        try {
            return authDAO.validateUser(email, password);
        } catch (SQLException e) {
            System.out.println("Error validating user: " + e.getMessage());
            return false;
        }
    }

    // Gets user role AFTER login
    public String getRole(String email) {
        try {
            return authDAO.getUserRole(email);
        } catch (SQLException e) {
            System.out.println("Error retrieving user role: " + e.getMessage());
            return null;
        }
    }
}
