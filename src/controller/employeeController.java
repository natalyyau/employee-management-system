package controller;

import dao.EmployeeInterface;
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
}

