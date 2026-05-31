package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareDao;
import com.neuedu.workpart.pojo.CareRecord;
import com.neuedu.workpart.pojo.CareProject;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CareService {
    private CareDao careDao = new CareDao();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public String createCareRecord(String careProject, Integer careNum, Integer hmId, Integer customerId, String customerName) {
        try {
            String careTime = LocalDateTime.now().format(FORMATTER);
            CareRecord record = new CareRecord(careProject, careTime, careNum, hmId, customerId, customerName);

            careDao.addCareRecord(record);

            updateProjectProgress(customerId, careProject, careNum);

            return "护理记录生成成功";
        } catch (Exception e) {
            return "护理记录生成失败：" + e.getMessage();
        }
    }

    private void updateProjectProgress(Integer customerId, String projectName, Integer careNum) {
        List<CareProject> projects = careDao.findProjectsByCustomerId(customerId);
        for (CareProject project : projects) {
            if (projectName.equals(project.getProjectName())) {
                project.setCompletedNum(project.getCompletedNum() + careNum);
                careDao.updateProject(project);
                break;
            }
        }
    }

    public List<CareRecord> getCareRecordsByCustomerAndHm(Integer customerId, Integer hmId) {
        return careDao.findByCustomerIdAndHmId(customerId, hmId);
    }

    public List<CareProject> getProjectsByCustomer(Integer customerId) {
        return careDao.findProjectsByCustomerId(customerId);
    }

    public String hideCareRecord(Integer recordId) {
        try {
            careDao.hideRecord(recordId);
            return "记录已隐藏";
        } catch (Exception e) {
            return "隐藏记录失败：" + e.getMessage();
        }
    }

    public String addCareProjectForCustomer(String projectName, String description, Integer customerId, String customerName) {
        try {
            CareProject project = new CareProject(projectName, description, customerId, customerName);
            careDao.addCareProject(project);
            return "护理项目添加成功";
        } catch (Exception e) {
            return "护理项目添加失败：" + e.getMessage();
        }
    }
}
