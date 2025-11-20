package dao;

import java.util.List;
import java.util.Map;

public interface PayrollInterface {
    List<Map<String, Object>> getPayrollHistoryByEmpId(int empId) throws Exception;
}

