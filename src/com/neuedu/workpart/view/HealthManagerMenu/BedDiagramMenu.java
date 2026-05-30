package com.neuedu.workpart.view.HealthManagerMenu;

import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.service.BedService;
import com.neuedu.workpart.view.IMenu;

import java.util.*;

public class BedDiagramMenu implements IMenu {

    public void execute() {
        BedService bedService = new BedService();
        List<Bed> allBeds = bedService.findAll();

        if (allBeds.isEmpty()) {
            System.out.println("暂无床位数据，请先初始化床位。");
            return;
        }

        // ========== 1. 统计面板 ==========
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

        // ========== 2. 按楼层查询房间及床位 ==========
        // 按楼层分组: floor -> { roomNumber -> [beds] }
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

        Scanner sc = new Scanner(System.in);
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

            if (floor == 0) {
                return;
            }

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
}
