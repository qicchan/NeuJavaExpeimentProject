package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareDao;
import com.neuedu.workpart.pojo.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CareStatusService {
    private final CareDao careDao = new CareDao();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public List<Customer> findAll() {
        return careDao.findAll();
    }

    public Customer findByName(String name) {
        return careDao.findByCustomerName(name);
    }

    public String add(String name, String careLevel, String careItem) {
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setCareLevel(careLevel);
        customer.setCareItem(careItem);
        customer.setUpdateTime(LocalDateTime.now().format(FORMATTER));
        return careDao.addCustomer(customer);
    }

    public boolean update(String name, String careLevel, String careItem) {
        Customer customer = careDao.findByCustomerName(name);
        if (customer == null) {
            return false;
        }
        customer.setCareLevel(careLevel);
        customer.setCareItem(careItem);
        customer.setUpdateTime(LocalDateTime.now().format(FORMATTER));
        return careDao.updateCustomer(customer);
    }

    public boolean delete(int id) {
        return careDao.deleteById(id);
    }
}
