package org.example.ui.page;

import org.example.domain.user.service.UserService;
import org.example.ui.component.panel.*;
import org.example.ui.component.label.ProfileLabel;  // 추가된 부분

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
        setResizable(false);  // 창 크기 조절 불가능하도록 설정
        setBackground(Color.WHITE);
    }

    private void createContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 헤더 패널
        mainPanel.add(new HeaderPanel(connection), BorderLayout.NORTH);

        // 중앙 컨텐츠를 담을 패널
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // 빠른 글쓰기 패널 (고정)
        JPanel quickPostPanel = createQuickPostPanel();
        quickPostPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),  // 하단 테두리
                BorderFactory.createEmptyBorder(10, 20, 10, 20)  // 패딩
        ));
        centerPanel.add(quickPostPanel, BorderLayout.NORTH);

        // 피드 패널 (스크롤 가능)
        JPanel feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.WHITE);

        // 피드 아이템 추가
        for (int i = 0; i < 10; i++) {
            JPanel tweetPanel = new TweetPanel(connection, this);
            tweetPanel.setMaximumSize(new Dimension(400, 150));
            tweetPanel.setPreferredSize(new Dimension(400, 150));
            feedPanel.add(tweetPanel);
            feedPanel.add(Box.createVerticalStrut(1));
        }

        // 피드 패널만 스크롤 가능하도록 설정
        JScrollPane scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        centerPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(new NavigationPanel(this), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createQuickPostPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

        // 크기 설정 추가
        panel.setMaximumSize(new Dimension(400, 60));
        panel.setPreferredSize(new Dimension(400, 60));

        // 프로필 이미지
        ProfileLabel profileLabel = new ProfileLabel(40, "src/main/java/org/example/asset/profileImage.png");
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        panel.add(profilePanel, BorderLayout.WEST);

        // 빈 입력 영역
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 0));

        JLabel placeholderLabel = new JLabel("무슨 일이 일어나고 있나요?");
        placeholderLabel.setForeground(Color.GRAY);
        placeholderLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        placeholderLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dispose();
                SwingUtilities.invokeLater(() -> new NewTweetPage(connection));
            }
        });

        contentPanel.add(placeholderLabel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        // 패널을 감싸는 컨테이너 추가
        JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(panel);

        return containerPanel;
    }

    // 피드 새로고침 메소드
    public void refreshFeed() {
        feedPanel.refreshFeed();
    }
}