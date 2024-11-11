package org.example.ui.component.panel;

import org.example.ui.page.NewTweetPage;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import org.example.ui.component.label.ProfileLabel;  // 추가된 부분


public class FeedPanel extends JPanel {
    private final Connection connection;
    private final JPanel mainFeedPanel;
    private final JFrame parentFrame;  // 상위 프레임 참조 추가

    public FeedPanel(Connection connection, JFrame parentFrame) {  // 생성자 수정
        this.connection = connection;
        this.parentFrame = parentFrame;  // 상위 프레임 저장
        this.mainFeedPanel = new JPanel();
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        mainFeedPanel.setLayout(new BoxLayout(mainFeedPanel, BoxLayout.Y_AXIS));
        mainFeedPanel.setBackground(Color.WHITE);

        try {
            int feedCount = 10;  // 실제로는 DB에서 가져와야 함

            if (feedCount > 0) {
                loadTweets();
            } else {
                mainFeedPanel.add(new EmptyFeedPanel());
            }
        } catch (Exception e) {
            System.out.println("피드 로드 실패: " + e.getMessage());
            e.printStackTrace();
        }

        JScrollPane scrollPane = new JScrollPane(mainFeedPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    private void loadTweets() {
        // 빠른 글쓰기 패널 추가
        JPanel quickPostPanel = createQuickPostPanel();
        quickPostPanel.setMaximumSize(new Dimension(400, 100));
        mainFeedPanel.add(quickPostPanel);

        // 구분선 추가
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setMaximumSize(new Dimension(450, 1));
        mainFeedPanel.add(separator);

        // 기존 피드 목록 추가
        for (int i = 0; i < 10; i++) {
            JPanel tweetPanel = new TweetPanel(connection, parentFrame);
            tweetPanel.setMaximumSize(new Dimension(400, 150));
            tweetPanel.setPreferredSize(new Dimension(400, 150));
            mainFeedPanel.add(tweetPanel);
            mainFeedPanel.add(Box.createVerticalStrut(1));
        }
    }

    private JPanel createQuickPostPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        // 전체 패널에 라인 보더 추가
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),  // 바깥쪽 라인 보더
                BorderFactory.createEmptyBorder(10, 20, 10, 20)    // 안쪽 여백
        ));

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

        // 클릭 이벤트 추가
        placeholderLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (parentFrame != null) {
                    parentFrame.dispose();
                }
                SwingUtilities.invokeLater(() -> {
                    new NewTweetPage(connection);
                });
            }
        });

        contentPanel.add(placeholderLabel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        return panel;
    }

    // 피드 새로고침 메소드
    public void refreshFeed() {
        mainFeedPanel.removeAll();
        try {
            // 빠른 글쓰기 패널은 항상 표시
            JPanel quickPostPanel = createQuickPostPanel();
            quickPostPanel.setMaximumSize(new Dimension(400, 100));
            mainFeedPanel.add(quickPostPanel);

            // 구분선 추가
            JSeparator separator = new JSeparator();
            separator.setForeground(Color.LIGHT_GRAY);
            separator.setMaximumSize(new Dimension(450, 1));
            mainFeedPanel.add(separator);

            int feedCount = 0;  // 실제로는 DB에서 가져와야 함

            if (feedCount > 0) {
                // 기존 피드 목록 추가
                for (int i = 0; i < 10; i++) {
                    JPanel tweetPanel = new TweetPanel(connection, parentFrame);
                    tweetPanel.setMaximumSize(new Dimension(400, 150));
                    tweetPanel.setPreferredSize(new Dimension(400, 150));
                    mainFeedPanel.add(tweetPanel);
                    mainFeedPanel.add(Box.createVerticalStrut(1));
                }
            } else {
                mainFeedPanel.add(new EmptyFeedPanel());
            }

            mainFeedPanel.revalidate();
            mainFeedPanel.repaint();
        } catch (Exception e) {
            System.out.println("피드 새로고침 실패: " + e.getMessage());
            e.printStackTrace();
        }
    }
}