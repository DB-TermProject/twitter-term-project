package org.example.ui.component.panel;

import javax.swing.*;
import java.awt.*;

public class MyPageNavigationPanel extends JPanel {
    public MyPageNavigationPanel() {
        setLayout(new GridLayout(1, 4)); // 1행 4열의 그리드 레이아웃
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(450, 60)); // 패널의 높이를 60으로 설정하여 세로축 길이 증가
        setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1)); // 최소한의 테두리 추가

        // 네비게이션 버튼 생성
        JButton homeButton = new JButton(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/home.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        JButton socialButton = new JButton(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/follow.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        JButton alertButton = new JButton(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/alarm.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));
        JButton myPageButton = new JButton(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/user.png").getImage().getScaledInstance(40, 40, Image.SCALE_SMOOTH)));

        // 버튼 스타일 설정 - 배경색 제거, 테두리 제거, 클릭 느낌을 유지하도록 설정
        JButton[] buttons = {homeButton, socialButton, alertButton, myPageButton};
        for (JButton button : buttons) {
            button.setContentAreaFilled(false);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setOpaque(false);
            button.setRolloverEnabled(true); // 버튼에 마우스를 올릴 때 효과 추가
            button.setPressedIcon(button.getIcon()); // 누를 때 아이콘을 유지하도록 설정
            button.addChangeListener(e -> {
                if (button.getModel().isPressed()) {
                    button.setBackground(Color.LIGHT_GRAY);
                    button.setOpaque(true);
                } else {
                    button.setBackground(Color.WHITE);
                    button.setOpaque(false);
                }
            });
        }

        // 버튼을 패널에 추가
        add(homeButton);
        add(socialButton);
        add(alertButton);
        add(myPageButton);
    }
}



