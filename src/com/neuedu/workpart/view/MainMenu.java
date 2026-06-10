package com.neuedu.workpart.view;

import com.neuedu.workpart.view.swing.MainFrame;
import javax.swing.*;

/**
 * 应用程序入口类。
 * 通过SwingUtilities在EDT线程中启动Swing图形界面主窗口
 */
public class MainMenu {

    /**
     * 主方法，启动东软颐养中心管理系统的图形界面
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame.getInstance().setVisible(true);
        });
    }
}
