package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeInterface {
    Map<String, Object> getEmployeeById(int empId) throws SQLException;
    List<Map<String, Object>> searchEmployees(String query) throws SQLException;
    void updateEmployee(Map<String, Object> employeeData) throws SQLException;
    int addEmployee(Map<String, Object> employeeData) throws SQLException;
    int getNextEmployeeId() throws SQLException;
    //New method for salary update
    void updateSalaryRange(double minSalary, double maxSalary, double percent) throws SQLException;

}

