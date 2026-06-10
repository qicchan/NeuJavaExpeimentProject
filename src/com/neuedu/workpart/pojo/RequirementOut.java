package com.neuedu.workpart.pojo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static com.neuedu.workpart.view.swing.MainFrame.currentUser;

public class RequirementOut {
    private Integer id;
    private String reason;
    private String outTime;
    private String expectReturnTime;
    private String actualReturnTime;
    private String customerName;
    private Integer customerId;
    private String applyTime;
    private Integer requireHMId;
    private boolean isApproved;
    private Integer approveAdminId;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public RequirementOut() {}

    public RequirementOut(String reason, String outTime, String expectReturnTime, String customerName, Integer customerId) {
        this.reason = reason;
        this.outTime = outTime;
        this.expectReturnTime = expectReturnTime;
        this.customerName = customerName;
        this.customerId = customerId;
        this.isApproved = false;
        this.applyTime = LocalDateTime.now().format(FORMATTER);
        this.actualReturnTime = null;
        this.requireHMId = currentUser.getId();
        this.approveAdminId = null;
    }

    public void registerReturn() {
        this.actualReturnTime = LocalDateTime.now().format(FORMATTER);
    }

    public void registerApprove() {
        this.approveAdminId=currentUser.getId();
        this.isApproved = true;
    }

    public Integer getApprovedAdmin() {
        return approveAdminId;
    }

    public void setApprovedAdmin(Integer approveAdminId) {
        this.approveAdminId = approveAdminId;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getOutTime() { return outTime; }
    public void setOutTime(String outTime) { this.outTime = outTime; }

    public String getExpectReturnTime() { return expectReturnTime; }
    public void setExpectReturnTime(String expectReturnTime) { this.expectReturnTime = expectReturnTime; }

    public String getActualReturnTime() { return actualReturnTime; }
    public void setActualReturnTime(String actualReturnTime) { this.actualReturnTime = actualReturnTime; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public Integer getCustomerId() { return customerId; }
    public void setCustomerId(Integer customerId) { this.customerId = customerId; }

    public boolean checkApproveStatus(){
        return isApproved;
    }

    public Integer getRequireHMId() { return requireHMId; }
    public void setRequireHMId(Integer requireHMId) { this.requireHMId = requireHMId; }

    public boolean isApproved() { return isApproved; }
    public void setApproved(boolean approved) { isApproved = approved; }

    public String getApplyTime() { return applyTime; }
    public void setApplyTime(String applyTime) { this.applyTime = applyTime; }

    @Override
    public String toString() {
        return "外出申请{客户=" + customerName + ", 事由=" + reason + ", 外出时间=" + outTime +
                ", 预计回院=" + expectReturnTime + ", 实际回院=" + (actualReturnTime == null ? "未回院" : actualReturnTime) +
                ", 审批状态=" + (isApproved ? "通过" : "未通过") + '}'+"提出护工id="+requireHMId+"审批人id="+approveAdminId;
    }
}
