package com.neuedu.workpart.view.swing;

import javax.swing.*;
import java.awt.*;

public class BedPanel extends JPanel {
    public BedPanel() {
        setLayout(new BorderLayout(10, 10));

        JLabel title = new JLabel("床位管理", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 20));
        title.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(title, BorderLayout.CENTER);

        JButton backBtn = new JButton("返回");
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnPanel.add(backBtn);
        add(btnPanel, BorderLayout.SOUTH);

        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }
}
