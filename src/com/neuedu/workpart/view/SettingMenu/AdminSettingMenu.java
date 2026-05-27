package com.neuedu.workpart.view.SettingMenu;

import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class AdminSettingMenu implements IMenu {
    public void execute() {
        Scanner sc=new Scanner(System.in);
        while(true) {
            System.out.println("1.添加管理员 2.查询所有管理员信息  3.根据条件查询管理员信息 4.修改管理员信息 5.删除管理员信息");
            System.out.println("6.添加健康管家 7.查询所有健康管家信息  8.根据条件查询健康管家信息 9.修改健康管家信息 10.删除健康管家信息");
            System.out.println("11.退出系统");
            //思考：如何先将用户信息存储到文件里，方便后续的查询或修改？
            //获取用户输入
            int result = sc.nextInt();//用result变量接收用户输入
            switch (result) {
                case 1:
                    //1.添加员工
                    AdminAddMenu aam = new AdminAddMenu();
                    aam.execute();
                    break;
                case 2:
                    //2.查询所有员工信息
                    AdminSearchMenu asm=new AdminSearchMenu();
                    asm.execute();
                    break;
                case 3:
                    //3.根据条件查询管理员
                    AdminSearchParamsMenu asp=new AdminSearchParamsMenu();
                    asp.execute();
                    break;
                case 4:
                    //更改管理员信息
                    AdminUpdateMenu aum = new AdminUpdateMenu();
                    aum.execute();
                    break;
                case 5:
                    //删除管理员
                    AdminDelMenu adm = new AdminDelMenu();
                    adm.execute();
                    break;
                case 6:
                    //1.添加健康管家
                    HealthManagerAddMenu hmam = new HealthManagerAddMenu();
                    hmam.execute();
                    break;
                case 7:
                    //2.查询所有健康管家
                    HealthManagerSearchMenu hmsm=new HealthManagerSearchMenu();
                    hmsm.execute();
                    break;
                case 8:
                    //3.根据条件查询健康管家
                    HealthManagerSearchParamsMenu hmsp=new HealthManagerSearchParamsMenu();
                    hmsp.execute();
                    break;
                case 9:
                    //更改健康管家信息
                    HealthManagerUpdateMenu hmum = new HealthManagerUpdateMenu();
                    hmum.execute();
                    break;
                case 10:
                    //删除健康管家
                    HealthManagerDelMenu hmdm = new HealthManagerDelMenu();
                    hmdm.execute();
                    break;
                case 11:
                    //退出系统
                    System.out.println("已退出");
                    return;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
