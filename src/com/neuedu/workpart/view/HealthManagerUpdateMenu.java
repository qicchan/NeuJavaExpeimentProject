package com.neuedu.workpart.view;

import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerUpdateMenu implements IMenu {

    @Override
    public void execute() {
        System.out.println("请输入ID");
        Scanner sc = new Scanner(System.in);
        int id = sc.nextInt();
        System.out.println("请输入新用户名");
        String name = sc.next();
        System.out.println("请输入新密码");
        String passWord = sc.next();
        System.out.println("请输入用户类型 1-管理员 2-护工");
        int userType = sc.nextInt();
        TUser newUser = new TUser(id, name, passWord, userType);
        MUserService userService = new MUserService();
        if(userService.updateUser(newUser)){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败，请检查id");
        }
    }
}
