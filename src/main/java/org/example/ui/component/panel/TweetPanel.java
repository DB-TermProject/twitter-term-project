package org.example.ui.component.panel;

import org.example.ui.component.label.ProfileLabel;
import org.example.ui.component.label.UserInfoLabel;
import org.example.ui.page.TweetDetailPage;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class TweetPanel extends JPanel {
    private final Connection connection;
    private final JFrame parentFrame;

    public TweetPanel(Connection connection, JFrame parentFrame) {
        this.connection = connection;
        this.parentFrame = parentFrame;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout(10, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.white, 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        // 왼쪽 프로필 이미지
        add(createProfileSection(), BorderLayout.WEST);

        // 오른쪽 컨텐츠 영역
        add(createContentSection(), BorderLayout.CENTER);

        // 패널 크기 설정
        setMaximumSize(new Dimension(350, 200));
        setPreferredSize(new Dimension(350, 200));

        // 클릭 이벤트 추가
        addMouseListener(new TweetClickListener());
    }

    private JPanel createProfileSection() {
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profilePanel.setBackground(Color.WHITE);

        // ProfileLabel 컴포넌트 사용
        ProfileLabel profileLabel = new ProfileLabel(40, "src/main/java/org/example/asset/profileImage.png");
        profilePanel.add(profileLabel);

        return profilePanel;
    }

    private JPanel createContentSection() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.WHITE);

        // 사용자 정보 (UserInfoLabel 컴포넌트 사용)
        UserInfoLabel userInfoLabel = new UserInfoLabel("사용자 이름", "@handle", "2h");
        userInfoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 트윗 내용
        JLabel tweetContent = new JLabel(
                "<html><body style='width: 240px'>" +
                        "여기에 트윗 내용이 들어갑니다. 긴 내용의 텍스트도 자동으로 줄바꿈이 됩니다." +
                        "</body></html>"
        );
        tweetContent.setBorder(BorderFactory.createEmptyBorder(2, 0, 0, 0));
        tweetContent.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 상호작용 패널 (InteractionPanel 컴포넌트 사용)
        InteractionPanel interactionPanel = new InteractionPanel(parentFrame);
        interactionPanel.setAlignmentX(Component.LEFT_ALIGNMENT);

        // 컴포넌트 추가
        contentPanel.add(userInfoLabel);
        contentPanel.add(Box.createVerticalStrut(2));
        contentPanel.add(tweetContent);
        contentPanel.add(Box.createVerticalStrut(10));
        contentPanel.add(interactionPanel);

        return contentPanel;
    }

    private class TweetClickListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            if (!isClickedOnInteractionPanel(e.getPoint())) {
                parentFrame.setVisible(false);
                SwingUtilities.invokeLater(() -> {
                    TweetDetailPage detailPage = new TweetDetailPage(
                            connection,
                            "사용자 이름",
                            "@handle",
                            "가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사가다나라맙마사",
                            "src/main/java/org/example/asset/profileImage.png",
                            "2h",
                            "0",
                            "0"
                    );
                    detailPage.setVisible(true);
                });
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            if (!isClickedOnInteractionPanel(e.getPoint())) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }
        }

        @Override
        public void mouseExited(MouseEvent e) {
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        }
    }

    private boolean isClickedOnInteractionPanel(Point clickPoint) {
        Component[] components = getComponents();
        for (Component component : components) {
            if (component instanceof JPanel) {
                JPanel contentPanel = (JPanel) component;
                Component[] contentComponents = contentPanel.getComponents();
                for (Component c : contentComponents) {
                    if (c instanceof InteractionPanel) {
                        Point relativePoint = SwingUtilities.convertPoint(this, clickPoint, c);
                        return c.contains(relativePoint);
                    }
                }
            }
        }
        return false;
    }

    // 필요한 경우 트윗 내용을 업데이트하는 메소드들
    public void updateContent(String username, String handle, String content) {
        Component contentPanel = getComponent(1);
        if (contentPanel instanceof JPanel) {
            Component userInfoLabel = ((JPanel) contentPanel).getComponent(0);
            Component tweetContent = ((JPanel) contentPanel).getComponent(2);

            if (userInfoLabel instanceof UserInfoLabel) {
                ((UserInfoLabel) userInfoLabel).setUsername(username);
                ((UserInfoLabel) userInfoLabel).setHandle(handle);
            }

            if (tweetContent instanceof JLabel) {
                ((JLabel) tweetContent).setText("<html><body style='width: 240px'>" + content + "</body></html>");
            }
        }
    }
}