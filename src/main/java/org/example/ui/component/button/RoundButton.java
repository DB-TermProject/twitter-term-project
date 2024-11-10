package org.example.ui.component.button;

import javax.swing.*;
import java.awt.*;

public class RoundButton extends JButton {
    public RoundButton(String text) {
        super(text);
        // 버튼 텍스트 설정
        setContentAreaFilled(false); // 기본 배경 제거
        setBorderPainted(false); // 테두리 제거
        setFocusPainted(false); // 포커스 효과 제거
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isPressed()) {
            g.setColor(Color.DARK_GRAY); // 눌렀을 때 색상 (원하는 색으로 변경 가능)
        } else {
            g.setColor(getBackground()); // 기본 배경 색상
        }
        g.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getHeight()); // 원형 모양으로 채움
        super.paintComponent(g); // 텍스트와 아이콘을 그리는 부분
    }
}
