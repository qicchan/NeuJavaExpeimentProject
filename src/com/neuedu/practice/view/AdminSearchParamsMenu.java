package com.neuedu.practice.view;

import com.neuedu.practice.pojo.TUser;
import com.neuedu.practice.service.TUserService;

import java.util.Scanner;

public class AdminSearchParamsMenu implements IMenu {

    @Override
    public void execute() {
        System.out.println("请输入用户名");
        Scanner sc=new Scanner(System.in);
        //获取用户输入的查询用户名
        String inputUserName=sc.next();
        //实例化service
        TUserService service=new TUserService();
        //调用servcie根据用户名查询的方法，并返回单个对象
        TUser resultUser=service.findByUserName(inputUserName);
        //根据查询结果判断是否有该用户
        if(resultUser!=null) {
            //如果有，就打印对象
            System.out.println(resultUser);
        }else{
            //没有，打印没有
            System.out.println("暂无结果");
        }

    }
}
