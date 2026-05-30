package com.neuedu.workpart.pojo;

import java.io.Serializable;

public class Customer implements Serializable {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String idNumber;
    private String bloodType;
    private String familyMember;
    private String contactPhone;
    private String building = "606";
    private String roomNumber;
    private String bedNumber;
    private String checkInTime;
    private String contractExpireTime;
    private String elderlyType; // "自理老人", "护理老人"
    private String status; // "正常", "已删除"

    public Customer() {}

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public String getIdNumber() { return idNumber; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
    public String getBloodType() { return bloodType; }
    public void setBloodType(String bloodType) { this.bloodType = bloodType; }
    public String getFamilyMember() { return familyMember; }
    public void setFamilyMember(String familyMember) { this.familyMember = familyMember; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public String getBuilding() { return building; }
    public void setBuilding(String building) { this.building = building; }
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
    public String getCheckInTime() { return checkInTime; }
    public void setCheckInTime(String checkInTime) { this.checkInTime = checkInTime; }
    public String getContractExpireTime() { return contractExpireTime; }
    public void setContractExpireTime(String contractExpireTime) { this.contractExpireTime = contractExpireTime; }
    public String getElderlyType() { return elderlyType; }
    public void setElderlyType(String elderlyType) { this.elderlyType = elderlyType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
