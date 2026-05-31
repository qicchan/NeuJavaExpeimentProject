package com.neuedu.workpart.view.HealthManagerMenu;

import java.util.Scanner;

public class HealthManagerMainMenu {
    public void execute() {
        System.out.println("==================欢迎来到护工系统===============");
        System.out.println("=====================功能菜单==================");
        System.out.println("\n1.健康管家");
        System.out.println("-查询自己服务的客户信息列表");
        System.out.println("-对客户进行日常护理并生成和护理记录");
        System.out.println("-选中客户可查询的护理记录信息，并可对记录进行删除/隐藏");
        System.out.println("\n2.客户管理");
        System.out.println("-外出申请");
        System.out.println("--为自己服务的客户提出外出申请");
        System.out.println("--查询自己服务的客户外出申请信息列表");
        System.out.println("-退住申请");
        System.out.println("--为自己服务的客户提出退住申请");
        System.out.println("--查询自己服务的客户退住申请信息列表");
        System.out.println("\n3.返回上一级菜单");

        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                CustomerMenu cm = new CustomerMenu();
                cm.execute();
                break;
            case 2:
                HealthManagerPowerMenu hpm = new HealthManagerPowerMenu();
                hpm.execute();
                break;
            case 3:
                HealthManagerMainMenu hm =new HealthManagerMainMenu();
                hm.execute();
                return;
            default:
                System.out.println("输入错误请检查选项");
                break;
        }
    }
}
