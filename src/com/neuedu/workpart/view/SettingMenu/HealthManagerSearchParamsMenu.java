package com.neuedu.workpart.view.SettingMenu;

import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerSearchParamsMenu implements IMenu {

    @Override
    public void execute() {
        System.out.println("请输入用户名");
        Scanner sc=new Scanner(System.in);
        String inputUserName=sc.next();
        MUserService service=new MUserService();
        TUser resultUser=service.findByUserName(inputUserName);
        if(resultUser!=null) {
            System.out.println(resultUser);
        }else{
            System.out.println("暂无结果");
        }
    }
}
