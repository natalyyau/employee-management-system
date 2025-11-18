package model;

public class User {
    private int userId;
    private String username;
    private String passwordHash;
    private String role;
    private int empid;

    public User() {}

    public User(int userId, String username, String passwordHash, String role, int empid) {
        this.userId = userId;
        this.username = username;
        this.passwordHash = passwordHash;
        this.role = role;
        this.empid = empid;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getEmpid() {
        return empid;
    }

    public void setEmpid(int empid) {
        this.empid = empid;
    }

    // Backward compatibility method
    public int getEmployeeId() {
        return empid;
    }

    public void setEmployeeId(int employeeId) {
        this.empid = employeeId;
    }
}

