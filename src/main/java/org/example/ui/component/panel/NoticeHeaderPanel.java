package org.example.ui.component.panel;

import javax.swing.*;
import java.awt.*;

public class NoticeHeaderPanel extends JPanel {
    public NoticeHeaderPanel() {
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

        // 왼쪽 컨테이너를 헤더에 추가
        add(leftContainer, BorderLayout.WEST);
    }
}

