package com.neuedu.workpart.view.swing;

import javax.swing.*;
import java.awt.*;

public class HealthManagerMenuPanel extends JPanel {
    public HealthManagerMenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);

        JLabel title = new JLabel("护工主菜单", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0;
        add(title, gbc);

        JLabel info = new JLabel("功能开发中...");
        info.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        gbc.gridy = 1;
        add(info, gbc);

        JButton logoutBtn = new JButton("退出登录");
        logoutBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        logoutBtn.setBackground(new Color(220, 80, 80));
        logoutBtn.setForeground(Color.WHITE);
        gbc.gridy = 2;
        add(logoutBtn, gbc);

        logoutBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.LOGIN));
    }
}
