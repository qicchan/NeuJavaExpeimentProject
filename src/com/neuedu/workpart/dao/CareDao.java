package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 客户护理数据访问对象（DAO）。
 * <p>负责客户护理信息的持久化操作，使用Jackson将数据序列化/反序列化到JSON文件。</p>
 * <p>数据文件：data/customers.json</p>
 *
 * @author QICHAN
 * @see com.neuedu.workpart.pojo.Customer
 * @see com.neuedu.workpart.service.CareStatusService
 */
public class CareDao {
    /** 客户护理数据文件路径 */
    public static final File FILE_NAME = new File("data/customers.json");
    /** Jackson JSON对象映射器，用于序列化和反序列化 */
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    /**
     * 添加客户护理信息，自动分配自增ID
     *
     * @param customer 待添加的客户对象
     * @return 操作结果提示信息
     * @throws RuntimeException 添加失败时抛出
     */
    public String addCustomer(Customer customer) {
        try {
            List<Customer> customerList = findAll();
            customer.setId(PersistentIdGenerator.getInstance().nextId());
            customerList.add(customer);
            om.writeValue(FILE_NAME, customerList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加客户失败", e);
        }
    }

    /**
     * 查询所有客户护理信息
     *
     * @return 客户列表，文件不存在或为空时返回空集合
     */
    public List<Customer> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<Customer>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 根据客户姓名精确查询
     *
     * @param customerName 客户姓名
     * @return 匹配的客户对象，未找到返回null
     */
    public Customer findByCustomerName(String customerName) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getCustomerName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 根据ID修改客户护理信息
     *
     * @param newCustomer 更新后的客户对象（需携带ID）
     * @return true-修改成功，false-客户不存在
     */
    public boolean updateCustomer(Customer newCustomer) {
        try {
            List<Customer> customerList = findAll();
            boolean isUpdate = false;
            for (int i = 0; i < customerList.size(); i++) {
                Customer customer = customerList.get(i);
                if (customer.getId().equals(newCustomer.getId())) {
                    customerList.set(i, newCustomer);
                    isUpdate = true;
                    break;
                }
            }
            if (isUpdate) {
                om.writeValue(FILE_NAME, customerList);
            }
            return isUpdate;
        } catch (IOException e) {
            throw new RuntimeException("修改客户信息失败", e);
        }
    }

    /**
     * 根据ID删除客户
     *
     * @param id 要删除的客户ID
     * @return true-删除成功，false-客户不存在
     */
    public boolean deleteById(int id) {
        try {
            List<Customer> customerList = findAll();
            boolean isDelete = false;
            for (int i = 0; i < customerList.size(); i++) {
                Customer customer = customerList.get(i);
                if (customer.getId() != null && customer.getId().equals(id)) {
                    customerList.remove(i);
                    isDelete = true;
                    break;
                }
            }
            if (isDelete) {
                om.writeValue(FILE_NAME, customerList);
            }
            return isDelete;
        } catch (IOException e) {
            throw new RuntimeException("删除客户失败", e);
        }
    }
}
