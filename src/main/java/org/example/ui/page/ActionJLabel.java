package org.example.ui.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ActionJLabel extends JLabel {
    // 생성자: 텍스트와 기본 설정 초기화
    public ActionJLabel(String text) {
        super(text);                    // JLabel에 텍스트 설정
        setForeground(Color.BLUE);       // 기본 텍스트 색상 파란색 설정
        setCursor(new Cursor(Cursor.HAND_CURSOR)); // 마우스 커서를 손가락 모양으로 설정

        // 마우스 이벤트 리스너 추가
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setForeground(Color.RED); // 마우스 올릴 때 텍스트 색상 빨간색으로 변경
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setForeground(Color.BLUE); // 마우스가 나가면 텍스트 색상 파란색으로 복원
            }
        });
    }
}
