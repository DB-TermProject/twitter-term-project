package org.example.ui.component.button;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class RoundButton2 extends JButton {
    public RoundButton2(String label) {
        super(label);
        setFocusable(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false); // 버튼을 완전히 투명하게 설정
        setPreferredSize(new Dimension(80, 80)); // 버튼 크기 설정
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setClip(new Ellipse2D.Float(0, 0, getWidth(), getHeight())); // 원형으로 클리핑 설정
        super.paintComponent(g2);
        g2.dispose();
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

