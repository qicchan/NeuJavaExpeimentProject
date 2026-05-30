package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.view.IMenu;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.MUserService;

import java.util.List;

public class HealthManagerSearchMenu implements IMenu {
    public void execute() {
        MUserService us=new MUserService();
        List<TUser> list=us.findAll();
        for(TUser user:list){
            System.out.println(user);
        }
    }
}
