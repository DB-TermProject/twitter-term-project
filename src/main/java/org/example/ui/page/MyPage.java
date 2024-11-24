package org.example.ui.page;

import org.example.ui.component.panel.MyPageHeaderPanel;
import org.example.ui.component.panel.MyPageMainPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MyPage extends JPanel {
    private Connection connection;

    public MyPage(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 헤더 패널 추가
        MyPageHeaderPanel headerPanel = new MyPageHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 메인 내용 패널 추가
        MyPageMainPanel mainPanel = new MyPageMainPanel();
        add(mainPanel, BorderLayout.CENTER);

    }
}