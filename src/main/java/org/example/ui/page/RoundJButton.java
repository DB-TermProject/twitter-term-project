package org.example.ui.page;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundJButton extends JButton {
    private int radius; // 버튼의 모서리 둥글기 반지름
    private int width; // 버튼의 너비
    private int height; // 버튼의 높이

    // 생성자: 라벨, 둥글기 반지름, 너비, 높이를 받아 초기화
    public RoundJButton(String label, int radius, int width, int height) {
        super(label);
        this.radius = radius;
        this.width = width;
        this.height = height;
        setOpaque(false);
    }

    // 버튼 배경을 둥글게 그리는 메서드
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 고품질 렌더링
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius); // 둥근 사각형 버튼 배경 생성
        super.paintComponent(g2);
        g2.dispose();
    }

    // 버튼의 테두리를 둥글게 그리는 메서드
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radius, radius); // 둥근 테두리 설정
        g2.dispose();
    }
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height); // 버튼의 기본 크기 설정

    }
}


