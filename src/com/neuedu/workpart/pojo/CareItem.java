package com.neuedu.workpart.pojo;

import java.io.Serializable;

/**
 * 护理项目实体类。
 * <p>记录护理项目的编号、名称、价格、状态、执行周期、执行次数和描述信息。</p>
 *
 * @author QICHAN
 * @see com.neuedu.workpart.dao.CareItemDao
 */
public class CareItem implements Serializable {
    private Integer id;
    private String code;
    private String name;
    private String price;
    private String status;
    private String executionPeriod;
    private String executionCount;
    private String description;

    public CareItem() {
    }

    public CareItem(Integer id, String code, String name, String price, String status,
                    String executionPeriod, String executionCount, String description) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.price = price;
        this.status = status;
        this.executionPeriod = executionPeriod;
        this.executionCount = executionCount;
        this.description = description;
    }

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getExecutionPeriod() { return executionPeriod; }
    public void setExecutionPeriod(String executionPeriod) { this.executionPeriod = executionPeriod; }

    public String getExecutionCount() { return executionCount; }
    public void setExecutionCount(String executionCount) { this.executionCount = executionCount; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    @Override
    public String toString() {
        return "CareItem{" +
                "id=" + id +
                ", 编号='" + code + '\'' +
                ", 名称='" + name + '\'' +
                ", 价格='" + price + '\'' +
                ", 状态='" + status + '\'' +
                ", 执行周期='" + executionPeriod + '\'' +
                ", 执行次数='" + executionCount + '\'' +
                ", 描述='" + description + '\'' +
                '}';
    }
}
