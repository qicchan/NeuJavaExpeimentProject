package com.neuedu.workpart.pojo;

import java.io.Serializable;

/**
 * 客户实体类，对应颐养中心的被护理客户。
 * <p>记录客户的基本信息及其护理等级、护理项目和最后更新时间。</p>
 * <p>数据通过Jackson序列化存储到JSON文件中。</p>
 *
 * @see com.neuedu.workpart.dao.CareDao
 */
public class Customer implements Serializable {
    /** 客户ID，主键，由PersistentIdGenerator自动生成 */
    private Integer id;
    /** 客户姓名 */
    private String customerName;
    /** 护理等级 */
    private String careLevel;
    /** 护理项目 */
    private String careItem;
    /** 最后更新时间，格式：yyyy-MM-dd HH:mm:ss */
    private String updateTime;
    /** 关联的护理项目编号 */
    private String careItemCode;

    /** 无参构造方法，Jackson反序列化时需要 */
    public Customer() {
    }
    public Customer(Integer id, String customerName, String careLevel, String careItem, String updateTime) {
        this.id = id;
        this.customerName = customerName;
        this.careLevel = careLevel;
        this.careItem = careItem;
        this.updateTime = updateTime;
    }

    /** 获取客户ID @return 客户ID */
    public Integer getId() {
        return id;
    }

    /** 设置客户ID @param id 客户ID */
    public void setId(Integer id) {
        this.id = id;
    }

    /** 获取客户姓名 @return 客户姓名 */
    public String getCustomerName() {
        return customerName;
    }

    /** 设置客户姓名 @param customerName 客户姓名 */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** 获取护理等级 @return 护理等级 */
    public String getCareLevel() {
        return careLevel;
    }

    /** 设置护理等级 @param careLevel 护理等级 */
    public void setCareLevel(String careLevel) {
        this.careLevel = careLevel;
    }

    /** 获取护理项目 @return 护理项目 */
    public String getCareItem() {
        return careItem;
    }

    /** 设置护理项目 @param careItem 护理项目 */
    public void setCareItem(String careItem) {
        this.careItem = careItem;
    }

    /** 获取最后更新时间 @return 更新时间字符串 */
    public String getUpdateTime() {
        return updateTime;
    }

    /** 设置最后更新时间 @param updateTime 更新时间字符串 */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getCareItemCode() {
        return careItemCode;
    }

    public void setCareItemCode(String careItemCode) {
        this.careItemCode = careItemCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", 客户姓名='" + customerName + '\'' +
                ", 护理等级='" + careLevel + '\'' +
                ", 护理项目='" + careItem + '\'' +
                ", 更新时间='" + updateTime + '\'' +
                ", 护理项目编号='" + careItemCode + '\'' +
                '}';
    }
}
