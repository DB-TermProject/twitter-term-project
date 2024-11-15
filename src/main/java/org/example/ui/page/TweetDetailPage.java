package org.example.ui.page;

import org.example.domain.comment.dto.CommentResDTO;
import org.example.ui.component.field.TweetField;
import org.example.ui.component.label.ProfileLabel;
import org.example.ui.component.label.UserInfoLabel;
import org.example.ui.component.panel.HeaderPanel;
import org.example.ui.component.panel.InteractionPanel;
import org.example.ui.component.panel.NavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TweetDetailPage extends JFrame {
    private final Connection connection;
    private final String username;
    private final String handle;
    private final String tweetContent;
    private final String profileImagePath;
    private final String timeStamp;
    private final String commentCount;
    private final String likeCount;
    private final int commentId=10;  // 댓글 ID를 저장할 필드
    // 댓글 입력 필드 추가
    private TweetField commentField;
    private boolean isCommentFieldVisible = false;  // 토글 상태 저장
    private TweetField replyField;    // 추가된 부분
    // CommentResDTO.Detail 필드 추가
    private List<CommentResDTO.Detail> commentDetail = new ArrayList<>();


    // 생성자 수정
    public TweetDetailPage(Connection con, String username, String handle,
                           String tweetContent, String profileImagePath,
                           String timeStamp, String commentCount, String likeCount
                           ) {  // commentDetail 파라미터 추가
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
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("Tweet");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setBackground(Color.WHITE);
        setResizable(false);  // 창 크기 조절 불가능하도록 설정

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
        commentField = new TweetField();
        commentField.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentField.setVisible(false);
        commentField.setMaximumSize(new Dimension(getWidth() - 40, commentField.getPreferredSize().height));
        commentSection.add(commentField);
        commentSection.add(Box.createVerticalStrut(10));  // 간격 추가

        // 댓글 목록
        JPanel commentsPanel = createCommentsPanel();
        commentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentSection.add(commentsPanel);

        // 댓글 영역에 스크롤 적용
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

        // 프로필 이미지 (좌측)
        ProfileLabel profileLabel = new ProfileLabel(50, profileImagePath);
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        userPanel.add(profilePanel, BorderLayout.WEST);

        // 오른쪽 패널 (사용자 정보와 트윗 내용을 포함)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        // 상단 패널 (사용자 정보와 옵션 버튼을 포함)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // 사용자 이름과 핸들
        UserInfoLabel tweetUserInfo = new UserInfoLabel(username, handle, timeStamp);
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        userInfoPanel.add(tweetUserInfo);
        topPanel.add(userInfoPanel, BorderLayout.CENTER);

        // 옵션 버튼 (현재 사용자가 작성자인 경우에만)
        if (isCurrentUserAuthor()) {
            JButton optionsButton = new JButton("...");
            optionsButton.setBorderPainted(false);
            optionsButton.setContentAreaFilled(false);
            optionsButton.setFocusPainted(false);
            optionsButton.setFont(new Font("Arial", Font.BOLD, 16));
            optionsButton.setForeground(Color.GRAY);
            optionsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            // 팝업 메뉴 생성
            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JMenuItem editItem = new JMenuItem("수정하기");
            JMenuItem deleteItem = new JMenuItem("삭제하기");

            editItem.setBackground(Color.WHITE);
            deleteItem.setBackground(Color.WHITE);

            // 메뉴 아이템에 이벤트 리스너 추가
            editItem.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> new NewTweetPage(connection));
            });

            deleteItem.addActionListener(e -> {
                // 삭제 기능 구현
            });

            popupMenu.add(editItem);
            popupMenu.add(deleteItem);

            // 옵션 버튼 클릭 이벤트
            optionsButton.addActionListener(e -> {
                popupMenu.show(optionsButton, 0, optionsButton.getHeight());
            });

            JPanel optionsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
            optionsPanel.setBackground(Color.WHITE);
            optionsPanel.add(optionsButton);
            topPanel.add(optionsPanel, BorderLayout.EAST);
        }
        rightPanel.add(topPanel);

        // 트윗 내용
        JPanel contentPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 10, 0));

        JLabel contentLabel = new JLabel("<html><body style='width: 350px; margin-right: 20px; padding-right: 100px'>" + tweetContent + "</body></html>");        contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        contentPanel.add(contentLabel);
        rightPanel.add(contentPanel);

        userPanel.add(rightPanel, BorderLayout.CENTER);

        // 상호작용 패널 (댓글, 좋아요)
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        InteractionPanel interactionPanel = new InteractionPanel(this, true);  // true로 설정하여 메인 트윗에만 댓글 아이콘 표시
        interactionPanel.setCommentCount(String.valueOf(countComments()));
        interactionPanel.setLikeCount(likeCount);

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
        detailPanel.add(statsPanel);

        return detailPanel;
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

    private JPanel createChildCommentPanel(CommentResDTO.Detail childComment) {
        JPanel replyPanel = new JPanel(new BorderLayout(10, 0));
        replyPanel.setBackground(Color.WHITE);
        replyPanel.setBorder(BorderFactory.createEmptyBorder(5, 40, 5, 0));  // 왼쪽 여백으로 들여쓰기

        // 프로필 이미지
        ProfileLabel profileLabel = new ProfileLabel(30, childComment.profileImg());
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        replyPanel.add(profilePanel, BorderLayout.WEST);

        // 내용 패널
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 사용자 정보
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        UserInfoLabel userInfoLabel = new UserInfoLabel(
                childComment.writer(),
                "@" + childComment.writer(),
                childComment.createdAt()
        );
        userInfoPanel.add(userInfoLabel);
        contentPanel.add(userInfoPanel);

        // 답글 내용
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        JLabel replyText = new JLabel("<html><body style='width: 250px'>" +
                childComment.content() + "</body></html>");
        textPanel.add(replyText);
        contentPanel.add(textPanel);

        // 좋아요 정보
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        interactionPanel.setBackground(Color.WHITE);
        InteractionPanel likePanel = new InteractionPanel(this, false);
        likePanel.setLikeCount(String.valueOf(childComment.likeCount()));
        interactionPanel.add(likePanel);
        contentPanel.add(interactionPanel);

        replyPanel.add(contentPanel, BorderLayout.CENTER);
        return replyPanel;
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

        // 메인 컨텐츠 패널
        JPanel mainContentPanel = new JPanel();
        mainContentPanel.setLayout(new BoxLayout(mainContentPanel, BoxLayout.Y_AXIS));
        mainContentPanel.setBackground(Color.WHITE);


        // 댓글 내용 패널 (기존 코드 유지)
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
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        JLabel commentText = new JLabel("<html><body style='width: 300px'>댓글 내용이 여기에 표시됩니다.</body></html>");
        textPanel.add(commentText);
        contentPanel.add(textPanel);


        // 상호작용 패널 (좋아요, 답글)
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        interactionPanel.setBackground(Color.white);


        // 좋아요 버튼
        InteractionPanel likePanel = new InteractionPanel(this, false);
        interactionPanel.add(likePanel);

        // 답글 버튼
        interactionPanel.add(Box.createHorizontalStrut(20));
        JButton replyButton = createReplyButton();
        interactionPanel.add(replyButton);
        contentPanel.add(interactionPanel);

        // 답글 목록 패널
        JPanel repliesPanel = new JPanel();
        repliesPanel.setLayout(new BoxLayout(repliesPanel, BoxLayout.Y_AXIS));
        repliesPanel.setBackground(Color.white);
        repliesPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));  // 왼쪽 여백을 20으로 줄임

        // 답글 입력창과 답글 목록을 포함할 패널
        JPanel replyContainer = new JPanel();
        replyContainer.setLayout(new BoxLayout(replyContainer, BoxLayout.Y_AXIS));
        replyContainer.setBackground(Color.white);
        replyContainer.setBorder(BorderFactory.createEmptyBorder(5, 100, 5, 20));
        replyContainer.setVisible(false);


        contentPanel.add(repliesPanel);
        replyContainer.setVisible(false);

        // 대댓글 입력 필드
        replyField = new TweetField();
        replyField.setAlignmentX(Component.LEFT_ALIGNMENT);
        replyField.setMaximumSize(new Dimension(270, 130));  // 높이 조정

        // replyContainer에 컴포넌트 추가
        replyContainer.add(Box.createVerticalStrut(10));  // 간격 추가
        replyContainer.add(replyField);  // 입력창 추가

        // 답글 버튼 클릭 이벤트
        replyButton.addActionListener(e -> {
            replyContainer.setVisible(!replyContainer.isVisible());
            if (replyContainer.isVisible()) {
                replyField.requestFocus();
                Long currentCommentId = 1L;

                for(CommentResDTO.Detail detail : commentDetail){
                    if (!detail.id().equals(currentCommentId))
                        continue;

                    JPanel replyPanel = createChildCommentPanel(detail);
                    repliesPanel.add(replyPanel);
                    repliesPanel.add(Box.createVerticalStrut(1));
                    repliesPanel.revalidate();
                    repliesPanel.repaint();
                }
            }
        });


        // 전체 구성
        contentPanel.add(repliesPanel);      // 답글 목록 추가
        replyContainer.add(replyField);      // 입력창을 replyContainer에 추가

        commentPanel.add(profilePanel, BorderLayout.WEST);
        commentPanel.add(contentPanel, BorderLayout.CENTER);
        commentPanel.add(replyContainer, BorderLayout.SOUTH);  // 답글 입력창을 아래쪽에 배치
        return commentPanel;
    }

    private JButton createReplyButton() {
        JButton replyButton = new JButton("답글보기");
        replyButton.setBorderPainted(false);
        replyButton.setContentAreaFilled(false);
        replyButton.setFocusPainted(false);
        replyButton.setForeground(Color.GRAY);
        replyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return replyButton;
    }

    // 현재 사용자가 작성자인지 확인하는 메소드
    private boolean isCurrentUserAuthor() {
        // 실제 구현에서는 현재 로그인한 사용자와 피드 작성자를 비교
        return true; // 임시 반환값
    }

}