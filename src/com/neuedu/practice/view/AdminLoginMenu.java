package com.neuedu.practice.view;

import com.neuedu.practice.service.TUserService;

import java.util.Scanner;

public class AdminLoginMenu implements IMenu {
    public void execute(){
        while(true){
        //管理员界面
        System.out.println("==================系统管理员登录===============");
        Scanner sc=new Scanner(System.in);
        System.out.println("请输入账号");
        //获取用户输入的用户名，并存到变量userName中
         String userName=sc.next();
        System.out.println("请输入密码");
        //获取用户输入的用户名，并存到变量userName中
        String password=sc.next();
        //登录合法校验
        System.out.println("请输入用户类型");
         int userType=sc.nextInt();
        //如果用户名和密码者是admin，不区分大小定,就登录成功
            // 此处需要改造
            TUserService userService=new TUserService();

        if(userService.findUserByAll(userType,userName,password)){
            //查询users，根据用户名+密码+usertype =1 三项查询，如果查询到了正常运行
            AdminMainMenu amm=new AdminMainMenu();
            amm.execute();
        }else{
            //否则登录失败
            System.out.println("用户名或密码错误，请重新输入");
            continue;
        }
      }

    }
}
