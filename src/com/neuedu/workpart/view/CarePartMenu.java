package com.neuedu.workpart.view;

import com.neuedu.workpart.service.CareStatusService;

import java.util.Scanner;

public class CarePartMenu implements IMenu {
    public void execute() {
        Scanner sc = new Scanner(System.in);
        CareStatusService careStatusService = new CareStatusService();
        while (true) {
            System.out.println("========== 护理管理菜单 ==========");
            System.out.println("1.查看所有用户护理状态");
            System.out.println("2.查询用户护理状态");
            System.out.println("3.查询用户护理等级和项目记录");
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
                    careStatusService.checkAll();
                    break;
                case 2:
                    careStatusService.checkByName();
                    break;
                case 3:
                    careStatusService.queryCareInfo();
                    break;
                case 4:
                    careStatusService.add();
                    break;
                case 5:
                    careStatusService.update();
                    break;
                case 6:
                    careStatusService.delete();
                    break;
                case 0:
                    return;
                default:
                    System.out.println("输入错误，请重新选择");
            }
        }
    }
}
