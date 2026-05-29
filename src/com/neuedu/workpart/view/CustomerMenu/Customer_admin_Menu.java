package com.neuedu.workpart.view.CustomerMenu;

import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class Customer_admin_Menu implements IMenu {
    //管理员——客户管理
    public void execute(){
        //1.外出登记 2 退住登记 3 入住登记 4.退出
        System.out.println("请选择客户管理任务");
        System.out.println("1.外出登记 2.退住登记 3.入住登记 4.退出");
        Scanner sc=new Scanner(System.in);
        int result=sc.nextInt();
        switch(result){
            case 1:
                CustomerGooutRegister goout=new CustomerGooutRegister();
                goout.execute();
                break;


            case 2:
                CustomerCheckoutRegister checkout=new CustomerCheckoutRegister();
                checkout.execute();
                break;

            case 3:
                CustomerCheckinRegister checkin =new CustomerCheckinRegister();
                checkin.execute();
                break;
            default:
                return;
        }
    }
}
