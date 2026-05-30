package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.Customer;
import com.neuedu.workpart.service.CareStatusService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class CarePanel extends JPanel {
    private final CareStatusService service = new CareStatusService();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public CarePanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("护理管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "客户姓名", "护理等级", "护理项目", "更新时间"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshBtn = new JButton("刷新列表");
        JButton searchBtn = new JButton("按姓名查询");
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("修改");
        JButton delBtn = new JButton("删除");
        JButton backBtn = new JButton("返回");

        btnPanel.add(refreshBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(delBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshTable());
        searchBtn.addActionListener(e -> searchByName());
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        delBtn.addActionListener(e -> deleteSelected());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        List<Customer> list = service.findAll();
        for (Customer c : list) {
            tableModel.addRow(new Object[]{c.getId(), c.getCustomerName(), c.getCareLevel(), c.getCareItem(), c.getUpdateTime()});
        }
    }

    private void searchByName() {
        String name = JOptionPane.showInputDialog(this, "请输入客户姓名：", "查询", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;
        Customer c = service.findByName(name.trim());
        if (c == null) {
            JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{c.getId(), c.getCustomerName(), c.getCareLevel(), c.getCareItem(), c.getUpdateTime()});
    }

    private void showAddDialog() {
        JTextField nameField = new JTextField(15);
        JTextField levelField = new JTextField(15);
        JTextField itemField = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
        panel.add(new JLabel("客户姓名：")); panel.add(nameField);
        panel.add(new JLabel("护理等级：")); panel.add(levelField);
        panel.add(new JLabel("护理项目：")); panel.add(itemField);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加客户护理信息", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String level = levelField.getText().trim();
            String item = itemField.getText().trim();
            if (name.isEmpty() || level.isEmpty() || item.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            service.add(name, level, item);
            JOptionPane.showMessageDialog(this, "添加成功");
            refreshTable();
        }
    }

    private void showEditDialog() {
        String name = JOptionPane.showInputDialog(this, "请输入要修改的客户姓名：", "修改", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;
        Customer c = service.findByName(name.trim());
        if (c == null) {
            JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextField levelField = new JTextField(c.getCareLevel(), 15);
        JTextField itemField = new JTextField(c.getCareItem(), 15);
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));
        panel.add(new JLabel("护理等级：")); panel.add(levelField);
        panel.add(new JLabel("护理项目：")); panel.add(itemField);

        int result = JOptionPane.showConfirmDialog(this, panel, "修改客户护理信息", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String level = levelField.getText().trim();
            String item = itemField.getText().trim();
            if (level.isEmpty() || item.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            service.update(name.trim(), level, item);
            JOptionPane.showMessageDialog(this, "修改成功");
            refreshTable();
        }
    }

    private void deleteSelected() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要删除的客户ID：", "删除", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            int confirm = JOptionPane.showConfirmDialog(this, "确定要删除ID为 " + id + " 的客户吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.delete(id)) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字ID", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
