package dao;

import java.sql.SQLException;

public interface AuthInterface {
    // Validates email and password
    boolean validateUser(String email, String password) throws SQLException;

    // Returns the role of the user (HR_ADMIN or EMPLOYEE)
    String getUserRole(String email) throws SQLException;

    // NEW: Get employee ID by email
    int getEmpIdByEmail(String email) throws SQLException;
}

