package com.neuedu.workpart.view;


import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while(true) {
            System.out.println("=========东软颐养中心管理系统=========");
            System.out.println("1------------管理员登录-------------");
            System.out.println("2------------ 护工登录--------------");
            System.out.println("3--------------退出---------------");
            //获取用户输入
            int result = sc.nextInt();
            //根据用户的输入跳转到不同的页面
            switch (result) {
                case 1:
                    //管理员登录
                    System.out.println("即将进入管理员登录...");
                    AdminLoginMenu alm=new AdminLoginMenu();
                    alm.execute();
                    break;
                case 2:
                    //护工登录
                    System.out.println("即将进入护工登录...");
                    HealthManagerLoginMenu hmlm = new HealthManagerLoginMenu();
                    hmlm.execute();
                    break;
                case 3:
                    //退出
                    System.out.println("已退出系统");
                    return;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
