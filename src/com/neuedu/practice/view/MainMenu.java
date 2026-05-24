package com.neuedu.practice.view;


import java.util.Scanner;

public class MainMenu {

    public static void main(String[] args) {
        while(true) {
            System.out.println("=========东软颐养中心管理系统=========");
            System.out.println("1--------管理员登录");
            System.out.println("2--------护工登录");
            System.out.println("3--------退出");
            //实例化Scanner
            Scanner sc = new Scanner(System.in);
            //获取用户输入
            int result = sc.nextInt();//用result变量接收用户输入
            System.out.println("用户输入的是：" + result);
            //根据用户的输入跳转到不同的页面
            //byte short int string
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
                    break;
                case 3:
//                /退出
                    System.exit(1);
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }
}
