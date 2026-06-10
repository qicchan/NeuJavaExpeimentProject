package com.neuedu.workpart.pojo;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * 用户实体类，对应系统中的管理员和护工两种角色。
 * <p>数据通过Jackson序列化存储到JSON文件中，实现Serializable接口以支持序列化。</p>
 *
 * <p>属性命名遵循小驼峰规范，类名遵循大驼峰规范。</p>
 *
 * @author QICHAN
 * @see com.neuedu.workpart.dao.TUserDao
 * @see com.neuedu.workpart.dao.MUserDao
 */
public class TUser implements Serializable {
    /** 用户ID，主键，由PersistentIdGenerator自动生成 */
    private Integer id;
    /** 用户名，登录账号 */
    private String userName;
    /** 用户密码 */
    @JsonProperty("passWord")
    private String password;
    /** 员工类型：1-管理员，2-护工 */
    private int userType=1;

    /** 无参构造方法，Jackson反序列化时需要 */
    public TUser() {
    }

    /**
     * 三参构造方法（不含用户类型，默认为管理员）
     *
     * @param id       用户ID
     * @param userName 用户名
     * @param password 密码
     */
    public TUser(Integer id, String userName, String password) {
        this.id = id;
        this.userName = userName;
        this.password = password;
    }

    /**
     * 全参构造方法
     *
     * @param id       用户ID
     * @param userName 用户名
     * @param password 密码
     * @param userType 用户类型：1-管理员，2-护工
     */
    public TUser(Integer id, String userName, String password, int userType) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.userType = userType;
    }

    /** 获取用户类型 @return 用户类型（1-管理员，2-护工） */
    public int getUserType() {
        return userType;
    }

    /** 设置用户类型 @param userType 用户类型（1-管理员，2-护工） */
    public void setUserType(int userType) {
        this.userType = userType;
    }

    /** 获取用户ID @return 用户ID */
    public Integer getId() {
        return id;
    }

    /** 设置用户ID @param id 用户ID */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取密码 @return 密码 */
    public String getPassword() {
        return password;
    }

    /** 设置密码 @param password 密码 */
    public void setPassword(String password) {
        this.password = password;
    }

    /** 获取用户名 @return 用户名 */
    public String getUserName() {
        return userName;
    }

    /** 设置用户名 @param userName 用户名 */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /** 返回用户信息的字符串表示，用于控制台输出 */
    @Override
    public String toString() {
        return "TUser{" +
                "id=" + id +
                ", 用户名='" + userName + '\'' +
                ", 密码='" + password + '\'' +
                ", 用户类型=" + userType +
                '}';
    }

}
