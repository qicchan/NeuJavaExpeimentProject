package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.TUser;

import javax.swing.*;
import java.awt.*;

/**
 * 系统主窗口（单例模式）。
 * <p>使用{@link CardLayout}实现面板切换，包含登录、管理员菜单、护工菜单、护理管理、客户管理、床位管理和系统设置等界面。</p>
 * <p>窗口大小900x600，标题"东软颐养中心管理系统"，不可调整大小。</p>
 *
 * @author QICHAN
 * @see LoginPanel
 * @see AdminMenuPanel
 * @see CarePanel
 * @see CareItemPanel
 * @see SettingPanel
 */
public class MainFrame extends JFrame {
    /** 单例实例 */
    private static MainFrame instance;
    /** 卡片布局管理器，用于切换不同功能面板 */
    private static final CardLayout cardLayout = new CardLayout();
    /** 内容面板，使用CardLayout管理各功能页面 */
    private static final JPanel contentPanel = new JPanel(cardLayout);

    /** 登录面板标识 */
    public static final String LOGIN = "login";
    /** 管理员主菜单面板标识 */
    public static final String ADMIN_MENU = "adminMenu";
    /** 护工主菜单面板标识 */
    public static final String HEALTH_MANAGER_MENU = "healthManagerMenu";
    /** 护理管理面板标识 */
    public static final String CARE = "care";
    /** 客户管理面板标识 */
    public static final String CUSTOMER = "customer";
    /** 床位管理面板标识 */
    public static final String BED = "bed";
    /** 系统设置面板标识 */
    public static final String SETTING = "setting";
    /** 护理项目管理面板标识 */
    public static final String CARE_ITEM = "careItem";
    /** 护理记录面板标识 */
    public static final String CARE_RECORD = "careRecord";
    /** 申请管理面板标识（护工使用） */
    public static final String REQUIREMENT = "requirement";
    /** 申请审批面板标识（管理员使用） */
    public static final String REQUIREMENT_APPROVAL = "requirementApproval";

    /** 当前登录用户 */
    public static TUser currentUser;

    /**
     * 私有构造方法，初始化窗口属性并注册所有功能面板
     */
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
        contentPanel.add(new CareItemPanel(), CARE_ITEM);
        contentPanel.add(new CustomerPanel(), CUSTOMER);
        contentPanel.add(new BedPanel(), BED);
        contentPanel.add(new CareRecordPanel(), CARE_RECORD);
        contentPanel.add(new RequirementPanel(), REQUIREMENT);
        contentPanel.add(new RequirementApprovalPanel(), REQUIREMENT_APPROVAL);
        contentPanel.add(new SettingPanel(), SETTING);

        add(contentPanel);
    }

    /**
     * 获取MainFrame单例实例（懒汉式，非线程安全，但非必要）
     *
     * @return MainFrame的唯一实例
     */
    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
        }
        return instance;
    }

    /**
     * 切换显示指定名称的面板
     *
     * @param name 面板标识（如LOGIN, ADMIN_MENU等常量）
     */
    public static void showPanel(String name) {
        cardLayout.show(contentPanel, name);
    }

    public static TUser getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(TUser user) {
        currentUser = user;
    }

    /**
     * 主方法入口，启动主窗口
     *
     * @param args 命令行参数（未使用）
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = getInstance();
            frame.setVisible(true);
        });
    }
}
