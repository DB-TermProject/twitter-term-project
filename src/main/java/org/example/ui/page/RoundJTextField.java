package org.example.ui.page;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundJTextField extends JTextField {
    private Shape shape; // 필드의 경계를 둥글게 만들기 위한 Shape 객체

    // 생성자: placeholder 텍스트와 투명 여부를 받아 초기화
    public RoundJTextField(String placeholder, boolean opaque) {
        super(placeholder.length()); // placeholder 길이에 맞춰 텍스트 필드 길이 설정
        setText(placeholder);        // 초기 텍스트로 placeholder 설정
        setOpaque(opaque);           // 텍스트 필드 투명도 설정
    }

    @Override
    public Dimension getPreferredSize() {
        // 텍스트 필드의 기본 크기 설정
        return new Dimension(150, 30);
    }

    // 필드 내부에 둥근 배경을 그리는 메서드
    protected void paintComponent(Graphics g) {
        g.setColor(getBackground()); // 배경색 설정
        g.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); // 둥근 사각형 배경
        super.paintComponent(g); // 텍스트와 기타 컴포넌트 그리기
    }

    // 필드의 둥근 테두리를 그리는 메서드
    protected void paintBorder(Graphics g) {
        g.setColor(getForeground()); // 테두리 색 설정
        g.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15); // 둥근 테두리 설정
    }

    // 마우스 이벤트 등에서 필드의 모서리 부분을 포함시키기 위해 경계 설정
    public boolean contains(int x, int y) {
        // shape가 null이거나 크기가 변경된 경우 다시 생성
        if (shape == null || !shape.getBounds().equals(getBounds())) {
            shape = new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        }
        return shape.contains(x, y); // 경계 내 클릭 여부 확인
    }
}
