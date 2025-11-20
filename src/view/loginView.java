package view;

import controller.authController;
import dao.AuthDAO;
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

            AuthDAO authDAO = new AuthDAO(conn);
            authController authController = new authController(authDAO);
            Scanner scanner = new Scanner(System.in);

            String email;
            String password;
            boolean loggedIn = false;

            System.out.println("=== Employee Management System Login ===");
            System.out.println("Type 'exit' at any time to quit.\n");

            // Loop until login is successful
            while (!loggedIn) {
                System.out.print("Email: ");
                email = scanner.nextLine();
                if ("exit".equalsIgnoreCase(email)) {
                    System.out.println("Exiting program...");
                    break;
                }

                System.out.print("Password: ");
                password = scanner.nextLine();
                if ("exit".equalsIgnoreCase(password)) {
                    System.out.println("Exiting program...");
                    break;
                }

                if (authController.login(email, password)) {
                    loggedIn = true; // exit loop
                    String role = authController.getRole(email);

                    if ("admin".equalsIgnoreCase(role)) {
                        System.out.println("Welcome HR Admin!");
                        adminView adminView = new adminView();
                        adminView.launch(); // Switch to admin dashboard

                    } else if ("employee".equalsIgnoreCase(role)) {
                        System.out.println("Welcome Employee!");
                        employeeView employeeView = new employeeView();
                        employeeView.launch(); // Switch to employee dashboard

                    } else {
                        System.out.println("Unknown role!");
                    }

                } else {
                    System.out.println("Invalid email or password. Please try again.\n");
                }
            }

            scanner.close();
            conn.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
