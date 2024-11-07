package org.example.ui.page;

import org.example.domain.user.service.UserService;
import org.example.ui.design.button.RoundJButton;
import org.example.ui.design.field.RoundJPasswordField;
import org.example.ui.design.field.RoundJTextField;
import org.example.ui.design.label.ActionJLabel;
import org.example.ui.design.label.ImageJLabel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;

public class LoginPage extends JFrame {
    private UserService userService; // UserService 객체 선언

    public LoginPage(Connection con) {
        super("Twitter Login");
        this.userService = new UserService(); // UserService 객체 초기화
        setTitle("Twitter Login");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        // 창 닫기 버튼 클릭 시 종료 여부를 묻는 확인 창 띄우기
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int flag = JOptionPane.showConfirmDialog(null, "Are You Sure To Exit Twitter?", "Confirm", JOptionPane.OK_CANCEL_OPTION);
                if (flag == 0) { // 확인 선택 시 프로그램 종료
                    System.exit(0);
                }
            }
        });

        // 메인 패널 생성 및 배경색 설정
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // panel 상하좌우로 10px씩 여백 패딩


        // Twitter 제목 라벨 생성 및 설정
        JLabel logInToTwitter = new JLabel("Twitter");
        logInToTwitter.setFont(new Font("맑은 고딕", Font.BOLD, 26));
        logInToTwitter.setHorizontalAlignment(JLabel.CENTER);


        // 트위터 로고 추가
        ImageJLabel twitterLogo = new ImageJLabel("/images/TwitterLogo.png", 50, 50);
        twitterLogo.setHorizontalAlignment(JLabel.CENTER);


        // 이메일 입력 필드 생성
        JPanel inputPanel1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc1 = new GridBagConstraints();
        gbc1.gridx = 0;
        gbc1.gridy = 0;
        gbc1.anchor = GridBagConstraints.CENTER; // 가로, 세로 모두 중앙에 정렬

        JLabel usernameLabel = new JLabel("Email :           ");
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(18f));


        RoundJTextField usernameField = new RoundJTextField("Email", true);
        usernameField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        usernameField.setPreferredSize(new Dimension(250, 30)); // 이메일 필드 크기 설정



        // 비밀번호 입력 필드 생성
        JPanel inputPanel2 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc2 = new GridBagConstraints();
        gbc2.gridx = 0;
        gbc2.gridy = 0;
        gbc2.anchor = GridBagConstraints.CENTER;

        JLabel passwordLabel = new JLabel("Password :  ");
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(18f));

        RoundJPasswordField passwordField = new RoundJPasswordField("Password", true);
        passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
        passwordField.setPreferredSize(new Dimension(250, 30));  // 비밀번호 필드 크기 설정


        // 로그인 버튼 생성 및 스타일 설정
        RoundJButton loginButton = new RoundJButton("Log in", 60,350,55);
        loginButton.setBackground(new Color(29, 161, 242)); // 하늘색 트위터 색상
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("맑은 고딕", Font.BOLD, 24));

        // 로그인 버튼을 위한 패널
        JPanel panel3 = new JPanel();
        panel3.setLayout(new FlowLayout(FlowLayout.CENTER));
        panel3.setBackground(Color.WHITE);


        // 비밀번호 찾기 및 회원가입을 위한 패널 생성
        JPanel panel2 = new JPanel(new GridLayout(1, 2, 10, 10));
        panel2.setBackground(Color.WHITE);


        // 비밀번호 찾기 버튼 생성 및 이벤트 처리
        ActionJLabel findPassword = new ActionJLabel("Forgot Password?");
        findPassword.setHorizontalAlignment(JLabel.RIGHT);
        findPassword.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose(); // 현재 창 닫기
                SwingUtilities.invokeLater(() -> {
                    FindPage findPage = new FindPage(con); // 비밀번호 찾기 페이지 열기
                    findPage.setVisible(true);
                });
            }
        });

        // 회원가입 버튼 생성 및 이벤트 처리
        ActionJLabel signUp = new ActionJLabel("Sign up for Twitter");
        signUp.setHorizontalAlignment(JLabel.LEFT);
        signUp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setVisible(false); // 현재 창 숨기기
                SwingUtilities.invokeLater(() -> {
                    SignUpPage signUpPage = new SignUpPage(con); // 회원가입 페이지 열기
                    signUpPage.setVisible(true);
                });
            }
        });



        // 컴포넌트를 메인 패널에 추가
        panel.add(logInToTwitter);
        panel.add(twitterLogo);

        // 이메일 입력 필드를 패널에 추가
        inputPanel1.setOpaque(false);
        inputPanel1.setBackground(new Color(0, 0, 0, 0));  // 투명 배경
        inputPanel1.add(usernameLabel, gbc1);
        gbc1.gridx = 1;
        inputPanel1.add(usernameField, gbc1);
        panel.add(inputPanel1);

        // 비밀번호 입력 필드를 패널에 추가
        inputPanel2.setOpaque(false);
        inputPanel2.setBackground(new Color(0, 0, 0, 0));  // 투명 배경
        inputPanel2.add(passwordLabel, gbc2);
        gbc2.gridx = 1;
        inputPanel2.add(passwordField, gbc2);
        panel.add(inputPanel2);

        // 로그인 버튼을 패널에 추가
        panel3.add(loginButton);
        panel.add(panel3);

        // 비밀번호 찾기, 회원가입 버튼을 패널에 추가
        panel2.add(findPassword);
        panel2.add(signUp);
        panel.add(panel2);

        // 로그인 버튼 클릭 이벤트
        loginButton.addActionListener(e -> {
            String userId = usernameField.getText();
            char[] password = passwordField.getPassword();
            String strPassword = new String(password);
            setVisible(false); // 창 숨기기
        });


        add(panel); // 메인 패널을 프레임에 추가

        setSize(450, 550);
        setResizable(false); // 창 크기 조절 비활성화
        setLocationRelativeTo(null); // 화면 중앙에 위치
        setVisible(true); // 창 보이기
    }
}
