package com.neuedu.workpart.view.swing;

import javax.swing.*;
import java.awt.*;

public class CarePanel extends JPanel {
    public CarePanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("护理项目管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 1;
        add(title, gbc);

        JButton careItemBtn = new JButton("护理项目管理");
        JButton backBtn = new JButton("返回");

        Font btnFont = new Font("微软雅黑", Font.PLAIN, 16);
        careItemBtn.setFont(btnFont);
        backBtn.setFont(btnFont);

        gbc.gridx = 0; gbc.gridy = 1; add(careItemBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        backBtn.setBackground(new Color(100, 149, 237));
        backBtn.setForeground(Color.WHITE);
        add(backBtn, gbc);

        careItemBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.CARE_ITEM));
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }
}
