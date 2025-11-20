package view;

import controller.adminController;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class adminView {
    private adminController controller;

    public adminView(adminController controller) {
        this.controller = controller;
    }

    public void launch() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== HR Admin Dashboard ===");
            System.out.println("1. Search Employee");
            System.out.println("2. Exit");
            System.out.print("Choose option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    System.out.print("Enter Employee ID, name, SSN, or DOB: ");
                    String query = scanner.nextLine();
                    List<Map<String, Object>> results = controller.searchEmployees(query);

                    if (results == null || results.isEmpty()) {
                        System.out.println("No employees found.");
                    } else {
                        printEmployeeTable(results);
                    }
                    break;

                case 2:
                    running = false;
                    System.out.println("Exiting admin dashboard...");
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    // Helper method to print a list of employees neatly
    private void printEmployeeTable(List<Map<String, Object>> employees) {
        // Print header with fixed width for each column
        System.out.printf(
            "%-6s | %-10s | %-10s | %-25s | %-10s | %-12s | %-10s | %-11s\n",
            "EmpID", "Fname", "Lname", "Email", "Role", "HireDate", "Salary", "SSN"
        );
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        for (Map<String, Object> emp : employees) {
            System.out.printf(
                "%-6d | %-10s | %-10s | %-25s | %-10s | %-12s | %-10.2f | %-11s\n",
                emp.get("empid"),
                emp.get("Fname"),
                emp.get("Lname"),
                emp.get("email"),
                emp.get("role"),
                emp.get("HireDate"),
                emp.get("Salary"),
                emp.get("SSN")
            );
        }
    }
}
