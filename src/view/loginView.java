package view;

import controller.adminController;
import controller.authController;
import controller.employeeController;
import dao.AuthDAO;
import dao.employeeDAO;
import dao.payrollDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class loginView {

    public static void main(String[] args) {
        try {
            // Connect to MySQL
            Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/employeeData", "root", "YOUR_SQL_PASSWORD");

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
                            // 1. Create DAO
                            employeeDAO empDAO = new employeeDAO(conn);
                            dao.reportsDAO reportsDAO = new dao.reportsDAO(conn);

                        // 2. Create Controller
                        adminController adminController = new adminController(empDAO, reportsDAO);

                        // 3. Create View
                        adminView adminView = new adminView(adminController);

                        // Launch Admin Dashboard
                        adminView.launch();

                    } else if ("employee".equalsIgnoreCase(role)) {
                                            // 1. Create DAO
                        employeeDAO empDAO = new employeeDAO(conn);
                        payrollDAO payrollDAO = new payrollDAO(conn);


                        // 2. Create Controller
                        employeeController empController = new employeeController(empDAO, payrollDAO);

                        // 3. Get empId of logged-in user
                        int empId = authController.getEmpId(email); 

                        // 4. Create View with controller and empId
                        employeeView empView = new employeeView(empController, empId);

                        // Launch Employee Dashboard
                        empView.launch();

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
