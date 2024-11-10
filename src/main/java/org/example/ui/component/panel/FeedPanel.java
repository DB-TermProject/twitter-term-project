package org.example.ui.component.panel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

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
        for (int i = 0; i < 10; i++) {
            // parentFrame을 TweetPanel에 전달
            JPanel tweetPanel = new TweetPanel(connection, parentFrame);
            tweetPanel.setMaximumSize(new Dimension(400, 150));
            tweetPanel.setPreferredSize(new Dimension(400, 150));
            mainFeedPanel.add(tweetPanel);
            mainFeedPanel.add(Box.createVerticalStrut(1));
        }
    }

    // 피드 새로고침 메소드
    public void refreshFeed() {
        mainFeedPanel.removeAll();
        try {
            int feedCount = 0;  // 실제로는 DB에서 가져와야 함

            if (feedCount > 0) {
                loadTweets();
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