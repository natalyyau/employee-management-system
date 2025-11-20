package dao;

import java.sql.*;
import java.util.*;

public class payrollDAO implements PayrollInterface {

    private Connection conn;

    public payrollDAO(Connection conn) {
        this.conn = conn;
    }

    @Override
    public List<Map<String, Object>> getPayrollHistoryByEmpId(int empId) throws Exception {
        List<Map<String, Object>> list = new ArrayList<>();

        String sql = "SELECT payID, pay_date, earnings, fed_tax, fed_med, fed_SS, " +
                     "state_tax, retire_401k, health_care " +
                     "FROM payroll WHERE empid = ? ORDER BY pay_date DESC";

        PreparedStatement ps = conn.prepareStatement(sql);
        ps.setInt(1, empId);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Map<String, Object> row = new HashMap<>();
            row.put("payID", rs.getInt("payID"));
            row.put("pay_date", rs.getDate("pay_date"));
            row.put("earnings", rs.getBigDecimal("earnings"));
            row.put("fed_tax", rs.getBigDecimal("fed_tax"));
            row.put("fed_med", rs.getBigDecimal("fed_med"));
            row.put("fed_SS", rs.getBigDecimal("fed_SS"));
            row.put("state_tax", rs.getBigDecimal("state_tax"));
            row.put("retire_401k", rs.getBigDecimal("retire_401k"));
            row.put("health_care", rs.getBigDecimal("health_care"));
            list.add(row);
        }

        return list;
    }
}
