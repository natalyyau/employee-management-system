package dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface EmployeeInterface {
    Map<String, Object> getEmployeeById(int empId) throws SQLException;
    List<Map<String, Object>> searchEmployees(String query) throws SQLException;
    void updateEmployee(Map<String, Object> employeeData) throws SQLException;
}

