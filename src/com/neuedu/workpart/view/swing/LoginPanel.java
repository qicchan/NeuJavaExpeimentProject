package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.service.TUserService;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JRadioButton adminRadio = new JRadioButton("管理员", true);
    private final JRadioButton healthRadio = new JRadioButton("护工");

    public LoginPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel title = new JLabel("东软颐养中心管理系统");
        title.setFont(new Font("微软雅黑", Font.BOLD, 24));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        gbc.gridwidth = 1; gbc.anchor = GridBagConstraints.EAST;
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("用户名："), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.anchor = GridBagConstraints.EAST;
        add(new JLabel("密  码："), gbc);
        gbc.gridx = 1; gbc.anchor = GridBagConstraints.WEST;
        add(passwordField, gbc);

        ButtonGroup group = new ButtonGroup();
        group.add(adminRadio);
        group.add(healthRadio);
        JPanel radioPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        radioPanel.add(adminRadio);
        radioPanel.add(healthRadio);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        add(radioPanel, gbc);

        JButton loginBtn = new JButton("登  录");
        loginBtn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        add(loginBtn, gbc);

        loginBtn.addActionListener(e -> doLogin());
        passwordField.addActionListener(e -> doLogin());
    }

    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名和密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (adminRadio.isSelected()) {
            TUserService service = new TUserService();
            if (service.findUserByAll(1, username, password)) {
                usernameField.setText("");
                passwordField.setText("");
                MainFrame.showPanel(MainFrame.ADMIN_MENU);
            } else {
                JOptionPane.showMessageDialog(this, "管理员用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            MUserService service = new MUserService();
            if (service.findUserByAll(2, username, password)) {
                usernameField.setText("");
                passwordField.setText("");
                MainFrame.showPanel(MainFrame.HEALTH_MANAGER_MENU);
            } else {
                JOptionPane.showMessageDialog(this, "护工用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
