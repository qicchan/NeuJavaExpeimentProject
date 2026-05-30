package com.neuedu.workpart.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * 由健康管家发出的申请外出实体类
 * @author 李佩宸
 */
public class RequirementOut {
    /** 申请ID */
    private Integer id;
    /** 外出事由 */
    private String reason;
    /** 外出时间 */
    private String outTime;
    /** 预计回院时间 */
    private String expectReturnTime;
    /** 实际回院时间 */
    private String actualReturnTime;
    /** 审批状态（默认：已提交） */
    private String approvalStatus;
    /** 客户姓名 */
    private String customerName;
    /** 客户ID */
    private Integer customerId;
    /** 申请时间（自动生成） */
    private String applyTime;

    /** 日期时间格式化器 */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RequirementOut() {
    }

    /**
     * 构造外出申请
     * @param reason 外出事由
     * @param outTime 外出时间
     * @param expectReturnTime 预计回院时间
     * @param customerName 客户姓名
     * @param customerId 客户ID
     */
    public RequirementOut(String reason, String outTime, String expectReturnTime, String customerName, Integer customerId) {
        this.reason = reason;
        this.outTime = outTime;
        this.expectReturnTime = expectReturnTime;
        this.customerName = customerName;
        this.customerId = customerId;
        this.approvalStatus = "已提交"; // 默认审批状态
        this.applyTime = LocalDateTime.now().format(FORMATTER); // 自动生成申请时间
        this.actualReturnTime = null; // 初始时未回院
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getOutTime() {
        return outTime;
    }

    public void setOutTime(String outTime) {
        this.outTime = outTime;
    }

    public String getExpectReturnTime() {
        return expectReturnTime;
    }

    public void setExpectReturnTime(String expectReturnTime) {
        this.expectReturnTime = expectReturnTime;
    }

    public String getActualReturnTime() {
        return actualReturnTime;
    }

    /**
     * 登记回院时间
     * @param actualReturnTime 实际回院时间
     */
    public void setActualReturnTime(String actualReturnTime) {
        this.actualReturnTime = actualReturnTime;
    }

    /**
     * 登记回院（使用当前时间）
     */
    public void registerReturn() {
        this.actualReturnTime = LocalDateTime.now().format(FORMATTER);
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
     * 重写toString方法，以自定义格式输出外出申请的关键信息
     */
    @Override
    public String toString() {
        return "\n============外出申请=============" +
                "\n姓名:" + customerName +
                "\n外出人id:" + customerId +
                "\n申请id:" + id +
                "\n外出事由:" + reason +
                "\n外出时间:" + outTime +
                "\n预计回院:" + expectReturnTime +
                "\n实际回院:" + (actualReturnTime == null ? "未回院" : actualReturnTime) +
                "\n审批状态:" + approvalStatus +
                "\n申请时间:" + applyTime +
                "\n==========================";
    }
}
