package com.neuedu.workpart.view;

import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.TUserService;

import java.util.Scanner;

public class AdminUpdateMenu implements IMenu {

    @Override
    public void execute() {
    //修改需要传ID
        System.out.println("请输入ID");
        Scanner sc = new Scanner(System.in);
            int id = sc.nextInt();
        System.out.println("请输入新用户名");
        String name = sc.next();
        System.out.println("请输入新密码");
        String passWord = sc.next();
        System.out.println("请输入修改管理员类型");
        int userType = sc.nextInt();
        TUser newUser = new TUser(id ,name, passWord, userType);
        TUserService userService = new TUserService();
        if(userService.updateUser(newUser)){
            System.out.println("修改成功");
        }else{
            System.out.println("修改失败，请检查id");
        }
    }
}
