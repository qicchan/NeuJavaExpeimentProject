package com.neuedu.workpart.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 由健康管家发出的申请退住实体类
 * @author 李佩宸
 */
public class RequirementQuit {
    /** 退住类型：1-正常退住，2-死亡退住，3-保留床位*/
    private Integer quitType;
    /** 退住原因 */
    private String reason;
    /** 退住时间 */
    private String quitTime;
    /** 审批状态（默认：已提交） */
    private String approvalStatus;
    /** 客户姓名 */
    private String customerName;
    /** 客户ID */
    private Integer customerId;
    /** 申请时间（自动生成） */
    private String applyTime;
    /** 申请ID */
    private Integer id;

    /** 日期时间格式化器 */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RequirementQuit() {
    }

    /**
     * 构造退住申请
     * @param quitType 退住类型
     * @param reason 退住原因
     * @param quitTime 退住时间
     * @param customerName 客户姓名
     * @param customerId 客户ID
     */
    public RequirementQuit(int quitType, String reason, String quitTime, String customerName, Integer customerId) {
        this.quitType = quitType;
        this.reason = reason;
        this.quitTime = quitTime;
        this.customerName = customerName;
        this.customerId = customerId;
        this.approvalStatus = "已提交"; // 默认审批状态
        this.applyTime = LocalDateTime.now().format(FORMATTER); // 自动生成申请时间
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getQuitType() {
        return quitType;
    }

    public void setQuitType(int quitType) {
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

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public void setApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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

    /**
     * 重写toString方法，输出退住申请的关键信息
     */
    @Override
    public String toString() {
        return "\n============退住申请============="+
                "\n姓名:" + customerName+
                "\n退住人id:" + customerId +
                "\n申请id:" + id +
                "\n退住类型:'" + quitType +
                "\n退住原因:" + reason+
                "\n推注时间:" + quitTime+
                "\n是否批准:" + approvalStatus+
                "\n申请时间:" + applyTime+
                "\n==========================";
    }
}

