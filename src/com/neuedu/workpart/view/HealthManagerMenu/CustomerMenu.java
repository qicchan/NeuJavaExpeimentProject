package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.pojo.CareProject;
import com.neuedu.workpart.pojo.CareRecord;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.service.CareService;
import com.neuedu.workpart.service.CustomerService;
import com.neuedu.workpart.view.IMenu;

import java.util.List;
import java.util.Scanner;

public class CustomerMenu implements IMenu {
    private CareService careService = new CareService();
    private CustomerService customerService = new CustomerService();
    private Scanner sc = new Scanner(System.in);
    private Integer currentHmId;

    public void execute() {
        System.out.println("请输入当前护工ID：");
        currentHmId = sc.nextInt();
        sc.nextLine();

        while (true) {
            System.out.println("\n=====健康管家功能菜单=====");
            System.out.println("1.查询自己服务的客户");
            System.out.println("2.对客户进行日常护理");
            System.out.println("3.查询护理记录并隐藏");
            System.out.println("4.返回上一级");
            System.out.print("请选择：");

            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    queryMyCustomers();
                    break;
                case 2:
                    dailyCareOperation();
                    break;
                case 3:
                    queryCareRecords();
                    break;
                case 4:
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    private void queryMyCustomers() {
        System.out.println("\n=====我服务的客户列表=====");
        System.out.println("1.查询所有客户");
        System.out.println("2.按客户姓名模糊查询");
        System.out.print("请选择：");
        int choice = sc.nextInt();
        sc.nextLine();

        List<Customer> customers;
        if (choice == 2) {
            System.out.print("请输入客户姓名：");
            String name = sc.nextLine();
            customers = customerService.searchCustomersByHmAndName(currentHmId, name);
        } else {
            customers = customerService.findCustomersByHmId(currentHmId);
        }

        if (customers.isEmpty()) {
            System.out.println("未找到相关客户");
        } else {
            for (Customer customer : customers) {
                System.out.println(customer);
            }
        }
    }

    private void dailyCareOperation() {
        System.out.println("\n=====日常护理操作=====");
        System.out.print("请输入客户ID：");
        Integer customerId = sc.nextInt();
        sc.nextLine();

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            System.out.println("客户不存在");
            return;
        }

        System.out.println("\n该客户的护理项目：");
        List<CareProject> projects = careService.getProjectsByCustomer(customerId);
        if (projects.isEmpty()) {
            System.out.println("该客户暂无护理项目");
            System.out.print("是否添加护理项目？（1-是，其他-否）：");
            int addChoice = sc.nextInt();
            sc.nextLine();
            if (addChoice == 1) {
                addNewProject(customerId, customer.getCustomer_name());
            }
            return;
        }

        for (int i = 0; i < projects.size(); i++) {
            CareProject project = projects.get(i);
            System.out.println((i + 1) + ". " + project.getProjectName()
                    + " - 已完成:" + project.getCompletedNum() + "/" + project.getTotalNum());
        }

        System.out.print("\n请选择要护理的项目编号：");
        int projectIndex = sc.nextInt() - 1;
        sc.nextLine();

        if (projectIndex < 0 || projectIndex >= projects.size()) {
            System.out.println("无效选择");
            return;
        }

        CareProject selectedProject = projects.get(projectIndex);
        System.out.print("请输入护理数量：");
        Integer careNum = sc.nextInt();
        sc.nextLine();

        String result = careService.createCareRecord(
                selectedProject.getProjectName(),
                careNum,
                currentHmId,
                customerId,
                customer.getCustomer_name()
        );
        System.out.println(result);
    }

    private void addNewProject(Integer customerId, String customerName) {
        System.out.print("请输入项目名称：");
        String projectName = sc.nextLine();
        System.out.print("请输入项目描述：");
        String description = sc.nextLine();
        System.out.print("请输入总数量：");
        Integer totalNum = sc.nextInt();
        sc.nextLine();

        String result = careService.addCareProjectForCustomer(projectName, description, customerId, customerName);
        System.out.println(result);
    }

    private void queryCareRecords() {
        System.out.println("\n=====护理记录查询=====");
        System.out.print("请输入客户ID：");
        Integer customerId = sc.nextInt();
        sc.nextLine();

        List<CareRecord> records = careService.getCareRecordsByCustomerAndHm(customerId, currentHmId);

        if (records.isEmpty()) {
            System.out.println("未找到护理记录");
            return;
        }

        for (CareRecord record : records) {
            System.out.println(record);
        }

        System.out.print("\n是否隐藏某条记录？（1-是，其他-否）：");
        int hideChoice = sc.nextInt();
        if (hideChoice == 1) {
            System.out.print("请输入记录ID：");
            Integer recordId = sc.nextInt();
            sc.nextLine();
            String result = careService.hideCareRecord(recordId);
            System.out.println(result);
        }
    }
}
