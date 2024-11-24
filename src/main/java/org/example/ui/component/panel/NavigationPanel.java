package org.example.ui.component.panel;

import org.example.ui.page.MainFrame;

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
                    handleNavigationClick(iconName);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    iconLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    iconLabel.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            return iconLabel;

        } catch (Exception e) {
            System.out.println("아이콘 로드 실패: " + iconName);
            return new JLabel();
        }
    }

    private void handleNavigationClick(String iconName) {
        if (parentFrame instanceof MainFrame) {
            MainFrame mainFrame = (MainFrame) parentFrame;
            switch (iconName) {
                case "home.png":
                    mainFrame.showPage("home");
                    break;
                case "follow.png":
                    mainFrame.showPage("follow");
                    break;
                case "alarm.png":
                    mainFrame.showPage("notice");  // 알림 페이지로 변경 가능
                    break;
                case "user.png":
                    mainFrame.showPage("mypage");
                    break;
            }
        }
    }
}