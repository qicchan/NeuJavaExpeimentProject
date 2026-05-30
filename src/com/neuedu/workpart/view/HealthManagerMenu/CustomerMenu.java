package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.view.IMenu;

public class CustomerMenu implements IMenu {
    public void execute(){
        System.out.println("=====健康管家=====");
        System.out.println("1.查询自己服务的客户信息列表");
        System.out.println("2.对客户进行日常护理并生成和护理记录");
        System.out.println("3.选中客户可查询的护理记录信息，并可对记录进行删除/隐藏");
    }
}
