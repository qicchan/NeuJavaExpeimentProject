package com.neuedu.workpart.dao;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.utils.PersistentIdGenerator;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDao {
    public static final File FILE_NAME = new File("data\\customers.json");
    private final ObjectMapper om = new ObjectMapper();

    public String addCustomer(Customer customer) throws IOException {
        List<Customer> list = findAll();
        customer.setId(String.valueOf(PersistentIdGenerator.getInstance().nextId()));
        list.add(customer);
        om.writeValue(FILE_NAME, list);
        return "添加成功";
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

    public Customer findById(String id) {
        return findAll().stream()
                .filter(c -> c.getId().equals(id))
                .findFirst().orElse(null);
    }

    public List<Customer> findByName(String name) {
        List<Customer> result = new ArrayList<>();
        for (Customer c : findAll()) {
            if (c.getName() != null && c.getName().contains(name)) {
                result.add(c);
            }
        }
        return result;
    }

    public boolean updateCustomer(Customer newCustomer) {
        try {
            List<Customer> list = findAll();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(newCustomer.getId())) {
                    list.set(i, newCustomer);
                    om.writeValue(FILE_NAME, list);
                    return true;
                }
            }
            return false;
        } catch (IOException e) {
            throw new RuntimeException("修改客户异常", e);
        }
    }

    public boolean deleteById(long id) {
        try {
            List<Customer> list = findAll();
            boolean removed = list.removeIf(c -> c.getId().equals(String.valueOf(id)));
            if (removed) {
                om.writeValue(FILE_NAME, list);
            }
            return removed;
        } catch (IOException e) {
            throw new RuntimeException("删除客户失败", e);
        }
    }
}
