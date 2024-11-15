package org.example.ui.page;

import org.example.ui.component.panel.MyPageHeaderPanel;
import org.example.ui.component.panel.MyPageMainPanel;
import org.example.ui.component.panel.MyPageNavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MyPage extends JFrame {
    private Connection connection;

    public MyPage(Connection connection) {
        this.connection = connection;
        setTitle("My Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        // 헤더 패널 추가
        MyPageHeaderPanel headerPanel = new MyPageHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 메인 내용 패널 추가
        MyPageMainPanel mainPanel = new MyPageMainPanel();
        add(mainPanel, BorderLayout.CENTER);

        // 내비게이션 패널 추가
        MyPageNavigationPanel navigationPanel = new MyPageNavigationPanel();
        add(navigationPanel, BorderLayout.SOUTH);

        setVisible(true);

    }

    public static void main(String[] args) {
        Connection connection = null; // 여기에 실제 Connection 객체 초기화 필요
        SwingUtilities.invokeLater(() -> new MyPage(connection));
    }
}


