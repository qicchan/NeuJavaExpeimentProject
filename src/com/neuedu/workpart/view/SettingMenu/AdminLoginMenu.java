package com.neuedu.workpart.view.SettingMenu;

import com.neuedu.workpart.service.TUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class AdminLoginMenu implements IMenu {
    public void execute(){
        Scanner sc=new Scanner(System.in);
        while(true){
        //管理员界面
        System.out.println("==================欢迎来到系统管理员登录===============");
        System.out.println("请输入账号（输入0返回主菜单）");
        String userName=sc.next();
        if("0".equals(userName)){
            return;
        }
        System.out.println("请输入密码");
        String password=sc.next();
        //登录合法校验
            TUserService userService=new TUserService();

        if(userService.findUserByAll(1,userName,password)){
            AdminSettingMenu amm=new AdminSettingMenu();
            amm.execute();
            return;
        }else{
            System.out.println("用户名或密码错误，请重新输入");
        }
      }
    }
}
