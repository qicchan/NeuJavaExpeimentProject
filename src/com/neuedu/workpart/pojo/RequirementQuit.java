package com.neuedu.workpart.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.neuedu.workpart.view.swing.MainFrame.currentUser;

public class RequirementQuit {
    private Integer id;
    private Integer quitType;
    private String reason;
    private String quitTime;
    private String customerName;
    private Integer customerId;
    private String applyTime;
    private Integer requireHMId;
    private boolean isApproved;
    private Integer approveAdminId;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RequirementQuit() {
    }

    public RequirementQuit(int quitType, String reason, String quitTime, String customerName, Integer customerId) {
        this.quitType = quitType;
        this.reason = reason;
        this.quitTime = quitTime;
        this.customerName = customerName;
        this.customerId = customerId;
        this.requireHMId = currentUser.getId();
        this.isApproved = false;
        this.applyTime = LocalDateTime.now().format(FORMATTER);
        this.approveAdminId = null;
    }

    public void registerApprove() {
        this.isApproved = true;
        this.approveAdminId = currentUser.getId();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuitType() {
        return quitType;
    }

    public void setQuitType(Integer quitType) {
        this.quitType = quitType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getQuitTime() {
        return quitTime;
    }

    public void setQuitTime(String quitTime) {
        this.quitTime = quitTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public Integer getRequireHMId() {
        return requireHMId;
    }

    public void setRequireHMId(Integer requireHMId) {
        this.requireHMId = requireHMId;
    }

    public boolean isApproved() {
        return isApproved;
    }

    public void setApproved(boolean approved) {
        isApproved = approved;
    }

    public Integer getApprovedAdmin() {
        return approveAdminId;
    }

    public void setApprovedAdmin(Integer approveAdminId) {
        this.approveAdminId = approveAdminId;
    }

    public boolean checkApprovedStatus() {
        return isApproved;
    }

    public Integer checkApprovedAdmin() {
        return approveAdminId;
    }

    @Override
    public String toString() {
        String typeStr = quitType == null ? "未知" : (quitType == 1 ? "正常退住" : (quitType == 2 ? "死亡退住" : "保留床位"));
        return "退住申请{客户=" + customerName + ", 类型=" + typeStr + ", 原因=" + reason +
                ", 退住时间=" + quitTime + ", 审批状态=" + (isApproved ? "通过" : "未通过") + '}' +
                ", 提出护工id=" + requireHMId + ", 审批人id=" + approveAdminId;
    }
}
