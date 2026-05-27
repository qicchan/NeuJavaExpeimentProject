package com.neuedu.workpart.view.SettingMenu;

import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.HealthManagerMainMenu;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerLoginMenu implements IMenu {
    public void execute(){
        Scanner sc=new Scanner(System.in);
        while(true){
            //护工登录界面
            System.out.println("==================欢迎来到护工登录系统===============");
            System.out.println("请输入账号（输入0返回主菜单）");
            String userName=sc.next();
            if("0".equals(userName)){
                return;
            }
            System.out.println("请输入密码");
            String password=sc.next();
            //登录合法校验
            MUserService userService=new MUserService();

            if(userService.findUserByAll(2,userName,password)){
                HealthManagerMainMenu hmm=new HealthManagerMainMenu();
                hmm.execute();
                return;
            }else{
                System.out.println("用户名或密码错误，请重新输入");
            }
        }
    }
}
