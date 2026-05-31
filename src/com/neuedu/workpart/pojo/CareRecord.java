package com.neuedu.workpart.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Chickun
 * 护工对客户进行护理，对应护理记录实体类
 */
public class CareRecord {
    private Integer id;
    private String careProject;
    private String careTime;
    private Integer careNum;
    private Integer hmId;
    private Integer customerId;
    private String customerName;
    private String status;
    private Integer isHidden;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public CareRecord() {
    }

    public CareRecord(String careProject, String careTime, Integer careNum, Integer hmId, Integer customerId, String customerName) {
        this.careProject = careProject;
        this.careTime = careTime;
        this.careNum = careNum;
        this.hmId = hmId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.status = "已完成";
        this.isHidden = 0;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCareProject() {
        return careProject;
    }

    public void setCareProject(String careProject) {
        this.careProject = careProject;
    }

    public String getCareTime() {
        return careTime;
    }

    public void setCareTime(String careTime) {
        this.careTime = careTime;
    }

    public Integer getCareNum() {
        return careNum;
    }

    public void setCareNum(Integer careNum) {
        this.careNum = careNum;
    }

    public Integer getHmId() {
        return hmId;
    }

    public void setHmId(Integer hmId) {
        this.hmId = hmId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getIsHidden() {
        return isHidden;
    }

    public void setIsHidden(Integer isHidden) {
        this.isHidden = isHidden;
    }

    @Override
    public String toString() {
        return "\n============护理记录=============" +
                "\n记录ID:" + id +
                "\n客户姓名:" + customerName +
                "\n客户ID:" + customerId +
                "\n护理项目:" + careProject +
                "\n护理时间:" + careTime +
                "\n护理数量:" + careNum +
                "\n护工ID:" + hmId +
                "\n状态:" + status +
                "\n==========================";
    }
}

