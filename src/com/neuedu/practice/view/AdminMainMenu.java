package com.neuedu.practice.view;

import java.util.Scanner;

public class AdminMainMenu implements IMenu {
    public void execute() {
        Scanner sc=new Scanner(System.in);
        while(true) {
            //@TODO  菜单需要完善
            /*
             用户管理
                 1--添加用户
                 2-- 查询所有
                 3-根据条件查询
             护理管理
                 4--护理级别
                 5-护理项目
                 。。。。。。。
             */
            System.out.println("1.添加员工 2.查询所有员工信息  3.根据条件查询员工信息 4.修改员工 5.删除员工 6.退出");
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
                    System.out.println("已退出");
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
