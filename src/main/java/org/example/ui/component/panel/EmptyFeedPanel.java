package org.example.ui.component.panel;

import javax.swing.*;
import java.awt.*;

public class EmptyFeedPanel extends JPanel {

    public EmptyFeedPanel() {
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(100, 20, 0, 20));

        // 메인 메시지
        JLabel messageLabel = new JLabel("아직 작성된 트윗이 없습니다");
        messageLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        messageLabel.setForeground(Color.GRAY);
        messageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 부가 설명
        JLabel subMessageLabel = new JLabel("첫 번째 트윗을 작성해보세요!");
        subMessageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        subMessageLabel.setForeground(Color.GRAY);
        subMessageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        // 트윗 작성 버튼
        JButton createTweetButton = new JButton("트윗 작성하기");
        createTweetButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        createTweetButton.setForeground(Color.WHITE);
        createTweetButton.setBackground(new Color(29, 161, 242)); // 트위터 파란색
        createTweetButton.setBorderPainted(false);
        createTweetButton.setFocusPainted(false);
        createTweetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        createTweetButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        createTweetButton.addActionListener(e -> {
            // TODO: 트윗 작성 페이지로 이동하는 로직 구현
            System.out.println("트윗 작성 버튼 클릭됨");
        });

        // 컴포넌트 추가
        add(Box.createVerticalGlue());
        add(messageLabel);
        add(Box.createVerticalStrut(10));
        add(subMessageLabel);
        add(Box.createVerticalStrut(20));
        add(createTweetButton);
        add(Box.createVerticalGlue());
    }
}