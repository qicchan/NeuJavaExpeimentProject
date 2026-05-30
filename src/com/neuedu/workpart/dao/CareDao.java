package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CareDao {
    public static final File FILE_NAME = new File("data\\customers.json");
    private final ObjectMapper om = new ObjectMapper();

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

    public Customer findByCustomerName(String customerName) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getCustomerName().equals(customerName)) {
                return customer;
            }
        }
        return null;
    }

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

    public boolean deleteById(int id) {
        try {
            List<Customer> customerList = findAll();
            boolean isDelete = false;
            for (int i = 0; i < customerList.size(); i++) {
                Customer customer = customerList.get(i);
                if (customer.getId() != null && customer.getId() == id) {
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
