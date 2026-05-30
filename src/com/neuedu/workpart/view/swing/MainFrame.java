package com.neuedu.workpart.view.swing;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private static MainFrame instance;
    private static final CardLayout cardLayout = new CardLayout();
    private static final JPanel contentPanel = new JPanel(cardLayout);

    public static final String LOGIN = "login";
    public static final String ADMIN_MENU = "adminMenu";
    public static final String HEALTH_MANAGER_MENU = "healthManagerMenu";
    public static final String CARE = "care";
    public static final String CUSTOMER = "customer";
    public static final String BED = "bed";
    public static final String SETTING = "setting";

    private MainFrame() {
        setTitle("东软颐养中心管理系统");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        contentPanel.add(new LoginPanel(), LOGIN);
        contentPanel.add(new AdminMenuPanel(), ADMIN_MENU);
        contentPanel.add(new HealthManagerMenuPanel(), HEALTH_MANAGER_MENU);
        contentPanel.add(new CarePanel(), CARE);
        contentPanel.add(new CustomerPanel(), CUSTOMER);
        contentPanel.add(new BedPanel(), BED);
        contentPanel.add(new SettingPanel(), SETTING);

        add(contentPanel);
    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    public static void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = getInstance();
            frame.setVisible(true);
        });
    }
}
