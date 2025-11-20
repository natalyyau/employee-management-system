package dao;

import java.sql.*;
import java.util.*;

public class employeeDAO implements EmployeeInterface {
    private Connection conn;

    public employeeDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public Map<String, Object> getEmployeeById(int empId) throws SQLException {
        String sql = "SELECT * FROM employees WHERE empid=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapEmployee(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Map<String, Object>> searchEmployees(String query) throws SQLException {
        String sql;
        PreparedStatement ps;

        // Check if query is numeric to treat as empid
        if (query.matches("\\d+")) {
            sql = "SELECT * FROM employees WHERE empid=? OR Fname LIKE ? OR Lname LIKE ? OR SSN=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, Integer.parseInt(query));
            ps.setString(2, "%" + query + "%");
            ps.setString(3, "%" + query + "%");
            ps.setString(4, query);
        } else {
            sql = "SELECT * FROM employees WHERE Fname LIKE ? OR Lname LIKE ? OR SSN=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, "%" + query + "%");
            ps.setString(2, "%" + query + "%");
            ps.setString(3, query);
        }

        List<Map<String, Object>> results = new ArrayList<>();
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                results.add(mapEmployee(rs));
            }
        }
        return results;
    }

    @Override
    public void updateEmployee(Map<String, Object> employeeData) throws SQLException {
        String sql = "UPDATE employees SET Fname=?, Lname=?, email=?, Salary=? WHERE empid=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, (String) employeeData.get("Fname"));
            ps.setString(2, (String) employeeData.get("Lname"));
            ps.setString(3, (String) employeeData.get("email"));
            ps.setDouble(4, (Double) employeeData.get("Salary"));
            ps.setInt(5, (Integer) employeeData.get("empid"));
            ps.executeUpdate();
        }
    }

    // Helper method to map a ResultSet row to a Map
    private Map<String, Object> mapEmployee(ResultSet rs) throws SQLException {
        Map<String, Object> employee = new HashMap<>();
        employee.put("empid", rs.getInt("empid"));
        employee.put("Fname", rs.getString("Fname"));
        employee.put("Lname", rs.getString("Lname"));
        employee.put("email", rs.getString("email"));
        employee.put("role", rs.getString("role"));
        employee.put("Salary", rs.getDouble("Salary"));
        employee.put("SSN", rs.getString("SSN"));
        employee.put("HireDate", rs.getDate("HireDate"));
        return employee;
    }
}
