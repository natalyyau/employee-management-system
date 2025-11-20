package controller;

import dao.EmployeeInterface;
import dao.PayrollInterface;
import java.util.List;
import java.util.Map;

public class employeeController {
    private EmployeeInterface employeeDAO;
    private PayrollInterface payrollDAO;

    public employeeController(EmployeeInterface employeeDAO, PayrollInterface payrollDAO) {
        this.employeeDAO = employeeDAO;
        this.payrollDAO = payrollDAO;
    }

    // Existing method
    public Map<String, Object> getEmployeeData(int empId) {
        try {
            return employeeDAO.getEmployeeById(empId);
        } catch (Exception e) {
            System.out.println("Error fetching employee data: " + e.getMessage());
            return null;
        }
    }

    // NEW: Get payroll history
    public List<Map<String, Object>> getPayrollHistory(int empId) {
        try {
            return payrollDAO.getPayrollHistoryByEmpId(empId);
        } catch (Exception e) {
            System.out.println("Error fetching payroll: " + e.getMessage());
            return null;
        }
    }
}
