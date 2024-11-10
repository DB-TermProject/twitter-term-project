package org.example.ui.page;

import org.example.ui.component.panel.NavigationPanel;
import org.example.ui.component.label.ProfileLabel;
import org.example.ui.component.label.UserInfoLabel;
import org.example.ui.component.panel.HeaderPanel;
import org.example.ui.component.panel.InteractionPanel;
import org.example.ui.component.field.TweetField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TweetDetailPage extends JFrame {
    private final Connection connection;
    private final String username;
    private final String handle;
    private final String tweetContent;
    private final String profileImagePath;
    private final String timeStamp;
    private final String commentCount;
    private final String likeCount;
    // 댓글 입력 필드 추가
    private TweetField commentField;
    private boolean isCommentFieldVisible = false;  // 토글 상태 저장


    public TweetDetailPage(Connection con, String username, String handle,
                           String tweetContent, String profileImagePath,
                           String timeStamp, String commentCount, String likeCount) {
        this.connection = con;
        this.username = username;
        this.handle = handle;
        //this.tweetContent = tweetContent;
        this.tweetContent = "가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하가나다라마바사아자차카타파하";
        this.profileImagePath = profileImagePath;
        this.timeStamp = timeStamp;
        this.commentCount = commentCount;
        this.likeCount = likeCount;

        initializeFrame();
        createContent();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Tweet");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
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

        // 헤더 패널
        mainPanel.add(new HeaderPanel(connection), BorderLayout.NORTH);

        // 중앙 컨텐츠 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // 1. 트윗 상세 내용 (고정)
        JPanel tweetDetailPanel = createTweetDetailPanel();
        tweetDetailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(tweetDetailPanel);

        // 2. 구분선
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setMaximumSize(new Dimension(450, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(separator);

        // 3. 댓글 영역 (스크롤 가능)
        JPanel commentSection = new JPanel();
        commentSection.setLayout(new BoxLayout(commentSection, BoxLayout.Y_AXIS));
        commentSection.setBackground(Color.WHITE);
        commentSection.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 댓글 입력 필드
        commentField = new TweetField();  // 클래스 필드 초기화
        commentField.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentField.setVisible(false);  // 기본적으로 숨김 상태로 설정
        commentSection.add(commentField);
        commentSection.add(Box.createVerticalStrut(10));

        // 댓글 목록
        JPanel commentsPanel = createCommentsPanel();
        commentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentSection.add(commentsPanel);

        // 댓글 영역에만 스크롤 적용
        JScrollPane scrollPane = new JScrollPane(commentSection);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);

        centerPanel.add(scrollPane);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 하단 네비게이션 바
        mainPanel.add(new NavigationPanel(this), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createTweetDetailPanel() {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new BoxLayout(detailPanel, BoxLayout.Y_AXIS));
        detailPanel.setBackground(Color.WHITE);
        detailPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // 사용자 정보 영역
        JPanel userPanel = new JPanel(new BorderLayout(10, 0));
        userPanel.setBackground(Color.WHITE);
        userPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 프로필 이미지
        ProfileLabel profileLabel = new ProfileLabel(50, profileImagePath);
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        userPanel.add(profilePanel, BorderLayout.WEST);

        // 사용자 이름과 핸들
        UserInfoLabel userInfoLabel = new UserInfoLabel(username, handle, timeStamp);
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        userInfoPanel.add(userInfoLabel);
        userPanel.add(userInfoPanel, BorderLayout.CENTER);

        // 트윗 내용
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        JLabel contentLabel = new JLabel("<html><body style='width: 350px'>" + tweetContent + "</body></html>");
        contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        contentPanel.add(contentLabel);


        JLabel timeLabel = new JLabel(timeStamp);
        timeLabel.setForeground(Color.GRAY);
        timeLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // 상호작용 패널 (댓글, 좋아요)
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

// 이 부분을 아래와 같이 수정
        InteractionPanel interactionPanel = new InteractionPanel(this);
        interactionPanel.setCommentCount(String.valueOf(countComments()));
        interactionPanel.setLikeCount(likeCount);

// 댓글 클릭 리스너 추가
        interactionPanel.addCommentClickListener(e -> {
            isCommentFieldVisible = !isCommentFieldVisible;
            commentField.setVisible(isCommentFieldVisible);
            if (isCommentFieldVisible) {
                commentField.requestFocus();
            }
        });

        statsPanel.add(interactionPanel);

        // 컴포넌트 추가
        detailPanel.add(userPanel);
        detailPanel.add(contentPanel);
        detailPanel.add(statsPanel);

        return detailPanel;
    }
    // 댓글 입력창 토글 메소드 추가
    private void toggleCommentField() {
        isCommentFieldVisible = !isCommentFieldVisible;
        commentField.setVisible(isCommentFieldVisible);
        if (isCommentFieldVisible) {
            commentField.requestFocus();
        }
    }

    // 댓글 수를 카운트하는 메소드 추가
    private int countComments() {
        // TODO: 실제로는 데이터베이스에서 댓글 수를 가져와야 함
        return 5;  // 현재는 임시로 5개로 설정
    }

    private JPanel createCommentsPanel() {
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBackground(Color.WHITE);
        commentsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));  // 좌측 여백 제거
        commentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);  // 패널 자체를 왼쪽 정렬

        // 댓글 목록
        for (int i = 0; i < 5; i++) {
            JPanel commentPanel = createCommentPanel();
            commentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);  // 각 댓글 패널도 왼쪽 정렬
            commentsPanel.add(commentPanel);
            commentsPanel.add(Box.createVerticalStrut(1));
        }

        return commentsPanel;
    }

    private JPanel createCommentPanel() {
        JPanel commentPanel = new JPanel(new BorderLayout(10, 0));
        commentPanel.setBackground(Color.WHITE);
        commentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        // 프로필 이미지
        ProfileLabel profileLabel = new ProfileLabel(40, "src/main/java/org/example/asset/profileImage.png");
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        commentPanel.add(profilePanel, BorderLayout.WEST);

        // 메인 컨텐츠 패널 (댓글 내용 + 대댓글)
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(Color.WHITE);

        // 댓글 내용 패널
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 사용자 정보
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        UserInfoLabel userInfoLabel = new UserInfoLabel("댓글 작성자", "@commenter", "1h");
        userInfoPanel.add(userInfoLabel);
        contentPanel.add(userInfoPanel);

        // 댓글 텍스트
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));  // 왼쪽에 5픽셀 여백 추가
        JLabel commentText = new JLabel("<html><body style='width: 300px'>댓글 내용이 여기에 표시됩니다.</body></html>");
        textPanel.add(commentText);
        contentPanel.add(textPanel);

        // 상호작용 패널 (좋아요, 답글)
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        interactionPanel.setBackground(Color.WHITE);

        // 좋아요 버튼
        InteractionPanel likePanel = new InteractionPanel(this);
        interactionPanel.add(likePanel);

        // 답글 버튼
        JButton replyButton = createReplyButton();
        interactionPanel.add(replyButton);

        contentPanel.add(interactionPanel);

        // 대댓글 입력 필드 (기본적으로 숨김)
        TweetField replyField = new TweetField();
        replyField.setVisible(false);

        // 대댓글 목록 패널
        JPanel repliesPanel = new JPanel();
        repliesPanel.setLayout(new BoxLayout(repliesPanel, BoxLayout.Y_AXIS));
        repliesPanel.setBackground(Color.WHITE);
        repliesPanel.setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));  // 왼쪽 여백으로 들여쓰기
        repliesPanel.setVisible(false);  // 기본적으로 숨김

        // 답글 버튼 클릭 이벤트
        replyButton.addActionListener(e -> {
            replyField.setVisible(!replyField.isVisible());  // 토글
            if (!repliesPanel.isVisible()) {
                repliesPanel.setVisible(true);
                // 대댓글 로드 (임시 데이터)
                loadReplies(repliesPanel);
            }
        });

        // 전체 구성
        mainContentPanel.add(contentPanel);
        mainContentPanel.add(replyField);
        mainContentPanel.add(repliesPanel);

        commentPanel.add(mainContentPanel, BorderLayout.CENTER);
        return commentPanel;
    }
    private JButton createReplyButton() {
        JButton replyButton = new JButton("답글");
        replyButton.setBorderPainted(false);
        replyButton.setContentAreaFilled(false);
        replyButton.setFocusPainted(false);
        replyButton.setForeground(Color.GRAY);
        replyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return replyButton;
    }

    private void loadReplies(JPanel repliesPanel) {
        // TODO: 데이터베이스에서 해당 댓글의 답글 목록을 가져와서 표시
        // 현재는 테스트를 위해 한 개만 표시
        JPanel replyPanel = createReplyPanel();
        repliesPanel.add(replyPanel);
        repliesPanel.add(Box.createVerticalStrut(1));
    }

    private JPanel createReplyPanel() {
        JPanel replyPanel = new JPanel(new BorderLayout(10, 0));
        replyPanel.setBackground(Color.WHITE);
        replyPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));

        // 프로필 이미지
        ProfileLabel profileLabel = new ProfileLabel(30, "src/main/java/org/example/asset/profileImage.png");
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        replyPanel.add(profilePanel, BorderLayout.WEST);

        // 내용
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 사용자 정보
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        UserInfoLabel userInfoLabel = new UserInfoLabel("답글 작성자", "@replier", "30m");
        userInfoPanel.add(userInfoLabel);
        contentPanel.add(userInfoPanel);

        // 답글 내용
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));  // 왼쪽에 5픽셀 여백 추가
        JLabel replyText = new JLabel("<html><body style='width: 250px'>답글 내용입니다.</body></html>");
        textPanel.add(replyText);
        contentPanel.add(textPanel);

        // 좋아요 버튼
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        interactionPanel.setBackground(Color.WHITE);
        interactionPanel.add(new InteractionPanel(this));
        contentPanel.add(interactionPanel);

        replyPanel.add(contentPanel, BorderLayout.CENTER);
        return replyPanel;
    }
}