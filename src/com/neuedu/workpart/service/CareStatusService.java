package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareDao;
import com.neuedu.workpart.pojo.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 客户护理信息业务逻辑服务层。
 * <p>封装{@link com.neuedu.workpart.dao.CareDao}，提供客户护理信息的增删改查功能。</p>
 * <p>新增和修改操作会自动记录当前时间作为更新时间。</p>
 *
 * @author QICHAN
 * @see com.neuedu.workpart.dao.CareDao
 * @see com.neuedu.workpart.pojo.Customer
 */
public class CareStatusService {
    /** 客户护理DAO实例 */
    private final CareDao careDao = new CareDao();
    /** 日期时间格式化器，格式：yyyy-MM-dd HH:mm:ss */
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询所有客户护理信息
     *
     * @return 客户列表
     */
    public List<Customer> findAll() {
        return careDao.findAll();
    }

    /**
     * 根据客户姓名查询护理信息
     *
     * @param name 客户姓名
     * @return 匹配的客户对象，未找到返回null
     */
    public Customer findByName(String name) {
        return careDao.findByCustomerName(name);
    }

    /**
     * 添加客户护理信息，自动设置当前时间为更新时间
     *
     * @param name      客户姓名
     * @param careLevel 护理等级
     * @param careItem  护理项目
     * @return 操作结果提示信息
     */
    public String add(String name, String careLevel, String careItem) {
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setCareLevel(careLevel);
        customer.setCareItem(careItem);
        customer.setUpdateTime(LocalDateTime.now().format(FORMATTER));
        return careDao.addCustomer(customer);
    }

    /**
     * 修改客户护理信息，根据姓名查找客户后更新护理等级和项目，并自动刷新更新时间
     *
     * @param name      客户姓名
     * @param careLevel 新的护理等级
     * @param careItem  新的护理项目
     * @return true-修改成功，false-客户不存在
     */
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

    /**
     * 根据ID删除客户护理信息
     *
     * @param id 客户ID
     * @return true-删除成功，false-客户不存在
     */
    public boolean delete(int id) {
        return careDao.deleteById(id);
    }
}
