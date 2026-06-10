package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.CareItem;
import com.neuedu.workpart.pojo.CareItemCustomer;
import com.neuedu.workpart.service.CareItemCustomerService;
import com.neuedu.workpart.service.CareItemService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 护理项目管理面板。
 * <p>提供护理项目的CRUD操作、状态切换、启用项目查询、关联客户和查看关联人。</p>
 *
 * @author QICHAN
 */
public class CareItemPanel extends JPanel {
    private final CareItemService service = new CareItemService();
    private final CareItemCustomerService assocService = new CareItemCustomerService();
    private final DefaultTableModel tableModel;
    private final JTable table;

    public CareItemPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("护理项目管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        String[] columns = {"ID", "项目编号", "项目名称", "价格", "状态", "执行周期", "执行次数", "描述"};
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

        JPanel btnPanel = new JPanel(new GridLayout(2, 5, 8, 8));
        JButton refreshBtn = new JButton("刷新列表");
        JButton searchBtn = new JButton("按编号查询");
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("修改");
        JButton statusBtn = new JButton("修改状态");
        JButton enabledBtn = new JButton("查看启用项目");
        JButton assocBtn = new JButton("关联客户");
        JButton viewAssocBtn = new JButton("查看关联人");
        JButton delBtn = new JButton("删除");
        JButton backBtn = new JButton("返回");

        btnPanel.add(refreshBtn);
        btnPanel.add(searchBtn);
        btnPanel.add(addBtn);
        btnPanel.add(editBtn);
        btnPanel.add(statusBtn);
        btnPanel.add(enabledBtn);
        btnPanel.add(assocBtn);
        btnPanel.add(viewAssocBtn);
        btnPanel.add(delBtn);
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshTable());
        searchBtn.addActionListener(e -> searchByCode());
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        statusBtn.addActionListener(e -> showStatusDialog());
        enabledBtn.addActionListener(e -> showEnabled());
        assocBtn.addActionListener(e -> showAssociateDialog());
        viewAssocBtn.addActionListener(e -> showAssociates());
        delBtn.addActionListener(e -> deleteSelected());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }

    private void refreshTable() {
        tableModel.setRowCount(0);
        for (CareItem item : service.findAll()) {
            tableModel.addRow(toRow(item));
        }
    }

    private Object[] toRow(CareItem item) {
        return new Object[]{item.getId(), item.getCode(), item.getName(), item.getPrice(),
                item.getStatus(), item.getExecutionPeriod(), item.getExecutionCount(), item.getDescription()};
    }

    private void searchByCode() {
        String code = JOptionPane.showInputDialog(this, "请输入项目编号：", "查询", JOptionPane.QUESTION_MESSAGE);
        if (code == null || code.trim().isEmpty()) return;
        CareItem item = service.findByCode(code.trim());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "未找到该项目", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        tableModel.addRow(toRow(item));
    }

    private void showAddDialog() {
        JTextField codeField = new JTextField(15);
        JTextField nameField = new JTextField(15);
        JTextField priceField = new JTextField(15);
        JTextField periodField = new JTextField(15);
        JTextField countField = new JTextField(15);
        JTextField descField = new JTextField(15);
        JTextField customerField = new JTextField(15);

        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 8));
        panel.add(new JLabel("项目编号："));   panel.add(codeField);
        panel.add(new JLabel("项目名称："));   panel.add(nameField);
        panel.add(new JLabel("价格："));       panel.add(priceField);
        panel.add(new JLabel("执行周期："));   panel.add(periodField);
        panel.add(new JLabel("执行次数："));   panel.add(countField);
        panel.add(new JLabel("描述："));       panel.add(descField);
        panel.add(new JLabel("关联客户姓名：")); panel.add(customerField);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加护理项目", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String code = codeField.getText().trim();
            String name = nameField.getText().trim();
            String price = priceField.getText().trim();
            String period = periodField.getText().trim();
            String count = countField.getText().trim();
            String desc = descField.getText().trim();
            String customerName = customerField.getText().trim();
            if (code.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "项目编号和名称不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            service.add(code, name, price, period, count, desc);
            if (!customerName.isEmpty()) {
                assocService.associate(code, customerName, count);
            }
            JOptionPane.showMessageDialog(this, "添加成功，状态默认为：启用");
            refreshTable();
        }
    }

    private void showEditDialog() {
        String code = JOptionPane.showInputDialog(this, "请输入要修改的项目编号：", "修改", JOptionPane.QUESTION_MESSAGE);
        if (code == null || code.trim().isEmpty()) return;
        CareItem item = service.findByCode(code.trim());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "未找到该项目", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(item.getName(), 15);
        JTextField priceField = new JTextField(item.getPrice(), 15);
        JTextField periodField = new JTextField(item.getExecutionPeriod(), 15);
        JTextField countField = new JTextField(item.getExecutionCount(), 15);
        JTextField descField = new JTextField(item.getDescription(), 15);

        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 8));
        panel.add(new JLabel("项目名称：")); panel.add(nameField);
        panel.add(new JLabel("价格："));     panel.add(priceField);
        panel.add(new JLabel("执行周期：")); panel.add(periodField);
        panel.add(new JLabel("执行次数：")); panel.add(countField);
        panel.add(new JLabel("描述："));     panel.add(descField);

        int result = JOptionPane.showConfirmDialog(this, panel, "修改护理项目", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String price = priceField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "项目名称不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            service.update(code.trim(), name, price, periodField.getText().trim(),
                    countField.getText().trim(), descField.getText().trim());
            JOptionPane.showMessageDialog(this, "修改成功");
            refreshTable();
        }
    }

    private void showStatusDialog() {
        String code = JOptionPane.showInputDialog(this, "请输入项目编号：", "修改状态", JOptionPane.QUESTION_MESSAGE);
        if (code == null || code.trim().isEmpty()) return;
        CareItem item = service.findByCode(code.trim());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "未找到该项目", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] options = {"启用", "停用"};
        JComboBox<String> statusBox = new JComboBox<>(options);
        statusBox.setSelectedItem(item.getStatus());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.add(new JLabel("当前状态：" + item.getStatus() + "  更改为："));
        panel.add(statusBox);

        int result = JOptionPane.showConfirmDialog(this, panel, "修改项目状态", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String newStatus = (String) statusBox.getSelectedItem();
            service.updateStatus(code.trim(), newStatus);
            JOptionPane.showMessageDialog(this, "状态已更新为：" + newStatus);
            refreshTable();
        }
    }

    private void showEnabled() {
        tableModel.setRowCount(0);
        List<CareItem> list = service.findEnabled();
        if (list.isEmpty()) {
            JOptionPane.showMessageDialog(this, "没有启用的护理项目", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        for (CareItem item : list) {
            tableModel.addRow(toRow(item));
        }
    }

    private void showAssociateDialog() {
        String code = JOptionPane.showInputDialog(this, "请输入项目编号：", "关联客户", JOptionPane.QUESTION_MESSAGE);
        if (code == null || code.trim().isEmpty()) return;
        CareItem item = service.findByCode(code.trim());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "未找到该项目", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextField customerField = new JTextField(15);
        JTextField countField = new JTextField(item.getExecutionCount(), 15);

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 8));
        panel.add(new JLabel("客户姓名：")); panel.add(customerField);
        panel.add(new JLabel("执行次数：")); panel.add(countField);

        int result = JOptionPane.showConfirmDialog(this, panel, "关联客户到项目：" + item.getName(),
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String customerName = customerField.getText().trim();
            String count = countField.getText().trim();
            if (customerName.isEmpty()) {
                JOptionPane.showMessageDialog(this, "客户姓名不能为空", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            assocService.associate(code.trim(), customerName, count);
            JOptionPane.showMessageDialog(this, "关联成功");
        }
    }

    private void showAssociates() {
        String code = JOptionPane.showInputDialog(this, "请输入项目编号：", "查看关联人", JOptionPane.QUESTION_MESSAGE);
        if (code == null || code.trim().isEmpty()) return;
        CareItem item = service.findByCode(code.trim());
        if (item == null) {
            JOptionPane.showMessageDialog(this, "未找到该项目", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        List<CareItemCustomer> associates = assocService.findByCareItemCode(code.trim());
        if (associates.isEmpty()) {
            JOptionPane.showMessageDialog(this, "该项目暂无关联客户", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String[] cols = {"ID", "客户姓名", "执行次数"};
        DefaultTableModel assocModel = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        for (CareItemCustomer ac : associates) {
            assocModel.addRow(new Object[]{ac.getId(), ac.getCustomerName(), ac.getExecutionCount()});
        }
        JTable assocTable = new JTable(assocModel);
        assocTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        assocTable.setRowHeight(28);
        assocTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));

        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.add(new JLabel("项目：" + item.getName() + "（编号：" + item.getCode() + "）"), BorderLayout.NORTH);
        panel.add(new JScrollPane(assocTable), BorderLayout.CENTER);
        panel.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, panel, "关联客户列表", JOptionPane.PLAIN_MESSAGE);
    }

    private void deleteSelected() {
        String idStr = JOptionPane.showInputDialog(this, "请输入要删除的项目ID：", "删除", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int id = Integer.parseInt(idStr.trim());
            int confirm = JOptionPane.showConfirmDialog(this, "确定要删除ID为 " + id + " 的项目吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (service.delete(id)) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "未找到该项目", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字ID", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
