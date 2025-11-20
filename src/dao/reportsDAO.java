package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class reportsDAO {

    private Connection conn;

    public reportsDAO(Connection conn) {
        this.conn = conn;
    }

    // Reports by Division
    public List<String> getPayReportByDivision() {
        List<String> report = new ArrayList<>();

        String sql =
            "SELECT d.Name AS division_name, " +
            "SUM(p.earnings) AS total_pay " +
            "FROM payroll p " +
            "JOIN employees e ON p.empid = e.empid " +
            "JOIN employee_division ed ON e.empid = ed.empid " +
            "JOIN division d ON ed.div_ID = d.ID " +
            "GROUP BY division_name " +
            "ORDER BY division_name";


        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String line =
                    "Division: " + rs.getString("division_name") +
                    " | Total Pay: $" + rs.getDouble("total_pay");


                report.add(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }

    // Reports by Job Title
    public List<String> getPayReportByJobTitle() {
        List<String> report = new ArrayList<>();

        String sql =
            "SELECT jt.job_title AS job_title, " +
            "SUM(p.earnings) AS total_pay " +
            "FROM payroll p " +
            "JOIN employees e ON p.empid = e.empid " +
            "JOIN employee_job_titles ejt ON e.empid = ejt.empid " +
            "JOIN job_titles jt ON ejt.job_title_id = jt.job_title_id " +
            "GROUP BY job_title " +
            "ORDER BY job_title";

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String line =
                    "Job Title: " + rs.getString("job_title") +
                    " | Total Pay: $" + rs.getDouble("total_pay");

                report.add(line);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return report;
    }
}
