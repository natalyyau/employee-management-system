package view;

import java.util.Scanner;
import controller.AuthController;

public class loginView {
    private Scanner scanner;
        private AuthController authController;

        public LoginView() {
            scanner = new Scanner(System.in);
            authController = new AuthController();
        }

    public void showLogin() {
        System.out.println("=== Welcome to Company Z Employee Management System ===");

        System.out.print("Username: ");
        String username = scanner.nextLine();

        System.out.print("Password: ");
        String password = scanner.nextLine();

        User loggedInUser = authController.login(username, password);

        if (loggedInUser != null) {
            System.out.println("Login successful! Welcome " + loggedInUser.getUsername());
            redirectDashboard(loggedInUser);
        } else {
            System.out.println("Invalid username or password. Try again.");
            showLogin(); // Retry login
        }
    }

    private void redirectDashboard(User user) {
        if (user.getRole().equals("HR_ADMIN")) {
            AdminDashboardView adminView = new AdminDashboardView(user);
            adminView.showMenu();
        } else if (user.getRole().equals("EMPLOYEE")) {
            EmployeeDashboardView employeeView = new EmployeeDashboardView(user);
            employeeView.showMenu();
        }
    }
}
