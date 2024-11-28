package org.example.ui.page;

import org.example.ui.component.panel.UpdateHeaderPanel;
import org.example.ui.component.panel.UpdateMainPanel;
import org.example.ui.component.panel.MyPageNavigationPanel;

import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class UpdateMemberInformationPage extends JPanel {
    private Connection connection;

    public UpdateMemberInformationPage(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(450, 700));

        // 헤더 패널 추가
        UpdateHeaderPanel headerPanel = new UpdateHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // 메인 내용 패널 추가
        JPanel mainPanel = new UpdateMainPanel(); // 나중에 UpdateMainPanel로 대체될 예정
        add(mainPanel, BorderLayout.CENTER);


    }
}




