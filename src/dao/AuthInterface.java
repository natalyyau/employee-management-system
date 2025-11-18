package dao;

import java.sql.SQLException;

public interface AuthInterface {
    // Validates username and password
    boolean validateUser(String username, String password) throws SQLException;

    // Returns the role of the user (HR_ADMIN or EMPLOYEE)
    String getUserRole(String username) throws SQLException;
}
