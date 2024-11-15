package org.example.ui.page;

import org.example.ui.component.panel.HeaderPanel;
import org.example.ui.component.panel.NavigationPanel;
import javax.swing.*;
import java.awt.*;
import java.sql.Connection;

public class NewTweetPage extends JFrame {
    private final Connection connection;
    private final JTextArea tweetArea;
    private static final int MAX_LENGTH = 280;

    public NewTweetPage(Connection connection) {
        this.connection = connection;
        this.tweetArea = new JTextArea();
        initializeFrame();
        createContent();
        setVisible(true);
    }

    private void initializeFrame() {
        setTitle("새 트윗");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void createContent() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 헤더 패널
        mainPanel.add(new HeaderPanel(connection), BorderLayout.NORTH);

        // 중앙 컨텐츠 패널
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 입력 영역을 포함하는 패널
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        // 트윗 입력 영역
        tweetArea.setLineWrap(true);
        tweetArea.setWrapStyleWord(true);
        tweetArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        tweetArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPanel.add(new JScrollPane(tweetArea), BorderLayout.CENTER);

        // 하단 패널 (글자 수 카운트)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // 글자 수 카운트
        JLabel countLabel = new JLabel("0/" + MAX_LENGTH);
        countLabel.setForeground(Color.GRAY);
        bottomPanel.add(countLabel, BorderLayout.WEST);

        // 글자 수 제한 설정
        tweetArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                updateCount(countLabel);
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                updateCount(countLabel);
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                updateCount(countLabel);
            }
        });

        centerPanel.add(contentPanel, BorderLayout.CENTER);
        centerPanel.add(bottomPanel, BorderLayout.SOUTH);

        // 하단 컨테이너
        JPanel bottomContainer = new JPanel(new BorderLayout());
        bottomContainer.setBackground(Color.WHITE);

        // 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));  // 중앙 정렬, 버튼 사이 간격 220
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        // 취소 버튼
        JButton cancelButton = new JButton("취소");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setBorderPainted(true);
        cancelButton.setPreferredSize(new Dimension(70, 30));
        cancelButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        cancelButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                HomeFeedPage homeFeedPage = new HomeFeedPage(connection);
                homeFeedPage.setVisible(true);
            });
        });

        // 글쓰기 버튼
        JButton writeButton = new JButton("글쓰기");
        writeButton.setBackground(Color.WHITE);
        writeButton.setForeground(Color.BLACK);
        writeButton.setFocusPainted(false);
        writeButton.setBorderPainted(true);
        writeButton.setPreferredSize(new Dimension(70, 30));
        writeButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        writeButton.addActionListener(e -> {
            dispose();
            SwingUtilities.invokeLater(() -> {
                HomeFeedPage homeFeedPage = new HomeFeedPage(connection);
                homeFeedPage.setVisible(true);
            });
        });

        // 버튼들을 패널에 추가
        buttonPanel.add(cancelButton);
        buttonPanel.add(writeButton);

        bottomContainer.add(buttonPanel, BorderLayout.NORTH);

        bottomContainer.add(new NavigationPanel(this, connection), BorderLayout.SOUTH);

        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomContainer, BorderLayout.SOUTH);

        add(mainPanel);
    }


    private void updateCount(JLabel countLabel) {
        String text = tweetArea.getText();
        if (!text.equals("무슨 일이 일어나고 있나요?")) {
            int length = text.length();
            countLabel.setText(length + "/" + MAX_LENGTH);

            if (length > MAX_LENGTH) {
                tweetArea.setText(text.substring(0, MAX_LENGTH));
                countLabel.setForeground(Color.RED);
            } else {
                countLabel.setForeground(Color.GRAY);
            }
        }
    }
}