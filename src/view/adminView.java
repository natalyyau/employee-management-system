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
            System.out.println("2. Pay Report by Division");
            System.out.println("3. Pay Report by Job Title");
            System.out.println("4. Exit");
            System.out.print("Choose option: ");

            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline
            System.out.println();

            switch (option) {
                case 1:
                    System.out.print("Enter Employee ID, name, SSN, or DOB: ");
                    String query = scanner.nextLine();
                    List<Map<String, Object>> results = controller.searchEmployees(query);

                    if (results == null || results.isEmpty()) {
                        System.out.println("No employees found.");
                    } else {
                        printEmployeeTable(results, scanner);
                    }
                    break;

                case 2:
                    List<String> divReport = controller.getPayReportByDivision();
                    for (String line : divReport) {
                    System.out.println(line);
                    }
                    break;

                case 3:
                    List<String> jobReport = controller.getPayReportByJobTitle();
                    for (String line : jobReport) {
                    System.out.println(line);
                    }
                    break;

                case 4:
                    running = false;
                    System.out.println("Exiting admin dashboard...");

                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

    // Helper method to print employees and allow editing
    private void printEmployeeTable(List<Map<String, Object>> employees, Scanner scanner) {
        System.out.println("\n=== Employee Search Results ===");
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

        // Ask if admin wants to edit an employee
        System.out.print("\nDo you want to edit an employee? (yes/no): ");
        String choice = scanner.nextLine().trim().toLowerCase();
        if (choice.equals("yes")) {
            System.out.print("Enter the Employee ID to edit: ");
            int empId = Integer.parseInt(scanner.nextLine());
            Map<String, Object> employee = controller.getEmployee(empId);

            if (employee != null) {
                editEmployee(employee, scanner);
            } else {
                System.out.println("Employee not found.");
            }
        }
    }

    // Method to edit employee details selectively
    private void editEmployee(Map<String, Object> employee, Scanner scanner) {
        System.out.println("\nEditing Employee: " + employee.get("empid"));
        boolean editing = true;

        while (editing) {
            System.out.println("\nSelect field to edit:");
            System.out.println("1. Fname");
            System.out.println("2. Lname");
            System.out.println("3. Email");
            System.out.println("4. Salary");
            System.out.println("5. Done editing");
            System.out.print("Choose option: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("New Fname [" + employee.get("Fname") + "]: ");
                    String fname = scanner.nextLine();
                    if (!fname.isEmpty()) employee.put("Fname", fname);
                    break;

                case "2":
                    System.out.print("New Lname [" + employee.get("Lname") + "]: ");
                    String lname = scanner.nextLine();
                    if (!lname.isEmpty()) employee.put("Lname", lname);
                    break;

                case "3":
                    System.out.print("New Email [" + employee.get("email") + "]: ");
                    String email = scanner.nextLine();
                    if (!email.isEmpty()) employee.put("email", email);
                    break;

                case "4":
                    System.out.print("New Salary [" + employee.get("Salary") + "]: ");
                    String salaryInput = scanner.nextLine();
                    if (!salaryInput.isEmpty()) employee.put("Salary", Double.parseDouble(salaryInput));
                    break;

                case "5":
                    editing = false; // exit loop
                    break;

                default:
                    System.out.println("Invalid option. Try again.");
            }
        }

        // Save changes to the database
        try {
            controller.updateEmployee(employee);
            System.out.println("Employee updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }
}
