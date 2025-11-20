package view;

import controller.employeeController;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class employeeView {

    private employeeController controller;
    private int empId; // currently logged-in employee ID

    public employeeView(employeeController controller, int empId) {
        this.controller = controller;
        this.empId = empId;
    }

    public void launch() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Employee Dashboard ===");
            System.out.println("1. View My Info");
            System.out.println("2. Search Employees");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    viewMyInfo();
                    break;
                case 2:
                    searchEmployees(scanner);
                    break;
                case 3:
                    running = false;
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }

    private void viewMyInfo() {
        Map<String, Object> emp = controller.getEmployeeData(empId);
        if (emp == null) {
            System.out.println("Employee data not found.");
            return;
        }
        // Print single employee in table format
        printEmployeeTable(List.of(emp));
    }

    private void searchEmployees(Scanner scanner) {
        System.out.print("Enter employee ID, name, DOB (YYYY-MM-DD), or SSN: ");
        String query = scanner.nextLine();
        List<Map<String, Object>> results = controller.searchEmployees(query);

        if (results == null || results.isEmpty()) {
            System.out.println("No employees found.");
            return;
        }

        System.out.println("\n--- Search Results ---");
        printEmployeeTable(results);
    }

    // Helper method to print employees in a formatted table
    private void printEmployeeTable(List<Map<String, Object>> employees) {
        // Table header
        System.out.printf(
            "%-6s | %-10s | %-10s | %-25s | %-10s | %-12s | %-10s | %-11s\n",
            "EmpID", "Fname", "Lname", "Email", "Role", "HireDate", "Salary", "SSN"
        );
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

        // Table rows
        for (Map<String, Object> emp : employees) {
            System.out.printf(
                "%-6s | %-10s | %-10s | %-25s | %-10s | %-12s | %-10s | %-11s\n",
                emp.getOrDefault("empid", ""),
                emp.getOrDefault("Fname", ""),
                emp.getOrDefault("Lname", ""),
                emp.getOrDefault("email", ""),
                emp.getOrDefault("role", ""),
                emp.getOrDefault("HireDate", ""),
                emp.getOrDefault("Salary", ""),
                emp.getOrDefault("SSN", "")
            );
        }
    }
}
