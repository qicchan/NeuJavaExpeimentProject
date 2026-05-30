package com.neuedu.workpart.pojo;

import java.io.Serializable;

public class Customer implements Serializable {
    private Integer id;
    private String customerName;
    private String careLevel;
    private String careItem;
    private String updateTime;

    public Customer() {
    }

    public Customer(Integer id, String customerName, String careLevel, String careItem, String updateTime) {
        this.id = id;
        this.customerName = customerName;
        this.careLevel = careLevel;
        this.careItem = careItem;
        this.updateTime = updateTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCareLevel() {
        return careLevel;
    }

    public void setCareLevel(String careLevel) {
        this.careLevel = careLevel;
    }

    public String getCareItem() {
        return careItem;
    }

    public void setCareItem(String careItem) {
        this.careItem = careItem;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", 客户姓名='" + customerName + '\'' +
                ", 护理等级='" + careLevel + '\'' +
                ", 护理项目='" + careItem + '\'' +
                ", 更新时间='" + updateTime + '\'' +
                '}';
    }
}
