package com.neuedu.workpart.pojo;

import java.io.Serializable;

public class Bed implements Serializable {
    private String id;
    private String bedNumber;
    private String roomNumber;
    private String building = "606";
    private String status; // "空闲", "外出", "有人"

    public Bed() {}

    public Bed(String id, String bedNumber, String roomNumber, String status) {
        this.id = id;
        this.bedNumber = bedNumber;
        this.roomNumber = roomNumber;
        this.status = status;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
