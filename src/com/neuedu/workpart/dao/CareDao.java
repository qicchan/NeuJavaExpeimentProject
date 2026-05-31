package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.CareRecord;
import com.neuedu.workpart.pojo.CareProject;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CareDao {
    public static final File CARE_RECORD_FILE = new File("data\\care_record.json");
    public static final File CARE_PROJECT_FILE = new File("data\\care_project.json");
    private final ObjectMapper om = new ObjectMapper();

    public String addCareRecord(CareRecord record) throws IOException {
        try {
            List<CareRecord> recordList = findAllRecords();
            record.setId(PersistentIdGenerator.getInstance().nextId());
            recordList.add(record);
            om.writeValue(CARE_RECORD_FILE, recordList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加护理记录失败", e);
        }
    }

    public List<CareRecord> findAllRecords() {
        if (!CARE_RECORD_FILE.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(CARE_RECORD_FILE, new TypeReference<List<CareRecord>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<CareRecord> findByCustomerId(Integer customerId) {
        List<CareRecord> allRecords = findAllRecords();
        List<CareRecord> result = new ArrayList<>();
        for (CareRecord record : allRecords) {
            if (customerId.equals(record.getCustomerId()) && (record.getIsHidden() == null || record.getIsHidden() == 0)) {
                result.add(record);
            }
        }
        return result;
    }

    public List<CareRecord> findByCustomerIdAndHmId(Integer customerId, Integer hmId) {
        List<CareRecord> allRecords = findAllRecords();
        List<CareRecord> result = new ArrayList<>();
        for (CareRecord record : allRecords) {
            if (customerId.equals(record.getCustomerId())
                    && hmId.equals(record.getHmId())
                    && (record.getIsHidden() == null || record.getIsHidden() == 0)) {
                result.add(record);
            }
        }
        return result;
    }

    public void hideRecord(Integer recordId) {
        List<CareRecord> recordList = findAllRecords();
        for (CareRecord record : recordList) {
            if (recordId.equals(record.getId())) {
                record.setIsHidden(1);
                break;
            }
        }
        try {
            om.writeValue(CARE_RECORD_FILE, recordList);
        } catch (IOException e) {
            throw new RuntimeException("隐藏记录失败", e);
        }
    }

    public String addCareProject(CareProject project) throws IOException {
        try {
            List<CareProject> projectList = findAllProjects();
            project.setId(PersistentIdGenerator.getInstance().nextId());
            projectList.add(project);
            om.writeValue(CARE_PROJECT_FILE, projectList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加护理项目失败", e);
        }
    }

    public List<CareProject> findAllProjects() {
        if (!CARE_PROJECT_FILE.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(CARE_PROJECT_FILE, new TypeReference<List<CareProject>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public List<CareProject> findProjectsByCustomerId(Integer customerId) {
        List<CareProject> allProjects = findAllProjects();
        List<CareProject> result = new ArrayList<>();
        for (CareProject project : allProjects) {
            if (customerId.equals(project.getCustomerId())) {
                result.add(project);
            }
        }
        return result;
    }

    public CareProject findProjectById(Integer projectId) {
        List<CareProject> allProjects = findAllProjects();
        for (CareProject project : allProjects) {
            if (projectId.equals(project.getId())) {
                return project;
            }
        }
        return null;
    }

    public void updateProject(CareProject project) {
        List<CareProject> projectList = findAllProjects();
        for (int i = 0; i < projectList.size(); i++) {
            if (project.getId().equals(projectList.get(i).getId())) {
                projectList.set(i, project);
                break;
            }
        }
        try {
            om.writeValue(CARE_PROJECT_FILE, projectList);
        } catch (IOException e) {
            throw new RuntimeException("更新项目失败", e);
        }
    }
}
