package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerMainMenu implements IMenu {
    public void execute() {
        Scanner sc=new Scanner(System.in);
        while(true) {
            System.out.println("1.添加用户 2.查询所有用户信息  3.根据条件查询用户信息 4.修改用户 5.删除用户 6.退出");
            //获取用户输入
            int result = sc.nextInt();
            switch (result) {
                case 1:
                    //1.添加用户
                    HealthManagerAddMenu aam = new HealthManagerAddMenu();
                    aam.execute();
                    break;
                case 2:
                    //2.查询所有用户信息
                    HealthManagerSearchMenu asm=new HealthManagerSearchMenu();
                    asm.execute();
                    break;
                case 3:
                    //3.根据条件查询用户
                    HealthManagerSearchParamsMenu asp=new HealthManagerSearchParamsMenu();
                    asp.execute();
                    break;
                case 4:
                    //修改用户信息
                    HealthManagerUpdateMenu aum = new HealthManagerUpdateMenu();
                    aum.execute();
                    break;
                case 5:
                    //删除用户
                    HealthManagerDelMenu adm = new HealthManagerDelMenu();
                    adm.execute();
                    break;
                case 6:
                    System.out.println("已退出");
                    return;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
