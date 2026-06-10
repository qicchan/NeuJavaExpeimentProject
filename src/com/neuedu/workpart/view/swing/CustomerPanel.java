package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.Bed;
import com.neuedu.workpart.pojo.BedUsageDetail;
import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.service.BedService;
import com.neuedu.workpart.service.BedUsageDetailService;
import com.neuedu.workpart.service.RequirementService;
import com.neuedu.workpart.service.ResidentCustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class CustomerPanel extends JPanel {
    private final ResidentCustomerService service = new ResidentCustomerService();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public CustomerPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("客户管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"编号", "姓名", "性别", "年龄", "身份证号", "楼栋", "房间号",
                "入院时间", "合同到期", "联系电话", "家属", "所属护工ID", "护理等级", "在院状态"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 13));
        table.setRowHeight(26);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 13));
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new GridLayout(3, 3, 8, 8));
        JButton refreshBtn = new JButton("刷新列表");
        JButton searchBtn = new JButton("按编号查询");
        JButton addBtn = new JButton("入住登记");
        JButton editBtn = new JButton("修改信息");
        JButton returnBtn = new JButton("回住登记");
        JButton removeBtn = new JButton("删除客户");
        JButton careLevelBtn = new JButton("护理等级查询");
        JButton showAllBtn = new JButton("显示全部");
        JButton backBtn = new JButton("返回");

        btnPanel.add(refreshBtn);   btnPanel.add(searchBtn);   btnPanel.add(addBtn);
        btnPanel.add(editBtn);      btnPanel.add(returnBtn);   btnPanel.add(removeBtn);
        btnPanel.add(careLevelBtn); btnPanel.add(showAllBtn);  btnPanel.add(backBtn);

        add(btnPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshTable());
        searchBtn.addActionListener(e -> searchById());
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        returnBtn.addActionListener(e -> returnCheckin());
        removeBtn.addActionListener(e -> deleteCustomer());
        careLevelBtn.addActionListener(e -> showCareLevelDialog());
        showAllBtn.addActionListener(e -> refreshTable());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }

    private Object[] toRow(ResidentCustomer c) {
        String sex = (c.getCustomer_sex() != null && c.getCustomer_sex() == 0) ? "男" : "女";
        String status;
        if (c.getIs_deleted() != null && c.getIs_deleted() == 1) {
            status = "已退住";
        } else if (c.getIs_deleted() != null && c.getIs_deleted() == 2) {
            status = "外出";
        } else {
            status = "在院";
        }
        String level = c.getCareLevel() == null ? "--" : c.getCareLevel();
        return new Object[]{
                c.getId(), c.getCustomer_name(), sex, c.getCustomer_age(),
                c.getIdcard(), c.getBuilding_no(), c.getRoom_no(),
                c.getCheckin_date(), c.getExpiration_date(),
                c.getContact_tel(), c.getFamily_member(), c.getUser_id(), level, status
        };
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (ResidentCustomer c : service.findAllCustomers()) {
            tableModel.addRow(toRow(c));
        }
    }

    private void searchById() {
        String idStr = JOptionPane.showInputDialog(this, "请输入客户编号：", "按编号查询", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            ResidentCustomer c = service.findCustomerById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            showCustomerDetail(c);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCustomerDetail(ResidentCustomer c) {
        String sex = (c.getCustomer_sex() != null && c.getCustomer_sex() == 0) ? "男" : "女";
        String status;
        if (c.getIs_deleted() != null && c.getIs_deleted() == 1) {
            status = "已退住";
        } else if (c.getIs_deleted() != null && c.getIs_deleted() == 2) {
            status = "外出";
        } else {
            status = "在院";
        }
        String level = c.getCareLevel() == null ? "未设置" : c.getCareLevel();

        JPanel panel = new JPanel(new GridLayout(14, 2, 8, 6));
        panel.add(new JLabel("编号："));       panel.add(new JLabel(String.valueOf(c.getId())));
        panel.add(new JLabel("姓名："));       panel.add(new JLabel(c.getCustomer_name()));
        panel.add(new JLabel("年龄："));       panel.add(new JLabel(String.valueOf(c.getCustomer_age())));
        panel.add(new JLabel("性别："));       panel.add(new JLabel(sex));
        panel.add(new JLabel("身份证号："));   panel.add(new JLabel(c.getIdcard() == null ? "--" : c.getIdcard()));
        panel.add(new JLabel("楼栋："));       panel.add(new JLabel(c.getBuilding_no() == null ? "--" : c.getBuilding_no()));
        panel.add(new JLabel("房间号："));     panel.add(new JLabel(c.getRoom_no() == null ? "--" : c.getRoom_no()));
        panel.add(new JLabel("入住时间："));   panel.add(new JLabel(c.getCheckin_date() == null ? "--" : c.getCheckin_date()));
        panel.add(new JLabel("合同到期："));   panel.add(new JLabel(c.getExpiration_date() == null ? "--" : c.getExpiration_date()));
        panel.add(new JLabel("联系电话："));   panel.add(new JLabel(c.getContact_tel() == null ? "--" : c.getContact_tel()));
        panel.add(new JLabel("家属："));       panel.add(new JLabel(c.getFamily_member() == null ? "--" : c.getFamily_member()));
        panel.add(new JLabel("所属护工ID："));     panel.add(new JLabel(c.getUser_id() == null ? "--" : String.valueOf(c.getUser_id())));
        panel.add(new JLabel("护理等级："));   panel.add(new JLabel(level));
        panel.add(new JLabel("在院状态："));   panel.add(new JLabel(status));

        JOptionPane.showMessageDialog(this, panel, "客户详细信息", JOptionPane.PLAIN_MESSAGE);
    }

    private void showAddDialog() {
        BedService bedService = new BedService();
        BedUsageDetailService detailService = new BedUsageDetailService();

        // 获取所有空闲床位
        List<Bed> vacantBeds = bedService.findByStatus("空闲");
        if (vacantBeds.isEmpty()) {
            JOptionPane.showMessageDialog(this, "当前无空闲床位，无法办理入住", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // 构建床位选项：楼栋-房间-床位号
        String[] bedOptions = new String[vacantBeds.size()];
        for (int i = 0; i < vacantBeds.size(); i++) {
            Bed b = vacantBeds.get(i);
            bedOptions[i] = b.getBuilding() + "栋 " + b.getRoomNumber() + "室 " + b.getBedNumber() + "号床";
        }

        JTextField nameField = new JTextField(15);
        JComboBox<String> sexBox = new JComboBox<>(new String[]{"男", "女"});
        JTextField idcardField = new JTextField(15);
        JTextField bloodField = new JTextField(15);
        JTextField familyField = new JTextField(15);
        JTextField telField = new JTextField(15);
        JComboBox<String> bedBox = new JComboBox<>(bedOptions);
        JTextField checkinField = new JTextField("2026-05-31", 15);
        JTextField expireField = new JTextField("2027-05-31", 15);
        JComboBox<String> levelBox = new JComboBox<>(new String[]{"自理老人", "半护理老人", "全护理老人", "特护老人"});

        JPanel panel = new JPanel(new GridLayout(10, 2, 5, 6));
        panel.add(new JLabel("客户姓名："));     panel.add(nameField);
        panel.add(new JLabel("性别："));         panel.add(sexBox);
        panel.add(new JLabel("身份证号："));     panel.add(idcardField);
        panel.add(new JLabel("血型："));         panel.add(bloodField);
        panel.add(new JLabel("家属姓名："));     panel.add(familyField);
        panel.add(new JLabel("联系电话："));     panel.add(telField);
        panel.add(new JLabel("选择床位："));     panel.add(bedBox);
        panel.add(new JLabel("入住时间："));     panel.add(checkinField);
        panel.add(new JLabel("合同到期时间：")); panel.add(expireField);
        panel.add(new JLabel("护理等级："));     panel.add(levelBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "入住登记", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "客户姓名不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // 获取选中的床位
            Bed selectedBed = vacantBeds.get(bedBox.getSelectedIndex());

            int sex = sexBox.getSelectedIndex();
            ResidentCustomer c = new ResidentCustomer(name, 0, sex, idcardField.getText().trim(),
                    bloodField.getText().trim(), familyField.getText().trim(),
                    telField.getText().trim(), selectedBed.getBuilding(),
                    selectedBed.getRoomNumber(), checkinField.getText().trim(),
                    expireField.getText().trim());
            c.setIs_deleted(0);
            c.setBed_id(Integer.parseInt(selectedBed.getId()));
            c.setCareLevel((String) levelBox.getSelectedItem());
            String msg = service.addCustomer(c);

            if ("添加成功".equals(msg)) {
                // 更新床位状态为"有人"
                selectedBed.setStatus("有人");
                bedService.updateBed(selectedBed);

                // 创建床位使用记录
                BedUsageDetail detail = new BedUsageDetail();
                detail.setCustomerId(String.valueOf(c.getId()));
                detail.setCustomerName(name);
                detail.setBedNumber(selectedBed.getBedNumber());
                detail.setRoomNumber(selectedBed.getRoomNumber());
                detail.setBuilding(selectedBed.getBuilding());
                detail.setStartTime(LocalDate.now().toString());
                detail.setStatus("正在使用");
                detailService.addDetail(detail);
            }

            JOptionPane.showMessageDialog(this, msg);
            refreshTable();
        }
    }

    private void showEditDialog() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要修改的客户编号：", "修改客户", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            ResidentCustomer c = service.findCustomerById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            JTextField nameField = new JTextField(c.getCustomer_name(), 15);
            JTextField telField = new JTextField(c.getContact_tel(), 15);
            JTextField familyField = new JTextField(c.getFamily_member(), 15);
            JTextField buildingField = new JTextField(c.getBuilding_no(), 15);
            JTextField roomField = new JTextField(c.getRoom_no(), 15);
            JTextField expireField = new JTextField(c.getExpiration_date(), 15);
            JComboBox<String> levelBox = new JComboBox<>(new String[]{"自理老人", "半护理老人", "全护理老人", "特护老人"});
            if (c.getCareLevel() != null) levelBox.setSelectedItem(c.getCareLevel());

            JPanel panel = new JPanel(new GridLayout(7, 2, 5, 8));
            panel.add(new JLabel("客户姓名："));     panel.add(nameField);
            panel.add(new JLabel("联系电话："));     panel.add(telField);
            panel.add(new JLabel("家属姓名："));     panel.add(familyField);
            panel.add(new JLabel("楼栋号："));       panel.add(buildingField);
            panel.add(new JLabel("房间号："));       panel.add(roomField);
            panel.add(new JLabel("合同到期时间：")); panel.add(expireField);
            panel.add(new JLabel("护理等级："));     panel.add(levelBox);

            int result = JOptionPane.showConfirmDialog(this, panel, "修改客户信息", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                c.setCustomer_name(nameField.getText().trim());
                c.setContact_tel(telField.getText().trim());
                c.setFamily_member(familyField.getText().trim());
                c.setBuilding_no(buildingField.getText().trim());
                c.setRoom_no(roomField.getText().trim());
                c.setExpiration_date(expireField.getText().trim());
                c.setCareLevel((String) levelBox.getSelectedItem());
                String msg = service.updateCustomer(c);
                JOptionPane.showMessageDialog(this, msg);
                refreshTable();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void returnCheckin() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要回住的客户编号：", "回住登记", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            ResidentCustomer c = service.findCustomerById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            RequirementService reqService = new RequirementService();
            RequirementOut out = reqService.findLatestUnreturnedOutByCustomerId(c.getId());

            if (c.getIs_deleted() == null || c.getIs_deleted() == 0) {
                if (out == null) {
                    JOptionPane.showMessageDialog(this, "该客户当前状态正常，无需回住登记", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            int confirm = JOptionPane.showConfirmDialog(this,
                    "确认回住客户：" + c.getCustomer_name() + "（编号：" + c.getId() + "）？",
                    "确认回住", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                c.setIs_deleted(0);
                service.updateCustomer(c);
                if (out != null) {
                    out.registerReturn();
                    reqService.updateOutRequirement(out);
                }
                JOptionPane.showMessageDialog(this, "回住登记成功！客户状态已恢复正常。");
                refreshTable();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteCustomer() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要删除的客户编号：", "删除客户", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            ResidentCustomer c = service.findCustomerById(id);
            if (c == null) {
                JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            int confirm = JOptionPane.showConfirmDialog(this,
                    "确认删除客户：" + c.getCustomer_name() + "（编号：" + c.getId() + "）？\n此操作不可恢复！",
                    "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                service.deleteCustomer(id);
                JOptionPane.showMessageDialog(this, "删除成功");
                refreshTable();
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showCareLevelDialog() {
        String[] options = {"查询护理等级", "按护理等级筛选"};
        int choice = JOptionPane.showOptionDialog(this, "请选择操作：", "护理等级查询",
                JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            String idStr = JOptionPane.showInputDialog(this, "请输入客户编号：", "查询护理等级", JOptionPane.QUESTION_MESSAGE);
            if (idStr == null || idStr.trim().isEmpty()) return;
            try {
                int id = Integer.parseInt(idStr.trim());
                ResidentCustomer c = service.findCustomerById(id);
                if (c == null) {
                    JOptionPane.showMessageDialog(this, "未找到该客户");
                    return;
                }

                String sex = (c.getCustomer_sex() != null && c.getCustomer_sex() == 0) ? "男" : "女";
                String status;
                if (c.getIs_deleted() != null && c.getIs_deleted() == 1) {
                    status = "已退住";
                } else if (c.getIs_deleted() != null && c.getIs_deleted() == 2) {
                    status = "外出";
                } else {
                    status = "在院";
                }
                String level = c.getCareLevel() == null ? "未设置" : c.getCareLevel();
                JOptionPane.showMessageDialog(this,
                        "编号：" + c.getId() +
                        "\n姓名：" + c.getCustomer_name() +
                        "\n年龄：" + c.getCustomer_age() +
                        "\n性别：" + sex +
                        "\n身份证号：" + (c.getIdcard() == null ? "--" : c.getIdcard()) +
                        "\n楼栋：" + (c.getBuilding_no() == null ? "--" : c.getBuilding_no()) +
                        "\n房间号：" + (c.getRoom_no() == null ? "--" : c.getRoom_no()) +
                        "\n入院时间：" + (c.getCheckin_date() == null ? "--" : c.getCheckin_date()) +
                        "\n合同到期：" + (c.getExpiration_date() == null ? "--" : c.getExpiration_date()) +
                        "\n联系电话：" + (c.getContact_tel() == null ? "--" : c.getContact_tel()) +
                        "\n家属：" + (c.getFamily_member() == null ? "--" : c.getFamily_member()) +
                        "\n所属护工ID：" + (c.getUser_id() == null ? "--" : c.getUser_id()) +
                        "\n护理等级：" + level +
                        "\n在院状态：" + status,
                        "客户信息", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
            }
        } else if (choice == 1) {
            String[] levels = {"自理老人", "半护理老人", "全护理老人", "特护老人"};
            String level = (String) JOptionPane.showInputDialog(this, "请选择护理等级：", "按护理等级筛选",
                    JOptionPane.QUESTION_MESSAGE, null, levels, levels[0]);
            if (level == null) return;

            List<ResidentCustomer> all = service.findAllCustomers();
            tableModel.setRowCount(0);
            for (ResidentCustomer c : all) {
                if (level.equals(c.getCareLevel())) {
                    tableModel.addRow(toRow(c));
                }
            }
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, "没有该护理等级的客户");
            }
        }
    }
}
