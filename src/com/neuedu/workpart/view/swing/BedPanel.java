package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.service.BedService;
import com.neuedu.workpart.service.BedUsageDetailService;
import com.neuedu.workpart.service.ResidentCustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;
import java.util.List;

public class BedPanel extends JPanel {
    private final BedService service = new BedService();
    private final BedUsageDetailService detailService = new BedUsageDetailService();
    private final ResidentCustomerService customerService = new ResidentCustomerService();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public BedPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("床位管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"编号", "客户姓名", "床位号", "房间号", "楼栋", "状态"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(0, 4, 8, 8));
        JButton refreshBtn = new JButton("刷新列表");
        JButton searchRoomBtn = new JButton("按房间查询");
        JButton searchStatusBtn = new JButton("按状态查询");
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("修改状态/所有人");
        JButton delBtn = new JButton("删除");
        JButton diagramBtn = new JButton("床位总览");
        JButton swapBtn = new JButton("床位调换");
        JButton usageQueryBtn = new JButton("使用查询");
        JButton usageUpdateBtn = new JButton("使用更新");
        JButton showAllBtn = new JButton("显示全部");
        JButton backBtn = new JButton("返回");

        btnPanel.add(refreshBtn);    btnPanel.add(searchRoomBtn);   btnPanel.add(searchStatusBtn); btnPanel.add(addBtn);
        btnPanel.add(editBtn);       btnPanel.add(delBtn);          btnPanel.add(diagramBtn);      btnPanel.add(swapBtn);
        btnPanel.add(usageQueryBtn); btnPanel.add(usageUpdateBtn);  btnPanel.add(showAllBtn);      btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshTable());
        searchRoomBtn.addActionListener(e -> searchByRoom());
        searchStatusBtn.addActionListener(e -> searchByStatus());
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        delBtn.addActionListener(e -> deleteSelected());
        diagramBtn.addActionListener(e -> showDiagram());
        swapBtn.addActionListener(e -> showSwapDialog());
        usageQueryBtn.addActionListener(e -> showUsageQuery());
        usageUpdateBtn.addActionListener(e -> showUsageUpdate());
        showAllBtn.addActionListener(e -> refreshTable());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }

    private String findCustomerIdByBed(String bedNumber) {
        // 1. 先从 BedUsageDetail 查找
        for (BedUsageDetail d : detailService.findAll()) {
            if ("正在使用".equals(d.getStatus()) && bedNumber.equals(d.getBedNumber())) {
                return d.getCustomerId();
            }
        }
        // 2. 兜底：从 ResidentCustomer 查找
        ResidentCustomer rc = findResidentCustomerByBedNumber(bedNumber);
        if (rc != null) return String.valueOf(rc.getId());
        return "--";
    }

    private String findCustomerNameByBed(String bedNumber) {
        // 1. 先从 BedUsageDetail 查找
        for (BedUsageDetail d : detailService.findAll()) {
            if ("正在使用".equals(d.getStatus()) && bedNumber.equals(d.getBedNumber())) {
                return d.getCustomerName();
            }
        }
        // 2. 兜底：从 ResidentCustomer 查找
        ResidentCustomer rc = findResidentCustomerByBedNumber(bedNumber);
        if (rc != null) return rc.getCustomer_name();
        return "--";
    }

    /**
     * 兜底方法：当 BedUsageDetail 中找不到记录时，
     * 通过床位号找到床位，再匹配 ResidentCustomer 的 room_no / bed_id
     */
    private ResidentCustomer findResidentCustomerByBedNumber(String bedNumber) {
        Bed bed = service.findByBedNumber(bedNumber);
        if (bed == null) return null;
        for (ResidentCustomer c : customerService.findAllCustomers()) {
            // 通过 bed_id 精确匹配
            if (c.getBed_id() != null && String.valueOf(c.getBed_id()).equals(bed.getId())) {
                return c;
            }
        }
        for (ResidentCustomer c : customerService.findAllCustomers()) {
            // 通过 room_no + building_no 匹配
            if (c.getRoom_no() != null && c.getRoom_no().equals(bed.getRoomNumber())
                    && c.getBuilding_no() != null && c.getBuilding_no().equals(bed.getBuilding())) {
                return c;
            }
        }
        return null;
    }

    private Object[] toRow(Bed bed) {
        // "有人"和"外出"都表示床位被占用，应显示客户信息
        boolean occupied = "有人".equals(bed.getStatus()) || "外出".equals(bed.getStatus());
        String customerId = occupied ? findCustomerIdByBed(bed.getBedNumber()) : "--";
        String customerName = occupied ? findCustomerNameByBed(bed.getBedNumber()) : "--";
        return new Object[]{customerId, customerName, bed.getBedNumber(), bed.getRoomNumber(), bed.getBuilding(), bed.getStatus()};
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (Bed bed : service.findAll()) {
            // 自动修复：床位被占用（"有人"/"外出"）但没有对应的使用详情，尝试补建
            boolean occupied = "有人".equals(bed.getStatus()) || "外出".equals(bed.getStatus());
            if (occupied) {
                boolean hasActiveDetail = false;
                for (BedUsageDetail d : detailService.findAll()) {
                    if ("正在使用".equals(d.getStatus()) && bed.getBedNumber().equals(d.getBedNumber())) {
                        hasActiveDetail = true;
                        break;
                    }
                }
                if (!hasActiveDetail) {
                    ResidentCustomer rc = findResidentCustomerByBedNumber(bed.getBedNumber());
                    if (rc != null) {
                        BedUsageDetail newDetail = new BedUsageDetail();
                        newDetail.setCustomerId(String.valueOf(rc.getId()));
                        newDetail.setCustomerName(rc.getCustomer_name());
                        newDetail.setBedNumber(bed.getBedNumber());
                        newDetail.setRoomNumber(bed.getRoomNumber());
                        newDetail.setBuilding(bed.getBuilding() != null ? bed.getBuilding() : "606");
                        newDetail.setStartTime(rc.getCheckin_date() != null ? rc.getCheckin_date() : LocalDate.now().toString());
                        newDetail.setEndTime(null);
                        newDetail.setStatus("正在使用");
                        detailService.addDetail(newDetail);
                    }
                }
            }
            tableModel.addRow(toRow(bed));
        }
    }

    private void searchByRoom() {
        String room = JOptionPane.showInputDialog(this, "请输入房间号：", "查询", JOptionPane.QUESTION_MESSAGE);
        if (room == null || room.trim().isEmpty()) return;
        List<Bed> list = service.findByRoomNumber(room.trim());
        tableModel.setRowCount(0);
        for (Bed bed : list) {
            tableModel.addRow(toRow(bed));
        }
        if (list.isEmpty()) JOptionPane.showMessageDialog(this, "未找到该房间的床位");
    }

    private void searchByStatus() {
        String[] options = {"空闲", "有人", "外出"};
        String status = (String) JOptionPane.showInputDialog(this, "请选择状态：", "查询",
                JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (status == null) return;
        List<Bed> list = service.findByStatus(status);
        tableModel.setRowCount(0);
        for (Bed bed : list) {
            tableModel.addRow(toRow(bed));
        }
        if (list.isEmpty()) JOptionPane.showMessageDialog(this, "没有" + status + "状态的床位");
    }

    private void showAddDialog() {
        JTextField bedNumField = new JTextField(15);
        JTextField roomField = new JTextField(15);
        JTextField buildingField = new JTextField("606", 15);
        String[] statuses = {"空闲", "有人", "外出"};
        JComboBox<String> statusBox = new JComboBox<>(statuses);

        List<ResidentCustomer> customers = customerService.findAllCustomers();
        String[] customerOptions;
        if (customers.isEmpty()) {
            customerOptions = new String[]{"暂无客户，请先添加客户"};
        } else {
            customerOptions = new String[customers.size()];
            for (int i = 0; i < customers.size(); i++) {
                ResidentCustomer c = customers.get(i);
                customerOptions[i] = c.getCustomer_name() + " (ID:" + c.getId() + ")";
            }
        }
        JComboBox<String> customerBox = new JComboBox<>(customerOptions);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 8));
        panel.add(new JLabel("床位号："));     panel.add(bedNumField);
        panel.add(new JLabel("房间号："));     panel.add(roomField);
        panel.add(new JLabel("楼栋："));       panel.add(buildingField);
        panel.add(new JLabel("状态："));       panel.add(statusBox);
        panel.add(new JLabel("选择客户："));   panel.add(customerBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加床位", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String bedNum = bedNumField.getText().trim();
            String room = roomField.getText().trim();
            if (bedNum.isEmpty() || room.isEmpty()) {
                JOptionPane.showMessageDialog(this, "床位号和房间号不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String selectedStatus = (String) statusBox.getSelectedItem();
            Bed bed = new Bed();
            bed.setBedNumber(bedNum);
            bed.setRoomNumber(room);
            bed.setBuilding(buildingField.getText().trim());
            bed.setStatus(selectedStatus);
            service.addBed(bed);

            if ("有人".equals(selectedStatus)) {
                if (!customers.isEmpty()) {
                    int selectedIndex = customerBox.getSelectedIndex();
                    ResidentCustomer selectedCustomer = customers.get(selectedIndex);
                    BedUsageDetail detail = new BedUsageDetail();
                    detail.setCustomerId(String.valueOf(selectedCustomer.getId()));
                    detail.setCustomerName(selectedCustomer.getCustomer_name());
                    detail.setBedNumber(bedNum);
                    detail.setRoomNumber(room);
                    detail.setBuilding(buildingField.getText().trim());
                    detail.setStartTime(java.time.LocalDate.now().toString());
                    detail.setStatus("正在使用");
                    detailService.addDetail(detail);
                }
            }
            JOptionPane.showMessageDialog(this, "添加成功");
            refreshTable();
        }
    }

    private Bed findBedByBuildingRoomBed() {
        JTextField buildingField = new JTextField("", 10);
        JTextField roomField = new JTextField(10);
        JTextField bedField = new JTextField(10);
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 8));
        panel.add(new JLabel("楼栋号：")); panel.add(buildingField);
        panel.add(new JLabel("房间号：")); panel.add(roomField);
        panel.add(new JLabel("床位号：")); panel.add(bedField);

        int result = JOptionPane.showConfirmDialog(this, panel, "查找床位", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return null;

        String building = buildingField.getText().trim();
        String room = roomField.getText().trim();
        String bed = bedField.getText().trim();
        if (building.isEmpty() || room.isEmpty() || bed.isEmpty()) {
            JOptionPane.showMessageDialog(this, "楼栋、房间号、床位号不能为空");
            return null;
        }

        for (Bed b : service.findAll()) {
            if (building.equals(b.getBuilding()) && room.equals(b.getRoomNumber()) && bed.equals(b.getBedNumber())) {
                return b;
            }
        }
        JOptionPane.showMessageDialog(this, "未找到该床位", "提示", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    private void showEditDialog() {
        Bed bed = findBedByBuildingRoomBed();
        if (bed == null) return;

        // 获取当前床位的使用详情
        BedUsageDetail currentDetail = null;
        if ("有人".equals(bed.getStatus())) {
            currentDetail = detailService.findByBuildingRoomBed(bed.getBuilding(), bed.getRoomNumber(), bed.getBedNumber());
        }

        // 状态选择 - 显示详细床位信息
        String[] options = {"空闲", "有人", "外出"};
        String newStatus = (String) JOptionPane.showInputDialog(this,
                "楼栋：" + bed.getBuilding() + "  房间：" + bed.getRoomNumber() + "  床位：" + bed.getBedNumber()
                        + "\n当前状态：" + bed.getStatus()
                        + (currentDetail != null ? "\n当前使用人：" + currentDetail.getCustomerName() + "（" + currentDetail.getCustomerId() + "）" : "")
                        + "\n\n请选择新状态：",
                "修改状态/所有人", JOptionPane.QUESTION_MESSAGE, null, options, bed.getStatus());
        if (newStatus == null) return;

        // 如果状态不变且不是"有人"，无需修改
        if (newStatus.equals(bed.getStatus()) && !"有人".equals(newStatus)) {
            JOptionPane.showMessageDialog(this, "状态未改变", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String customerId = null;
        String customerName = null;

        if ("有人".equals(newStatus)) {
            // 状态改为"有人"，需要设置所有人
            JTextField cidField = new JTextField(currentDetail != null ? currentDetail.getCustomerId() : "", 15);
            JTextField cnameField = new JTextField(currentDetail != null ? currentDetail.getCustomerName() : "", 15);

            JPanel customerPanel = new JPanel(new GridLayout(2, 2, 5, 8));
            customerPanel.add(new JLabel("客户编号："));
            customerPanel.add(cidField);
            customerPanel.add(new JLabel("客户姓名："));
            customerPanel.add(cnameField);

            int result = JOptionPane.showConfirmDialog(this, customerPanel, "设置所有人信息",
                    JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result != JOptionPane.OK_OPTION) return;

            customerId = cidField.getText().trim();
            customerName = cnameField.getText().trim();
            if (customerId.isEmpty() || customerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "客户编号和姓名不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 处理使用详情：结束旧记录，创建新记录
            String today = LocalDate.now().toString();

            // 如果之前是"有人"状态，结束旧的usage detail
            if (currentDetail != null) {
                currentDetail.setEndTime(today);
                currentDetail.setStatus("使用历史");
                detailService.updateDetail(currentDetail);
            }

            // 如果之前是"有人"但换了人，或者从其他状态变为"有人"，创建新记录
            boolean samePerson = currentDetail != null
                    && customerId.equals(currentDetail.getCustomerId())
                    && customerName.equals(currentDetail.getCustomerName());

            if (!samePerson) {
                BedUsageDetail newDetail = new BedUsageDetail();
                newDetail.setCustomerId(customerId);
                newDetail.setCustomerName(customerName);
                newDetail.setBedNumber(bed.getBedNumber());
                newDetail.setRoomNumber(bed.getRoomNumber());
                newDetail.setBuilding(bed.getBuilding());
                newDetail.setStartTime(today);
                newDetail.setStatus("正在使用");
                detailService.addDetail(newDetail);

                // 更新客户信息
                try {
                    ResidentCustomer customer = customerService.findCustomerById(Integer.parseInt(customerId));
                    if (customer != null) {
                        customer.setRoom_no(bed.getRoomNumber());
                        customer.setBuilding_no(bed.getBuilding());
                        customer.setBed_id(Integer.parseInt(bed.getId()));
                        customerService.updateCustomer(customer);
                    }
                } catch (NumberFormatException ignored) {}
            }
        } else {
            // 状态改为"空闲"或"外出"，结束当前使用记录
            if (currentDetail != null) {
                String today = LocalDate.now().toString();
                currentDetail.setEndTime(today);
                currentDetail.setStatus("使用历史");
                detailService.updateDetail(currentDetail);

                // 清除客户床位信息
                try {
                    ResidentCustomer customer = customerService.findCustomerById(Integer.parseInt(currentDetail.getCustomerId()));
                    if (customer != null) {
                        customer.setRoom_no(null);
                        customer.setBuilding_no(null);
                        customer.setBed_id(null);
                        customerService.updateCustomer(customer);
                    }
                } catch (NumberFormatException ignored) {}
            }
        }

        bed.setStatus(newStatus);
        boolean ok = service.updateBed(bed);
        JOptionPane.showMessageDialog(this, ok ? "修改成功！状态已更新为：" + newStatus : "修改失败");
        refreshTable();
    }

    private void deleteSelected() {
        Bed bed = findBedByBuildingRoomBed();
        if (bed == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "确定要删除 " + bed.getBuilding() + "栋 " + bed.getRoomNumber() + "室 " + bed.getBedNumber() + "号床位吗？",
                "确认删除", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            long id = Long.parseLong(bed.getId());
            if (service.deleteBed(id)) {
                JOptionPane.showMessageDialog(this, "删除成功");
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "删除失败");
            }
        }
    }

    private void showDiagram() {
        List<Bed> allBeds = service.findAll();
        if (allBeds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "暂无床位数据", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        int total = allBeds.size();
        long vacant = allBeds.stream().filter(b -> "空闲".equals(b.getStatus())).count();
        long occupied = allBeds.stream().filter(b -> "有人".equals(b.getStatus())).count();
        long out = allBeds.stream().filter(b -> "外出".equals(b.getStatus())).count();

        StringBuilder sb = new StringBuilder();
        sb.append("========== 床位整体使用信息 ==========\n");
        sb.append("总床位数: ").append(total).append(" 张\n");
        sb.append("空闲床位: ").append(vacant).append(" 张\n");
        sb.append("外出床位: ").append(out).append(" 张\n");
        sb.append("有人床位: ").append(occupied).append(" 张\n");
        sb.append("======================================\n\n");

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

        for (Map.Entry<Integer, Map<String, List<Bed>>> floorEntry : floorMap.entrySet()) {
            sb.append("--- ").append(floorEntry.getKey()).append("楼 ---\n");
            for (Map.Entry<String, List<Bed>> roomEntry : floorEntry.getValue().entrySet()) {
                sb.append("  【").append(roomEntry.getKey()).append("室】");
                for (Bed bed : roomEntry.getValue()) {
                    sb.append(" ").append(bed.getBedNumber()).append("(").append(bed.getStatus()).append(")");
                }
                sb.append("\n");
            }
        }

        JTextArea textArea = new JTextArea(sb.toString());
        textArea.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        JOptionPane.showMessageDialog(this, scrollPane, "床位总览图", JOptionPane.PLAIN_MESSAGE);
    }

    private void showSwapDialog() {
        String input = JOptionPane.showInputDialog(this, "请输入客户编号或姓名：", "床位调换", JOptionPane.QUESTION_MESSAGE);
        if (input == null || input.trim().isEmpty()) return;

        ResidentCustomer customer = null;
        try {
            customer = customerService.findCustomerById(Integer.parseInt(input.trim()));
        } catch (NumberFormatException ignored) {}
        if (customer == null) {
            List<ResidentCustomer> matches = customerService.searchCustomersByName(input.trim());
            if (matches.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未找到该客户");
                return;
            }
            if (matches.size() == 1) {
                customer = matches.get(0);
            } else {
                String[] names = new String[matches.size()];
                for (int i = 0; i < matches.size(); i++) names[i] = matches.get(i).getCustomer_name() + " (ID:" + matches.get(i).getId() + ")";
                String selected = (String) JOptionPane.showInputDialog(this, "请选择客户：", "多客户匹配", JOptionPane.QUESTION_MESSAGE, null, names, names[0]);
                if (selected == null) return;
                customer = matches.get(java.util.Arrays.asList(names).indexOf(selected));
            }
        }

        if (customer.getIs_deleted() != null && customer.getIs_deleted() == 1) {
            JOptionPane.showMessageDialog(this, "该客户已退住，无法调换床位");
            return;
        }

        BedUsageDetail activeDetail = detailService.findActiveByCustomerId(String.valueOf(customer.getId()));
        if (activeDetail == null) {
            // 兜底：尝试从客户已有信息自动创建使用记录
            if (customer.getRoom_no() != null && !customer.getRoom_no().isEmpty()) {
                Bed customerBed = null;
                // 通过 bed_id 查找
                if (customer.getBed_id() != null) {
                    for (Bed b : service.findAll()) {
                        if (String.valueOf(customer.getBed_id()).equals(b.getId())) {
                            customerBed = b;
                            break;
                        }
                    }
                }
                // 通过 room_no 查找该房间内状态为"有人"的床位
                if (customerBed == null) {
                    for (Bed b : service.findByRoomNumber(customer.getRoom_no())) {
                        if ("有人".equals(b.getStatus())) {
                            customerBed = b;
                            break;
                        }
                    }
                }
                if (customerBed != null) {
                    activeDetail = new BedUsageDetail();
                    activeDetail.setCustomerId(String.valueOf(customer.getId()));
                    activeDetail.setCustomerName(customer.getCustomer_name());
                    activeDetail.setBedNumber(customerBed.getBedNumber());
                    activeDetail.setRoomNumber(customerBed.getRoomNumber());
                    activeDetail.setBuilding(customerBed.getBuilding() != null ? customerBed.getBuilding() : "606");
                    activeDetail.setStartTime(customer.getCheckin_date() != null ? customer.getCheckin_date() : LocalDate.now().toString());
                    activeDetail.setEndTime(null);
                    activeDetail.setStatus("正在使用");
                    detailService.addDetail(activeDetail);
                }
            }
        }
        if (activeDetail == null) {
            JOptionPane.showMessageDialog(this, "该客户当前无正在使用的床位，无法调换");
            return;
        }

        List<Bed> vacantBeds = service.findByStatus("空闲");
        if (vacantBeds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "当前无空闲床位");
            return;
        }

        String[] bedOptions = new String[vacantBeds.size()];
        for (int i = 0; i < vacantBeds.size(); i++) {
            Bed b = vacantBeds.get(i);
            bedOptions[i] = b.getRoomNumber() + "室 " + b.getBedNumber();
        }
        String selectedBed = (String) JOptionPane.showInputDialog(this,
                "当前床位：" + activeDetail.getRoomNumber() + "室 " + activeDetail.getBedNumber() + "\n请选择目标床位：",
                "选择目标床位", JOptionPane.QUESTION_MESSAGE, null, bedOptions, bedOptions[0]);
        if (selectedBed == null) return;
        Bed targetBed = vacantBeds.get(java.util.Arrays.asList(bedOptions).indexOf(selectedBed));

        int confirm = JOptionPane.showConfirmDialog(this,
                "确认调换？\n客户：" + customer.getCustomer_name() + "\n原床位：" + activeDetail.getRoomNumber() + "室 " + activeDetail.getBedNumber() +
                        "\n新床位：" + targetBed.getRoomNumber() + "室 " + targetBed.getBedNumber(),
                "确认调换", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;

        String today = LocalDate.now().toString();
        Bed oldBed = service.findByBedNumber(activeDetail.getBedNumber());

        activeDetail.setEndTime(today);
        activeDetail.setStatus("使用历史");
        detailService.updateDetail(activeDetail);

        BedUsageDetail newDetail = new BedUsageDetail();
        newDetail.setCustomerId(String.valueOf(customer.getId()));
        newDetail.setCustomerName(customer.getCustomer_name());
        newDetail.setBedNumber(targetBed.getBedNumber());
        newDetail.setRoomNumber(targetBed.getRoomNumber());
        newDetail.setBuilding("606");
        newDetail.setStartTime(today);
        newDetail.setEndTime(null);
        newDetail.setStatus("正在使用");
        detailService.addDetail(newDetail);

        if (oldBed != null) { oldBed.setStatus("空闲"); service.updateBed(oldBed); }
        targetBed.setStatus("有人");
        service.updateBed(targetBed);

        customer.setRoom_no(targetBed.getRoomNumber());
        customer.setBuilding_no("606");
        customerService.updateCustomer(customer);

        JOptionPane.showMessageDialog(this, "床位调换成功！");
        refreshTable();
    }

    private void showUsageQuery() {
        String name = JOptionPane.showInputDialog(this, "客户姓名（留空查询全部）：", "使用查询", JOptionPane.QUESTION_MESSAGE);
        if (name == null) return;

        String[] statusOptions = {"正在使用", "使用历史", "全部"};
        String statusChoice = (String) JOptionPane.showInputDialog(this, "请选择状态：", "使用查询",
                JOptionPane.QUESTION_MESSAGE, null, statusOptions, statusOptions[0]);
        if (statusChoice == null) return;

        String status = "全部".equals(statusChoice) ? "" : statusChoice;
        List<BedUsageDetail> results = detailService.multiConditionSearch(name.trim(), "", status);

        String[] cols = {"ID", "客户名", "床位号", "房间号", "入住时间", "结束时间", "状态"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        for (BedUsageDetail d : results) {
            String endTime = d.getEndTime() == null ? "--" : d.getEndTime();
            model.addRow(new Object[]{d.getId(), d.getCustomerName(), d.getBedNumber(), d.getRoomNumber(), d.getStartTime(), endTime, d.getStatus()});
        }
        JTable resultTable = new JTable(model);
        resultTable.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        resultTable.setRowHeight(26);
        JScrollPane scrollPane = new JScrollPane(resultTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));
        JOptionPane.showMessageDialog(this, scrollPane, "查询结果（共" + results.size() + "条）", JOptionPane.PLAIN_MESSAGE);
    }

    private void showUsageUpdate() {
        JTextField buildingField = new JTextField("", 10);
        JTextField roomField = new JTextField(10);
        JTextField bedField = new JTextField(10);
        JPanel searchPanel = new JPanel(new GridLayout(3, 2, 5, 8));
        searchPanel.add(new JLabel("楼栋号：")); searchPanel.add(buildingField);
        searchPanel.add(new JLabel("房间号：")); searchPanel.add(roomField);
        searchPanel.add(new JLabel("床位号：")); searchPanel.add(bedField);

        int searchResult = JOptionPane.showConfirmDialog(this, searchPanel, "查找床位使用记录", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (searchResult != JOptionPane.OK_OPTION) return;

        String building = buildingField.getText().trim();
        String room = roomField.getText().trim();
        String bed = bedField.getText().trim();
        if (building.isEmpty() || room.isEmpty() || bed.isEmpty()) {
            JOptionPane.showMessageDialog(this, "楼栋、房间号、床位号不能为空");
            return;
        }

        BedUsageDetail detail = detailService.findByBuildingRoomBed(building, room, bed);
        if (detail == null) {
            JOptionPane.showMessageDialog(this, "未找到该床位的正在使用记录");
            return;
        }

        boolean isActive = "正在使用".equals(detail.getStatus());
        String[] options = isActive ? new String[]{"设置结束时间并释放床位", "仅修改结束时间"} : new String[]{"修改结束时间"};
        String action = (String) JOptionPane.showInputDialog(this,
                "客户：" + detail.getCustomerName() + "\n床位：" + detail.getBedNumber()
                        + "\n房间：" + detail.getRoomNumber() + "\n当前状态：" + detail.getStatus(),
                "使用更新", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        if (action == null) return;

        JTextField endTimeField = new JTextField(LocalDate.now().toString(), 15);
        JPanel panel = new JPanel(new GridLayout(1, 2, 5, 8));
        panel.add(new JLabel("结束时间："));
        panel.add(endTimeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "设置结束时间", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result != JOptionPane.OK_OPTION) return;

        String newEndTime = endTimeField.getText().trim();
        if (newEndTime.isEmpty()) {
            JOptionPane.showMessageDialog(this, "结束时间不能为空");
            return;
        }

        detail.setEndTime(newEndTime);

        boolean freeBed = action.startsWith("设置结束时间");
        if (freeBed) {
            detail.setStatus("使用历史");
        }

        boolean ok = detailService.updateDetail(detail);
        if (ok && freeBed) {
            Bed bed1 = service.findByBedNumber(detail.getBedNumber());
            if (bed1 != null) {
                bed1.setStatus("空闲");
                service.updateBed(bed1);
            }
            try {
                ResidentCustomer customer = customerService.findCustomerById(Integer.parseInt(detail.getCustomerId()));
                if (customer != null) {
                    customer.setRoom_no(null);
                    customer.setBuilding_no(null);
                    customer.setBed_id(null);
                    customerService.updateCustomer(customer);
                }
            } catch (NumberFormatException ignored) {}
        }
        JOptionPane.showMessageDialog(this, ok ? "修改成功" : "修改失败");
        refreshTable();
    }
}
