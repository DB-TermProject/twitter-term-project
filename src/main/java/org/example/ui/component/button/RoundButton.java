package org.example.ui.component.button;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton extends JButton {
    public RoundButton(String label) {
        super(label);
        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false); // 버튼을 완전히 투명하게 설정
        setPreferredSize(new Dimension(30, 30)); // 버튼 크기 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        if (getModel().isArmed()) {
            g.setColor(Color.LIGHT_GRAY); // 버튼 클릭 시 색상 변경
        } else {
            g.setColor(new Color(0, 0, 0, 0)); // 완전히 투명한 배경색 설정
        }
        g.fillOval(0, 0, getWidth(), getHeight());

        // 아이콘을 표시하도록 기본 동작 수행
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        // 테두리 제거
    }


    @Override
    public boolean contains(int x, int y) {
        Shape shape = new Ellipse2D.Float(0, 0, getWidth(), getHeight());
        return shape.contains(x, y);
    }
}


