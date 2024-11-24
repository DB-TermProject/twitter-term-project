package org.example.ui.page;

import org.example.domain.comment.dto.CommentResDTO;
import org.example.domain.comment.usecase.CommentUseCase;
import org.example.domain.post.dto.PostResDTO;
import org.example.domain.post.usecase.PostUseCase;
import org.example.ui.component.field.TweetField;
import org.example.ui.component.label.ProfileLabel;
import org.example.ui.component.label.UserInfoLabel;
import org.example.ui.component.panel.HeaderPanel;
import org.example.ui.component.panel.InteractionPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class TweetDetailPage extends JPanel {
    private static TweetDetailPage instance;
    private final PostUseCase postUseCase = new PostUseCase();
    private final CommentUseCase commentUseCase = new CommentUseCase();
    private final Connection connection;
    private PostResDTO.Detail dto; //원래 스태틱 아니였음
    private TweetField commentField;
    private boolean isCommentFieldVisible = false;
    private List<CommentResDTO.Detail> commentDto = new ArrayList<>();

    public static TweetDetailPage getInstance(Connection connection) {
        if(instance == null) {
            instance = new TweetDetailPage(connection);
        }
        return instance;
    }

    private TweetDetailPage(Connection connection) {
        System.out.println("constructor");
        this.connection = connection;
        dto = new PostResDTO.Detail(null, "", 0L, 0L, "", "", "", false);  // 기본값으로 초기화
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        initializeComponents();
    }

    public void fetch(Long postId) {
        SwingUtilities.invokeLater(() -> {
            dto = postUseCase.readPost(postId);
            commentDto.clear();
            commentDto.addAll(commentUseCase.readComments(postId));
            updateContent();
            revalidate();
            repaint();
        });
    }

    private void initializeComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 헤더 패널
        mainPanel.add(new HeaderPanel(connection), BorderLayout.NORTH);

        // 중앙 컨텐츠 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 20));

        // 트윗 상세 내용
        JPanel tweetDetailPanel = createTweetDetailPanel();
        tweetDetailPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(tweetDetailPanel);

        // 구분선
        JSeparator separator = new JSeparator();
        separator.setForeground(Color.LIGHT_GRAY);
        separator.setMaximumSize(new Dimension(450, 1));
        separator.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(separator);

        // 댓글 영역
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
        commentSection.add(Box.createVerticalStrut(10));

        // 댓글 목록
        JPanel commentsPanel = createCommentsPanel();
        commentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        commentSection.add(commentsPanel);

        // 스크롤 패널
        JScrollPane scrollPane = new JScrollPane(commentSection);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        centerPanel.add(scrollPane);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        add(mainPanel, BorderLayout.CENTER);
    }

    private void updateContent() {
        removeAll();
        initializeComponents();
        revalidate();
        repaint();
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
        ProfileLabel profileLabel = new ProfileLabel(50, dto.profileImg());
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        userPanel.add(profilePanel, BorderLayout.WEST);

        // 오른쪽 패널
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBackground(Color.WHITE);

        // 상단 패널
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        // 사용자 이름과 핸들
        UserInfoLabel tweetUserInfo = new UserInfoLabel(dto.writer(), dto.createdAt());
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        userInfoPanel.add(tweetUserInfo);
        topPanel.add(userInfoPanel, BorderLayout.CENTER);

        if (isCurrentUserAuthor()) {
            JButton optionsButton = new JButton("...");
            optionsButton.setBorderPainted(false);
            optionsButton.setContentAreaFilled(false);
            optionsButton.setFocusPainted(false);
            optionsButton.setFont(new Font("Arial", Font.BOLD, 16));
            optionsButton.setForeground(Color.GRAY);
            optionsButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

            JPopupMenu popupMenu = new JPopupMenu();
            popupMenu.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

            JMenuItem editItem = new JMenuItem("수정하기");
            JMenuItem deleteItem = new JMenuItem("삭제하기");
            editItem.setBackground(Color.WHITE);
            deleteItem.setBackground(Color.WHITE);

            editItem.addActionListener(e -> {
                SwingUtilities.invokeLater(() -> new NewTweetPage(connection));
            });

            deleteItem.addActionListener(e -> {
                // 삭제 기능 구현
            });

            popupMenu.add(editItem);
            popupMenu.add(deleteItem);

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

        JLabel contentLabel = new JLabel("<html><body style='width: 350px; margin-right: 20px; padding-right: 100px'>"
                + dto.content() + "</body></html>");
        contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentLabel.setBorder(BorderFactory.createEmptyBorder(5, 0, 10, 0));
        contentPanel.add(contentLabel);
        rightPanel.add(contentPanel);

        userPanel.add(rightPanel, BorderLayout.CENTER);

        // 상호작용 패널
        JPanel statsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        statsPanel.setBackground(Color.WHITE);
        statsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        InteractionPanel interactionPanel = new InteractionPanel(this, true);    // true
        interactionPanel.setCommentCount(String.valueOf(countComments()));
        interactionPanel.setLikeCount(String.valueOf(dto.likeCount()));
        interactionPanel.addCommentClickListener(e -> {
            isCommentFieldVisible = !isCommentFieldVisible;
            commentField.setVisible(isCommentFieldVisible);
            if (isCommentFieldVisible) {
                commentField.requestFocus();
            }
        });
        statsPanel.add(interactionPanel);

        detailPanel.add(userPanel);
        detailPanel.add(statsPanel);

        return detailPanel;
    }

    private int countComments() {
        return commentDto.size();
    }

    private JPanel createCommentsPanel() {
        JPanel commentsPanel = new JPanel();
        commentsPanel.setLayout(new BoxLayout(commentsPanel, BoxLayout.Y_AXIS));
        commentsPanel.setBackground(Color.WHITE);
        commentsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        commentsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        for (CommentResDTO.Detail comment : commentDto) {
            JPanel commentPanel = createCommentPanel(comment);
            commentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
            commentsPanel.add(commentPanel);
            commentsPanel.add(Box.createVerticalStrut(1));
        }

        return commentsPanel;
    }

    private JPanel createCommentPanel(CommentResDTO.Detail comment) {
        JPanel commentPanel = new JPanel(new BorderLayout(10, 0));
        commentPanel.setBackground(Color.WHITE);
        commentPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 0, 10, 0)
        ));

        // 프로필 이미지
        ProfileLabel profileLabel = new ProfileLabel(40, comment.profileImg());
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        profilePanel.setBackground(Color.WHITE);
        profilePanel.add(profileLabel);
        commentPanel.add(profilePanel, BorderLayout.WEST);

        // 내용 패널
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 사용자 정보
        JPanel userInfoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        userInfoPanel.setBackground(Color.WHITE);
        UserInfoLabel userInfoLabel = new UserInfoLabel(
                comment.writer(),
                "@" + comment.writer(),
                comment.createdAt()
        );
        userInfoPanel.add(userInfoLabel);
        contentPanel.add(userInfoPanel);

        // 댓글 내용
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        JLabel commentText = new JLabel("<html><body style='width: 300px'>" + comment.content() + "</body></html>");
        textPanel.add(commentText);
        contentPanel.add(textPanel);

        // 상호작용 패널
        JPanel interactionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        interactionPanel.setBackground(Color.WHITE);
        InteractionPanel likePanel = new InteractionPanel(this, false);
        likePanel.setLikeCount(String.valueOf(comment.likeCount()));
        interactionPanel.add(likePanel);
        contentPanel.add(interactionPanel);

        commentPanel.add(contentPanel, BorderLayout.CENTER);
        return commentPanel;
    }

    private boolean isCurrentUserAuthor() {
        return true;    // true
    }
}