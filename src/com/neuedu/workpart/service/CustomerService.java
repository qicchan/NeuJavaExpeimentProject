package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CustomerDao;
import com.neuedu.workpart.pojo.Customer;

import java.io.IOException;
import java.util.List;

public class CustomerService {
    CustomerDao customerDao = new CustomerDao();

    public String addCustomer(Customer customer) {
        try {
            return customerDao.addCustomer(customer);
        } catch (IOException e) {
            return "添加失败";
        }
    }

    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    public Customer findById(String id) {
        return customerDao.findById(id);
    }

    public List<Customer> findByName(String name) {
        return customerDao.findByName(name);
    }

    public boolean updateCustomer(Customer customer) {
        return customerDao.updateCustomer(customer);
    }

    public boolean deleteCustomer(long id) {
        return customerDao.deleteById(id);
    }
}
