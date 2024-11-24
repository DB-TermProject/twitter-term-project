package org.example.ui.page;

import org.example.ui.component.panel.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class MainFrame extends JFrame {
    private static MainFrame instance;
    private JPanel contentPanel;
    private CardLayout cardLayout;
    private Connection connection;

    public MainFrame(Connection connection) {
        instance = this;
        this.connection = connection;
        initializeFrame();
        setupPanels();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Twitter");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
    }

    private void setupPanels() {
        contentPanel = new JPanel();
        cardLayout = new CardLayout();
        contentPanel.setLayout(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // 인증 관련 페이지들 (네비게이션 바 없음)
        contentPanel.add(new LoginPage(connection), "login");
        contentPanel.add(new SignUpPage(connection), "signup");
        contentPanel.add(new ChangePasswordPage(connection), "changePassword");

        // 메인 페이지들 (네비게이션 바 포함)
        JPanel homePanel = new JPanel(new BorderLayout());
        homePanel.add(HomeFeedPage.getInstance(connection), BorderLayout.CENTER);
        homePanel.add(new NavigationPanel(this, connection), BorderLayout.SOUTH);
        contentPanel.add(homePanel, "home");

        JPanel followPanel = new JPanel(new BorderLayout());
        followPanel.add(new FollowPage(connection), BorderLayout.CENTER);
        followPanel.add(new NavigationPanel(this, connection), BorderLayout.SOUTH);
        contentPanel.add(followPanel, "follow");

        JPanel noticePanel = new JPanel(new BorderLayout());
        noticePanel.add(new NoticePage(connection), BorderLayout.CENTER);
        noticePanel.add(new NavigationPanel(this, connection), BorderLayout.SOUTH);
        contentPanel.add(noticePanel, "notice");

        JPanel myPagePanel = new JPanel(new BorderLayout());
        myPagePanel.add(new MyPage(connection), BorderLayout.CENTER);
        myPagePanel.add(new NavigationPanel(this, connection), BorderLayout.SOUTH);
        contentPanel.add(myPagePanel, "mypage");

        // 수정된 부분
        JPanel newTweetPanel = new JPanel(new BorderLayout());
        newTweetPanel.add(new NewTweetPage(connection), BorderLayout.CENTER);
        newTweetPanel.add(new NavigationPanel(this, connection), BorderLayout.SOUTH);
        contentPanel.add(newTweetPanel, "newTweet");

        JPanel detailPanel = new JPanel(new BorderLayout());
        detailPanel.add(TweetDetailPage.getInstance(connection), BorderLayout.CENTER);
        contentPanel.add(detailPanel, "detail");

        add(contentPanel, BorderLayout.CENTER);

        // 초기 페이지를 명시적으로 login으로 설정
        SwingUtilities.invokeLater(() -> {
            cardLayout.show(contentPanel, "login");
        });
    }

    public static MainFrame getInstance() {
        return instance;
    }

    // 페이지 전환 메소드
    public void showPage(String pageName) {
        cardLayout.show(contentPanel, pageName);
    }

    public void showNewTweetPage() {
        cardLayout.show(contentPanel, "newTweet");
    }

    // 데이터베이스 연결 getter
    public Connection getConnection() {
        return connection;
    }


    public void showSignUpPage() {
        cardLayout.show(contentPanel, "signup");
    }

    public JPanel getContentPanel() {
        return contentPanel;
    }

    public void showHomeFeedPage(Long userId) {
        cardLayout.show(contentPanel, "home");
        HomeFeedPage.getInstance(connection).fetch(userId);
    }

    public void showTweetDetailPage(Long postId) {
        cardLayout.show(contentPanel, "detail");
        TweetDetailPage.getInstance(connection).fetch(postId);
    }
}