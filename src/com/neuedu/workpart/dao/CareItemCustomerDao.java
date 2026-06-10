package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.CareItemCustomer;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 护理项目-客户关联数据访问对象。
 * <p>数据存储在 data/care_item_customers.json。</p>
 *
 * @author QICHAN
 */
public class CareItemCustomerDao {
    public static final File FILE_NAME = new File("data/care_item_customers.json");
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    public String add(CareItemCustomer assoc) {
        try {
            List<CareItemCustomer> list = findAll();
            assoc.setId(PersistentIdGenerator.getInstance().nextId());
            list.add(assoc);
            om.writeValue(FILE_NAME, list);
            return "关联成功";
        } catch (Exception e) {
            throw new RuntimeException("关联失败", e);
        }
    }

    public List<CareItemCustomer> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<CareItemCustomer>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<CareItemCustomer> findByCareItemCode(String careItemCode) {
        List<CareItemCustomer> result = new ArrayList<>();
        for (CareItemCustomer ac : findAll()) {
            if (ac.getCareItemCode().equals(careItemCode)) {
                result.add(ac);
            }
        }
        return result;
    }

    public boolean update(CareItemCustomer updated) {
        try {
            List<CareItemCustomer> list = findAll();
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(updated.getId())) {
                    list.set(i, updated);
                    found = true;
                    break;
                }
            }
            if (found) {
                om.writeValue(FILE_NAME, list);
            }
            return found;
        } catch (IOException e) {
            throw new RuntimeException("修改关联失败", e);
        }
    }

    public boolean deleteById(int id) {
        try {
            List<CareItemCustomer> list = findAll();
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId() != null && list.get(i).getId().equals(id)) {
                    list.remove(i);
                    found = true;
                    break;
                }
            }
            if (found) {
                om.writeValue(FILE_NAME, list);
            }
            return found;
        } catch (IOException e) {
            throw new RuntimeException("删除关联失败", e);
        }
    }
}
