package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.ResidentCustomerService;
import com.neuedu.workpart.service.MUserService;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * 管理员主菜单面板。
 * <p>登录成功后显示的功能导航界面，包含五个功能按钮：</p>
 * <ul>
 *   <li>客户管理 - 跳转到客户管理面板</li>
 *   <li>床位管理 - 跳转到床位管理面板</li>
 *   <li>护理管理 - 跳转到护理管理面板</li>
 *   <li>系统设置 - 跳转到系统设置面板</li>
 *   <li>退出登录 - 返回登录面板</li>
 * </ul>
 *
 * @author QICHAN
 * @see MainFrame
 */
public class AdminMenuPanel extends JPanel {
    /**
     * 构造方法，初始化管理员主菜单布局并绑定按钮导航事件
     */
    public AdminMenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("管理员主菜单", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        JButton customerBtn = new JButton("客户管理");
        JButton bedBtn = new JButton("床位管理");
        JButton careBtn = new JButton("护理项目管理");
        JButton assignBtn = new JButton("分配客户");
        JButton approvalBtn = new JButton("申请审批");
        JButton settingBtn = new JButton("系统设置");
        JButton logoutBtn = new JButton("退出登录");

        Font btnFont = new Font("微软雅黑", Font.PLAIN, 16);
        customerBtn.setFont(btnFont);
        bedBtn.setFont(btnFont);
        careBtn.setFont(btnFont);
        assignBtn.setFont(btnFont);
        approvalBtn.setFont(btnFont);
        settingBtn.setFont(btnFont);
        logoutBtn.setFont(btnFont);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; add(customerBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(bedBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(careBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(assignBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 3; add(approvalBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 3; add(settingBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        logoutBtn.setBackground(new Color(220, 80, 80));
        logoutBtn.setForeground(Color.WHITE);
        add(logoutBtn, gbc);

        customerBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.CUSTOMER));
        bedBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.BED));
        careBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.CARE_ITEM));
        assignBtn.addActionListener(e -> showAssignDialog());
        approvalBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.REQUIREMENT_APPROVAL));
        settingBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.SETTING));
        logoutBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.LOGIN));
    }

    private void showAssignDialog() {
        ResidentCustomerService customerService = new ResidentCustomerService();
        MUserService userService = new MUserService();

        String idStr = JOptionPane.showInputDialog(this, "请输入客户编号：", "分配客户", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            int customerId = Integer.parseInt(idStr.trim());
            ResidentCustomer customer = customerService.findCustomerById(customerId);
            if (customer == null) {
                JOptionPane.showMessageDialog(this, "未找到该客户", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            List<TUser> allUsers = userService.findAll();
            List<TUser> hmList = new java.util.ArrayList<>();
            for (TUser u : allUsers) {
                if (u.getUserType() == 2) hmList.add(u);
            }
            if (hmList.isEmpty()) {
                JOptionPane.showMessageDialog(this, "暂无可用护工", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }

            String[] options = new String[hmList.size()];
            for (int i = 0; i < hmList.size(); i++) {
                TUser u = hmList.get(i);
                options[i] = u.getUserName() + " (ID:" + u.getId() + ")";
            }
            JComboBox<String> hmBox = new JComboBox<>(options);

            JPanel panel = new JPanel(new GridLayout(3, 2, 5, 8));
            panel.add(new JLabel("客户编号：")); panel.add(new JLabel(String.valueOf(customer.getId())));
            panel.add(new JLabel("客户姓名：")); panel.add(new JLabel(customer.getCustomer_name()));
            panel.add(new JLabel("选择护工：")); panel.add(hmBox);

            int result = JOptionPane.showConfirmDialog(this, panel, "分配客户给护工", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
            if (result == JOptionPane.OK_OPTION) {
                int idx = hmBox.getSelectedIndex();
                Integer hmId = hmList.get(idx).getId();
                String msg = customerService.assignCustomerToHm(customerId, hmId);
                JOptionPane.showMessageDialog(this, msg);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
