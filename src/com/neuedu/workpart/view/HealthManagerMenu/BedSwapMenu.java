package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.service.BedService;
import com.neuedu.workpart.service.BedUsageDetailService;
import com.neuedu.workpart.service.CustomerService;
import com.neuedu.workpart.view.IMenu;

import java.time.LocalDate;
import java.util.*;

public class BedSwapMenu implements IMenu {

    public void execute() {
        BedService bedService = new BedService();
        CustomerService customerService = new CustomerService();
        BedUsageDetailService detailService = new BedUsageDetailService();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println();
            System.out.println("========== 床位调换 ==========");

            // ---- 1. 查找客户 ----
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

            // ---- 2. 显示客户当前床位 ----
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

            // ---- 3. 按楼层分组显示房间 ----
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

            // ---- 4. 显示目标房间的空闲床位 ----
            List<Bed> roomBeds = bedService.findByRoomNumber(targetRoom);
            List<Bed> vacantBeds = new ArrayList<>();
            for (Bed b : roomBeds) {
                if ("空闲".equals(b.getStatus())) {
                    vacantBeds.add(b);
                }
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

            // ---- 5. 确认调换 ----
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

            // ---- 6. 执行调换 ----
            String today = LocalDate.now().toString();
            Bed oldBed = bedService.findByBedNumber(activeDetail.getBedNumber());

            // 6a. 旧使用详情失效
            activeDetail.setEndTime(today);
            activeDetail.setStatus("使用历史");
            detailService.updateDetail(activeDetail);

            // 6b. 创建新使用详情
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

            // 6c. 更新旧床位为空闲
            if (oldBed != null) {
                oldBed.setStatus("空闲");
                bedService.updateBed(oldBed);
            }

            // 6d. 更新新床位为有人
            targetBed.setStatus("有人");
            bedService.updateBed(targetBed);

            // 6e. 更新客户信息
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
            if (!"y".equalsIgnoreCase(sc.nextLine().trim())) {
                return;
            }
        }
    }
}
