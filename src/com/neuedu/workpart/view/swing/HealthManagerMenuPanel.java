package com.neuedu.workpart.view.swing;

import com.neuedu.workpart.pojo.ResidentCustomer;
import com.neuedu.workpart.service.ResidentCustomerService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class HealthManagerMenuPanel extends JPanel {
    public HealthManagerMenuPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("护工主菜单", SwingConstants.CENTER);
        title.setFont(new Font("微软雅黑", Font.BOLD, 22));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        add(title, gbc);

        JButton careRecordBtn = new JButton("护理记录管理");
        JButton requirementBtn = new JButton("申请管理");
        JButton customerBtn = new JButton("客户查询");
        JButton logoutBtn = new JButton("退出登录");

        Font btnFont = new Font("微软雅黑", Font.PLAIN, 16);
        careRecordBtn.setFont(btnFont);
        requirementBtn.setFont(btnFont);
        customerBtn.setFont(btnFont);
        logoutBtn.setFont(btnFont);

        gbc.gridwidth = 1;
        gbc.gridx = 0; gbc.gridy = 1; add(careRecordBtn, gbc);
        gbc.gridx = 1; gbc.gridy = 1; add(requirementBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2; add(customerBtn, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        logoutBtn.setBackground(new Color(220, 80, 80));
        logoutBtn.setForeground(Color.WHITE);
        add(logoutBtn, gbc);

        careRecordBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.CARE_RECORD));
        requirementBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.REQUIREMENT));
        customerBtn.addActionListener(e -> showCustomerQueryDialog());
        logoutBtn.addActionListener(e -> MainFrame.showPanel(MainFrame.LOGIN));
    }

    private void showCustomerQueryDialog() {
        ResidentCustomerService service = new ResidentCustomerService();
        String idStr = JOptionPane.showInputDialog(this, "请输入客户编号查询（留空查看全部）：", "客户查询", JOptionPane.QUESTION_MESSAGE);

        if (idStr == null) return;
        idStr = idStr.trim();

        if (idStr.isEmpty()) {
            List<ResidentCustomer> list = service.findAllCustomers();
            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(this, "未找到客户信息", "提示", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            String[] cols = {"编号", "姓名", "性别", "年龄", "身份证号", "楼栋", "房间号",
                    "入院时间", "合同到期", "联系电话", "家属", "护工ID", "护理等级", "在院状态"};
            DefaultTableModel model = new DefaultTableModel(cols, 0) {
                public boolean isCellEditable(int row, int column) { return false; }
            };
            for (ResidentCustomer c : list) {
                String sex = (c.getCustomer_sex() != null && c.getCustomer_sex() == 0) ? "男" : "女";
                String status = (c.getIs_deleted() != null && c.getIs_deleted() == 1) ? "已退住" : "在院";
                String level = c.getCareLevel() == null ? "--" : c.getCareLevel();
                model.addRow(new Object[]{c.getId(), c.getCustomer_name(), sex, c.getCustomer_age(),
                        c.getIdcard(), c.getBuilding_no(), c.getRoom_no(),
                        c.getCheckin_date(), c.getExpiration_date(),
                        c.getContact_tel(), c.getFamily_member(), c.getUser_id(), level, status});
            }
            JTable table = new JTable(model);
            table.setFont(new Font("微软雅黑", Font.PLAIN, 14));
            table.setRowHeight(28);
            table.getTableHeader().setFont(new Font("微软雅黑", Font.BOLD, 14));

            JPanel panel = new JPanel(new BorderLayout(5, 5));
            panel.add(new JScrollPane(table), BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(900, 300));
            JOptionPane.showMessageDialog(this, panel, "客户列表", JOptionPane.PLAIN_MESSAGE);
        } else {
            try {
                int id = Integer.parseInt(idStr);
                ResidentCustomer c = service.findCustomerById(id);
                if (c == null) {
                    JOptionPane.showMessageDialog(this, "未找到客户信息", "提示", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String sex = (c.getCustomer_sex() != null && c.getCustomer_sex() == 0) ? "男" : "女";
                String status = (c.getIs_deleted() != null && c.getIs_deleted() == 1) ? "已退住" : "在院";
                String level = c.getCareLevel() == null ? "未设置" : c.getCareLevel();

                JPanel detail = new JPanel(new GridLayout(14, 2, 8, 6));
                detail.add(new JLabel("编号："));       detail.add(new JLabel(String.valueOf(c.getId())));
                detail.add(new JLabel("姓名："));       detail.add(new JLabel(c.getCustomer_name()));
                detail.add(new JLabel("年龄："));       detail.add(new JLabel(String.valueOf(c.getCustomer_age())));
                detail.add(new JLabel("性别："));       detail.add(new JLabel(sex));
                detail.add(new JLabel("身份证号："));   detail.add(new JLabel(c.getIdcard() == null ? "--" : c.getIdcard()));
                detail.add(new JLabel("楼栋："));       detail.add(new JLabel(c.getBuilding_no() == null ? "--" : c.getBuilding_no()));
                detail.add(new JLabel("房间号："));     detail.add(new JLabel(c.getRoom_no() == null ? "--" : c.getRoom_no()));
                detail.add(new JLabel("入住时间："));   detail.add(new JLabel(c.getCheckin_date() == null ? "--" : c.getCheckin_date()));
                detail.add(new JLabel("合同到期："));   detail.add(new JLabel(c.getExpiration_date() == null ? "--" : c.getExpiration_date()));
                detail.add(new JLabel("联系电话："));   detail.add(new JLabel(c.getContact_tel() == null ? "--" : c.getContact_tel()));
                detail.add(new JLabel("家属："));       detail.add(new JLabel(c.getFamily_member() == null ? "--" : c.getFamily_member()));
                detail.add(new JLabel("护工ID："));     detail.add(new JLabel(c.getUser_id() == null ? "--" : String.valueOf(c.getUser_id())));
                detail.add(new JLabel("护理等级："));   detail.add(new JLabel(level));
                detail.add(new JLabel("在院状态："));   detail.add(new JLabel(status));

                JOptionPane.showMessageDialog(this, detail, "客户详细信息", JOptionPane.PLAIN_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "请输入有效的数字编号", "错误", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
