package com.neuedu.workpart.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.utils.PersistentIdGenerator;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.core.type.TypeReference;

/**
 * 客户数据访问层
 * 负责Customer对象的JSON文件持久化操作（增删改查）
 * 数据存储于 data/customer_users.json
 */
public class CustomerDao {
    /**
     * JSON数据文件路径
     */
    public static final File FILE_NAME = new File("data\\customer_users.json");
    /**
     * Jackson序列化/反序列化工具
     */
    private final ObjectMapper om = new ObjectMapper();

    /**
     * 新增客户
     *
     * @param customer 客户对象（id由系统自动生成）
     * @return 操作结果提示
     */
    public String addUser(Customer customer) throws IOException {
        try {
            List<Customer> customerList = findAll();
            customer.setId(PersistentIdGenerator.getInstance().nextId());
            customerList.add(customer);
            om.writeValue(FILE_NAME, customerList);
            return "添加成功";
        } catch (Exception e) {
            throw new RuntimeException("添加用户失败", e);
        }
    }

    /**
     * 查询全部客户
     *
     * @return 客户列表，文件不存在或读取异常时返回空列表
     */
    public List<Customer> findAll() {
        if (!FILE_NAME.exists()) {
            return new ArrayList<>();
        }
        try {
            return om.readValue(FILE_NAME, new TypeReference<List<Customer>>() {
            });
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    /**
     * 按客户姓名查询
     *
     * @param userName 客户姓名
     * @return 匹配的Customer对象，未找到返回null
     */
    public Customer findByUserName(String userName) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getCustomer_name().equals(userName)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 按ID查询客户
     *
     * @param id 客户主键ID
     * @return 匹配的Customer对象，未找到返回null
     */
    public Customer findById(Integer id) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 按身份证号查询客户（用于防重复登记）
     *
     * @param idcard 身份证号
     * @return 匹配的Customer对象，未找到返回null
     */
    public Customer findByIdcard(String idcard) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getIdcard().equals(idcard)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 按房间号查询客户
     *
     * @param room_no 房间号
     * @return 匹配的Customer对象，未找到返回null
     */
    public Customer findbyRoomNo(String room_no) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getRoom_no().equals(room_no)) {
                return customer;
            }
        }
        return null;
    }

    /**
     * 按ID更新客户全部信息
     * 遍历列表找到ID匹配的客户后，将传入对象的各字段值逐一套用
     *
     * @param customer 包含更新后信息的客户对象
     */
    public void updateUser(Customer customer) {
        List<Customer> customerList = findAll();
        for (Customer c : customerList) {
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
            }
        }
    }

    /**
     * 按ID删除客户（物理删除，从列表中移除）
     *
     * @param id 客户主键ID
     */
    public void deleteById(Integer id) {
        List<Customer> customerList = findAll();
        for (Customer customer : customerList) {
            if (customer.getId().equals(id)) {
                customerList.remove(customer);
                break;
            }
        }
    }
}