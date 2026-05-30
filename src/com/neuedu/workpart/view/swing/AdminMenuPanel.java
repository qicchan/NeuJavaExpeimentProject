package com.neuedu.workpart.view.swing;

import javax.swing.*;
import java.awt.*;

public class AdminMenuPanel extends JPanel {
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
        JButton careBtn = new JButton("护理管理");
        JButton settingBtn = new JButton("系统设置");
        JButton logoutBtn = new JButton("退出登录");

        Font btnFont = new Font("微软雅黑", Font.PLAIN, 16);
        customerBtn.setFont(btnFont);
        bedBtn.setFont(btnFont);
        careBtn.setFont(btnFont);
        settingBtn.setFont(btnFont);
        logoutBtn.setFont(btnFont);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; add(customerBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(bedBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(careBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 2; add(settingBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        logoutBtn.setBackground(new Color(220, 80, 80));
        logoutBtn.setForeground(Color.WHITE);
        add(logoutBtn, gbc);

        customerBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.CUSTOMER));
        bedBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.BED));
        careBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.CARE));
        settingBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.SETTING));
        logoutBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.LOGIN));
    }
}
