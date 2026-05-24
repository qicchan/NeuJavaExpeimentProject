package com.neuedu.practice.view;

import com.neuedu.practice.pojo.TUser;
import com.neuedu.practice.service.TUserService;

import java.util.Scanner;

public class AdminAddMenu implements IMenu {
    public void execute() {
        System.out.println("==================系统用户===============");
        Scanner sc=new Scanner(System.in);

        System.out.println("新用户账号");
        //获取用户输入的用户名，并存到变量userName中
        String userName=sc.next();
        System.out.println("新用户密码");
        //获取用户输入的用户名，并存到变量userName中
        String password=sc.next();
        System.out.println("用户类型 1-管理员 2-护工");
        //获取用户输入的用户名，并存到变量userName中
        int userType=sc.nextInt();
        TUser user = new TUser();//调用无参构造实例化一个对象
        user.setUserType(userType);
        //调置对象的用户名为用户输入的
        user.setUserName(userName);
        //设置对象的密码为用户输入的密码
        user.setPassword(password);
        //一会儿调用方法存文件
        TUserService us=new TUserService();
        //将用户信息传给service的addUser
        //用result来接收返回结果
        String result=us.addUser(user);
//        /在控制台打印结果
        System.out.println(result);
    }
}
