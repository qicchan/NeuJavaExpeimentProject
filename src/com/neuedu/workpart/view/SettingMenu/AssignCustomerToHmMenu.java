package com.neuedu.workpart.view.SettingMenu;

import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.CustomerService;
import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.view.IMenu;

import java.util.List;
import java.util.Scanner;

public class AssignCustomerToHmMenu implements IMenu {
    @Override
    public void execute() {
        Scanner sc = new Scanner(System.in);
        CustomerService customerService = new CustomerService();
        MUserService userService = new MUserService();

        System.out.println("\n=====为客户分配护工=====");

        System.out.print("请输入客户ID：");
        Integer customerId = sc.nextInt();
        sc.nextLine();

        Customer customer = customerService.findCustomerById(customerId);
        if (customer == null) {
            System.out.println("客户不存在");
            return;
        }

        System.out.println("客户信息：");
        System.out.println(customer);

        System.out.println("\n可选的护工列表：");
        List<TUser> allUsers = userService.findAll();
        int index = 1;
        for (TUser user : allUsers) {
            if (user.getUserType() == 2) {
                System.out.println(index + ". " + user.getUserName() + " (ID: " + user.getId() + ")");
                index++;
            }
        }

        System.out.print("\n请选择护工编号：");
        int hmIndex = sc.nextInt() - 1;
        sc.nextLine();

        int hmCount = 0;
        Integer selectedHmId = null;
        for (TUser user : allUsers) {
            if (user.getUserType() == 2) {
                if (hmCount == hmIndex) {
                    selectedHmId = user.getId();
                    break;
                }
                hmCount++;
            }
        }

        if (selectedHmId == null) {
            System.out.println("无效选择");
            return;
        }

        String result = customerService.assignCustomerToHm(customerId, selectedHmId);
        System.out.println(result);
    }
}
