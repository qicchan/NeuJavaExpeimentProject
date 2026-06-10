package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareItemDao;
import com.neuedu.workpart.pojo.CareItem;

import java.util.ArrayList;
import java.util.List;

/**
 * 护理项目业务逻辑服务层。
 *
 * @author QICHAN
 * @see com.neuedu.workpart.dao.CareItemDao
 */
public class CareItemService {
    private final CareItemDao dao = new CareItemDao();

    public String add(String code, String name, String price,
                      String executionPeriod, String executionCount, String description) {
        CareItem item = new CareItem();
        item.setCode(code);
        item.setName(name);
        item.setPrice(price);
        item.setStatus("启用");
        item.setExecutionPeriod(executionPeriod);
        item.setExecutionCount(executionCount);
        item.setDescription(description);
        return dao.add(item);
    }

    public List<CareItem> findAll() {
        return dao.findAll();
    }

    public CareItem findByCode(String code) {
        return dao.findByCode(code);
    }

    public boolean update(String code, String name, String price,
                          String executionPeriod, String executionCount, String description) {
        CareItem item = dao.findByCode(code);
        if (item == null) {
            return false;
        }
        item.setName(name);
        item.setPrice(price);
        item.setExecutionPeriod(executionPeriod);
        item.setExecutionCount(executionCount);
        item.setDescription(description);
        return dao.update(item);
    }

    public boolean updateStatus(String code, String status) {
        CareItem item = dao.findByCode(code);
        if (item == null) {
            return false;
        }
        item.setStatus(status);
        return dao.update(item);
    }

    public List<CareItem> findEnabled() {
        List<CareItem> result = new ArrayList<>();
        for (CareItem item : dao.findAll()) {
            if ("启用".equals(item.getStatus())) {
                result.add(item);
            }
        }
        return result;
    }

    public boolean delete(int id) {
        return dao.deleteById(id);
    }
}
