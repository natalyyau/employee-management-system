package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    @Override
    public int getNextEmployeeId() throws SQLException {
        String sql = "SELECT COALESCE(MAX(empid), 0) + 1 AS next_id FROM employees";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("next_id");
            }
        }
        return 1; // Default to 1 if table is empty
    }

    @Override
    public int addEmployee(Map<String, Object> employeeData) throws SQLException {
        // Get next employee ID
        int empId = getNextEmployeeId();
        
        String sql = "INSERT INTO employees (empid, Fname, Lname, email, HireDate, Salary, SSN, password, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, empId);
            ps.setString(2, (String) employeeData.get("Fname"));
            ps.setString(3, (String) employeeData.get("Lname"));
            ps.setString(4, (String) employeeData.get("email"));
            
            // Handle optional HireDate
            if (employeeData.get("HireDate") != null) {
                if (employeeData.get("HireDate") instanceof java.sql.Date) {
                    ps.setDate(5, (java.sql.Date) employeeData.get("HireDate"));
                } else {
                    ps.setDate(5, java.sql.Date.valueOf((String) employeeData.get("HireDate")));
                }
            } else {
                ps.setDate(5, new java.sql.Date(System.currentTimeMillis())); // Default to today
            }
            
            ps.setDouble(6, (Double) employeeData.get("Salary"));
            
            // Handle optional SSN
            if (employeeData.get("SSN") != null) {
                ps.setString(7, (String) employeeData.get("SSN"));
            } else {
                ps.setString(7, null);
            }
            
            // Handle password (for login credentials)
            if (employeeData.get("password") != null) {
                ps.setString(8, (String) employeeData.get("password"));
            } else {
                // Default password: email (user can change later)
                ps.setString(8, (String) employeeData.get("email"));
            }
            
            // Handle role
            if (employeeData.get("role") != null) {
                ps.setString(9, (String) employeeData.get("role"));
            } else {
                ps.setString(9, "employee"); // Default role
            }
            
            ps.executeUpdate();
        }
        
        return empId;
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
