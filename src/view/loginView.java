package view;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class loginView {

    public static void main(String[] args) {
        try {
            // Connect to MySQL
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employeeData", "root", "password");

            AuthDAO authDAO = new AuthDAOImpl(conn);
            AuthController authController = new AuthController(authDAO);
            Scanner scanner = new Scanner(System.in);

            System.out.println("=== Employee Management System Login ===");
            System.out.print("Username: ");
            String username = scanner.nextLine();

            System.out.print("Password: ");
            String password = scanner.nextLine();

            if (authController.login(username, password)) {
                String role = authController.getRole(username);
                if ("HR_ADMIN".equals(role)) {
                    System.out.println("Welcome HR Admin!");
                    // Launch Admin Dashboard here
                } else if ("EMPLOYEE".equals(role)) {
                    System.out.println("Welcome Employee!");
                    // Launch Employee Dashboard here
                } else {
                    System.out.println("Unknown role!");
                }
            }

            scanner.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}


