package controller;

import dao.EmployeeInterface;
import java.util.List;
import java.util.Map;

public class employeeController {
    private EmployeeInterface employeeDAO;

    public employeeController(EmployeeInterface employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    // Fetch employee data by empId
    public Map<String, Object> getEmployeeData(int empId) {
        try {
            return employeeDAO.getEmployeeById(empId);
        } catch (Exception e) {
            System.out.println("Error fetching employee data: " + e.getMessage());
            return null;
        }
    }

    // Search for employees by ID, name, DOB, or SSN
    public List<Map<String, Object>> searchEmployees(String query) {
        try {
            return employeeDAO.searchEmployees(query);
        } catch (Exception e) {
            System.out.println("Error searching employees: " + e.getMessage());
            return null;
        }
    }
}
