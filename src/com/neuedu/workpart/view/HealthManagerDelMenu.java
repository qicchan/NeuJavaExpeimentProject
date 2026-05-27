package com.neuedu.workpart.view;

import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class HealthManagerDelMenu implements IMenu {
    public void execute() {
        Scanner sc = new Scanner(System.in);
        System.out.println("确定要删除吗？是请输入1，否则输入0");
        int decide = sc.nextInt();
        if (decide == 1) {
            System.out.println("请输入要删除的用户id");
            Integer id = sc.nextInt();
            MUserService userService = new MUserService();
            boolean result= userService.deleteUser(id);
            if (result) {
                System.out.println("删除成功");
            }else{
                System.out.println("删除失败，请检查id是否正确");
            }
        }
    }
}
