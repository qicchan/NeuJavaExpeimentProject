package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.CareItem;
import com.neuedu.workpart.pojo.CareRecord;
import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.CareItemService;
import com.neuedu.workpart.service.CareRecordService;
import com.neuedu.workpart.service.ResidentCustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 护理记录面板（护工使用）。
 * <p>提供护理记录的查看、新增和隐藏功能。</p>
 */
public class CareRecordPanel extends JPanel {
    private final CareRecordService service = new CareRecordService();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public CareRecordPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("护理记录管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "客户姓名", "客户ID", "护理项目", "护理时间", "护理数量", "护工ID", "状态"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshBtn = new JButton("刷新列表");
        JButton addBtn = new JButton("新增护理记录");
        JButton hideBtn = new JButton("隐藏记录");
        JButton backBtn = new JButton("返回");

        btnPanel.add(refreshBtn);
        btnPanel.add(addBtn);
        btnPanel.add(hideBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshTable());
        addBtn.addActionListener(e -> showAddDialog());
        hideBtn.addActionListener(e -> hideRecord());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.HEALTH_MANAGER_MENU));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<CareRecord> list = service.findAllRecords();
        for (CareRecord r : list) {
            if (r.getIsHidden() == null || r.getIsHidden() == 0) {
                tableModel.addRow(new Object[]{r.getId(), r.getCustomerName(), r.getCustomerId(),
                        r.getCareProject(), r.getCareTime(), r.getCareNum(), r.getHmId(), r.getStatus()});
            }
        }
    }

    private void showAddDialog() {
        ResidentCustomerService customerService = new ResidentCustomerService();

        TUser currentUser = MainFrame.getCurrentUser();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this,
                    "未获取到登录用户信息，请重新登录！",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer currentHmId = currentUser.getId();

        CareItemService careItemService = new CareItemService();
        List<CareItem> enabledItems = careItemService.findEnabled();
        if (enabledItems.isEmpty()) {
            JOptionPane.showMessageDialog(this, "暂无可用的护理项目，请先在护理项目管理中添加", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String[] options = new String[enabledItems.size()];
        for (int i = 0; i < enabledItems.size(); i++) {
            CareItem item = enabledItems.get(i);
            options[i] = item.getName() + " (编码:" + item.getCode() + ")";
        }
        JComboBox<String> projectBox = new JComboBox<>(options);

        JTextField numField = new JTextField("1", 15);
        JTextField customerIdField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 8));
        panel.add(new JLabel("护理项目：")); panel.add(projectBox);
        panel.add(new JLabel("护理数量：")); panel.add(numField);
        panel.add(new JLabel("当前护工ID：")); panel.add(new JLabel(String.valueOf(currentHmId)));
        panel.add(new JLabel("客户ID：")); panel.add(customerIdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "新增护理记录", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int selectedIndex = projectBox.getSelectedIndex();
                String project = enabledItems.get(selectedIndex).getName();
                int num = Integer.parseInt(numField.getText().trim());
                int customerId = Integer.parseInt(customerIdField.getText().trim());

                if (project.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "护理项目不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                ResidentCustomer customer = customerService.findCustomerById(customerId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(this,
                            "未找到ID为 " + customerId + " 的客户，请检查客户编号是否正确！",
                            "客户不存在",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (customer.getIs_deleted() != null && customer.getIs_deleted() == 1) {
                    JOptionPane.showMessageDialog(this,
                            "客户【" + customer.getCustomer_name() + "】已退住，无法添加护理记录！",
                            "客户已退住",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (customer.getUser_id() == null) {
                    JOptionPane.showMessageDialog(this,
                            "客户【" + customer.getCustomer_name() + "】尚未分配护工，请联系管理员进行分配！",
                            "未分配护工",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (!customer.getUser_id().equals(currentHmId)) {
                    JOptionPane.showMessageDialog(this,
                            "您未被分配给该客户！\n" +
                                    "客户【" + customer.getCustomer_name() + "】的负责护工ID为: " + customer.getUser_id(),
                            "权限不足",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String customerName = customer.getCustomer_name();
                String msg = service.createCareRecord(project, num, currentHmId, customerId, customerName);
                JOptionPane.showMessageDialog(this, msg);
                refreshTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this,
                        "数量和客户ID必须为有效的数字！",
                        "输入格式错误",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void hideRecord() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要隐藏的记录ID：", "隐藏记录", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            String msg = service.hideCareRecord(id);
            JOptionPane.showMessageDialog(this, msg);
            refreshTable();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字ID", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
