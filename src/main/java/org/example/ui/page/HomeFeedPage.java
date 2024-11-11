package org.example.ui.page;

import org.example.domain.user.service.UserService;
import org.example.ui.component.panel.*;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class HomeFeedPage extends JFrame {
    private final UserService userService;
    private final Connection connection;
    private final FeedPanel feedPanel;

    public HomeFeedPage(Connection connection) {
        this.connection = connection;
        this.userService = new UserService();
        initializeFrame();
        this.feedPanel = new FeedPanel(connection, this);
        createContent();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Twitter");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBackground(Color.WHITE);
    }

    private void createContent() {
        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 각 섹션 추가
        mainPanel.add(new HeaderPanel(connection), BorderLayout.NORTH);  // 헤더
        mainPanel.add(feedPanel, BorderLayout.CENTER);                   // 피드
        mainPanel.add(new NavigationPanel(this), BorderLayout.SOUTH);   // 네비게이션

        add(mainPanel);
    }

    // 피드 새로고침 메소드
    public void refreshFeed() {
        feedPanel.refreshFeed();
    }
}