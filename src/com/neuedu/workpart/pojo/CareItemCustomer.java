package com.neuedu.workpart.pojo;

import java.io.Serializable;

/**
 * 护理项目-客户关联实体。
 * <p>记录某个客户关联了哪个护理项目，以及该项目对该客户的执行次数。</p>
 * <p>一个护理项目可以关联多个客户，每个客户有独立的执行次数。</p>
 *
 * @author QICHAN
 */
public class CareItemCustomer implements Serializable {
    private Integer id;
    private String careItemCode;
    private String customerName;
    private String executionCount;

    public CareItemCustomer() {
    }

    public CareItemCustomer(Integer id, String careItemCode, String customerName, String executionCount) {
        this.id = id;
        this.careItemCode = careItemCode;
        this.customerName = customerName;
        this.executionCount = executionCount;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCareItemCode() { return careItemCode; }
    public void setCareItemCode(String careItemCode) { this.careItemCode = careItemCode; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getExecutionCount() { return executionCount; }
    public void setExecutionCount(String executionCount) { this.executionCount = executionCount; }

    @Override
    public String toString() {
        return "CareItemCustomer{" +
                "id=" + id +
                ", 项目编号='" + careItemCode + '\'' +
                ", 客户姓名='" + customerName + '\'' +
                ", 执行次数='" + executionCount + '\'' +
                '}';
    }
}
