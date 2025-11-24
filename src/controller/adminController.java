package controller;

import java.sql.Date;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import dao.EmployeeInterface;

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

    public List<String> getPayReportByDivision() {
        return reportsDAO.getPayReportByDivision();
    }

    public List<String> getPayReportByJobTitle() {
        return reportsDAO.getPayReportByJobTitle();
    }

    public List<Map<String, Object>> getEmployeesHiredInRange(String startDate, String endDate) {
        try {
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);
            return reportsDAO.getEmployeesHiredInRange(start, end);
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        } catch (Exception e) {
            System.out.println("Error fetching employees hired in range: " + e.getMessage());
        }
        return Collections.emptyList();
    }

    public int addEmployee(Map<String, Object> employeeData) {
        try {
            return employeeDAO.addEmployee(employeeData);
        } catch (Exception e) {
            System.out.println("Error adding employee: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }
}
