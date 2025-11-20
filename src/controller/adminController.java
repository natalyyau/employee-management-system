package controller;

import dao.EmployeeInterface;
import java.util.List;
import java.util.Map;

public class adminController {
    private EmployeeInterface employeeDAO;
    private dao.reportsDAO reportsDAO;

    public adminController(EmployeeInterface employeeDAO, dao.reportsDAO reportsDAO) {
        this.employeeDAO = employeeDAO;
        this.reportsDAO = reportsDAO;
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
            employeeDAO.updateEmployee(employeeData);
        } catch (Exception e) {
            System.out.println("Error updating employee: " + e.getMessage());
        }
    }

    public java.util.List<String> getPayReportByDivision() {
        return reportsDAO.getPayReportByDivision();
    }

    public java.util.List<String> getPayReportByJobTitle() {
        return reportsDAO.getPayReportByJobTitle();
    }
}
