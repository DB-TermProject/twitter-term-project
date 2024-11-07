package org.example.ui.page;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundJPasswordField extends JPasswordField {
    private Shape shape; // 필드의 경계를 둥글게 하기 위한 Shape 객체

    @Override
    public Dimension getPreferredSize() {
        // 필드의 기본 크기 설정
        return new Dimension(150, 30);
    }

    // 생성자: placeholder와 투명도 설정을 위한 초기화
    public RoundJPasswordField(String placeholder, boolean opaque) {
        super(placeholder.length()); // placeholder 길이만큼 텍스트 필드의 길이 설정
        setText(placeholder);        // 초기 텍스트로 placeholder 설정
        setOpaque(opaque);           // 필드의 투명도 설정
    }

    // 필드 내부에 둥근 배경을 그리는 메서드
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground()); // 배경색 설정
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); // 둥근 사각형 배경
        super.paintComponent(g); // 기본 텍스트 필드 컴포넌트 그리기
    }

    // 필드의 둥근 테두리를 그리는 메서드
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground()); // 테두리 색상 설정
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); // 둥근 테두리 그리기
    }

    // 둥근 모서리 경계를 설정하여 클릭 영역을 제한하는 메서드
    public boolean contains(int x, int y) {
        // shape가 null이거나 크기가 변경된 경우 다시 생성
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
        return shape.contains(x, y); // 경계 내에서만 클릭을 인식
    }
}
