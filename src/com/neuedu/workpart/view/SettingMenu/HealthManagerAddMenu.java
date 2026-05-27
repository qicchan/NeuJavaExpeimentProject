package com.neuedu.workpart.view.SettingMenu;

import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerAddMenu implements IMenu {
    public void execute() {
        System.out.println("==================护工-添加用户===============");
        Scanner sc=new Scanner(System.in);

        System.out.println("新用户账号");
        String userName=sc.next();
        System.out.println("新用户密码");
        String password=sc.next();
        System.out.println("用户类型 1-管理员 2-护工");
        int userType=sc.nextInt();
        TUser user = new TUser();
        user.setUserType(userType);
        user.setUserName(userName);
        user.setPassword(password);
        //设置账号密码
        MUserService us=new MUserService();
        String result=us.addUser(user);
        System.out.println(result);
    }
}
