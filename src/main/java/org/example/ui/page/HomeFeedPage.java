package org.example.ui.page;

import org.example.domain.post.dto.PostResDTO;
import org.example.domain.post.usecase.PostUseCase;
import org.example.ui.component.label.ProfileLabel;
import org.example.ui.component.panel.HeaderPanel;
import org.example.ui.component.panel.TweetPanel;
import org.example.util.config.UserConfig;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class HomeFeedPage extends JPanel {
    private static HomeFeedPage instance;
    private final PostUseCase postUseCase = new PostUseCase();
    private final Connection connection;
    private final UserConfig userConfig = UserConfig.getInstance();
    private List<PostResDTO.Detail> data = new ArrayList<>();
    private JPanel feedPanel;
    private JScrollPane scrollPane;
    private JPanel centerPanel;

    public static HomeFeedPage getInstance(Connection connection) {
        if (instance == null) {
            instance = new HomeFeedPage(connection);
        }
        return instance;
    }

    private HomeFeedPage(Connection connection) {
        this.connection = connection;
        setBackground(Color.WHITE);
        setLayout(new BorderLayout());
        initializeComponents();
    }

    private void initializeComponents() {
        // 헤더 패널
        add(new HeaderPanel(connection), BorderLayout.NORTH);

        // 중앙 패널 초기화
        centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);

        // 빠른 글쓰기 패널
        JPanel quickPostPanel = createQuickPostPanel();
        quickPostPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        centerPanel.add(quickPostPanel, BorderLayout.NORTH);

        // 피드 패널 초기화
        feedPanel = new JPanel();
        feedPanel.setLayout(new BoxLayout(feedPanel, BoxLayout.Y_AXIS));
        feedPanel.setBackground(Color.WHITE);

        // 스크롤 패널 설정
        scrollPane = new JScrollPane(feedPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void fetch(Long userId) {
        SwingUtilities.invokeLater(() -> {
            data.clear();
            data.addAll(postUseCase.readHomeFeed(userId));
            updateFeedPanel();
        });
    }

    private void updateFeedPanel() {
        feedPanel.removeAll();

        for (PostResDTO.Detail detail : data) {
            JPanel tweetPanel = new TweetPanel(connection, MainFrame.getInstance(), detail);
            tweetPanel.setMaximumSize(new Dimension(400, 150));
            tweetPanel.setPreferredSize(new Dimension(400, 150));
            tweetPanel.setMinimumSize(new Dimension(400, 150));

            tweetPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    MainFrame mainFrame = MainFrame.getInstance();
                    mainFrame.showTweetDetailPage(detail.id());
                }
            });

            feedPanel.add(tweetPanel);
            feedPanel.add(Box.createVerticalStrut(1));
        }

        feedPanel.revalidate();
        feedPanel.repaint();
        scrollPane.revalidate();
        scrollPane.repaint();
    }

    private JPanel createQuickPostPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));

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
                Window window = SwingUtilities.getWindowAncestor(HomeFeedPage.this);
                if (window instanceof MainFrame) {
                    ((MainFrame) window).showNewTweetPage();
                }
            }
        });

        contentPanel.add(placeholderLabel, BorderLayout.CENTER);
        panel.add(contentPanel, BorderLayout.CENTER);

        JPanel containerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
        containerPanel.setBackground(Color.WHITE);
        containerPanel.add(panel);

        return containerPanel;
    }
}