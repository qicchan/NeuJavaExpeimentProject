package com.neuedu.workpart.pojo;

public class CareProject {
    private Integer id;
    private String projectName;
    private String description;
    private Integer totalNum;
    private Integer completedNum;
    private Integer customerId;
    private String customerName;

    public CareProject() {
    }

    public CareProject(String projectName, String description, Integer customerId, String customerName) {
        this.projectName = projectName;
        this.description = description;
        this.totalNum = 0;
        this.completedNum = 0;
        this.customerId = customerId;
        this.customerName = customerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(Integer totalNum) {
        this.totalNum = totalNum;
    }

    public Integer getCompletedNum() {
        return completedNum;
    }

    public void setCompletedNum(Integer completedNum) {
        this.completedNum = completedNum;
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

    @Override
    public String toString() {
        return "\n============护理项目=============" +
                "\n项目名称:" + projectName +
                "\n项目描述:" + description +
                "\n客户姓名:" + customerName +
                "\n客户ID:" + customerId +
                "\n总数量:" + totalNum +
                "\n已完成数量:" + completedNum +
                "\n==========================";
    }
}
