package com.neuedu.workpart.service;

import com.neuedu.workpart.dao.CareDao;
import com.neuedu.workpart.pojo.Customer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class CareStatusService {
    private final CareDao careDao = new CareDao();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void checkAll() {
        List<Customer> customerList = careDao.findAll();
        if (customerList.isEmpty()) {
            System.out.println("暂无客户数据");
            return;
        }
        System.out.println("===== 所有客户护理信息 =====");
        for (Customer customer : customerList) {
            System.out.println(customer);
        }
    }

    public void checkByName() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("请输入客户姓名：");
        String name = sc.nextLine();
        Customer customer = careDao.findByCustomerName(name);
        if (customer == null) {
            System.out.println("未找到该客户");
        } else {
            System.out.println(customer);
        }
    }

    public void queryCareInfo() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("请输入客户姓名：");
        String name = sc.nextLine();
        Customer customer = careDao.findByCustomerName(name);
        if (customer == null) {
            System.out.println("未找到该客户");
        } else {
            System.out.println("客户：" + customer.getCustomerName());
            System.out.println("护理等级：" + customer.getCareLevel());
            System.out.println("护理项目：" + customer.getCareItem());
            System.out.println("记录时间：" + customer.getUpdateTime());
        }
    }

    public void add() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("请输入客户姓名：");
        String name = sc.nextLine();
        System.out.print("请输入护理等级：");
        String careLevel = sc.nextLine();
        System.out.print("请输入护理项目：");
        String careItem = sc.nextLine();
        Customer customer = new Customer();
        customer.setCustomerName(name);
        customer.setCareLevel(careLevel);
        customer.setCareItem(careItem);
        //调用LocalDateTime方法记录操作时间
        customer.setUpdateTime(LocalDateTime.now().format(FORMATTER));
        String result = careDao.addCustomer(customer);
        System.out.println(result);
    }

    public void update() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("请输入要修改的客户姓名：");
        String name = sc.nextLine();
        Customer customer = careDao.findByCustomerName(name);
        if (customer == null) {
            System.out.println("未找到该客户");
            return;
        }
        System.out.println("当前信息：" + customer);
        System.out.print("请输入新的护理等级：");
        String careLevel = sc.nextLine();
        System.out.print("请输入新的护理项目：");
        String careItem = sc.nextLine();
        customer.setCareLevel(careLevel);
        customer.setCareItem(careItem);
        customer.setUpdateTime(LocalDateTime.now().format(FORMATTER));
        boolean success = careDao.updateCustomer(customer);
        if (success) {
            System.out.println("修改成功");
        } else {
            System.out.println("修改失败");
        }
    }

    public void delete() {
        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("请输入要删除的客户ID：");
        int id = sc.nextInt();
        boolean success = careDao.deleteById(id);
        if (success) {
            System.out.println("删除成功");
        } else {
            System.out.println("未找到该客户，删除失败");
        }
    }
}
