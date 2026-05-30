package com.neuedu.workpart.view;

import com.neuedu.workpart.view.HealthManagerMenu.BedDiagramMenu;
import com.neuedu.workpart.view.HealthManagerMenu.BedSwapMenu;
import com.neuedu.workpart.view.HealthManagerMenu.BedUsageQueryMenu;
import com.neuedu.workpart.view.HealthManagerMenu.BedUsageUpdateMenu;

import java.util.Scanner;

public class BedMenu implements IMenu {

    public void execute() {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println();
            System.out.println("========== 床位管理 ==========");
            System.out.println("1. 床位整体信息图");
            System.out.println("2. 床位调换");
            System.out.println("3. 查询客户床位使用详情");
            System.out.println("4. 修改床位使用结束时间");
            System.out.println("0. 返回上级菜单");
            System.out.print("请选择: ");

            String choice = sc.nextLine().trim();
            switch (choice) {
                case "1":
                    new BedDiagramMenu().execute();
                    break;
                case "2":
                    new BedSwapMenu().execute();
                    break;
                case "3":
                    new BedUsageQueryMenu().execute();
                    break;
                case "4":
                    new BedUsageUpdateMenu().execute();
                    break;
                case "0":
                    return;
                default:
                    System.out.println("无效选择，请重新输入。");
            }
        }
    }
}
