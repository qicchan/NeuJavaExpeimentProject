package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.RequirementOut;
import com.neuedu.workpart.pojo.RequirementQuit;
import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.service.RequirementService;
import com.neuedu.workpart.service.ResidentCustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 申请管理面板（外出申请和退住申请）。
 */
public class RequirementPanel extends JPanel {
    private final RequirementService service = new RequirementService();
    private final DefaultTableModel outTableModel;
    private final DefaultTableModel quitTableModel;
    private final JTabbedPane tabbedPane;

    public RequirementPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("申请管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();

        String[] outCols = {"ID", "客户姓名", "客户ID", "外出事由", "外出时间", "预计回院", "实际回院", "是否审批", "审批者ID", "申请时间"};
        outTableModel = new DefaultTableModel(outCols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable outTable = new JTable(outTableModel);
        outTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        outTable.setRowHeight(28);
        outTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        JPanel outPanel = new JPanel(new BorderLayout(5, 5));
        outPanel.add(new JScrollPane(outTable), BorderLayout.CENTER);
        JPanel outBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton refreshOutBtn = new JButton("刷新");
        JButton addOutBtn = new JButton("新建外出申请");
        outBtnPanel.add(refreshOutBtn);
        outBtnPanel.add(addOutBtn);
        outPanel.add(outBtnPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("外出申请", outPanel);

        String[] quitCols = {"ID", "客户姓名", "客户ID", "退住类型", "退住原因", "退住时间", "是否审批", "审批者ID", "申请护工ID", "申请时间"};
        quitTableModel = new DefaultTableModel(quitCols, 0) {
            public boolean isCellEditable(int row, int column) { return false; }
        };
        JTable quitTable = new JTable(quitTableModel);
        quitTable.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        quitTable.setRowHeight(28);
        quitTable.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        JPanel quitPanel = new JPanel(new BorderLayout(5, 5));
        quitPanel.add(new JScrollPane(quitTable), BorderLayout.CENTER);
        JPanel quitBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton refreshQuitBtn = new JButton("刷新");
        JButton addQuitBtn = new JButton("新建退住申请");
        quitBtnPanel.add(refreshQuitBtn);
        quitBtnPanel.add(addQuitBtn);
        quitPanel.add(quitBtnPanel, BorderLayout.SOUTH);
        tabbedPane.addTab("退住申请", quitPanel);

        add(tabbedPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton backBtn = new JButton("返回");
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshOutBtn.addActionListener(e -> refreshOutTable());
        addOutBtn.addActionListener(e -> showAddOutDialog());
        refreshQuitBtn.addActionListener(e -> refreshQuitTable());
        addQuitBtn.addActionListener(e -> showAddQuitDialog());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.HEALTH_MANAGER_MENU));
    }

    private void refreshOutTable() {
        outTableModel.setRowCount(0);
        for (RequirementOut out : service.getAllOutRequirements()) {
            outTableModel.addRow(new Object[]{
                    out.getId(),
                    out.getCustomerName(),
                    out.getCustomerId(),
                    out.getReason(),
                    out.getOutTime(),
                    out.getExpectReturnTime(),
                    out.getActualReturnTime() == null ? "未回院" : out.getActualReturnTime(),
                    out.checkApproveStatus() ? "已审批" : "待审批",
                    out.getApprovedAdmin() == null ? "--" : out.getApprovedAdmin(),
                    out.getApplyTime() == null ? "--" : out.getApplyTime()
            });
        }
    }

    private void refreshQuitTable() {
        quitTableModel.setRowCount(0);
        for (RequirementQuit quit : service.getAllQuitRequirements()) {
            String typeStr = quit.getQuitType() == 1 ? "正常退住" : (quit.getQuitType() == 2 ? "死亡退住" : "保留床位");
            quitTableModel.addRow(new Object[]{
                    quit.getId(),
                    quit.getCustomerName(),
                    quit.getCustomerId(),
                    typeStr,
                    quit.getReason(),
                    quit.getQuitTime(),
                    quit.checkApprovedStatus() ? "已审批" : "待审批",
                    quit.getApprovedAdmin() == null ? "--" : quit.getApprovedAdmin(),
                    quit.getRequireHMId() == null ? "--" : quit.getRequireHMId(),
                    quit.getApplyTime() == null ? "--" : quit.getApplyTime()
            });
        }
    }

    private void showAddOutDialog() {
        ResidentCustomerService customerService = new ResidentCustomerService();
        JTextField customerIdField = new JTextField(15);
        JTextField reasonField = new JTextField(15);
        JTextField outTimeField = new JTextField("2026-05-31 08:00:00", 15);
        JTextField returnTimeField = new JTextField("2026-05-31 18:00:00", 15);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 8));
        panel.add(new JLabel("客户ID："));       panel.add(customerIdField);
        panel.add(new JLabel("外出事由："));     panel.add(reasonField);
        panel.add(new JLabel("外出时间："));     panel.add(outTimeField);
        panel.add(new JLabel("预计回院时间：")); panel.add(returnTimeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "新建外出申请", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int customerId = Integer.parseInt(customerIdField.getText().trim());
                ResidentCustomer customer = customerService.findCustomerById(customerId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(this, "未找到该客户，无法提出外出申请", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (customer.getIs_deleted() != null && customer.getIs_deleted() == 1) {
                    JOptionPane.showMessageDialog(this, "该客户已退住，无法提出外出申请", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (customer.getIs_deleted() != null && customer.getIs_deleted() == 2) {
                    JOptionPane.showMessageDialog(this, "该客户已外出，无法重复提出外出申请", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String name = customer.getCustomer_name();
                String reason = reasonField.getText().trim();
                String outTime = outTimeField.getText().trim();
                String returnTime = returnTimeField.getText().trim();
                if (reason.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "请填写外出事由", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String msg = service.createOutRequirement(reason, outTime, returnTime, name, customerId);
                JOptionPane.showMessageDialog(this, msg);
                refreshOutTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "客户ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showAddQuitDialog() {
        ResidentCustomerService customerService = new ResidentCustomerService();
        JTextField customerIdField = new JTextField(15);
        String[] types = {"正常退住", "死亡退住", "保留床位"};
        JComboBox<String> typeBox = new JComboBox<>(types);
        JTextField reasonField = new JTextField(15);
        JTextField quitTimeField = new JTextField("2026-05-31 10:00:00", 15);

        JPanel panel = new JPanel(new GridLayout(4, 2, 5, 8));
        panel.add(new JLabel("客户ID："));   panel.add(customerIdField);
        panel.add(new JLabel("退住类型：")); panel.add(typeBox);
        panel.add(new JLabel("退住原因：")); panel.add(reasonField);
        panel.add(new JLabel("退住时间：")); panel.add(quitTimeField);

        int result = JOptionPane.showConfirmDialog(this, panel, "新建退住申请", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            try {
                int customerId = Integer.parseInt(customerIdField.getText().trim());
                ResidentCustomer customer = customerService.findCustomerById(customerId);
                if (customer == null) {
                    JOptionPane.showMessageDialog(this, "未找到该客户，无法提出退住申请", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (customer.getIs_deleted() != null && customer.getIs_deleted() == 1) {
                    JOptionPane.showMessageDialog(this, "该客户已退住，不可重复操作", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String name = customer.getCustomer_name();
                int type = typeBox.getSelectedIndex() + 1;
                String reason = reasonField.getText().trim();
                String quitTime = quitTimeField.getText().trim();
                if (reason.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "请填写退住原因", "提示", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                String msg = service.createQuitRequirement(type, reason, quitTime, name, customerId);
                JOptionPane.showMessageDialog(this, msg);
                refreshQuitTable();
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "客户ID必须为数字", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
