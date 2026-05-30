package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.service.BedUsageDetailService;
import com.neuedu.workpart.view.IMenu;

import java.util.Scanner;

public class BedUsageUpdateMenu implements IMenu {

    public void execute() {
        BedUsageDetailService service = new BedUsageDetailService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("========== 修改床位使用结束时间 ==========");
            System.out.print("请输入要修改的记录ID（输入0返回）: ");
            String id = sc.nextLine().trim();
            if ("0".equals(id)) return;

            BedUsageDetail detail = service.findById(id);
            if (detail == null) {
                System.out.println("未找到该记录，请检查ID。");
                continue;
            }

            System.out.println("当前记录信息:");
            System.out.println("  客户名: " + detail.getCustomerName());
            System.out.println("  床位号: " + detail.getBedNumber());
            System.out.println("  房间号: " + detail.getRoomNumber());
            System.out.println("  入住时间: " + detail.getStartTime());
            System.out.println("  结束时间: " + (detail.getEndTime() == null ? "（未设置）" : detail.getEndTime()));
            System.out.println("  状态: " + detail.getStatus());

            System.out.println();
            System.out.print("请输入新的结束时间（格式2026-05-27）: ");
            String newEndTime = sc.nextLine().trim();

            if (newEndTime.isEmpty()) {
                System.out.println("结束时间不能为空。");
                continue;
            }

            detail.setEndTime(newEndTime);
            if ("正在使用".equals(detail.getStatus()) && newEndTime != null && !newEndTime.isEmpty()) {
                detail.setStatus("使用历史");
                System.out.println("注意: 设置了结束时间，状态将自动变更为'使用历史'。");
            }

            boolean ok = service.updateDetail(detail);
            System.out.println(ok ? "修改成功。" : "修改失败。");

            System.out.print("是否继续修改？(y/n): ");
            if (!"y".equalsIgnoreCase(sc.nextLine().trim())) {
                return;
            }
        }
    }
}
