package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.CareItem;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 护理项目数据访问对象。
 * <p>负责护理项目的持久化操作，数据存储在 data/care_items.json。</p>
 *
 * @author QICHAN
 * @see com.neuedu.workpart.pojo.CareItem
 */
public class CareItemDao {
    public static final File FILE_NAME = new File("data/care_items.json");
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    public String add(CareItem item) {
        try {
            List<CareItem> list = findAll();
            item.setId(PersistentIdGenerator.getInstance().nextId());
            list.add(item);
            om.writeValue(FILE_NAME, list);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加护理项目失败", e);
        }
    }

    public List<CareItem> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<CareItem>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public CareItem findByCode(String code) {
        for (CareItem item : findAll()) {
            if (item.getCode().equals(code)) {
                return item;
            }
        }
        return null;
    }

    public CareItem findById(int id) {
        for (CareItem item : findAll()) {
            if (item.getId() != null && item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }

    public boolean update(CareItem newItem) {
        try {
            List<CareItem> list = findAll();
            boolean found = false;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(newItem.getId())) {
                    list.set(i, newItem);
                    found = true;
                    break;
                }
            }
            if (found) {
                om.writeValue(FILE_NAME, list);
            }
            return found;
        } catch (IOException e) {
            throw new RuntimeException("修改护理项目失败", e);
        }
    }

    public boolean deleteById(int id) {
        try {
            List<CareItem> list = findAll();
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
            throw new RuntimeException("删除护理项目失败", e);
        }
    }
}
