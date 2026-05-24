package com.neuedu.practice.pojo;


import java.io.Serializable;

/*
实体类
类名大驼峰，每个单词首字母都大写
属性、方法都是小驼几，第一个单词全小写，第二个单词以后都首字母大写

 */
public class TUser implements Serializable {
    //属性私有，不能在外面随意改值
    private Integer id;
    private String userName;
    private String password;
    /* 员工类型 1-管理员 2-护工*/
    private int userType=1;
    public TUser() {
    }

    public TUser(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    public TUser(Integer id, String userName, String password, int userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    public int getUserType() {
        return userType;
    }

    public void setUserType(int userType) {
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassWord() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "TUser{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", userType=" + userType +
                '}';
    }

    public void setPassWord(String number) {
        this.password = number;
    }
}
