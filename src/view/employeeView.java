package view;

import controller.employeeController;
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
            System.out.println("2. Logout");
            System.out.print("Choose option: ");
            int option = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (option) {
                case 1:
                    viewMyInfo();
                    break;
                case 2:
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

        System.out.println("\n--- My Info ---");
        System.out.println("Employee ID : " + emp.get("empid"));
        System.out.println("Name        : " + emp.get("Fname") + " " + emp.get("Lname"));
        System.out.println("Email       : " + emp.get("email"));
        System.out.println("Role        : " + emp.get("role"));
        System.out.println("Salary      : $" + emp.get("Salary"));
        System.out.println("SSN         : " + emp.get("SSN"));
        System.out.println("Hire Date   : " + emp.get("HireDate"));
        System.out.println("-----------------\n");
    }
}


