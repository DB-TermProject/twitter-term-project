package org.example.ui.component.panel;

import org.example.ui.component.button.BackButton;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;


public class HeaderPanel extends JPanel {
    private final Connection connection;

    public HeaderPanel(Connection connection) {
        this.connection = connection;
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.white));

        // 트위터 로고와 제목을 담을 패널
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        titlePanel.setBackground(Color.WHITE);

        try {
            // 트위터 로고 로드
            ImageIcon logoIcon = new ImageIcon("src/main/java/org/example/asset/twitter.png");
            Image scaledLogo = logoIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledLogo));

            // 제목 레이블
            JLabel titleLabel = new JLabel("Twitter");
            titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

            titlePanel.add(logoLabel);
            titlePanel.add(titleLabel);
        } catch (Exception e) {
            System.out.println("로고 로드 실패");
        }

        add(titlePanel, BorderLayout.CENTER);
    }
}