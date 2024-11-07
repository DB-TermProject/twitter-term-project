package org.example.ui.page;

import javax.swing.*;
import java.sql.Connection;

public class FindPage extends JFrame {
    // 생성자: FindPage 창 초기화
    public FindPage(Connection con) {
        setTitle("Find Password");       // 창 제목 설정
        setSize(300, 200);                // 창 크기 설정
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 창 닫을 시 메모리에서 해제
        setLocationRelativeTo(null);      // 창을 화면 중앙에 배치
    }
}
