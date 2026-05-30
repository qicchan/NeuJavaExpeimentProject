package com.neuedu.workpart.view;

import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.service.CareStatusService;

import java.util.List;
import java.util.Scanner;

public class CarePartMenu implements IMenu {
    private final CareStatusService careStatusService = new CareStatusService();

    public void execute() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("========== 护理管理菜单 ==========");
            System.out.println("1.查看所有用户护理状态");
            System.out.println("2.查询用户护理状态");
            System.out.println("3.查询用户护理等级和项目");
            System.out.println("4.添加用户护理信息");
            System.out.println("5.修改用户护理信息");
            System.out.println("6.删除用户护理信息");
            System.out.println("0.返回上级菜单");
            System.out.println("==================================");
            System.out.print("请选择操作：");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    List<Customer> list = careStatusService.findAll();
                    if (list.isEmpty()) {
                        System.out.println("暂无客户数据");
                    } else {
                        for (Customer c : list) {
                            System.out.println(c);
                        }
                    }
                    break;
                case 2:
                    System.out.print("请输入客户姓名：");
                    String name2 = sc.nextLine();
                    Customer c2 = careStatusService.findByName(name2);
                    if (c2 == null) {
                        System.out.println("未找到该客户");
                    } else {
                        System.out.println(c2);
                    }
                    break;
                case 3:
                    System.out.print("请输入客户姓名：");
                    String name3 = sc.nextLine();
                    Customer c3 = careStatusService.findByName(name3);
                    if (c3 == null) {
                        System.out.println("未找到该客户");
                    } else {
                        System.out.println("客户：" + c3.getCustomerName());
                        System.out.println("护理等级：" + c3.getCareLevel());
                        System.out.println("护理项目：" + c3.getCareItem());
                        System.out.println("记录时间：" + c3.getUpdateTime());
                    }
                    break;
                case 4:
                    System.out.print("请输入客户姓名：");
                    String name4 = sc.nextLine();
                    System.out.print("请输入护理等级：");
                    String level4 = sc.nextLine();
                    System.out.print("请输入护理项目：");
                    String item4 = sc.nextLine();
                    System.out.println(careStatusService.add(name4, level4, item4));
                    break;
                case 5:
                    System.out.print("请输入要修改的客户姓名：");
                    String name5 = sc.nextLine();
                    System.out.print("请输入新的护理等级：");
                    String level5 = sc.nextLine();
                    System.out.print("请输入新的护理项目：");
                    String item5 = sc.nextLine();
                    if (careStatusService.update(name5, level5, item5)) {
                        System.out.println("修改成功");
                    } else {
                        System.out.println("未找到该客户");
                    }
                    break;
                case 6:
                    System.out.print("请输入要删除的客户ID：");
                    int id = sc.nextInt();
                    if (careStatusService.delete(id)) {
                        System.out.println("删除成功");
                    } else {
                        System.out.println("未找到该客户，删除失败");
                    }
                    break;
                case 0:
                    return;
                default:
                    System.out.println("输入错误，请重新选择");
            }
        }
    }
}
