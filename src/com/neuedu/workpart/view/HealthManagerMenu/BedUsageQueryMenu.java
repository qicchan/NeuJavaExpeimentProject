package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.service.BedUsageDetailService;
import com.neuedu.workpart.view.IMenu;

import java.util.List;
import java.util.Scanner;

public class BedUsageQueryMenu implements IMenu {

    public void execute() {
        BedUsageDetailService service = new BedUsageDetailService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("========== 查询客户床位使用详情 ==========");

            System.out.print("客户姓名（模糊查询，回车跳过）: ");
            String name = sc.nextLine().trim();

            System.out.print("入住日期（格式2026-05-27，回车跳过）: ");
            String date = sc.nextLine().trim();

            System.out.print("使用状态（1-正在使用 2-使用历史 回车=默认正在使用）: ");
            String statusInput = sc.nextLine().trim();
            String status;
            if (statusInput.isEmpty()) {
                status = "正在使用";
            } else if ("1".equals(statusInput)) {
                status = "正在使用";
            } else if ("2".equals(statusInput)) {
                status = "使用历史";
            } else {
                status = statusInput;
            }

            List<BedUsageDetail> results = service.multiConditionSearch(name, date, status);

            System.out.println();
            if (results.isEmpty()) {
                System.out.println("未查询到符合条件的记录。");
            } else {
                System.out.println("共查询到 " + results.size() + " 条记录:");
                System.out.println("----------------------------------------------------------------------");
                System.out.printf("%-6s %-8s %-10s %-8s %-12s %-12s %-10s%n",
                        "ID", "客户名", "床位号", "房间号", "入住时间", "结束时间", "状态");
                System.out.println("----------------------------------------------------------------------");
                for (BedUsageDetail d : results) {
                    String endTime = d.getEndTime() == null ? "--" : d.getEndTime();
                    System.out.printf("%-6s %-8s %-10s %-8s %-12s %-12s %-10s%n",
                            d.getId(), d.getCustomerName(), d.getBedNumber(),
                            d.getRoomNumber(), d.getStartTime(), endTime, d.getStatus());
                }
                System.out.println("----------------------------------------------------------------------");
            }

            System.out.println();
            System.out.print("是否继续查询？(y/n): ");
            String cont = sc.nextLine().trim();
            if (!"y".equalsIgnoreCase(cont)) {
                return;
            }
        }
    }
}
