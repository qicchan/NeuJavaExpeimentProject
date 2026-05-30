package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.RequirementQuit;
import com.neuedu.workpart.service.RequirementService;
import com.neuedu.workpart.view.IMenu;

import java.util.List;
import java.util.Scanner;

//健康管家功能菜单
public class HealthManagerPowerMenu implements IMenu {
    private RequirementService requirementService = new RequirementService();
    private Scanner sc = new Scanner(System.in);

    public void execute() {
        while (true) {
            System.out.println("\n\n=====客户管理=====");
            System.out.println("外出申请");
            System.out.println("1.为自己服务的客户提出外出申请");
            System.out.println("2.查询自己服务的客户外出申请信息列表");
            System.out.println("退住申请");
            System.out.println("3.为自己服务的客户提出退住申请");
            System.out.println("4.查询自己服务的客户退住申请信息列表");
            System.out.println("5.返回上一级菜单");
            System.out.print("请选择：");

            int choice = sc.nextInt();
            sc.nextLine(); // 消耗换行符

            switch (choice) {
                case 1:
                    createOutRequirement();
                    break;
                case 2:
                    searchOutRequirement();
                    break;
                case 3:
                    createQuitRequirement();
                    break;
                case 4:
                    searchQuitRequirement();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("无效选择，请重新输入！");
            }
        }
    }

    /**
     * 创建外出申请
     */
    private void createOutRequirement() {
        System.out.println("=====提出外出申请=====");
        System.out.print("请输入客户姓名：");
        String customerName = sc.nextLine();
        System.out.print("请输入客户ID：");
        Integer customerId = sc.nextInt();
        sc.nextLine(); // 消耗换行符
        System.out.print("请输入外出事由：");
        String reason = sc.nextLine();
        System.out.print("请输入外出时间（yyyy-MM-dd HH:mm:ss）：");
        String outTime = sc.nextLine();
        System.out.print("请输入预计回院时间（yyyy-MM-dd HH:mm:ss）：");
        String expectReturnTime = sc.nextLine();

        try {
            String result = requirementService.createOutRequirement(reason, outTime, expectReturnTime,
                    customerName, customerId);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("操作失败：" + e.getMessage());
        }
    }

    /**
     * 查询外出申请
     */
    private void searchOutRequirement() {
        System.out.println("=====查询外出申请=====");
        System.out.println("1.查询所有外出申请");
        System.out.println("2.按客户姓名查询");
        System.out.print("请选择：");
        int choice = sc.nextInt();
        sc.nextLine(); // 消耗换行符

        List<RequirementOut> outList;
        if (choice == 2) {
            System.out.print("请输入客户姓名（支持模糊查询）：");
            String customerName = sc.nextLine();
            outList = requirementService.searchOutByCustomerName(customerName);
        } else {
            outList = requirementService.getAllOutRequirements();
        }

        if (outList.isEmpty()) {
            System.out.println("未找到相关记录");
        } else {
            for (RequirementOut out : outList) {
                System.out.println(out);
            }
        }

        // 询问是否登记回院
        System.out.println("是否登记回院？（1-是，其他-否）");
        int returnChoice = sc.nextInt();
        if (returnChoice == 1) {
            System.out.print("请输入外出申请ID：");
            Integer id = sc.nextInt();
            try {
                requirementService.registerReturn(id);
                System.out.println("回院登记成功");
            } catch (Exception e) {
                System.out.println("回院登记失败：" + e.getMessage());
            }
        }
    }

    /**
     * 创建退住申请
     */
    private void createQuitRequirement() {
        System.out.println("=====提出退住申请=====");
        System.out.print("请输入客户姓名：");
        String customerName = sc.nextLine();
        System.out.print("请输入客户ID：");
        Integer customerId = sc.nextInt();
        sc.nextLine(); // 消耗换行符
        System.out.print("请输入退住类型：");
        String quitType = sc.nextLine();
        System.out.print("请输入退住原因：");
        String reason = sc.nextLine();
        System.out.print("请输入退住时间（yyyy-MM-dd HH:mm:ss）：");
        String quitTime = sc.nextLine();

        try {
            String result = requirementService.createQuitRequirement(quitType, reason, quitTime,
                    customerName, customerId);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("操作失败：" + e.getMessage());
        }
    }

    /**
     * 查询退住申请
     */
    private void searchQuitRequirement() {
        System.out.println("=====查询退住申请=====");
        System.out.println("1.查询所有退住申请");
        System.out.println("2.按客户姓名查询");
        System.out.print("请选择：");
        int choice = sc.nextInt();
        sc.nextLine(); // 消耗换行符

        List<RequirementQuit> quitList;
        if (choice == 2) {
            System.out.print("请输入客户姓名（支持模糊查询）：");
            String customerName = sc.nextLine();
            quitList = requirementService.searchQuitByCustomerName(customerName);
        } else {
            quitList = requirementService.getAllQuitRequirements();
        }

        if (quitList.isEmpty()) {
            System.out.println("未找到相关记录");
        } else {
            for (RequirementQuit quit : quitList) {
                System.out.println(quit);
            }
        }
    }
}

