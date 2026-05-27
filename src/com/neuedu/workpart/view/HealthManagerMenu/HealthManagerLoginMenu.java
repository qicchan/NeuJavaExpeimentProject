package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerLoginMenu implements IMenu {
    public void execute(){
        while(true){
            //护工登录界面
            System.out.println("==================欢迎来到护工登录系统===============");
            Scanner sc=new Scanner(System.in);
            System.out.println("请输入账号");
            //获取用户输入的用户名，并存到变量userName中
            String userName=sc.next();
            System.out.println("请输入密码");
            //获取用户输入的密码，并存到变量password中
            String password=sc.next();
            //登录合法校验
            MUserService userService=new MUserService();

            if(userService.findUserByAll(2,userName,password)){
                //查询users，根据用户名+密码+usertype=2 三项查询，如果查询到了正常运行
                HealthManagerMainMenu hmm=new HealthManagerMainMenu();
                hmm.execute();
            }else{
                //否则登录失败
                System.out.println("用户名或密码错误，请重新输入");
                continue;
            }
        }
    }
}
