package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CustomerDao;
import com.neuedu.workpart.pojo.Customer;

import java.io.IOException;
import java.util.List;

public class CustomerService {
    private CustomerDao customerDao = new CustomerDao();

    public String addCustomer(Customer customer) {
        try {
            return customerDao.addUser(customer);
        } catch (IOException e) {
            return "添加客户失败：" + e.getMessage();
        }
    }

    public List<Customer> findAllCustomers() {
        return customerDao.findAll();
    }

    public List<Customer> findCustomersByHmId(Integer hmId) {
        return customerDao.findByUserId(hmId);
    }

    public List<Customer> searchCustomersByName(String name) {
        List<Customer> allCustomers = customerDao.findAll();
        List<Customer> result = new java.util.ArrayList<>();
        for (Customer customer : allCustomers) {
            if (customer.getCustomer_name().contains(name)) {
                result.add(customer);
            }
        }
        return result;
    }

    public List<Customer> searchCustomersByHmAndName(Integer hmId, String name) {
        List<Customer> hmCustomers = customerDao.findByUserId(hmId);
        List<Customer> result = new java.util.ArrayList<>();
        for (Customer customer : hmCustomers) {
            if (customer.getCustomer_name().contains(name)) {
                result.add(customer);
            }
        }
        return result;
    }

    public Customer findCustomerById(Integer id) {
        return customerDao.findById(id);
    }

    public String assignCustomerToHm(Integer customerId, Integer hmId) {
        Customer customer = customerDao.findById(customerId);
        if (customer == null) {
            return "客户不存在";
        }
        customer.setUser_id(hmId);
        customerDao.updateUser(customer);
        return "分配成功";
    }

    public String updateCustomer(Customer customer) {
        try {
            customerDao.updateUser(customer);
            return "更新成功";
        } catch (Exception e) {
            return "更新失败：" + e.getMessage();
        }
    }
}
