package com.neuedu.workpart.view;

import com.neuedu.workpart.view.CustomerMenu.Customer_admin_Menu;

import java.util.Scanner;

public class AdminChosingMenu implements IMenu {
    public void execute(){
        System.out.println("请选择管理员执行任务");
        System.out.println("1.客户管理 2.床位管理 3.护理管理 4.设置界面");
        Scanner sc = new Scanner(System.in);
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                Customer_admin_Menu customerMenu = new Customer_admin_Menu();
                customerMenu.execute();
                break;
            case 2:
                BedMenu bedMenu = new BedMenu();
                bedMenu.execute();
                break;
            case 3:
                 CarePart carePart = new CarePart();
                 carePart.execute();
            case 4:
                com.neuedu.workpart.view.SettingMenu.AdminSettingMenu asm=new com.neuedu.workpart.view.SettingMenu.AdminSettingMenu();
                asm.execute();
        }

    }
}
