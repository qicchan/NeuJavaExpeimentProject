package com.neuedu.workpart.view;

import com.neuedu.workpart.view.swing.MainFrame;
import javax.swing.*;

public class MainMenu {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame.getInstance().setVisible(true);
        });
    }
}
