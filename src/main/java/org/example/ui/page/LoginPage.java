package org.example.ui.page;

import org.example.domain.user.dto.UserResDTO.LoginResponse;
import org.example.domain.user.usecase.UserUseCase;
import org.example.ui.component.button.RoundJButton;
import org.example.ui.component.field.RoundJPasswordField;
import org.example.ui.component.field.RoundJTextField;
import org.example.ui.component.label.ActionJLabel;
import org.example.ui.component.label.ImageJLabel;
import org.example.util.config.UserConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;

import static org.example.domain.user.dto.UserReqDTO.Login;

public class LoginPage extends JPanel {
    private final UserUseCase userUseCase = new UserUseCase();
    private final UserConfig userConfig = UserConfig.getInstance();

    public LoginPage(Connection con) {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // 메인 패널 생성 및 배경색 설정
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Twitter 제목 라벨
        JLabel logInToTwitter = new JLabel("Twitter");
        logInToTwitter.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        logInToTwitter.setHorizontalAlignment(JLabel.CENTER);

        // 트위터 로고
        ImageJLabel twitterLogo = new ImageJLabel("src/main/java/org/example/asset/TwitterLogo.png", 50, 50);
        twitterLogo.setHorizontalAlignment(JLabel.CENTER);

        // 이메일 입력 필드
        JPanel inputPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.CENTER;

        JLabel usernameLabel = new JLabel("Email :           ");
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(18f));

        RoundJTextField usernameField = new RoundJTextField("Email", true);
        usernameField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        usernameField.setPreferredSize(new Dimension(250, 30));

        // 비밀번호 입력 필드
        JPanel inputPanel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.CENTER;

        JLabel passwordLabel = new JLabel("Password :  ");
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(18f));

        RoundJPasswordField passwordField = new RoundJPasswordField("Password", true);
        passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        passwordField.setPreferredSize(new Dimension(250, 30));

        // 로그인 버튼
        RoundJButton loginButton = new RoundJButton("Log in", 60, 350, 55);
        loginButton.setBackground(new Color(29, 161, 242));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("맑은 고딕", Font.BOLD, 24));

        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3.setBackground(Color.WHITE);

        // 비밀번호 찾기 및 회원가입 패널
        JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        panel2.setBackground(Color.WHITE);


        ActionJLabel signUp = new ActionJLabel("Sign up for Twitter");
        signUp.setHorizontalAlignment(JLabel.CENTER);
        signUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame mainFrame = MainFrame.getInstance();
                mainFrame.showSignUpPage();
            }
        });

        // 컴포넌트 추가
        panel.add(logInToTwitter);
        panel.add(twitterLogo);

        inputPanel1.setOpaque(false);
        inputPanel1.setBackground(new Color(0, 0, 0, 0));
        inputPanel1.add(usernameLabel, gbc1);
        gbc1.gridx = 1;
        inputPanel1.add(usernameField, gbc1);
        panel.add(inputPanel1);

        inputPanel2.setOpaque(false);
        inputPanel2.setBackground(new Color(0, 0, 0, 0));
        inputPanel2.add(passwordLabel, gbc2);
        gbc2.gridx = 1;
        inputPanel2.add(passwordField, gbc2);
        panel.add(inputPanel2);

        panel3.add(loginButton);
        panel.add(panel3);

        panel2.add(signUp);
        panel.add(panel2);

        loginButton.addActionListener(e -> {
            String userId = usernameField.getText();
            char[] password = passwordField.getPassword();
            String strPassword = new String(password);

            try {
                // 로그인 검증 로직
                LoginResponse me = userUseCase.login(new Login(userId, strPassword));
                userConfig.setMyId(me.id());

                MainFrame mainFrame = MainFrame.getInstance();
                mainFrame.showHomeFeedPage(me.id());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                        "Wrong ID or Password!",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel, BorderLayout.CENTER);
    }
}