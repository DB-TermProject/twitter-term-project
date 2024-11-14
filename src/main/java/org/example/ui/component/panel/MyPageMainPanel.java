package org.example.ui.component.panel;

import org.example.ui.component.button.RoundButton2;

import javax.swing.*;
import java.awt.*;

public class MyPageMainPanel extends JPanel {
    public MyPageMainPanel() {
        setLayout(new GridBagLayout()); // GridBagLayout을 사용하여 간격 조정
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 0, 0); // 각 컴포넌트 간의 간격 설정
        gbc.gridx = 0;
        gbc.weightx = 1.0;

        // 첫 번째 줄: 프로필 사진 버튼
        gbc.gridy = 0;
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        profilePanel.setBackground(Color.WHITE);
        RoundButton2 profileButton = new RoundButton2("");
        profileButton.setIcon(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/profileImage.png").getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH)));
        profilePanel.add(profileButton);
        add(profilePanel, gbc);

        // 두 번째 줄: 사용자 이름과 파란뱃지 로고
        gbc.gridy++;
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        namePanel.setBackground(Color.WHITE);
        JLabel userNameLabel = new JLabel("Trump");
        userNameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 18));
        JLabel badgeIcon = new JLabel(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/BlueBadge.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        namePanel.add(userNameLabel);
        namePanel.add(badgeIcon);
        add(namePanel, gbc);

        // 세 번째 줄: 계정 공개 여부 표시
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 0, 0);
        JLabel accountTypeLabel = new JLabel("Public account"); // "Public account" 또는 "Private account"
        accountTypeLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
        add(accountTypeLabel, gbc);

        // 네 번째 줄: 소속
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, 0, 0);
        JLabel affiliationLabel = new JLabel("US government account");
        affiliationLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        add(affiliationLabel, gbc);

        // 다섯 번째 줄: 한줄 소개
        gbc.gridy++;
        gbc.insets = new Insets(0, 5, -5, 0); // 다섯 번째 줄과 여섯 번째 줄 간격을 줄이기 위해 Insets 설정

        JLabel introductionLabel = new JLabel("45th President of the United States of America.");
        introductionLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16));
        add(introductionLabel, gbc);

        // 여섯 번째 줄: 팔로잉/팔로워 소개
        gbc.gridy++;
        gbc.insets = new Insets(0, 0, 15, 0); // 여섯 번째 줄과 일곱 번째 줄 간격을 넓히기 위해 Insets 설정
        JPanel followPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        followPanel.setBackground(Color.WHITE);
        JLabel followingNumberLabel = new JLabel("34");
        followingNumberLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 숫자 부분 굵게 설정
        JLabel followingLabel = new JLabel("Following");
        followingLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16)); // 숫자 부분 굵게 설정
        JLabel followersNumberLabel = new JLabel(" 33.4M");
        followersNumberLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16)); // 숫자 부분 굵게 설정
        JLabel followersLabel = new JLabel("Followers");
        followersLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 16)); // 숫자 부분 굵게 설정
        followPanel.add(followingNumberLabel);
        followPanel.add(followingLabel);
        followPanel.add(followersNumberLabel);
        followPanel.add(followersLabel);
        add(followPanel, gbc);

        // 일곱 번째 줄: 표와 버튼
        gbc.gridy++;

        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(Color.WHITE);

        // 상단 버튼 부분 (tweet, comment, likes)
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3));
        buttonPanel.setPreferredSize(new Dimension(0, 70));
        buttonPanel.setBackground(Color.WHITE);

        JButton tweetButton = new JButton("Tweet");
        tweetButton.setContentAreaFilled(false);
        tweetButton.setFocusPainted(false);
        tweetButton.setOpaque(false);
        tweetButton.addChangeListener(e -> {
            if (tweetButton.getModel().isPressed()) {
                tweetButton.setBackground(Color.LIGHT_GRAY);
                tweetButton.setOpaque(true);
            } else {
                tweetButton.setBackground(Color.WHITE);
                tweetButton.setOpaque(false);
            }
        });

        JButton commentButton = new JButton("Comment");
        commentButton.setContentAreaFilled(false);
        commentButton.setFocusPainted(false);
        commentButton.setOpaque(false);
        commentButton.addChangeListener(e -> {
            if (commentButton.getModel().isPressed()) {
                commentButton.setBackground(Color.LIGHT_GRAY);
                commentButton.setOpaque(true);
            } else {
                commentButton.setBackground(Color.WHITE);
                commentButton.setOpaque(false);
            }
        });

        JButton likesButton = new JButton("Likes");
        likesButton.setContentAreaFilled(false);
        likesButton.setFocusPainted(false);
        likesButton.setOpaque(false);
        likesButton.addChangeListener(e -> {
            if (likesButton.getModel().isPressed()) {
                likesButton.setBackground(Color.LIGHT_GRAY);
                likesButton.setOpaque(true);
            } else {
                likesButton.setBackground(Color.WHITE);
                likesButton.setOpaque(false);
            }
        });

        buttonPanel.add(tweetButton);
        buttonPanel.add(commentButton);
        buttonPanel.add(likesButton);

        tablePanel.add(buttonPanel, BorderLayout.NORTH);

        // 나머지 빈 공백 부분
        JPanel emptySpace = new JPanel();
        emptySpace.setBackground(Color.LIGHT_GRAY);
        tablePanel.add(emptySpace, BorderLayout.CENTER);

        add(tablePanel, gbc);
    }
}







