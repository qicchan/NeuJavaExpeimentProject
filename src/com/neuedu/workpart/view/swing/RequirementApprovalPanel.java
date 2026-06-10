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
 * 申请审批面板（管理员使用）。
 * <p>提供外出申请和退住申请的查看与审批功能。</p>
 */
public class RequirementApprovalPanel extends JPanel {
    private final RequirementService service = new RequirementService();
    private final DefaultTableModel outTableModel;
    private final DefaultTableModel quitTableModel;
    private final JTabbedPane tabbedPane;

    public RequirementApprovalPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("申请审批管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.NORTH);

        tabbedPane = new JTabbedPane();

        // 外出申请tab
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
        tabbedPane.addTab("外出申请", outPanel);

        // 退住申请tab
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
        tabbedPane.addTab("退住申请", quitPanel);

        add(tabbedPane, BorderLayout.CENTER);

        // 底部按钮
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton refreshBtn = new JButton("刷新列表");
        JButton approveBtn = new JButton("申请审批");
        JButton backBtn = new JButton("返回");

        refreshBtn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        approveBtn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        backBtn.setFont(new Font("微软雅黑", Font.PLAIN, 14));

        bottomPanel.add(refreshBtn);
        bottomPanel.add(approveBtn);
        bottomPanel.add(backBtn);
        add(bottomPanel, BorderLayout.SOUTH);

        refreshBtn.addActionListener(e -> refreshTables());
        approveBtn.addActionListener(e -> showApproveDialog());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }

    private void refreshTables() {
        refreshOutTable();
        refreshQuitTable();
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

    private void showApproveDialog() {
        int tabIndex = tabbedPane.getSelectedIndex();
        String typeName = tabIndex == 0 ? "外出申请" : "退住申请";

        String idStr = JOptionPane.showInputDialog(this,
                "请输入要审批的" + typeName + "ID：",
                "申请审批",
                JOptionPane.QUESTION_MESSAGE);

        if (idStr == null || idStr.trim().isEmpty()) return;

        try {
            int id = Integer.parseInt(idStr.trim());

            if (tabIndex == 0) {
                approveOutRequirement(id);
            } else {
                approveQuitRequirement(id);
            }

            refreshTables();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "请输入有效的数字ID",
                    "错误",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void approveOutRequirement(int id) {
        RequirementOut out = service.findOutById(id);
        if (out == null) {
            JOptionPane.showMessageDialog(this,
                    "未找到ID为 " + id + " 的外出申请",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (out.checkApproveStatus()) {
            JOptionPane.showMessageDialog(this,
                    "该申请已经审批通过，无需重复审批",
                    "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this,
                "确认审批通过以下外出申请？\n" +
                        "申请ID: " + out.getId() + "\n" +
                        "客户姓名: " + out.getCustomerName() + "\n" +
                        "外出事由: " + out.getReason() + "\n" +
                        "外出时间: " + out.getOutTime(),
                "确认审批",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);


        if (confirm == JOptionPane.YES_OPTION) {
            out.registerApprove();
            service.updateOutRequirement(out);

            ResidentCustomerService customerService = new ResidentCustomerService();
            ResidentCustomer customer = customerService.findCustomerById(out.getCustomerId());
            if (customer != null) {
                customer.setIs_deleted(2);
                customerService.updateCustomer(customer);
            }

            JOptionPane.showMessageDialog(this,
                    "外出申请审批成功！",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
        }


    }

    private void approveQuitRequirement(int id) {
        RequirementQuit quit = service.findQuitById(id);
        if (quit == null) {
            JOptionPane.showMessageDialog(this,
                    "未找到ID为 " + id + " 的退住申请",
                    "提示",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (quit.checkApprovedStatus()) {
            JOptionPane.showMessageDialog(this,
                    "该申请已经审批通过，无需重复审批",
                    "提示",
                    JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        String typeStr = quit.getQuitType() == 1 ? "正常退住" : (quit.getQuitType() == 2 ? "死亡退住" : "保留床位");

        int confirm = JOptionPane.showConfirmDialog(this,
                "确认审批通过以下退住申请？\n" +
                        "申请ID: " + quit.getId() + "\n" +
                        "客户姓名: " + quit.getCustomerName() + "\n" +
                        "退住类型: " + typeStr + "\n" +
                        "退住原因: " + quit.getReason(),
                "确认审批",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        // ... existing code ...
        if (confirm == JOptionPane.YES_OPTION) {
            quit.registerApprove();
            service.updateQuitRequirement(quit);

            ResidentCustomerService customerService = new ResidentCustomerService();
            ResidentCustomer customer = customerService.findCustomerById(quit.getCustomerId());
            if (customer != null) {
                customer.setIs_deleted(1);
                customerService.updateCustomer(customer);
            }

            JOptionPane.showMessageDialog(this,
                    "退住申请审批成功！客户状态已更新为退住。",
                    "成功",
                    JOptionPane.INFORMATION_MESSAGE);
        }
    }
}

