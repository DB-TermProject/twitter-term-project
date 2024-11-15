package org.example.ui.component.panel;

import org.example.ui.page.FollowPage;
import org.example.ui.page.HomeFeedPage;
import org.example.ui.page.MyPage;
import org.example.ui.page.NewTweetPage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class NavigationPanel extends JPanel {
    private final JFrame parentFrame;
    private final Connection connection;

    public NavigationPanel(JFrame parentFrame, Connection connection) {
        this.parentFrame = parentFrame;
        this.connection = connection;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 40, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));
        setPreferredSize(new Dimension(450, 60));

        // 네비게이션 아이콘 추가
        add(createNavigationIcon("home.png"));
        add(createNavigationIcon("follow.png"));
        add(createNavigationIcon("alarm.png"));
        add(createNavigationIcon("user.png"));
    }

    private JLabel createNavigationIcon(String iconName) {
        try {
            ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/asset/" + iconName);
            Image scaledImage = originalIcon.getImage().getScaledInstance(25, 25, Image.SCALE_SMOOTH);
            JLabel iconLabel = new JLabel(new ImageIcon(scaledImage));
            iconLabel.setPreferredSize(new Dimension(40, 40));

            // 마우스 이벤트 처리
            iconLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    handleNavigationClick(connection, iconName);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    iconLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));  // 변경된 부분
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    iconLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));  // 변경된 부분
                }
            });

            return iconLabel;

        } catch (Exception e) {
            System.out.println("아이콘 로드 실패: " + iconName);
            return new JLabel();
        }
    }

    private void handleNavigationClick(Connection connection, String iconName) {
        // 네비게이션 클릭 처리
        switch (iconName) {
            case "home.png":
                parentFrame.dispose();  // 현재 창 닫기
                SwingUtilities.invokeLater(() -> {
                    HomeFeedPage homePage = new HomeFeedPage(connection);
                    homePage.setVisible(true);
                });
                break;
            case "follow.png":
                parentFrame.dispose();  // 현재 창 닫기
                SwingUtilities.invokeLater(() -> {
                    FollowPage followPage = new FollowPage(connection);
                    followPage.setVisible(true);
                });
                break;
            case "alarm.png":
                parentFrame.dispose();  // 현재 창 닫기
                SwingUtilities.invokeLater(() -> {
                    HomeFeedPage homePage = new HomeFeedPage(connection);
                    homePage.setVisible(true);
                });
                break;
            case "user.png":
                parentFrame.dispose();  // 현재 창 닫기
                SwingUtilities.invokeLater(() -> {
                    MyPage myPage = new MyPage(connection);
                    myPage.setVisible(true);
                });
                break;
        }
    }
}