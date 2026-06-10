package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.RequirementQuit;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RequirementDao {
    public static final File OUT_FILE = new File("data/requirement_out.json");
    public static final File QUIT_FILE = new File("data/requirement_quit.json");
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    public String addOutRequirement(RequirementOut out) {
        try {
            List<RequirementOut> list = findAllOut();
            out.setId(PersistentIdGenerator.getInstance().nextId());
            list.add(out);
            om.writeValue(OUT_FILE, list);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加外出申请失败", e);
        }
    }

    public List<RequirementOut> findAllOut() {
        if (!OUT_FILE.exists()) return new ArrayList<>();
        try {
            return om.readValue(OUT_FILE, new TypeReference<List<RequirementOut>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public RequirementOut findOutById(Integer id) {
        for (RequirementOut out : findAllOut()) {
            if (out.getId().equals(id)) return out;
        }
        return null;
    }

    public List<RequirementOut> findByCustomerNameOut(String customerName) {
        List<RequirementOut> result = new ArrayList<>();
        for (RequirementOut out : findAllOut()) {
            if (out.getCustomerName() != null && out.getCustomerName().contains(customerName)) {
                result.add(out);
            }
        }
        return result;
    }

    public void updateOutRequirement(RequirementOut updated) {
        List<RequirementOut> list = findAllOut();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                break;
            }
        }
        try {
            om.writeValue(OUT_FILE, list);
        } catch (IOException e) {
            throw new RuntimeException("更新外出申请失败", e);
        }
    }

    public String addQuitRequirement(RequirementQuit quit) {
        try {
            List<RequirementQuit> list = findAllQuit();
            quit.setId(PersistentIdGenerator.getInstance().nextId());
            list.add(quit);
            om.writeValue(QUIT_FILE, list);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加退住申请失败", e);
        }
    }

    public List<RequirementQuit> findAllQuit() {
        if (!QUIT_FILE.exists()) return new ArrayList<>();
        try {
            return om.readValue(QUIT_FILE, new TypeReference<List<RequirementQuit>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public RequirementQuit findQuitById(Integer id) {
        for (RequirementQuit quit : findAllQuit()) {
            if (quit.getId().equals(id)) return quit;
        }
        return null;
    }

    public List<RequirementQuit> findByCustomerNameQuit(String customerName) {
        List<RequirementQuit> result = new ArrayList<>();
        for (RequirementQuit quit : findAllQuit()) {
            if (quit.getCustomerName() != null && quit.getCustomerName().contains(customerName)) {
                result.add(quit);
            }
        }
        return result;
    }

    public void updateQuitRequirement(RequirementQuit updated) {
        List<RequirementQuit> list = findAllQuit();
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).getId().equals(updated.getId())) {
                list.set(i, updated);
                break;
            }
        }
        try {
            om.writeValue(QUIT_FILE, list);
        } catch (IOException e) {
            throw new RuntimeException("更新退住申请失败", e);
        }
    }
}
