package com.neuedu.workpart.view;

import com.neuedu.workpart.view.HealthManagerMenu.CustomerMenu;

import java.util.Scanner;

public class AdminChosingMenu implements IMenu {
    public void execute(){
        System.out.println("请选择管理员执行任务");
        System.out.println("1.客户管理 2.床位管理 3.护理管理 4.设置界面");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                CustomerMenu customerMenu = new CustomerMenu();
                customerMenu.execute();
                break;
            case 2:
                BedMenu bedMenu = new BedMenu();
                bedMenu.execute();
                break;
            case 3:
                 CarePart carePart = new CarePart();
                 carePart.execute();
                 break;
            case 4:
                com.neuedu.workpart.view.SettingMenu.AdminSettingMenu asm=new com.neuedu.workpart.view.SettingMenu.AdminSettingMenu();
                asm.execute();
                break;
            default:
                System.out.println("输入错误请检查选项");
                break;
        }

    }
}
