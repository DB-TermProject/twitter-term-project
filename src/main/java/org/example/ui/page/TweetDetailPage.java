package org.example.ui.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class TweetDetailPage extends JFrame {
    private final Connection connection;
    private final String username;
    private final String handle;
    private final String tweetContent;
    private final String profileImagePath;
    private final String timeStamp;
    private final String commentCount;
    private final String likeCount;

    public TweetDetailPage(Connection con, String username, String handle,
                           String tweetContent, String profileImagePath,
                           String timeStamp, String commentCount, String likeCount) {
        this.connection = con;
        this.username = username;
        this.handle = handle;
        this.tweetContent = tweetContent;
        this.profileImagePath = profileImagePath;
        this.timeStamp = timeStamp;
        this.commentCount = commentCount;
        this.likeCount = likeCount;

        initializeFrame();
        createContent();
    }

    private void initializeFrame() {
        setTitle("Tweet");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true); // 창 보이기

        // 뒤로가기 버튼 처리
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
                // 이전 페이지로 돌아가기
                SwingUtilities.invokeLater(() -> {
                    HomeFeedPage homeFeedPage = new HomeFeedPage(connection);
                    homeFeedPage.setVisible(true);
                });
            }
        });
    }

    private void createContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 상단 헤더 (뒤로가기 버튼 포함)
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 트윗 상세 내용
        JPanel contentPanel = createTweetDetailPanel();
        mainPanel.add(new JScrollPane(contentPanel), BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(Color.WHITE);
        headerPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY));

        // 뒤로가기 버튼
        JButton backButton = new JButton("←");
        backButton.setBorderPainted(false);
        backButton.setContentAreaFilled(false);
        backButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                HomeFeedPage homeFeedPage = new HomeFeedPage(connection);
                homeFeedPage.setVisible(true);
            });
        });

        JLabel titleLabel = new JLabel("Tweet");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        headerPanel.add(backButton, BorderLayout.WEST);
        headerPanel.add(titleLabel, BorderLayout.CENTER);

        return headerPanel;
    }

    private JPanel createTweetDetailPanel() {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // 사용자 정보 패널
        JPanel userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setBackground(Color.WHITE);

        // 프로필 이미지
        try {
            ImageIcon profileIcon = new ImageIcon(profileImagePath);
            Image scaledImage = profileIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            JLabel profileLabel = new JLabel(new ImageIcon(scaledImage));
            userPanel.add(profileLabel, BorderLayout.WEST);
        } catch (Exception e) {
            System.out.println("프로필 이미지 로드 실패");
        }

        // 사용자 이름과 핸들
        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BoxLayout(namePanel, BoxLayout.Y_AXIS));
        namePanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel(username);
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));

        JLabel handleLabel = new JLabel(handle);
        handleLabel.setForeground(Color.GRAY);

        namePanel.add(nameLabel);
        namePanel.add(handleLabel);
        userPanel.add(namePanel, BorderLayout.CENTER);

        // 트윗 내용
        JLabel contentLabel = new JLabel("<html><body style='width: 350px'>" + tweetContent + "</body></html>");
        contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        // 시간 정보
        JLabel timeLabel = new JLabel(timeStamp);
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));

        // 통계 패널 (좋아요, 댓글 수)
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 30, 10));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY));

        // 댓글 수
        JPanel commentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        commentPanel.setBackground(Color.WHITE);
        try {
            ImageIcon commentIcon = new ImageIcon("src/main/java/org/example/ui/page/icons/comment.png");
            Image scaledComment = commentIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel commentIconLabel = new JLabel(new ImageIcon(scaledComment));
            JLabel commentCountLabel = new JLabel(commentCount);
            commentPanel.add(commentIconLabel);
            commentPanel.add(commentCountLabel);
        } catch (Exception e) {
            System.out.println("댓글 아이콘 로드 실패");
        }

        // 좋아요 수
        JPanel likePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        likePanel.setBackground(Color.WHITE);
        try {
            ImageIcon heartIcon = new ImageIcon("src/main/java/org/example/ui/page/icons/heart.png");
            Image scaledHeart = heartIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel heartIconLabel = new JLabel(new ImageIcon(scaledHeart));
            JLabel likeCountLabel = new JLabel(likeCount);
            likePanel.add(heartIconLabel);
            likePanel.add(likeCountLabel);
        } catch (Exception e) {
            System.out.println("하트 아이콘 로드 실패");
        }

        statsPanel.add(commentPanel);
        statsPanel.add(likePanel);

        // 모든 컴포넌트를 detailPanel에 추가
        detailPanel.add(userPanel);
        detailPanel.add(contentLabel);
        detailPanel.add(timeLabel);
        detailPanel.add(statsPanel);

        return detailPanel;
    }
}