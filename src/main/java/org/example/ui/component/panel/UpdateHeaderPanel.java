package org.example.ui.component.panel;

import javax.swing.*;
import java.awt.*;

public class UpdateHeaderPanel extends JPanel {
    public UpdateHeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(450, 40));

        // 왼쪽 로고와 텍스트 컨테이너
        JPanel leftContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftContainer.setBackground(Color.WHITE);

        // 트위터 로고 이미지 크기 조정 및 JLabel 설정
        ImageIcon twitterLogoIcon = new ImageIcon("src/main/java/org/example/asset/twitter.png");
        Image scaledLogo = twitterLogoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel twitterLogo = new JLabel(new ImageIcon(scaledLogo));

        JLabel twitterText = new JLabel("Twitter");
        twitterText.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        leftContainer.add(twitterLogo);
        leftContainer.add(twitterText);

        // 오른쪽 버튼 컨테이너
        JPanel rightContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightContainer.setBackground(Color.WHITE);

        // 수정 완료 버튼
        JButton updateButton = new JButton("Apply Changes");
        updateButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                updateButton.setBackground(Color.LIGHT_GRAY);
                updateButton.setOpaque(true);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                updateButton.setBackground(null);
                updateButton.setOpaque(false);
            }
        });
        updateButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        updateButton.setFocusPainted(false);
        updateButton.setContentAreaFilled(false); // 배경색 제거
        updateButton.setBorderPainted(false);

        rightContainer.add(updateButton);

        // 왼쪽과 오른쪽 컨테이너를 헤더에 추가
        add(leftContainer, BorderLayout.WEST);
        add(rightContainer, BorderLayout.EAST);
    }
}

