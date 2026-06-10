package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.CareRecord;
import com.neuedu.workpart.pojo.CareProject;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CareRecordDao {
    public static final File CARE_RECORD_FILE = new File("data/care_record.json");
    public static final File CARE_PROJECT_FILE = new File("data/care_project.json");
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    public String addCareRecord(CareRecord record) {
        try {
            List<CareRecord> list = findAllRecords();
            record.setId(PersistentIdGenerator.getInstance().nextId());
            list.add(record);
            om.writeValue(CARE_RECORD_FILE, list);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加护理记录失败", e);
        }
    }

    public List<CareRecord> findAllRecords() {
        if (!CARE_RECORD_FILE.exists()) return new ArrayList<>();
        try {
            return om.readValue(CARE_RECORD_FILE, new TypeReference<List<CareRecord>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<CareRecord> findByCustomerId(Integer customerId) {
        List<CareRecord> result = new ArrayList<>();
        for (CareRecord r : findAllRecords()) {
            if (customerId.equals(r.getCustomerId()) && (r.getIsHidden() == null || r.getIsHidden() == 0)) {
                result.add(r);
            }
        }
        return result;
    }

    public List<CareRecord> findByCustomerIdAndHmId(Integer customerId, Integer hmId) {
        List<CareRecord> result = new ArrayList<>();
        for (CareRecord r : findAllRecords()) {
            if (customerId.equals(r.getCustomerId()) && hmId.equals(r.getHmId())
                    && (r.getIsHidden() == null || r.getIsHidden() == 0)) {
                result.add(r);
            }
        }
        return result;
    }

    public void hideRecord(Integer recordId) {
        List<CareRecord> list = findAllRecords();
        for (CareRecord r : list) {
            if (recordId.equals(r.getId())) {
                r.setIsHidden(1);
                break;
            }
        }
        try {
            om.writeValue(CARE_RECORD_FILE, list);
        } catch (IOException e) {
            throw new RuntimeException("隐藏记录失败", e);
        }
    }

    public String addCareProject(CareProject project) {
        try {
            List<CareProject> list = findAllProjects();
            project.setId(PersistentIdGenerator.getInstance().nextId());
            list.add(project);
            om.writeValue(CARE_PROJECT_FILE, list);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加护理项目失败", e);
        }
    }

    public List<CareProject> findAllProjects() {
        if (!CARE_PROJECT_FILE.exists()) return new ArrayList<>();
        try {
            return om.readValue(CARE_PROJECT_FILE, new TypeReference<List<CareProject>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<CareProject> findProjectsByCustomerId(Integer customerId) {
        List<CareProject> result = new ArrayList<>();
        for (CareProject p : findAllProjects()) {
            if (customerId.equals(p.getCustomerId())) result.add(p);
        }
        return result;
    }

    public CareProject findProjectById(Integer projectId) {
        for (CareProject p : findAllProjects()) {
            if (projectId.equals(p.getId())) return p;
        }
        return null;
    }

    public void updateProject(CareProject project) {
        List<CareProject> list = findAllProjects();
        for (int i = 0; i < list.size(); i++) {
            if (project.getId().equals(list.get(i).getId())) {
                list.set(i, project);
                break;
            }
        }
        try {
            om.writeValue(CARE_PROJECT_FILE, list);
        } catch (IOException e) {
            throw new RuntimeException("更新项目失败", e);
        }
    }
}
