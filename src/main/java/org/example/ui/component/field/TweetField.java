package org.example.ui.component.field;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class TweetField extends JPanel {
    private final JTextArea textArea;
    private final int maxLength = 280; // 트위터 글자 수 제한

    public TweetField() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(1, 0, 1, 0, Color.LIGHT_GRAY),
                new EmptyBorder(10, 20, 10, 20)
        ));

        // 텍스트 영역 초기화
        textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);  // 자동 줄바꿈
        textArea.setWrapStyleWord(true);  // 단어 단위 줄바꿈
        textArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        textArea.setBorder(null);

        // 플레이스홀더 설정
        textArea.setText("댓글을 입력하세요...");
        textArea.setForeground(Color.GRAY);

        // 텍스트 영역에 이벤트 리스너 추가
        textArea.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (textArea.getText().equals("댓글을 입력하세요...")) {
                    textArea.setText("");
                    textArea.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent evt) {
                if (textArea.getText().isEmpty()) {
                    textArea.setText("댓글을 입력하세요...");
                    textArea.setForeground(Color.GRAY);
                }
            }
        });

        // 글자 수 제한 설정
        textArea.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                checkLength();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                checkLength();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                checkLength();
            }
        });

        // 하단 패널 (글자 수 표시 및 버튼)
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(new EmptyBorder(5, 0, 0, 0));

        // 글자 수 표시 레이블
        JLabel countLabel = new JLabel("0/" + maxLength);
        countLabel.setForeground(Color.GRAY);
        bottomPanel.add(countLabel, BorderLayout.WEST);

        // 댓글 작성 버튼
        JButton replyButton = new JButton("댓글");
        replyButton.setBackground(new Color(29, 155, 240));  // 트위터 파란색
        replyButton.setForeground(Color.WHITE);
        replyButton.setFocusPainted(false);
        replyButton.setBorderPainted(false);
        replyButton.setPreferredSize(new Dimension(70, 30));
        replyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // 버튼 클릭 이벤트
        replyButton.addActionListener(e -> {
            String text = textArea.getText();
            if (!text.equals("댓글을 입력하세요...") && !text.isEmpty()) {
                // TODO: 댓글 저장 로직 구현
                System.out.println("댓글 작성: " + text);
                textArea.setText("댓글을 입력하세요...");
                textArea.setForeground(Color.GRAY);
                countLabel.setText("0/" + maxLength);
            }
        });

        bottomPanel.add(replyButton, BorderLayout.EAST);

        // 컴포넌트 추가
        add(new JScrollPane(textArea), BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    // 글자 수 체크 메소드
    private void checkLength() {
        String text = textArea.getText();
        if (!text.equals("댓글을 입력하세요...")) {
            int length = text.length();
            JLabel countLabel = (JLabel) ((JPanel) getComponent(1)).getComponent(0);
            countLabel.setText(length + "/" + maxLength);

            if (length > maxLength) {
                textArea.setText(text.substring(0, maxLength));
                countLabel.setForeground(Color.RED);
            } else {
                countLabel.setForeground(Color.GRAY);
            }
        }
    }

    // 텍스트 가져오기 메소드
    public String getText() {
        String text = textArea.getText();
        return text.equals("댓글을 입력하세요...") ? "" : text;
    }

    // 텍스트 설정 메소드
    public void setText(String text) {
        textArea.setText(text);
        textArea.setForeground(Color.BLACK);
    }
}