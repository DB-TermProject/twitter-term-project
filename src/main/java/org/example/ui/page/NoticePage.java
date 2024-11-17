package org.example.ui.page;

import org.example.ui.component.panel.MyPageNavigationPanel;
import org.example.ui.component.panel.NoticeHeaderPanel;
import org.example.ui.component.panel.NoticeMainPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class NoticePage extends JFrame {
    private Connection connection;

    public NoticePage(Connection connection) {
        this.connection = connection;
        setTitle("Notice Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        // 헤더 패널 추가
        NoticeHeaderPanel headerPanel = new NoticeHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 메인 내용 패널 추가 (스크롤 가능하도록 설정)
        NoticeMainPanel mainPanel = new NoticeMainPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);

        // 내비게이션 패널 추가 (기존 패널 재사용)
        MyPageNavigationPanel navigationPanel = new MyPageNavigationPanel();
        add(navigationPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) {
        Connection connection = null; // 여기에 실제 Connection 객체 초기화 필요
        SwingUtilities.invokeLater(() -> new NoticePage(connection));
    }
}


