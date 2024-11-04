package org.example.ui.page;

import org.example.domain.user.service.UserService;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class LoginPage extends JFrame {
    private final UserService userService = new UserService();

    public LoginPage(Connection con) {
        super("Twitter Login");
        setTitle("Twitter Login");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(null, "Are You Sure To Exit Twitter?","Confirm", JOptionPane.OK_CANCEL_OPTION);
                if(flag == 0){
                    System.exit(0);
                }
            }
        });

        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        // panel 상하좌우로 10px씩 여백 패딩
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

//        ImageJLabel twitterLogo = new ImageJLabel("../TwitterLogo.png", 40,40);

        // Log in to Twitter Label
        JLabel logInToTwitter = new JLabel("Log in to Twitter");
        logInToTwitter.setFont(new Font("sans-serif", Font.BOLD, 26));
        logInToTwitter.setHorizontalAlignment(JLabel.CENTER);

        // username, phone, email 텍스트 필드
//        RoundJTextField usernameField = new RoundJTextField("Email or User ID",true);
//        usernameField.setFont(new Font("맑은 고딕",Font.PLAIN,16));


        // password 패스워드 필드, JLayeredPane
        /**
         * 패스워드 필드위에 텍스트 띄우는 기능 보류
         */
//        RoundJPasswordField passwordField = new RoundJPasswordField("Password", true);

        // 버튼
//        RoundJButton loginButton = new RoundJButton("Log in",60);

        // 비밀번호 찾기, 회원가입을 위한 panel
        JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        panel2.setBackground(Color.WHITE);

        // 비밀번호 찾기 JLabel
//        ActionJLabel findPassword = new ActionJLabel("Forgot Password?");
//        findPassword.setHorizontalAlignment(JLabel.RIGHT);
//        findPassword.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                dispose();
//                SwingUtilities.invokeLater(() -> {
//                    FindPage findPage = new FindPage(con);
//                    findPage.setVisible(true);
//                });
//            }
//        });

        // 회원가입 JLabel
//        ActionJLabel signUp = new ActionJLabel("Sign up for Twitter");
//        signUp.setHorizontalAlignment(JLabel.LEFT);
//        signUp.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                setVisible(false);
//                SwingUtilities.invokeLater(() -> {
//                    SignUpPage signUpPage = new SignUpPage(con);
//                });;
//            }
//        });

//        panel.add(twitterLogo);
        panel.add(logInToTwitter);
//        panel.add(usernameField);
//        panel.add(passwordField);
//        panel.add(loginButton);
        panel.add(panel2);
//        panel2.add(findPassword);
//        panel2.add(signUp);


//        loginButton.addActionListener(e -> {
//            String userId = usernameField.getText();
//            char[] password = passwordField.getPassword();
//            String strPassword = new String(password);
            setVisible(false);
//            userService.logInUser(userId,strPassword,con,this);
//        });

        add(panel);

        setSize(450, 550);
        setResizable(false); // Make the frame not resizable
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
