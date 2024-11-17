package org.example.ui.page;

import org.example.ui.component.panel.MyPageHeaderPanel;
import org.example.ui.component.panel.MyPageMainPanel;
import org.example.ui.component.panel.MyPageNavigationPanel;
import org.example.ui.component.panel.NavigationPanel;

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


        // 네비게이션 패널 추가
        NavigationPanel navigationPanel = new NavigationPanel(this, connection);
        navigationPanel.setBounds(0, 610, 450, 60);  // 위치와 크기 설정
        add(navigationPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);  // 화면 중앙에 표시
        setVisible(true);

        setVisible(true);

    }

}


