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
            System.out.println("2. View My Pay Statement History");
            System.out.println("3. Logout");
            System.out.print("Choose option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    viewMyInfo();
                    break;

                case 2:
                    viewPayHistory();
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

    /* ===============================
       VIEW PERSONAL EMPLOYEE INFO
       =============================== */
    private void viewMyInfo() {
        System.out.println("\n=== My Employee Info ===");
        Map<String, Object> emp = controller.getEmployeeData(empId);
        if (emp == null) {
            System.out.println("Employee data not found.");
            return;
        }
        printEmployeeTable(emp);
    }

    private void printEmployeeTable(Map<String, Object> emp) {
        System.out.printf(
            "%-6s | %-10s | %-10s | %-25s | %-10s | %-12s | %-10s | %-11s\n",
            "EmpID", "Fname", "Lname", "Email", "Role", "HireDate", "Salary", "SSN"
        );
        System.out.println("-----------------------------------------------------------------------------------------------------------------");

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

    /* ===============================
       VIEW PAY HISTORY
       =============================== */
    private void viewPayHistory() {
        System.out.println("\n=== Pay Statement History ===");

        List<Map<String, Object>> history = controller.getPayrollHistory(empId);

        if (history == null || history.isEmpty()) {
            System.out.println("No payroll history found.");
            return;
        }

        printPayrollTable(history);
    }

    private void printPayrollTable(List<Map<String, Object>> history) {
        System.out.printf(
            "%-6s | %-12s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s\n",
            "PayID", "Pay Date", "Earnings", "Fed Tax", "Medicare", "SocialSec",
            "StateTax", "401k", "Health"
        );
        System.out.println("---------------------------------------------------------------------------------------------------------------------");

        for (Map<String, Object> row : history) {
            System.out.printf(
                "%-6s | %-12s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s | %-10s\n",
                row.getOrDefault("payID", ""),
                row.getOrDefault("pay_date", ""),
                row.getOrDefault("earnings", ""),
                row.getOrDefault("fed_tax", ""),
                row.getOrDefault("fed_med", ""),
                row.getOrDefault("fed_SS", ""),
                row.getOrDefault("state_tax", ""),
                row.getOrDefault("retire_401k", ""),
                row.getOrDefault("health_care", "")
            );
        }
    }
}

