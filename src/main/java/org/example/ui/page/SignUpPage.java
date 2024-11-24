package org.example.ui.page;

import org.example.domain.user.dto.UserReqDTO;
import org.example.domain.user.usecase.UserUseCase;
import org.example.util.config.UserConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;


public class SignUpPage extends JPanel {
    private final UserUseCase userUseCase = new UserUseCase();
    private final UserConfig userConfig = UserConfig.getInstance();

    public SignUpPage(Connection con) {
        setLayout(null);
        setBackground(Color.WHITE);

        // 로고 이미지 불러와서 크기 조절
        ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/asset/TwitterLogo.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(50, 40, Image.SCALE_SMOOTH);
        ImageIcon resizedIcon = new ImageIcon(scaledImage);
        JLabel logoLabel = new JLabel(resizedIcon);
        logoLabel.setBounds(10, 10, 50, 50);
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

        // Email 입력 필드
        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setBounds(50, 180, 80, 25);
        add(emailLabel);

        RoundedTextField emailField = new RoundedTextField(15);
        emailField.setBounds(130, 180, 250, 30);
        add(emailField);

        // Password 입력 필드
        JLabel passwordLabel = new JLabel("Password :");
        passwordLabel.setBounds(50, 260, 80, 25);
        add(passwordLabel);

        RoundedPasswordField passwordField = new RoundedPasswordField(15);
        passwordField.setBounds(130, 260, 250, 30);
        add(passwordField);

        // 이름 입력 필드
        JLabel nameLabel = new JLabel("Name :");
        nameLabel.setBounds(50, 340, 80, 25);
        add(nameLabel);

        RoundedTextField nameField = new RoundedTextField(15);
        nameField.setBounds(130, 340, 250, 30);
        add(nameField);

        // 안내 메시지
        JLabel guideLabel = new JLabel("<html>By clicking Sign Up, you become a member of our Twitter.<br>You can see and like many posts.</html>");
        guideLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        guideLabel.setForeground(Color.GRAY);
        guideLabel.setBounds(60, 395, 320, 40);
        add(guideLabel);

        // Sign Up 버튼
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(150, 470, 150, 40);
        signUpButton.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setBackground(new Color(0, 200, 0));
        signUpButton.setOpaque(true);
        signUpButton.setBorderPainted(false);
        signUpButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signUpButton.addActionListener(e -> {
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String name = nameField.getText();

            try {
                userUseCase.signUp(new UserReqDTO.SignUp(email, password, name));
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            if (email.isEmpty() || password.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (email.equals("test@example.com")) {
                JOptionPane.showMessageDialog(this, "This Email is already in use.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "<html>Congratulations!<br>Sign Up is complete</html>");
                Window window = SwingUtilities.getWindowAncestor(this);
                if (window != null) {
                    window.setVisible(false);
                }
                SwingUtilities.invokeLater(() -> new LoginPage(con));
            }
        });
        add(signUpButton);

        // 이미 계정이 있는 경우 로그인 페이지로 이동
        JLabel alreadyAccountLabel = new JLabel("Already have an account?");
        alreadyAccountLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        alreadyAccountLabel.setForeground(new Color(0, 120, 255));
        alreadyAccountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        alreadyAccountLabel.setBounds(0, 535, 450, 30);
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
                MainFrame mainFrame = MainFrame.getInstance();
                mainFrame.showPage("login");
            }
        });
        add(alreadyAccountLabel);
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

}
