package org.example.ui.page;

import org.example.domain.user.service.UserService;
import org.example.ui.component.field.RoundJTextField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class UpdateMemberInformationPage extends JFrame {
    private UserService userService;

    public UpdateMemberInformationPage(Connection con) {
        super("회원 정보 변경");
        this.userService = new UserService();
        setTitle("회원 정보 변경");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        setSize(450, 550);
        setResizable(false); // 창 크기 조절 비활성화
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setVisible(true); // 창 보이기



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

        // 메인 패널 생성
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(Color.WHITE);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 0, 20, 0); // 줄 사이 여백을 20px로 설정

        // 첫 번째 줄: 회원 정보 변경 텍스트와 '저장' 버튼
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("회원 정보 변경", SwingConstants.LEFT);
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton saveButton = new JButton("수정 완료");
        saveButton.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLACK);
        saveButton.setBorderPainted(false);
        saveButton.setFocusPainted(false);
        topPanel.add(saveButton, BorderLayout.EAST);

        gbc.gridy = 0; // 첫 번째 줄 위치
        mainPanel.add(topPanel, gbc);

        // 세 번째 줄: 사용자 이름
        JPanel namePanel = new JPanel(new BorderLayout());
        namePanel.setBackground(Color.WHITE);

        JLabel nameLabel = new JLabel("Trump", SwingConstants.LEFT);
        nameLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        namePanel.add(nameLabel, BorderLayout.WEST);

        gbc.gridy = 2; // 세 번째 줄 위치
        mainPanel.add(namePanel, gbc);

        // 네 번째 줄: Public Account, Private Account 버튼
        JPanel accountPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 5));
        accountPanel.setBackground(Color.WHITE);

        JButton publicButton = new JButton("Public Account");
        JButton privateButton = new JButton("Private Account");

        publicButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        privateButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        publicButton.setBackground(Color.WHITE);
        privateButton.setBackground(Color.WHITE);
        publicButton.setForeground(Color.BLUE);
        privateButton.setForeground(Color.BLACK);
        publicButton.setBorderPainted(false);
        privateButton.setBorderPainted(false);

        publicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                publicButton.setForeground(Color.BLUE);
                privateButton.setForeground(Color.BLACK);
            }
        });
        privateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                privateButton.setForeground(Color.BLUE);
                publicButton.setForeground(Color.BLACK);
            }
        });

        accountPanel.add(publicButton);
        accountPanel.add(privateButton);

        gbc.gridy = 3; // 네 번째 줄 위치
        mainPanel.add(accountPanel, gbc);

        // 다섯 번째 줄: 자기소개 입력 필드
        JPanel introducePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        introducePanel.setBackground(Color.WHITE);

        RoundJTextField introduceField = new RoundJTextField("introduce", true);
        introduceField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        introduceField.setPreferredSize(new Dimension(250, 30));

        introducePanel.add(introduceField);
        gbc.gridy = 4; // 다섯 번째 줄 위치
        mainPanel.add(introducePanel, gbc);

        // 여섯 번째 줄: 추가 필드 입력
        JPanel additionalPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        additionalPanel.setBackground(Color.WHITE);

        RoundJTextField additionalField = new RoundJTextField("additional", true);
        additionalField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        additionalField.setPreferredSize(new Dimension(250, 30));

        additionalPanel.add(additionalField);
        gbc.gridy = 5; // 여섯 번째 줄 위치
        mainPanel.add(additionalPanel, gbc);

        add(mainPanel);
    }
}


