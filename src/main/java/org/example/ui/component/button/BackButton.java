package org.example.ui.component.button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BackButton extends JButton {

    public BackButton() {
        initialize();
        addListeners();
    }

    private void initialize() {
        // 버튼 텍스트 설정
        setText("←");

        // 버튼 폰트 설정
        setFont(new Font("맑은 고딕", Font.PLAIN, 20));

        // 버튼 스타일 설정
        setBorderPainted(false);  // 테두리 제거
        setContentAreaFilled(false);  // 배경 채우기 제거
        setFocusPainted(false);  // 포커스 표시 제거

        // 버튼 크기 설정
        setPreferredSize(new Dimension(50, 30));

        // 배경색 설정
        setBackground(Color.WHITE);
    }

    private void addListeners() {
        // 마우스 이벤트 처리
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    // 버튼 UI 커스터마이징
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 버튼이 눌렸을 때의 효과
        if (getModel().isPressed()) {
            g2.setColor(new Color(240, 240, 240));
            g2.fillOval(0, 0, getWidth(), getHeight());
        }

        g2.dispose();
        super.paintComponent(g);
    }
}