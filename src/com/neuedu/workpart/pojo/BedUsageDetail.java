package com.neuedu.workpart.pojo;

import java.io.Serializable;

public class BedUsageDetail implements Serializable {
    private String id;
    private String customerId;
    private String customerName;
    private String bedNumber;
    private String roomNumber;
    private String building = "606";
    private String startTime;
    private String endTime;
    private String status; // "正在使用", "使用历史"

    public BedUsageDetail() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public String getStartTime() { return startTime; }
    public void setStartTime(String startTime) { this.startTime = startTime; }
    public String getEndTime() { return endTime; }
    public void setEndTime(String endTime) { this.endTime = endTime; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
