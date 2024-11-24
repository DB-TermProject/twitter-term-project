package org.example.ui.page;

import org.example.ui.component.panel.UpdateHeaderPanel;
import org.example.ui.component.panel.UpdateMainPanel;
import org.example.ui.component.panel.MyPageNavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UpdateMemberInformationPage extends JFrame {
    private Connection connection;

    public UpdateMemberInformationPage(Connection connection) {
        this.connection = connection;
        setTitle("Update Member Information Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        // 헤더 패널 추가
        UpdateHeaderPanel headerPanel = new UpdateHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 메인 내용 패널 추가 (아직 구현되지 않았으므로 빈 패널로 대체)
        JPanel mainPanel = new UpdateMainPanel(); // 나중에 UpdateMainPanel로 대체될 예정
        add(mainPanel, BorderLayout.CENTER);

        // 내비게이션 패널 추가 (아직 구현되지 않았으므로 빈 패널로 대체)
        JPanel navigationPanel = new MyPageNavigationPanel(); // 나중에 UpdateNavigationPanel로 대체될 예정
        add(navigationPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

}



