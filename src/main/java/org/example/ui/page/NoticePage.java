package org.example.ui.page;

import org.example.ui.component.panel.NoticeHeaderPanel;
import org.example.ui.component.panel.NoticeMainPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class NoticePage extends JPanel {
    private Connection connection;

    public NoticePage(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 헤더 패널 추가
        NoticeHeaderPanel headerPanel = new NoticeHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 메인 내용 패널 추가 (스크롤 가능하도록 설정)
        NoticeMainPanel mainPanel = new NoticeMainPanel();
        JScrollPane scrollPane = new JScrollPane(mainPanel,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }
}