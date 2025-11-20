package controller;

import dao.EmployeeInterface;
import java.util.List;
import java.util.Map;

public class adminController {
    private EmployeeInterface employeeDAO;

    public adminController(EmployeeInterface employeeDAO) {
        this.employeeDAO = employeeDAO;
    }

    public Map<String, Object> getEmployee(int empId) {
        try {
            return employeeDAO.getEmployeeById(empId);
        } catch (Exception e) {
            System.out.println("Error fetching employee: " + e.getMessage());
            return null;
        }
    }

    public List<Map<String, Object>> searchEmployees(String query) {
        try {
            return employeeDAO.searchEmployees(query);
        } catch (Exception e) {
            System.out.println("Error searching employees: " + e.getMessage());
            return null;
        }
    }

    public void updateEmployee(Map<String, Object> employeeData) {
    try {
        employeeDAO.updateEmployee(employeeData); // This updates the DB
    } catch (Exception e) {
        System.out.println("Error updating employee: " + e.getMessage());
    }
}
}

