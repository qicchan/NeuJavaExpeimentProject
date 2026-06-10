package com.neuedu.workpart.dao;

import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.utils.JsonUtil;
import com.neuedu.workpart.utils.PersistentIdGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

public class ResidentCustomerDao {
    public static final File FILE_NAME = new File("data/resident_customers.json");
    private static final com.fasterxml.jackson.databind.ObjectMapper om = JsonUtil.INSTANCE;

    public String addUser(ResidentCustomer customer) {
        try {
            List<ResidentCustomer> customerList = findAll();
            customer.setId(PersistentIdGenerator.getInstance().nextId());
            customerList.add(customer);
            om.writeValue(FILE_NAME, customerList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加用户失败", e);
        }
    }

    public List<ResidentCustomer> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<ResidentCustomer>>() {});
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    public ResidentCustomer findByUserName(String userName) {
        for (ResidentCustomer customer : findAll()) {
            if (customer.getCustomer_name().equals(userName)) {
                return customer;
            }
        }
        return null;
    }

    public ResidentCustomer findById(Integer id) {
        for (ResidentCustomer customer : findAll()) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public ResidentCustomer findByIdcard(String idcard) {
        for (ResidentCustomer customer : findAll()) {
            if (customer.getIdcard().equals(idcard)) {
                return customer;
            }
        }
        return null;
    }

    public ResidentCustomer findByRoomNo(String room_no) {
        for (ResidentCustomer customer : findAll()) {
            if (customer.getRoom_no().equals(room_no)) {
                return customer;
            }
        }
        return null;
    }

    public void updateUser(ResidentCustomer customer) {
        List<ResidentCustomer> customerList = findAll();
        for (ResidentCustomer c : customerList) {
            if (c.getId().equals(customer.getId())) {
                c.setCustomer_name(customer.getCustomer_name());
                c.setCustomer_age(customer.getCustomer_age());
                c.setCustomer_sex(customer.getCustomer_sex());
                c.setIdcard(customer.getIdcard());
                c.setRoom_no(customer.getRoom_no());
                c.setBuilding_no(customer.getBuilding_no());
                c.setCheckin_date(customer.getCheckin_date());
                c.setExpiration_date(customer.getExpiration_date());
                c.setContact_tel(customer.getContact_tel());
                c.setBed_id(customer.getBed_id());
                c.setPsychosomatic_state(customer.getPsychosomatic_state());
                c.setAttention(customer.getAttention());
                c.setBirthday(customer.getBirthday());
                c.setHeight(customer.getHeight());
                c.setWeight(customer.getWeight());
                c.setBlood_type(customer.getBlood_type());
                c.setFilepath(customer.getFilepath());
                c.setUser_id(customer.getUser_id());
                c.setLevel_id(customer.getLevel_id());
                c.setFamily_member(customer.getFamily_member());
                c.setIs_deleted(customer.getIs_deleted());
                c.setCareLevel(customer.getCareLevel());
                break;
            }
        }
        try {
            om.writeValue(FILE_NAME, customerList);
        } catch (IOException e) {
            throw new RuntimeException("更新客户信息失败", e);
        }
    }

    public void deleteById(Integer id) {
        List<ResidentCustomer> customerList = findAll();
        ResidentCustomer target = null;
        for (ResidentCustomer customer : customerList) {
            if (customer.getId().equals(id)) {
                target = customer;
                break;
            }
        }
        if (target != null) {
            customerList.remove(target);
            try {
                om.writeValue(FILE_NAME, customerList);
            } catch (IOException e) {
                throw new RuntimeException("删除客户失败", e);
            }
        }
    }

    public List<ResidentCustomer> findByUserId(Integer userId) {
        List<ResidentCustomer> result = new ArrayList<>();
        for (ResidentCustomer customer : findAll()) {
            if (userId.equals(customer.getUser_id())) {
                result.add(customer);
            }
        }
        return result;
    }
}
