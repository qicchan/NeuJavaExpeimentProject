package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareRecordDao;
import com.neuedu.workpart.pojo.CareRecord;
import com.neuedu.workpart.pojo.CareProject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CareRecordService {
    private final CareRecordDao dao = new CareRecordDao();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String createCareRecord(String careProject, Integer careNum, Integer hmId,
                                   Integer customerId, String customerName) {
        try {
            String careTime = LocalDateTime.now().format(FORMATTER);
            CareRecord record = new CareRecord(careProject, careTime, careNum, hmId, customerId, customerName);
            dao.addCareRecord(record);
            updateProjectProgress(customerId, careProject, careNum);
            return "护理记录生成成功";
        } catch (Exception e) {
            return "护理记录生成失败：" + e.getMessage();
        }
    }

    private void updateProjectProgress(Integer customerId, String projectName, Integer careNum) {
        for (CareProject p : dao.findProjectsByCustomerId(customerId)) {
            if (projectName.equals(p.getProjectName())) {
                p.setCompletedNum(p.getCompletedNum() + careNum);
                dao.updateProject(p);
                break;
            }
        }
    }

    public List<CareRecord> getCareRecordsByCustomerAndHm(Integer customerId, Integer hmId) {
        return dao.findByCustomerIdAndHmId(customerId, hmId);
    }

    public List<CareProject> getProjectsByCustomer(Integer customerId) {
        return dao.findProjectsByCustomerId(customerId);
    }

    public List<CareRecord> findAllRecords() {
        return dao.findAllRecords();
    }

    public String hideCareRecord(Integer recordId) {
        try {
            dao.hideRecord(recordId);
            return "记录已隐藏";
        } catch (Exception e) {
            return "隐藏记录失败：" + e.getMessage();
        }
    }

    public String addCareProject(String projectName, String description, Integer customerId, String customerName) {
        try {
            dao.addCareProject(new CareProject(projectName, description, customerId, customerName));
            return "护理项目添加成功";
        } catch (Exception e) {
            return "护理项目添加失败：" + e.getMessage();
        }
    }
}
