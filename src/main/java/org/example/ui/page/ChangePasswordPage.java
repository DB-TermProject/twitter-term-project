package org.example.ui.page;

import org.example.domain.user.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class ChangePasswordPage extends JFrame {
    private UserService userService;

    public ChangePasswordPage(Connection con) {
        super("Change Password");
        this.userService = new UserService();
        setTitle("Change Password");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setSize(450, 700);
        setResizable(false);
        setLocationRelativeTo(null);

        // 메인 패널 생성
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(100, 20, 0, 20));

        // 상단: 제목 레이블
        JLabel titleLabel = new JLabel("비밀번호 변경", SwingConstants.CENTER);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // 중단: 입력 필드 패널 (GridBagLayout 사용)
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(50, 5, 10, 5); // 컴포넌트 간격 설정

        // 현재 비밀번호
        JLabel currentPasswordLabel = new JLabel("기존 비밀번호:");
        currentPasswordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(currentPasswordLabel, gbc);

        JPasswordField currentPasswordField = new JPasswordField();
        currentPasswordField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 0;
        inputPanel.add(currentPasswordField, gbc);

        // 새 비밀번호
        JLabel newPasswordLabel = new JLabel("새 비밀번호:");
        newPasswordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(newPasswordLabel, gbc);

        JPasswordField newPasswordField = new JPasswordField();
        newPasswordField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 1;
        inputPanel.add(newPasswordField, gbc);

        // 새 비밀번호 확인
        JLabel confirmPasswordLabel = new JLabel("새 비밀번호 확인:");
        confirmPasswordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(confirmPasswordLabel, gbc);

        JPasswordField confirmPasswordField = new JPasswordField();
        confirmPasswordField.setPreferredSize(new Dimension(250, 30));
        gbc.gridx = 1;
        gbc.gridy = 2;
        inputPanel.add(confirmPasswordField, gbc);

        mainPanel.add(inputPanel, BorderLayout.CENTER);

        // 하단: 버튼 패널
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setBackground(Color.WHITE);

        JButton changeButton = new JButton("확인");
        changeButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        changeButton.setBackground(Color.WHITE); // 배경색 흰색
        changeButton.setForeground(Color.BLACK);

        JButton cancelButton = new JButton("취소");
        cancelButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        cancelButton.setBackground(Color.WHITE); // 배경색 흰색
        cancelButton.setForeground(Color.BLACK);

        buttonPanel.add(changeButton);
        buttonPanel.add(cancelButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        add(mainPanel);

        // 창 닫기 이벤트
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(null, "Are You Sure To Exit Twitter?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
                if (flag == JOptionPane.OK_OPTION) {
                    System.exit(0);
                }
            }
        });

        // 버튼 동작 추가
        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();


            }
        });

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }
}





