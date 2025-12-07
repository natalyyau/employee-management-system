package view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import controller.adminController;

public class adminView {
    private adminController controller;
    private int adminId;

    public adminView(adminController controller, int adminId) {
        this.controller = controller;
        this.adminId = adminId;
}

    public void launch() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== HR Admin Dashboard ===");
            System.out.println("1. Search Employee");
            System.out.println("2. Pay Report by Division");
            System.out.println("3. Pay Report by Job Title");
            System.out.println("4. Employees Hired in Date Range");
            System.out.println("5. Add New Employee");
            System.out.println("6. Update Salary by Percentage Range");
            System.out.println("7. View My Profile");
            System.out.println("8. Exit");
            System.out.print("Choose option: ");

            int option = scanner.nextInt();
            scanner.nextLine();
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
                    System.out.print("Enter start date (YYYY-MM-DD): ");
                    String startDate = scanner.nextLine().trim();
                
                    System.out.print("Enter end date (YYYY-MM-DD): ");
                    String endDate = scanner.nextLine().trim();
                
                    // Validate date format
                    java.sql.Date start = null;
                    java.sql.Date end = null;
                
                    try {
                        start = java.sql.Date.valueOf(startDate);
                        end = java.sql.Date.valueOf(endDate);
                    } catch (IllegalArgumentException e) {
                        System.out.println("ERROR: Invalid date format. Use YYYY-MM-DD.");
                        break;
                    }
                
                    // Validate range
                    if (start.after(end)) {
                        System.out.println("ERROR: Invalid date range. Start date cannot be after end date.");
                        break;
                    }
                
                    // Fetch from controller
                    List<Map<String, Object>> hiredEmployees = controller.getEmployeesHiredInRange(startDate, endDate);
                
                    if (hiredEmployees == null || hiredEmployees.isEmpty()) {
                        System.out.println("No employees found hired between " + startDate + " and " + endDate + ".");
                    } else {
                        printHiredEmployeesTable(hiredEmployees);
                    }
                    break;
                

                case 5:
                    addNewEmployee(scanner);
                    break;

                case 6:
                    updateSalaryByRange(scanner);
                    break;

                case 7:
                    viewMyProfile();
                    break;
                
                case 8:
                    running = false;
                    System.out.println("Exiting admin dashboard...");
                    break;


                default:
                    System.out.println("Invalid option.");
            }
        }

        scanner.close();
    }

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
    private void editEmployee(Map<String, Object> employee, Scanner scanner) {
        System.out.println("\nEditing Employee: " + employee.get("empid"));
        boolean editing = true;
    
        Map<String, Object> updated = new HashMap<>(employee);
    
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
                    System.out.print("New Fname [" + updated.get("Fname") + "]: ");
                    String fname = scanner.nextLine().trim();
                    if (fname.isEmpty()) {
                        System.out.println("ERROR: Blank field not allowed.");
                    } else updated.put("Fname", fname);
                    break;
    
                case "2":
                    System.out.print("New Lname [" + updated.get("Lname") + "]: ");
                    String lname = scanner.nextLine().trim();
                    if (lname.isEmpty()) {
                        System.out.println("ERROR: Blank field not allowed.");
                    } else updated.put("Lname", lname);
                    break;
    
                case "3":
                    System.out.print("New Email [" + updated.get("email") + "]: ");
                    String email = scanner.nextLine().trim();
                    if (email.isEmpty()) {
                        System.out.println("ERROR: Blank field not allowed.");
                    } else updated.put("email", email);
                    break;
    
                case "4":
                    System.out.print("New Salary [" + updated.get("Salary") + "]: ");
                    String sal = scanner.nextLine().trim();
                    if (sal.isEmpty()) {
                        System.out.println("ERROR: Blank field not allowed.");
                    } else {
                        try {
                            updated.put("Salary", Double.parseDouble(sal));
                        } catch (NumberFormatException e) {
                            System.out.println("ERROR: Invalid salary value.");
                        }
                    }
                    break;
    
                case "5":
                    editing = false;
                    break;
    
                default:
                    System.out.println("Invalid option.");
            }
        }
    
        // Final validation BEFORE UPDATE
        if (updated.get("Fname").toString().isEmpty()
            || updated.get("Lname").toString().isEmpty()
            || updated.get("email").toString().isEmpty()
            || updated.get("Salary") == null) 
        {
            System.out.println("\nERROR: One or more required fields are blank. Update cancelled.");
            return;
        }
    
        // Save changes
        try {
            controller.updateEmployee(updated);
            System.out.println("Employee updated successfully!");
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
}

    private void viewMyProfile() {
        System.out.println("\n=== My Profile ===");
    
        Map<String, Object> me = controller.getEmployee(adminId);
    
        if (me == null) {
            System.out.println("Error: Could not load your profile.");
            return;
        }
    
        System.out.println("Employee ID: " + me.get("empid"));
        System.out.println("First Name: " + me.get("Fname"));
        System.out.println("Last Name: " + me.get("Lname"));
        System.out.println("Email: " + me.get("email"));
        System.out.println("Role: " + me.get("role"));
        
        if (me.get("HireDate") != null) {
            System.out.println("Hire Date: " + me.get("HireDate"));
        } else {
            System.out.println("Hire Date: Not set");
        }
        
        if (me.get("Salary") != null) {
            System.out.println("Salary: $" + String.format("%.2f", me.get("Salary")));
        } else {
            System.out.println("Salary: Not set");
        }
        
        if (me.get("SSN") != null) {
            System.out.println("SSN: " + me.get("SSN"));
        } else {
            System.out.println("SSN: Not provided");
        }
        
        System.out.println();
    }
    
    private void printHiredEmployeesTable(List<Map<String, Object>> employees) {
        System.out.println("\n=== Employees Hired in Date Range ===");
        System.out.printf(
            "%-6s | %-15s | %-15s | %-12s | %-30s | %-20s\n",
            "EmpID", "First Name", "Last Name", "Hire Date", "Job Title", "Division"
        );
        System.out.println("------------------------------------------------------------------------------------------------------------------------");

        for (Map<String, Object> emp : employees) {
            System.out.printf(
                "%-6d | %-15s | %-15s | %-12s | %-30s | %-20s\n",
                emp.get("empid"),
                emp.get("Fname"),
                emp.get("Lname"),
                emp.get("HireDate"),
                emp.get("job_title"),
                emp.get("division_name")
            );
        }
        System.out.println("\nTotal employees: " + employees.size());
    }

    private void addNewEmployee(Scanner scanner) {
        System.out.println("\n=== Add New Employee ===");
        Map<String, Object> employeeData = new HashMap<>();

        System.out.print("First Name (required): ");
        String fname = scanner.nextLine().trim();
        if (fname.isEmpty()) {
            System.out.println("Error: First Name is required.");
            return;
        }
        employeeData.put("Fname", fname);

        System.out.print("Last Name (required): ");
        String lname = scanner.nextLine().trim();
        if (lname.isEmpty()) {
            System.out.println("Error: Last Name is required.");
            return;
        }
        employeeData.put("Lname", lname);

        System.out.print("Email (required): ");
        String email = scanner.nextLine().trim();
        if (email.isEmpty() || !email.contains("@")) {
            System.out.println("Error: Valid email is required.");
            return;
        }
        employeeData.put("email", email);

        System.out.print("Salary (required): ");
        String salaryInput = scanner.nextLine().trim();
        double salary;
        try {
            salary = Double.parseDouble(salaryInput);
            if (salary < 0) {
                System.out.println("Error: Salary must be a positive number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid salary format.");
            return;
        }
        employeeData.put("Salary", salary);

        System.out.print("Hire Date (YYYY-MM-DD, optional - press Enter for today): ");
        String hireDateInput = scanner.nextLine().trim();
        if (!hireDateInput.isEmpty()) {
            try {
                java.sql.Date.valueOf(hireDateInput);
                employeeData.put("HireDate", hireDateInput);
            } catch (IllegalArgumentException e) {
                System.out.println("Warning: Invalid date format. Using today's date.");
            }
        }

        System.out.print("SSN (optional - press Enter to skip): ");
        String ssn = scanner.nextLine().trim();
        if (!ssn.isEmpty()) {
            employeeData.put("SSN", ssn);
        }

        System.out.print("Role (admin/employee, optional - press Enter for 'employee'): ");
        String role = scanner.nextLine().trim().toLowerCase();
        if (!role.isEmpty() && (role.equals("admin") || role.equals("employee"))) {
            employeeData.put("role", role);
        } else if (!role.isEmpty()) {
            System.out.println("Warning: Invalid role. Using default 'employee'.");
        }

        System.out.print("Default Password (optional - press Enter to use email as password): ");
        String password = scanner.nextLine().trim();
        if (!password.isEmpty()) {
            employeeData.put("password", password);
        }

        System.out.println("\n=== Employee Summary ===");
        System.out.println("Name: " + fname + " " + lname);
        System.out.println("Email: " + email);
        System.out.println("Salary: $" + String.format("%.2f", salary));
        System.out.println("Hire Date: " + (employeeData.get("HireDate") != null ? employeeData.get("HireDate") : "Today"));
        System.out.println("SSN: " + (ssn.isEmpty() ? "Not provided" : ssn));
        System.out.println("Role: " + (employeeData.get("role") != null ? employeeData.get("role") : "employee"));
        System.out.println("Password: " + (password.isEmpty() ? "Email (default)" : "Custom"));

        System.out.print("\nConfirm adding this employee? (yes/no): ");
        String confirm = scanner.nextLine().trim().toLowerCase();
        if (!confirm.equals("yes")) {
            System.out.println("Employee addition cancelled.");
            return;
        }

        int empId = controller.addEmployee(employeeData);
        if (empId > 0) {
            System.out.println("\nEmployee added successfully!");
            System.out.println("Employee ID: " + empId);
            System.out.println("Login credentials:");
            System.out.println("  Email: " + email);
            System.out.println("  Password: " + (password.isEmpty() ? email : password));
            System.out.println("\nPlease inform the employee to change their password after first login.");
        } else {
            System.out.println("\nError: Failed to add employee.");
        }
    }

    private void updateSalaryByRange(Scanner scanner) {
        System.out.println("\n=== Update Salary by Percentage Range ===");

        try {
            System.out.print("Enter minimum salary: ");
            double minSalary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter maximum salary: ");
            double maxSalary = Double.parseDouble(scanner.nextLine().trim());

            System.out.print("Enter percentage increase (example: 3.2): ");
            double percent = Double.parseDouble(scanner.nextLine().trim());

            if (minSalary < 0 || maxSalary < 0 || percent <= 0) {
                System.out.println("Error: Values must be positive numbers.");
                return;
            }

            if (minSalary > maxSalary) {
                System.out.println("Error: Minimum salary cannot be greater than maximum salary.");
                return;
            }

            System.out.println("\nYou entered:");
            System.out.println("Min Salary: " + minSalary);
            System.out.println("Max Salary: " + maxSalary);
            System.out.println("Increase %: " + percent);

            System.out.print("\nConfirm update? (yes/no): ");
            String confirm = scanner.nextLine().trim().toLowerCase();

            if (!confirm.equals("yes")) {
                System.out.println("Salary update cancelled.");
                return;
            }

            controller.updateSalaryRange(minSalary, maxSalary, percent);

            System.out.println("\nSalary update completed.");

        } catch (NumberFormatException e) {
            System.out.println("Invalid number format. Please try again.");
        } catch (Exception e) {
            System.out.println("Error updating salary: " + e.getMessage());
        }
    }
}
