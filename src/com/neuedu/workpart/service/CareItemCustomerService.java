package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareItemCustomerDao;
import com.neuedu.workpart.pojo.CareItemCustomer;

import java.util.List;

/**
 * 护理项目-客户关联业务逻辑服务层。
 *
 * @author QICHAN
 */
public class CareItemCustomerService {
    private final CareItemCustomerDao dao = new CareItemCustomerDao();

    public String associate(String careItemCode, String customerName, String executionCount) {
        CareItemCustomer assoc = new CareItemCustomer();
        assoc.setCareItemCode(careItemCode);
        assoc.setCustomerName(customerName);
        assoc.setExecutionCount(executionCount);
        return dao.add(assoc);
    }

    public List<CareItemCustomer> findByCareItemCode(String careItemCode) {
        return dao.findByCareItemCode(careItemCode);
    }

    public boolean updateExecutionCount(int id, String executionCount) {
        List<CareItemCustomer> all = dao.findAll();
        for (CareItemCustomer ac : all) {
            if (ac.getId() != null && ac.getId().equals(id)) {
                ac.setExecutionCount(executionCount);
                return dao.update(ac);
            }
        }
        return false;
    }

    public boolean delete(int id) {
        return dao.deleteById(id);
    }
}
