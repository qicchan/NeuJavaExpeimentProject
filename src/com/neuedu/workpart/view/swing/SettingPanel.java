package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.TUser;
import com.neuedu.workpart.service.MUserService;
import com.neuedu.workpart.service.TUserService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * 系统设置面板（Swing版）。
 * <p>提供管理员用户和护工用户的管理功能，通过顶部按钮切换用户类型。</p>
 * <p>使用JTable展示用户列表，支持以下操作：</p>
 * <ul>
 *   <li>切换用户类型 - 在管理员和护工之间切换</li>
 *   <li>刷新 - 加载当前类型的所有用户</li>
 *   <li>按用户名查询 - 弹出输入框精确查询</li>
 *   <li>添加 - 弹出对话框填写用户名和密码</li>
 *   <li>修改 - 按用户名查找后编辑信息</li>
 *   <li>删除 - 输入用户ID并确认后删除</li>
 *   <li>返回 - 回到管理员主菜单</li>
 * </ul>
 *
 * @author QICHAN
 * @see com.neuedu.workpart.service.TUserService
 * @see com.neuedu.workpart.service.MUserService
 * @see MainFrame
 */
public class SettingPanel extends JPanel {
    /** 管理员用户服务 */
    private final TUserService adminService = new TUserService();
    /** 护工用户服务 */
    private final MUserService hmService = new MUserService();
    /** 表格数据模型 */
    private final DefaultTableModel tableModel;
    /** 用户信息表格 */
    private final JTable table;
    /** 标题标签，根据当前管理的用户类型动态更新 */
    private final JLabel titleLabel;
    /** 当前管理模式：0-未选择，1-管理员，2-护工 */
    private int currentMode = 0;

    /**
     * 构造方法，初始化系统设置面板布局、表格和操作按钮
     */
    public SettingPanel() {
        setLayout(new BorderLayout(10, 10));

        titleLabel = new JLabel("系统设置", SwingConstants.CENTER);
        titleLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        add(titleLabel, BorderLayout.NORTH);

        String[] columns = {"ID", "用户名", "密码", "用户类型"};
        tableModel = new DefaultTableModel(columns, 0) {
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        table.setRowHeight(28);
        table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));
        add(new JScrollPane(table), BorderLayout.CENTER);

        JPanel topBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton adminBtn = new JButton("管理员用户管理");
        JButton hmBtn = new JButton("护工用户管理");
        topBtnPanel.add(adminBtn);
        topBtnPanel.add(hmBtn);

        JPanel actionBtnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton refreshBtn = new JButton("刷新");
        JButton searchBtn = new JButton("按用户名查询");
        JButton addBtn = new JButton("添加");
        JButton editBtn = new JButton("修改");
        JButton delBtn = new JButton("删除");
        JButton backBtn = new JButton("返回");
        actionBtnPanel.add(refreshBtn);
        actionBtnPanel.add(searchBtn);
        actionBtnPanel.add(addBtn);
        actionBtnPanel.add(editBtn);
        actionBtnPanel.add(delBtn);
        actionBtnPanel.add(backBtn);

        JPanel southPanel = new JPanel(new GridLayout(2, 1));
        southPanel.add(topBtnPanel);
        southPanel.add(actionBtnPanel);
        add(southPanel, BorderLayout.SOUTH);

        adminBtn.addActionListener(e -> { currentMode = 1; titleLabel.setText("管理员用户管理"); refreshTable(); });
        hmBtn.addActionListener(e -> { currentMode = 2; titleLabel.setText("护工用户管理"); refreshTable(); });
        refreshBtn.addActionListener(e -> refreshTable());
        searchBtn.addActionListener(e -> searchByName());
        addBtn.addActionListener(e -> showAddDialog());
        editBtn.addActionListener(e -> showEditDialog());
        delBtn.addActionListener(e -> deleteSelected());
        backBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.ADMIN_MENU));
    }

    /** 刷新表格数据，根据当前管理模式加载对应的用户列表 */
    private void refreshTable() {
        if (currentMode == 0) {
            JOptionPane.showMessageDialog(this, "请先选择要管理的用户类型", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        tableModel.setRowCount(0);
        List<TUser> list = currentMode == 1 ? adminService.findAll() : hmService.findAll();
        for (TUser u : list) {
            String typeStr = u.getUserType() == 1 ? "管理员" : "护工";
            tableModel.addRow(new Object[]{u.getId(), u.getUserName(), u.getPassword(), typeStr});
        }
    }

    /** 按用户名查询，弹出输入框并显示查询结果 */
    private void searchByName() {
        if (currentMode == 0) {
            JOptionPane.showMessageDialog(this, "请先选择要管理的用户类型", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = JOptionPane.showInputDialog(this, "请输入用户名：", "查询", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;
        TUser user = currentMode == 1 ? adminService.findByUserName(name.trim()) : hmService.findByUserName(name.trim());
        tableModel.setRowCount(0);
        if (user != null) {
            String typeStr = user.getUserType() == 1 ? "管理员" : "护工";
            tableModel.addRow(new Object[]{user.getId(), user.getUserName(), user.getPassword(), typeStr});
        } else {
            JOptionPane.showMessageDialog(this, "未找到该用户", "提示", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    /** 显示添加用户的对话框 */
    private void showAddDialog() {
        if (currentMode == 0) {
            JOptionPane.showMessageDialog(this, "请先选择要管理的用户类型", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JTextField nameField = new JTextField(15);
        JTextField pwdField = new JTextField(15);
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));
        panel.add(new JLabel("用户名：")); panel.add(nameField);
        panel.add(new JLabel("密  码：")); panel.add(pwdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "添加用户", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            String name = nameField.getText().trim();
            String pwd = pwdField.getText().trim();
            if (name.isEmpty() || pwd.isEmpty()) {
                JOptionPane.showMessageDialog(this, "请填写完整信息", "提示", JOptionPane.WARNING_MESSAGE);
                return;
            }
            TUser user = new TUser();
            user.setUserName(name);
            user.setPassword(pwd);
            user.setUserType(currentMode);
            String msg = currentMode == 1 ? adminService.addUser(user) : hmService.addUser(user);
            JOptionPane.showMessageDialog(this, msg);
            refreshTable();
        }
    }

    /** 显示修改用户信息的对话框，先按用户名查找再编辑 */
    private void showEditDialog() {
        if (currentMode == 0) {
            JOptionPane.showMessageDialog(this, "请先选择要管理的用户类型", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String name = JOptionPane.showInputDialog(this, "请输入要修改的用户名：", "修改", JOptionPane.QUESTION_MESSAGE);
        if (name == null || name.trim().isEmpty()) return;
        TUser user = currentMode == 1 ? adminService.findByUserName(name.trim()) : hmService.findByUserName(name.trim());
        if (user == null) {
            JOptionPane.showMessageDialog(this, "未找到该用户", "提示", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        JTextField nameField = new JTextField(user.getUserName(), 15);
        JTextField pwdField = new JTextField(user.getPassword(), 15);
        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));
        panel.add(new JLabel("用户名：")); panel.add(nameField);
        panel.add(new JLabel("密  码：")); panel.add(pwdField);

        int result = JOptionPane.showConfirmDialog(this, panel, "修改用户", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            user.setUserName(nameField.getText().trim());
            user.setPassword(pwdField.getText().trim());
            boolean success = currentMode == 1 ? adminService.updateUser(user) : hmService.updateUser(user);
            JOptionPane.showMessageDialog(this, success ? "修改成功" : "修改失败");
            refreshTable();
        }
    }

    /** 删除指定ID的用户，包含确认对话框 */
    private void deleteSelected() {
        if (currentMode == 0) {
            JOptionPane.showMessageDialog(this, "请先选择要管理的用户类型", "提示", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String idStr = JOptionPane.showInputDialog(this, "请输入要删除的用户ID：", "删除", JOptionPane.QUESTION_MESSAGE);
        if (idStr == null || idStr.trim().isEmpty()) return;
        try {
            long id = Long.parseLong(idStr.trim());
            int confirm = JOptionPane.showConfirmDialog(this, "确定要删除ID为 " + id + " 的用户吗？", "确认删除", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                boolean success = currentMode == 1 ? adminService.deleteUser(id) : hmService.deleteUser(id);
                if (success) {
                    JOptionPane.showMessageDialog(this, "删除成功");
                    refreshTable();
                } else {
                    JOptionPane.showMessageDialog(this, "未找到该用户", "提示", JOptionPane.WARNING_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "请输入有效的数字ID", "错误", JOptionPane.ERROR_MESSAGE);
        }
    }
}
