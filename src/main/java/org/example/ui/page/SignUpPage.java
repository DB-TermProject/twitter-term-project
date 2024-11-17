package org.example.ui.page;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

public class SignUpPage extends JFrame {

    public SignUpPage(Connection con) {


        setTitle("Twitter Sign Up");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(null);

        // 배경색 설정 (흰색)
        getContentPane().setBackground(Color.WHITE);

        // 로고 이미지 불러와서 크기 조절
        ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/asset/TwitterLogo.png"); // 트위터 로고 이미지 파일 경로
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);

        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setBounds(10, 10, 50, 50); // 로고 위치와 크기 설정
        add(logoLabel);

        // 타이틀 "twitter" 텍스트 설정
        JLabel titleLabel = new JLabel("twitter");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 35));
        titleLabel.setBounds(70, 10, 150, 50);
        add(titleLabel);

        // "Sign Up" 타이틀 추가
        JLabel signUpLabel = new JLabel("Create a new account");
        signUpLabel.setFont(new Font("맑은 고딕", Font.BOLD, 25));
        signUpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        signUpLabel.setBounds(25, 90, 400, 50);
        add(signUpLabel);

        // Email 입력 필드 레이블
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setBounds(50, 180, 80, 25);
        add(emailLabel);

        // 둥근 모양의 Email 입력 텍스트 필드
        RoundedTextField emailField = new RoundedTextField(15);
        emailField.setBounds(130, 180, 250, 30);
        add(emailField);

        // Password 입력 필드 레이블
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(50, 260, 80, 25);
        add(passwordLabel);

        // 둥근 모양의 Password 입력 필드
        RoundedPasswordField passwordField = new RoundedPasswordField(15);
        passwordField.setBounds(130, 260, 250, 30);
        add(passwordField);

        // 이름 입력 필드 레이블
        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(50, 340, 80, 25);
        add(nameLabel);

        // 둥근 모양의 이름 입력 텍스트 필드
        RoundedTextField nameField = new RoundedTextField(15);
        nameField.setBounds(130, 340, 250, 30);
        add(nameField);

        // 안내 메시지 레이블
        JLabel guideLabel = new JLabel("<html>By clicking Sign Up, you become a member of our Twitter.<br>You can see and like many posts.</html>");
        guideLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        guideLabel.setForeground(Color.GRAY); // 흐릿한 색상 적용
        guideLabel.setBounds(60, 395, 320, 40); // 위치 조정
        add(guideLabel);

        // Sign Up 버튼
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 470, 150, 40);
        signUpButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(new Color(0, 200, 0)); // 녹색 배경
        signUpButton.setOpaque(true);
        signUpButton.setBorderPainted(false);

        // 마우스 커서 모양 변경 (손가락 모양)
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Sign Up 버튼 클릭 이벤트 구현
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String password = new String(passwordField.getPassword());
                String name = nameField.getText();

                // 필드가 비어 있는지 확인하는 조건 추가
                if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpPage.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (email.equals("test@example.com")) {
                    // 특정 이메일일 경우 이미 사용 중이라는 메시지 + 중복된 이메일일 경우
                    JOptionPane.showMessageDialog(SignUpPage.this, "This Email is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 모든 정보가 올바르게 입력되었을 경우
                    JOptionPane.showMessageDialog(SignUpPage.this, "<html>Congratulations!<br>Sign Up is complete</html>");
                    dispose(); // 현재 창 닫기
                    // new NextPage(); // 다음 페이지 열기 (주석 처리)
                }
            }
        });

        add(signUpButton);

        // 이미 계정이 있는 경우 로그인 페이지로 이동 안내 레이블
        JLabel alreadyAccountLabel = new JLabel("Already have an account?");
        alreadyAccountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        alreadyAccountLabel.setForeground(new Color(0, 120, 255)); // 파란색 텍스트
        alreadyAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alreadyAccountLabel.setBounds(0, 535, 450, 30);

        // 마우스를 올리면 색상이 변경되며, 클릭 시 LoginPage 열기
        alreadyAccountLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        alreadyAccountLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                alreadyAccountLabel.setForeground(new Color(0, 100, 200));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                alreadyAccountLabel.setForeground(new Color(0, 120, 255));
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // 현재 SignUpPage 창 닫기
                // new LoginPage(); // LoginPage 창 열기 (임시 주석처리)
            }
        });

        add(alreadyAccountLabel);

        setVisible(true);
    }

    // 둥근 모양의 JTextField 커스텀 클래스
    class RoundedTextField extends JTextField {
        private int arcWidth;

        public RoundedTextField(int arcWidth) {
            super();
            this.arcWidth = arcWidth;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcWidth);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcWidth);
            g2.dispose();
        }
    }

    // 둥근 모양의 JPasswordField 커스텀 클래스
    class RoundedPasswordField extends JPasswordField {
        private int arcWidth;

        public RoundedPasswordField(int arcWidth) {
            super();
            this.arcWidth = arcWidth;
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), arcWidth, arcWidth);
            super.paintComponent(g);
            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arcWidth, arcWidth);
            g2.dispose();
        }
    }

    // 메인 메서드: 프로그램 시작 지점

}
