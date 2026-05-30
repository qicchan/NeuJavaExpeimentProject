package com.neuedu.workpart.view;

import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.service.BedService;
import com.neuedu.workpart.service.BedUsageDetailService;
import com.neuedu.workpart.service.CustomerService;

import java.time.LocalDate;
import java.util.*;

public class BedMenu implements IMenu {

    private final BedService bedService = new BedService();
    private final CustomerService customerService = new CustomerService();
    private final BedUsageDetailService detailService = new BedUsageDetailService();
    private final Scanner sc = new Scanner(System.in);

    public void execute() {
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
                case "1": bedDiagram(); break;
                case "2": bedSwap(); break;
                case "3": bedUsageQuery(); break;
                case "4": bedUsageUpdate(); break;
                case "0": return;
                default: System.out.println("无效选择，请重新输入。");
            }
        }
    }

    // ==================== 1. 床位整体信息图 ====================
    private void bedDiagram() {
        List<Bed> allBeds = bedService.findAll();

        if (allBeds.isEmpty()) {
            System.out.println("暂无床位数据，请先初始化床位。");
            return;
        }

        int total = allBeds.size();
        long vacant = allBeds.stream().filter(b -> "空闲".equals(b.getStatus())).count();
        long occupied = allBeds.stream().filter(b -> "有人".equals(b.getStatus())).count();
        long out = allBeds.stream().filter(b -> "外出".equals(b.getStatus())).count();

        System.out.println();
        System.out.println("========== 床位整体使用信息 ==========");
        System.out.println("总床位数:   " + total + " 张");
        System.out.println("空闲床位:   " + vacant + " 张");
        System.out.println("外出床位:   " + out + " 张");
        System.out.println("有人床位:   " + occupied + " 张");
        System.out.println("======================================");

        Map<Integer, Map<String, List<Bed>>> floorMap = new TreeMap<>();
        for (Bed bed : allBeds) {
            try {
                int roomNum = Integer.parseInt(bed.getRoomNumber());
                int floor = roomNum / 100;
                floorMap.putIfAbsent(floor, new LinkedHashMap<>());
                floorMap.get(floor).putIfAbsent(bed.getRoomNumber(), new ArrayList<>());
                floorMap.get(floor).get(bed.getRoomNumber()).add(bed);
            } catch (NumberFormatException ignored) {}
        }

        int floor;
        while (true) {
            System.out.println();
            System.out.println("请输入要查询的楼层（默认1楼，输入0返回）:");
            System.out.println("可选楼层: " + floorMap.keySet());
            System.out.print("> ");

            String input = sc.nextLine().trim();
            if (input.isEmpty()) {
                floor = 1;
            } else {
                try {
                    floor = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    System.out.println("输入无效，请输入数字。");
                    continue;
                }
            }

            if (floor == 0) return;

            Map<String, List<Bed>> rooms = floorMap.get(floor);
            if (rooms == null || rooms.isEmpty()) {
                System.out.println("该楼层暂无床位数据。");
                continue;
            }

            String building = allBeds.get(0).getBuilding();
            System.out.println();
            System.out.println("========== " + building + "栋 " + floor + "楼 床位列表 ==========");

            for (Map.Entry<String, List<Bed>> roomEntry : rooms.entrySet()) {
                String room = roomEntry.getKey();
                List<Bed> beds = roomEntry.getValue();
                beds.sort(Comparator.comparing(Bed::getBedNumber));

                System.out.println("【" + room + "室】共 " + beds.size() + " 张床:");
                for (Bed bed : beds) {
                    System.out.println("    床位号: " + bed.getBedNumber() + "  状态: " + bed.getStatus());
                }
            }
            System.out.println("==========================================");
        }
    }

    // ==================== 2. 床位调换 ====================
    private void bedSwap() {
        while (true) {
            System.out.println();
            System.out.println("========== 床位调换 ==========");

            System.out.print("请输入客户姓名或ID（输入0返回）: ");
            String input = sc.nextLine().trim();
            if ("0".equals(input)) return;

            Customer customer = customerService.findById(input);
            if (customer == null) {
                List<Customer> matches = customerService.findByName(input);
                if (matches.isEmpty()) {
                    System.out.println("未找到该客户，请重试。");
                    continue;
                } else if (matches.size() == 1) {
                    customer = matches.get(0);
                } else {
                    System.out.println("找到多个匹配客户:");
                    for (int i = 0; i < matches.size(); i++) {
                        Customer c = matches.get(i);
                        System.out.println("  " + (i + 1) + ". ID:" + c.getId() + " 姓名:" + c.getName());
                    }
                    System.out.print("请选择序号: ");
                    int idx;
                    try {
                        idx = Integer.parseInt(sc.nextLine().trim()) - 1;
                        if (idx < 0 || idx >= matches.size()) {
                            System.out.println("无效选择。");
                            continue;
                        }
                        customer = matches.get(idx);
                    } catch (NumberFormatException e) {
                        System.out.println("无效输入。");
                        continue;
                    }
                }
            }

            BedUsageDetail activeDetail = detailService.findActiveByCustomerId(customer.getId());
            if (activeDetail == null) {
                System.out.println("客户【" + customer.getName() + "】当前无正在使用的床位，无法调换。");
                continue;
            }

            System.out.println();
            System.out.println("客户【" + customer.getName() + "】当前床位信息:");
            System.out.println("  楼栋: " + activeDetail.getBuilding());
            System.out.println("  房间号: " + activeDetail.getRoomNumber());
            System.out.println("  床位号: " + activeDetail.getBedNumber());
            System.out.println("  入住时间: " + activeDetail.getStartTime());

            List<Bed> allBeds = bedService.findAll();
            Map<Integer, Set<String>> floorRooms = new TreeMap<>();
            for (Bed b : allBeds) {
                try {
                    int roomNum = Integer.parseInt(b.getRoomNumber());
                    int floor = roomNum / 100;
                    floorRooms.putIfAbsent(floor, new LinkedHashSet<>());
                    floorRooms.get(floor).add(b.getRoomNumber());
                } catch (NumberFormatException ignored) {}
            }

            System.out.println();
            System.out.println("请选择目标房间:");
            List<String> roomList = new ArrayList<>();
            for (Map.Entry<Integer, Set<String>> entry : floorRooms.entrySet()) {
                System.out.println("  [" + entry.getKey() + "楼]");
                List<String> sorted = new ArrayList<>(entry.getValue());
                Collections.sort(sorted);
                for (String room : sorted) {
                    roomList.add(room);
                    System.out.println("    " + roomList.size() + ". " + room + "室");
                }
            }
            System.out.print("请输入序号（输入0取消）: ");
            int roomIdx;
            try {
                roomIdx = Integer.parseInt(sc.nextLine().trim());
                if (roomIdx == 0) continue;
                if (roomIdx < 1 || roomIdx > roomList.size()) {
                    System.out.println("无效选择。");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("无效输入。");
                continue;
            }

            String targetRoom = roomList.get(roomIdx - 1);
            List<Bed> roomBeds = bedService.findByRoomNumber(targetRoom);
            List<Bed> vacantBeds = new ArrayList<>();
            for (Bed b : roomBeds) {
                if ("空闲".equals(b.getStatus())) vacantBeds.add(b);
            }

            if (vacantBeds.isEmpty()) {
                System.out.println("房间 " + targetRoom + " 当前无空闲床位。");
                continue;
            }

            System.out.println();
            System.out.println("房间 " + targetRoom + " 的空闲床位:");
            for (int i = 0; i < vacantBeds.size(); i++) {
                System.out.println("  " + (i + 1) + ". 床位号: " + vacantBeds.get(i).getBedNumber());
            }
            System.out.print("请选择床位序号（输入0取消）: ");
            int bedIdx;
            try {
                bedIdx = Integer.parseInt(sc.nextLine().trim());
                if (bedIdx == 0) continue;
                if (bedIdx < 1 || bedIdx > vacantBeds.size()) {
                    System.out.println("无效选择。");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("无效输入。");
                continue;
            }

            Bed targetBed = vacantBeds.get(bedIdx - 1);

            System.out.println();
            System.out.println("========== 调换确认 ==========");
            System.out.println("客户: " + customer.getName());
            System.out.println("原床位: " + activeDetail.getRoomNumber() + "室 " + activeDetail.getBedNumber());
            System.out.println("新床位: " + targetRoom + "室 " + targetBed.getBedNumber());
            System.out.print("确认调换？(y/n): ");
            if (!"y".equalsIgnoreCase(sc.nextLine().trim())) {
                System.out.println("已取消调换。");
                continue;
            }

            String today = LocalDate.now().toString();
            Bed oldBed = bedService.findByBedNumber(activeDetail.getBedNumber());

            activeDetail.setEndTime(today);
            activeDetail.setStatus("使用历史");
            detailService.updateDetail(activeDetail);

            BedUsageDetail newDetail = new BedUsageDetail();
            newDetail.setCustomerId(customer.getId());
            newDetail.setCustomerName(customer.getName());
            newDetail.setBedNumber(targetBed.getBedNumber());
            newDetail.setRoomNumber(targetRoom);
            newDetail.setBuilding("606");
            newDetail.setStartTime(today);
            newDetail.setEndTime(null);
            newDetail.setStatus("正在使用");
            detailService.addDetail(newDetail);

            if (oldBed != null) {
                oldBed.setStatus("空闲");
                bedService.updateBed(oldBed);
            }

            targetBed.setStatus("有人");
            bedService.updateBed(targetBed);

            customer.setRoomNumber(targetRoom);
            customer.setBedNumber(targetBed.getBedNumber());
            customer.setBuilding("606");
            customerService.updateCustomer(customer);

            System.out.println();
            System.out.println("床位调换成功！");
            System.out.println("客户【" + customer.getName() + "】已从 " +
                    activeDetail.getRoomNumber() + "室" + activeDetail.getBedNumber() +
                    " 调换至 " + targetRoom + "室" + targetBed.getBedNumber());

            System.out.println();
            System.out.print("是否继续调换？(y/n): ");
            if (!"y".equalsIgnoreCase(sc.nextLine().trim())) return;
        }
    }

    // ==================== 3. 查询客户床位使用详情 ====================
    private void bedUsageQuery() {
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

            List<BedUsageDetail> results = detailService.multiConditionSearch(name, date, status);

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
            if (!"y".equalsIgnoreCase(sc.nextLine().trim())) return;
        }
    }

    // ==================== 4. 修改床位使用结束时间 ====================
    private void bedUsageUpdate() {
        while (true) {
            System.out.println();
            System.out.println("========== 修改床位使用结束时间 ==========");
            System.out.print("请输入要修改的记录ID（输入0返回）: ");
            String id = sc.nextLine().trim();
            if ("0".equals(id)) return;

            BedUsageDetail detail = detailService.findById(id);
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

            boolean ok = detailService.updateDetail(detail);
            System.out.println(ok ? "修改成功。" : "修改失败。");

            System.out.print("是否继续修改？(y/n): ");
            if (!"y".equalsIgnoreCase(sc.nextLine().trim())) return;
        }
    }
}
