package com.neuedu.workpart.view.AdministratorMenu;

import com.neuedu.workpart.view.IMenu;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.TUserService;

import java.util.List;

public class AdminSearchMenu implements IMenu {
    public void execute() {
        TUserService us=new TUserService();
        //list
        List<TUser> list=us.findAll();
        //for(元素类型 别名:循环对象)
        for(TUser user:list){
            //如果想改格式，去改tuser的toString
            System.out.println(user);
        }

    }
}
