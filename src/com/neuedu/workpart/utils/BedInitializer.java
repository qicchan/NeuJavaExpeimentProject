package com.neuedu.workpart.utils;

import com.neuedu.workpart.dao.BedDao;
import com.neuedu.workpart.dao.BedUsageDetailDao;
import com.neuedu.workpart.dao.CustomerDao;
import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.pojo.Customer;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BedInitializer {

    private static final String BUILDING = "606";
    private static final int[] ROOMS_PER_FLOOR = {101, 102, 103, 104, 105};
    private static final int BEDS_PER_ROOM = 3;

    // 名字由控制台输入，不再硬编码
    private static final String[] GENDERS = {"男", "女"};
    private static final String[] BLOOD_TYPES = {"A", "B", "O", "AB"};
    private static final String[] ELDERLY_TYPES = {"自理老人", "护理老人"};

    public static void initAll() {
        BedDao bedDao = new BedDao();
        CustomerDao customerDao = new CustomerDao();
        BedUsageDetailDao detailDao = new BedUsageDetailDao();

        // ---- 1. 初始化床位 ----
        if (bedDao.findAll().isEmpty()) {
            initBeds(bedDao);
        } else {
            System.out.println("床位数据已存在，共 " + bedDao.findAll().size() + " 张。");
        }

        // ---- 2. 初始化客户 ----
        if (customerDao.findAll().isEmpty()) {
            initCustomers(customerDao);
        } else {
            System.out.println("客户数据已存在，共 " + customerDao.findAll().size() + " 人。");
        }

        // ---- 3. 初始化床位使用详情 ----
        if (detailDao.findAll().isEmpty()) {
            initUsageDetails(bedDao, customerDao, detailDao);
        } else {
            System.out.println("床位使用详情已存在，共 " + detailDao.findAll().size() + " 条。");
        }
    }

    private static void initBeds(BedDao bedDao) {
        Random rand = new Random();
        String[] statuses = {"空闲", "有人", "外出"};
        double[] weights = {0.3, 0.5, 0.2};
        int count = 0;

        for (int floor = 1; floor <= 3; floor++) {
            for (int roomBase : ROOMS_PER_FLOOR) {
                int roomNumber = floor * 100 + (roomBase % 100);
                for (int b = 1; b <= BEDS_PER_ROOM; b++) {
                    Bed bed = new Bed();
                    bed.setBuilding(BUILDING);
                    bed.setRoomNumber(String.valueOf(roomNumber));
                    bed.setBedNumber(roomNumber + "-" + b);
                    double r = rand.nextDouble();
                    if (r < weights[0]) bed.setStatus(statuses[0]);
                    else if (r < weights[0] + weights[1]) bed.setStatus(statuses[1]);
                    else bed.setStatus(statuses[2]);
                    try {
                        bedDao.addBed(bed);
                        count++;
                    } catch (IOException e) {
                        System.err.println("添加床位失败: " + bed.getBedNumber());
                    }
                }
            }
        }
        System.out.println("床位数据初始化完成，共 " + count + " 张。");
    }

    /**
     * 从控制台读取客户名字，每行输入一个姓名，输入空行结束。
     * @return 名字列表
     */
    private static List<String> readNamesFromConsole() {
        List<String> names = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入客户姓名（每行一个，输入空行结束）：");
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                break;
            }
            names.add(line);
        }
        return names;
    }

    private static void initCustomers(CustomerDao customerDao) {
        List<String> names = readNamesFromConsole();
        if (names.isEmpty()) {
            System.out.println("未输入任何名字，跳过客户初始化。");
            return;
        }

        Random rand = new Random();
        String[] pastDates = {"2025-03-15", "2025-06-20", "2025-09-10", "2025-11-05", "2025-12-01",
                             "2026-01-10", "2026-02-14", "2026-03-01", "2026-03-20", "2026-04-05",
                             "2026-04-15", "2026-04-28", "2026-05-01", "2026-05-08", "2026-05-12"};

        for (int i = 0; i < names.size(); i++) {
            String fullName = names.get(i);
            Customer c = new Customer();
            c.setName(fullName);
            c.setAge(65 + rand.nextInt(25));
            c.setGender(GENDERS[rand.nextInt(2)]);
            c.setIdNumber("32010" + (1950 + rand.nextInt(30)) + "0101" + String.format("%04d", i));
            c.setBloodType(BLOOD_TYPES[rand.nextInt(4)]);
            // 从输入的名字中随机取一个姓作为家属姓氏
            String randomName = names.get(rand.nextInt(names.size()));
            String surname = randomName.substring(0, 1);
            c.setFamilyMember(surname + (rand.nextBoolean() ? "先生" : "女士"));
            c.setContactPhone("138" + String.format("%08d", rand.nextInt(100000000)));
            c.setBuilding(BUILDING);
            c.setElderlyType(ELDERLY_TYPES[rand.nextInt(2)]);
            c.setStatus("正常");

            if (i < pastDates.length) {
                c.setCheckInTime(pastDates[i]);
            } else {
                c.setCheckInTime(pastDates[pastDates.length - 1]);
            }
            c.setContractExpireTime("2027-" + String.format("%02d", 1 + rand.nextInt(12)) + "-" + String.format("%02d", 1 + rand.nextInt(28)));

            try {
                customerDao.addCustomer(c);
            } catch (IOException e) {
                System.err.println("添加客户失败: " + c.getName());
            }
        }
        System.out.println("客户数据初始化完成，共 " + names.size() + " 人。");
    }

    private static void initUsageDetails(BedDao bedDao, CustomerDao customerDao, BedUsageDetailDao detailDao) {
        List<Customer> customers = customerDao.findAll();
        List<Bed> allBeds = bedDao.findAll();
        List<Bed> vacantBeds = new ArrayList<>();
        for (Bed b : allBeds) {
            if ("空闲".equals(b.getStatus())) vacantBeds.add(b);
        }

        Random rand = new Random();
        int assignCount = Math.min(10, Math.min(customers.size(), vacantBeds.size()));
        String today = LocalDate.now().toString();

        for (int i = 0; i < assignCount; i++) {
            Customer customer = customers.get(i);
            Bed bed = vacantBeds.get(i);

            // 创建正在使用的详情
            BedUsageDetail detail = new BedUsageDetail();
            detail.setCustomerId(customer.getId());
            detail.setCustomerName(customer.getName());
            detail.setBedNumber(bed.getBedNumber());
            detail.setRoomNumber(bed.getRoomNumber());
            detail.setBuilding(BUILDING);
            detail.setStartTime(customer.getCheckInTime());
            detail.setEndTime(null);
            detail.setStatus("正在使用");

            try {
                detailDao.addDetail(detail);
            } catch (IOException e) {
                System.err.println("添加使用详情失败: " + customer.getName());
                continue;
            }

            // 更新床位状态为有人
            bed.setStatus("有人");
            bedDao.updateBed(bed);

            // 更新客户房间床位信息
            customer.setRoomNumber(bed.getRoomNumber());
            customer.setBedNumber(bed.getBedNumber());
            customer.setBuilding(BUILDING);
            customerDao.updateCustomer(customer);
        }

        // 为前3个客户额外创建使用历史记录
        for (int i = 0; i < Math.min(3, assignCount); i++) {
            Customer customer = customers.get(i);
            Bed oldBed = vacantBeds.get(assignCount + i < vacantBeds.size() ? assignCount + i : i);

            BedUsageDetail history = new BedUsageDetail();
            history.setCustomerId(customer.getId());
            history.setCustomerName(customer.getName());
            history.setBedNumber(oldBed.getBedNumber());
            history.setRoomNumber(oldBed.getRoomNumber());
            history.setBuilding(BUILDING);
            history.setStartTime("2025-01-01");
            history.setEndTime(customer.getCheckInTime());
            history.setStatus("使用历史");

            try {
                detailDao.addDetail(history);
            } catch (IOException e) {
                System.err.println("添加历史详情失败: " + customer.getName());
            }
        }

        System.out.println("床位使用详情初始化完成，共 " + detailDao.findAll().size() + " 条。");
    }

    public static void initBeds() {
        initAll();
    }

    public static void main(String[] args) {
        initAll();
    }
}
