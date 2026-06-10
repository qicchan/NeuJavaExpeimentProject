package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.ResidentCustomerDao;
import com.neuedu.workpart.pojo.ResidentCustomer;

import java.util.ArrayList;
import java.util.List;

public class ResidentCustomerService {
    private final ResidentCustomerDao customerDao = new ResidentCustomerDao();

    public String addCustomer(ResidentCustomer customer) {
        try {
            return customerDao.addUser(customer);
        } catch (Exception e) {
            return "添加客户失败：" + e.getMessage();
        }
    }

    public List<ResidentCustomer> findAllCustomers() {
        return customerDao.findAll();
    }

    public List<ResidentCustomer> findCustomersByHmId(Integer hmId) {
        return customerDao.findByUserId(hmId);
    }

    public List<ResidentCustomer> searchCustomersByName(String name) {
        List<ResidentCustomer> result = new ArrayList<>();
        for (ResidentCustomer customer : customerDao.findAll()) {
            if (customer.getCustomer_name().contains(name)) {
                result.add(customer);
            }
        }
        return result;
    }

    public List<ResidentCustomer> searchCustomersByHmAndName(Integer hmId, String name) {
        List<ResidentCustomer> result = new ArrayList<>();
        for (ResidentCustomer customer : customerDao.findByUserId(hmId)) {
            if (customer.getCustomer_name().contains(name)) {
                result.add(customer);
            }
        }
        return result;
    }

    public ResidentCustomer findCustomerById(Integer id) {
        return customerDao.findById(id);
    }

    public String assignCustomerToHm(Integer customerId, Integer hmId) {
        ResidentCustomer customer = customerDao.findById(customerId);
        if (customer == null) {
            return "客户不存在";
        }
        customer.setUser_id(hmId);
        customerDao.updateUser(customer);
        return "分配成功";
    }

    public String updateCustomer(ResidentCustomer customer) {
        try {
            customerDao.updateUser(customer);
            return "更新成功";
        } catch (Exception e) {
            return "更新失败：" + e.getMessage();
        }
    }

    public void deleteCustomer(Integer id) {
        customerDao.deleteById(id);
    }
}
