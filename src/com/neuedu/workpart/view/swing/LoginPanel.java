package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.service.TUserService;

import javax.swing.*;
import java.awt.*;

/**
 * 登录面板。
 * <p>提供用户名和密码输入框，以及管理员/护工角色选择单选按钮。</p>
 * <p>登录成功后根据角色跳转到对应的主菜单面板，登录失败弹出错误提示。</p>
 *
 * @author QICHAN
 * @see MainFrame
 */
public class LoginPanel extends JPanel {
    /** 用户名输入框 */
    private final JTextField usernameField = new JTextField(15);
    /** 密码输入框 */
    private final JPasswordField passwordField = new JPasswordField(15);
    /** 管理员角色选择按钮（默认选中） */
    private final JRadioButton adminRadio = new JRadioButton("管理员", true);
    /** 护工角色选择按钮 */
    private final JRadioButton healthRadio = new JRadioButton("护工");

    /**
     * 构造方法，初始化登录界面布局并绑定登录事件
     */
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

    /**
     * 执行登录验证逻辑。
     * <p>根据选中的角色类型，调用对应Service验证用户名和密码，成功则跳转主菜单。</p>
     */
    private void doLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "请输入用户名和密码", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (adminRadio.isSelected()) {
            TUserService service = new TUserService();
            TUser user = service.findByUserName(username);
            if (user != null && user.getUserType() == 1 && user.getPassword().equals(password)) {
                MainFrame.setCurrentUser(user);
                usernameField.setText("");
                passwordField.setText("");
                MainFrame.showPanel(MainFrame.ADMIN_MENU);
            } else {
                JOptionPane.showMessageDialog(this, "管理员用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            MUserService service = new MUserService();
            TUser user = service.findByUserName(username);
            if (user != null && user.getUserType() == 2 && user.getPassword().equals(password)) {
                MainFrame.setCurrentUser(user);
                usernameField.setText("");
                passwordField.setText("");
                MainFrame.showPanel(MainFrame.HEALTH_MANAGER_MENU);
            } else {
                JOptionPane.showMessageDialog(this, "护工用户名或密码错误", "登录失败", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
