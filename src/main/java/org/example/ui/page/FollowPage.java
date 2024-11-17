package org.example.ui.page;

import org.example.ui.component.button.RoundJButton;

import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import org.example.ui.component.panel.*;

public class FollowPage extends JFrame {

    private JLabel[] optionLabels;
    private JLabel followingCountLabel;
    private JLabel followerCountLabel;

    private JPanel innerPanel;
    private Connection connection;
    private List<String[]> followingUsers; // List to manage following users
    private List<String[]> recommendUsers;

    public FollowPage(Connection connection) {
        // Store the connection for future database interactions
        this.connection = connection;

        // Initialize the following user list
        followingUsers = new ArrayList<>();
        followingUsers.add(new String[]{"User1", "(Company) Company1", "(Intro) Intro1", "src/main/java/org/example/asset/profile.png", "true", "true", "false",null});
        followingUsers.add(new String[]{"User2", "(Company) Company2", "(Intro) Intro2", "src/main/java/org/example/asset/profile.png", "false", "true", "true",null});
        followingUsers.add(new String[]{"User3", "(Company) Company3", "(Intro) Intro3", "src/main/java/org/example/asset/profile.png", "true", "false", "false",null});
        followingUsers.add(new String[]{"User4", "(Company) Company4", "(Intro) Intro4", "src/main/java/org/example/asset/profile.png", "true", "true", "false",null});
        followingUsers.add(new String[]{"User5", "(Company) Company5", "(Intro) Intro5", "src/main/java/org/example/asset/profile.png", "false", "false", "true",null});
        followingUsers.add(new String[]{"User6", "(Company) Company6", "(Intro) Intro6", "src/main/java/org/example/asset/profile.png", "true", "true", "false",null});
        followingUsers.add(new String[]{"User7", "(Company) Company7", "(Intro) Intro7", "src/main/java/org/example/asset/profile.png", "true", "false", "false",null});
        followingUsers.add(new String[]{"User8", "(Company) Company8", "(Intro) Intro8", "src/main/java/org/example/asset/profile.png", "false", "true", "false",null});
        followingUsers.add(new String[]{"User9", "(Company) Company9", "(Intro) Intro9", "src/main/java/org/example/asset/profile.png", "true", "false", "false",null});
        followingUsers.add(new String[]{"User10", "(Company) Company10", "(Intro) Intro10", "src/main/java/org/example/asset/profile.png", "false", "true", "true",null});
        followingUsers.add(new String[]{"User11", "(Company) Company11", "(Intro) Intro11", "src/main/java/org/example/asset/profile.png", "true", "true", "false",null});
        followingUsers.add(new String[]{"User12", "(Company) Company12", "(Intro) Intro12", "src/main/java/org/example/asset/profile.png", "true", "false", "true",null});

        recommendUsers = new ArrayList<>();
        recommendUsers.add(new String[]{"User13", "(Company) Company13", "(Intro) Intro13", "src/main/java/org/example/asset/profile.png", "false", "false", "false","user1"});
        recommendUsers.add(new String[]{"User14", "(Company) Company14", "(Intro) Intro14", "src/main/java/org/example/asset/profile.png", "false", "false", "false","user3"});
        recommendUsers.add(new String[]{"User15", "(Company) Company15", "(Intro) Intro15", "src/main/java/org/example/asset/profile.png", "false", "false", "false","user4"});
        recommendUsers.add(new String[]{"User16", "(Company) Company16", "(Intro) Intro16", "src/main/java/org/example/asset/profile.png", "false", "false", "false","user6"});
        recommendUsers.add(new String[]{"User17", "(Company) Company17", "(Intro) Intro17", "src/main/java/org/example/asset/profile.png", "false", "false", "false","user7"});
        recommendUsers.add(new String[]{"User18", "(Company) Company18", "(Intro) Intro18", "src/main/java/org/example/asset/profile.png", "false", "false", "false","user9"});

        setTitle("Follow Page");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // BorderLayout 사용
        getContentPane().setBackground(Color.WHITE);

        // Create the top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 400, 140);
        topPanel.setBackground(Color.WHITE);
        add(topPanel);

        // Load and resize the Twitter logo image
        JLabel logo = new JLabel();
        int logoSize = 24;
        try {
            Image twitterImage = ImageIO.read(new File("src/main/java/org/example/asset/twitter.png"));
            Image resizedImage = twitterImage.getScaledInstance(logoSize, logoSize, Image.SCALE_SMOOTH);
            logo.setIcon(new ImageIcon(resizedImage));
        } catch (IOException e) {
            e.printStackTrace();
            logo.setText("●");
        }
        logo.setBounds(30, 10, logoSize, logoSize);
        topPanel.add(logo);

        JLabel twitterLabel = new JLabel("Twitter");
        twitterLabel.setFont(new Font("마린 고딕", Font.PLAIN, 16));
        twitterLabel.setBounds(30 + logoSize + 5, 10, 100, logoSize);
        topPanel.add(twitterLabel);

        JLabel socialLabel = new JLabel("Social");
        socialLabel.setFont(new Font("마린 고딕", Font.BOLD, 18));
        socialLabel.setBounds(30, 40, 100, 25);
        topPanel.add(socialLabel);

        // Options for the top panel
        String[] options = {"Following", "Follower", "Recommend", "Send Request", "Received Request", "Block"};
        optionLabels = new JLabel[options.length];
        int x = 30;
        int y = 80;

        for (int i = 0; i < options.length; i++) {
            JLabel optionLabel = new JLabel(options[i]);
            optionLabel.setFont(new Font("마린 고딕", Font.PLAIN, 12));
            int adjustedX = x + (options[i].equals("Block") ? 20 : 0);
            optionLabel.setBounds(adjustedX, y + (i < 3 ? 0 : 15), 100, 20);
            optionLabel.setForeground(i == 0 ? Color.BLUE : Color.BLACK);
            optionLabel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            final int index = i;
            optionLabel.addMouseListener(new MouseInputAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    setActiveOption(index);
                    switchPage(index);
                }
            });

            topPanel.add(optionLabel);
            optionLabels[i] = optionLabel;
            x += 120;
            if (i == 2) {
                x = 30;
                y += 15;
            }
        }

        setActiveOption(0);

        // Following count label next to the "Following" option
        followingCountLabel = new JLabel("0"); // Initial count set to 0
        followingCountLabel.setFont(new Font("마린 고딕", Font.PLAIN, 12));
        followingCountLabel.setForeground(Color.BLACK);
        followingCountLabel.setBounds(optionLabels[0].getX() + 65, optionLabels[0].getY() + 1, 50, 20); // Positioned close to "Following"
        topPanel.add(followingCountLabel);

        // Follower count label next to the "Follower" option
        followerCountLabel = new JLabel("0"); // Initial count set to 0
        followerCountLabel.setFont(new Font("마린 고딕", Font.PLAIN, 12));
        followerCountLabel.setForeground(Color.BLACK);
        followerCountLabel.setBounds(optionLabels[1].getX() + 60, optionLabels[1].getY() + 1, 50, 20); // Positioned close to "Follower"
        topPanel.add(followerCountLabel);

        // Inner panel without border inside the rounded panel
        innerPanel = new JPanel();
        innerPanel.setBackground(Color.WHITE);
        innerPanel.setLayout(new BorderLayout());

        // Wrap innerPanel in a JScrollPane
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setBounds(10, 10, 295, 320); // Set bounds for scrollPane
        scrollPane.setBorder(null); // Optional: Remove border for cleaner look

        // Set custom scroll bars to hide their appearance but keep functionality
        scrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        // Set faster scrolling speed
        scrollPane.getVerticalScrollBar().setUnitIncrement(5); // Adjust this value to make scrolling faster

        // Create the rounded panel and add the scrollPane to it
        RoundedPanel roundedPanel = new RoundedPanel();
        roundedPanel.setBounds(30, 150, 315, 340);
        roundedPanel.setBackground(Color.white);
        roundedPanel.setLayout(null);
        roundedPanel.add(scrollPane); // Add scrollPane inside roundedPanel

        add(roundedPanel);

        // Default content for "Following"
        updateFollowingCount((int) followingUsers.stream().filter(user -> user[6].equals("false")&&user[4].equals("true")).count());
        updateFollowerCount((int) followingUsers.stream().filter(user -> user[6].equals("false")&&user[5].equals("true")).count());
        switchPage(0);

        // Bottom navigation panel
        JPanel bottomNavPanel = new JPanel();
        bottomNavPanel.setLayout(new GridLayout(1, 4));
        bottomNavPanel.setBounds(0, 510, 400, 50);
        bottomNavPanel.setBackground(Color.WHITE);
        bottomNavPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.LIGHT_GRAY));

        // Add navigation icons
        bottomNavPanel.add(createNavIcon("src/main/java/org/example/asset/home.png", 25, 25));
        bottomNavPanel.add(createNavIcon("src/main/java/org/example/asset/follow.png", 25, 25));
        bottomNavPanel.add(createNavIcon("src/main/java/org/example/asset/alarm.png", 25, 25));
        bottomNavPanel.add(createNavIcon("src/main/java/org/example/asset/user.png", 25, 25));

        add(bottomNavPanel);

        setVisible(true);
        // scrollPane의 크기와 위치 조정 (네비게이션 바를 위한 공간 확보)
        scrollPane.setBounds(30, 150, 330, 425);  // 높이를 425로 수정

        // 네비게이션 패널 추가
        NavigationPanel navigationPanel = new NavigationPanel(this, con);
        navigationPanel.setBounds(0, 610, 450, 40);  // 위치와 크기 설정
        add(navigationPanel);

        setLocationRelativeTo(null);  // 화면 중앙에 표시
        setVisible(true);
    }

    private void setActiveOption(int index) {
        for (JLabel label : optionLabels) {
            label.setForeground(Color.BLACK);
        }
        optionLabels[index].setForeground(Color.BLUE);
    }

    private void switchPage(int index) {
        innerPanel.removeAll();

        if (index == 0) { // "Following" page content
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); // Set vertical alignment

            for (String[] user : followingUsers) {
                if (user[6].equals("true")) {
                    // 차단된 유저의 팔로우 상태와 팔로워 상태를 false로 설정
                    user[4] = "false"; // 팔로우 상태 초기화
                    user[5] = "false"; // 팔로워 상태 초기화
                }

                if (user[4].equals("true")) {
                    JPanel userPanel = createUserPanel(user);
                    innerPanel.add(userPanel); // Add user panel to innerPanel



                }
            }
        } else if (index == 1) { // "Follower" page content
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); // Set vertical alignment

            for (String[] user : followingUsers) {
                if (user[5].equals("true")) {
                    JPanel userPanel = createUserPanel(user);
                    innerPanel.add(userPanel); // Add user panel to innerPanel



                }
            }
        }
        else if(index==2){
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));
            for (String[] user : recommendUsers) {
                if (user[4].equals("false")) {
                    JPanel userPanel = createRecommendPanel(user);
                    innerPanel.add(userPanel); // Add user panel to innerPanel



                }
            }

        }
        else if (index == 3) { // "Send Request" page content
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); // Set vertical alignment

            for (String[] user : followingUsers) {
                if (user[4].equals("true") && user[5].equals("false")) {
                    JPanel userPanel = createUserPanel(user);

                    // 여기서 버튼 텍스트를 "Cancel"로 설정합니다.
                    JButton followButton = (JButton) ((JPanel) userPanel.getComponent(2)).getComponent(0);
                    followButton.setText("Cancel");
                    followButton.setBackground(new Color(255, 159, 159));

                    innerPanel.add(userPanel); // Add user panel to innerPanel


                }
            }
        }

        else if (index == 4) { // "Received Request" page content
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); // Set vertical alignment

            for (String[] user : followingUsers) {
                if (user[4].equals("false") && user[5].equals("true")) {
                    JPanel userPanel = createUserPanel(user);

                    // 여기서 버튼 텍스트를 "Accept"로 설정합니다.
                    JButton followButton = (JButton) ((JPanel) userPanel.getComponent(2)).getComponent(0);
                    followButton.setText("Accept");
                    followButton.setBackground(new Color(135, 206, 250));



                    innerPanel.add(userPanel); // Add user panel to innerPanel


                }
            }
        }
        else if (index == 5) { // "Block" page content
            innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS)); // Set vertical alignment

            for (String[] user : followingUsers) {
                if (user[6].equals("true")) { // 차단된 유저만 표시
                    JPanel userPanel = createUserPanel(user);

                    // Unblock 버튼 추가 및 설정
                    RoundJButton unblockButton = new RoundJButton("Unblock", 15,80,30);

                    //JButton blockButton = new JButton("Unblock");
                    unblockButton.setFont(new Font("마린 고딕", Font.PLAIN, 10));
                    unblockButton.setFocusPainted(false);
                    unblockButton.setBackground(new Color(255, 159, 159)); // 자몽색 for Unblock
                    unblockButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
                    // blockButton.setPreferredSize(new Dimension(80, 30));

                    // Unblock 버튼 액션 리스너 추가
                    unblockButton.addActionListener(e -> {
                        user[6] = "false"; // 차단 해제
                        switchPage(5); // Block 페이지 다시 로드하여 UI 업데이트
                    });

                    JPanel rightPanel = (JPanel) userPanel.getComponent(2); // Right panel 가져오기
                    rightPanel.removeAll(); // 기존 버튼 제거
                    rightPanel.add(unblockButton); // Unblock 버튼 추가
                    rightPanel.revalidate();
                    rightPanel.repaint();

                    innerPanel.add(userPanel); // Add user panel to innerPanel


                }
            }
        }

        else {
            JLabel contentLabel = new JLabel();
            contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            contentLabel.setText(getPageContentText(index));
            innerPanel.add(contentLabel, BorderLayout.CENTER);
        }

        innerPanel.revalidate();
        innerPanel.repaint();
    }
    private JPanel createRecommendPanel(String[] user) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout()); // Use BorderLayout for better alignment
        userPanel.setPreferredSize(new Dimension(295, 80));
        userPanel.setMaximumSize(new Dimension(295, 80)); // Ensure uniform size
        userPanel.setBackground(Color.WHITE);

        // Left side: Profile image
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(60, 80));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BorderLayout());

        JLabel profileImageLabel = new JLabel();
        profileImageLabel.setPreferredSize(new Dimension(50, 50));
        try {
            // Load profile image from the provided path in the user data
            String imagePath = user[3]; // Get the image path from the user data
            Image profileImage = ImageIO.read(new File(imagePath));
            Image resizedProfileImage = profileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            // Convert image to circular shape
            BufferedImage bufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setClip(new Ellipse2D.Float(0, 0, 50, 50)); // Circular clipping
            g2d.drawImage(resizedProfileImage, 0, 0, null);
            g2d.dispose();

            profileImageLabel.setIcon(new ImageIcon(bufferedImage));
        } catch (IOException e) {
            e.printStackTrace();
            profileImageLabel.setOpaque(true);
            profileImageLabel.setBackground(Color.BLUE); // Fallback to blue background if loading fails
        }
        leftPanel.add(profileImageLabel, BorderLayout.CENTER);

        // Center: User info
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null); // Use null layout for custom positioning

        // Adjusted positions for name, affiliation, and introduction
        JLabel nameLabel = new JLabel(user[0]);
        nameLabel.setFont(new Font("마린 고딕", Font.BOLD, 12));
        nameLabel.setBounds(10, 15, 200, 20); // Adjusted downward and closer to profile

        JLabel affiliationLabel = new JLabel(user[1]);
        affiliationLabel.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        affiliationLabel.setBounds(10, 35, 200, 15); // Slightly lower position

        JLabel introductionLabel = new JLabel(user[2]);
        introductionLabel.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        introductionLabel.setBounds(10, 50, 200, 15); // Slightly lower position

        JLabel related = new JLabel("Related to ("+user[7]+")");
        related.setFont(new Font("마린 고딕", Font.PLAIN, 9));
        related.setBounds(55, 19, 200, 15); // Slightly lower position


        centerPanel.add(nameLabel);
        centerPanel.add(affiliationLabel);
        centerPanel.add(introductionLabel);
        centerPanel.add(related);


        // Right side: Follow buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100, 80));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));




        RoundJButton blockButton = new RoundJButton("Follow", 15,80,30);

        blockButton.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        blockButton.setFocusPainted(false);
        blockButton.setBackground(new Color(135, 206, 250)); // 하늘색 팔로우버튼
        blockButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
       // blockButton.setPreferredSize(new Dimension(80, 30));



        blockButton.addActionListener(e -> {
            recommendUsers.remove(user); // 유저를 목록에서 제거
            innerPanel.remove(userPanel); // UI에서도 제거
            innerPanel.revalidate();
            innerPanel.repaint();
            user[4] = "true"; // 팔로우 상태 초기화
            user[5] = "false"; // 팔로워 상태 초기화
            user[7] = null;
            rightPanel.revalidate();
            rightPanel.repaint();
            followingUsers.add(user);
            recommendUsers.remove(user);

            updateFollowingCount((int) followingUsers.stream().filter(u -> u[4].equals("true")).count());

            switchPage(2); // Block 페이지로 다시 전환하여 UI를 업데이트
        });

        rightPanel.add(blockButton);



        userPanel.add(leftPanel, BorderLayout.WEST);
        userPanel.add(centerPanel, BorderLayout.CENTER);
        userPanel.add(rightPanel, BorderLayout.EAST);

        return userPanel;
    }



    private JPanel createUserPanel(String[] user) {
        JPanel userPanel = new JPanel();
        userPanel.setLayout(new BorderLayout()); // Use BorderLayout for better alignment
        userPanel.setPreferredSize(new Dimension(295, 80));
        userPanel.setMaximumSize(new Dimension(295, 80)); // Ensure uniform size
        userPanel.setBackground(Color.WHITE);

        // Left side: Profile image
        JPanel leftPanel = new JPanel();
        leftPanel.setPreferredSize(new Dimension(60, 80));
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new BorderLayout());

        JLabel profileImageLabel = new JLabel();
        profileImageLabel.setPreferredSize(new Dimension(50, 50));
        try {
            // Load profile image from the provided path in the user data
            String imagePath = user[3]; // Get the image path from the user data
            Image profileImage = ImageIO.read(new File(imagePath));
            Image resizedProfileImage = profileImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH);

            // Convert image to circular shape
            BufferedImage bufferedImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = bufferedImage.createGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setClip(new Ellipse2D.Float(0, 0, 50, 50)); // Circular clipping
            g2d.drawImage(resizedProfileImage, 0, 0, null);
            g2d.dispose();

            profileImageLabel.setIcon(new ImageIcon(bufferedImage));
        } catch (IOException e) {
            e.printStackTrace();
            profileImageLabel.setOpaque(true);
            profileImageLabel.setBackground(Color.BLUE); // Fallback to blue background if loading fails
        }
        leftPanel.add(profileImageLabel, BorderLayout.CENTER);

        // Center: User info
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(Color.WHITE);
        centerPanel.setLayout(null); // Use null layout for custom positioning

        // Adjusted positions for name, affiliation, and introduction
        JLabel nameLabel = new JLabel(user[0]);
        nameLabel.setFont(new Font("마린 고딕", Font.BOLD, 12));
        nameLabel.setBounds(10, 15, 200, 20); // Adjusted downward and closer to profile

        JLabel affiliationLabel = new JLabel(user[1]);
        affiliationLabel.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        affiliationLabel.setBounds(10, 35, 200, 15); // Slightly lower position

        JLabel introductionLabel = new JLabel(user[2]);
        introductionLabel.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        introductionLabel.setBounds(10, 50, 200, 15); // Slightly lower position





        centerPanel.add(nameLabel);
        centerPanel.add(affiliationLabel);
        centerPanel.add(introductionLabel);

        // Right side: Follow/Following buttons
        JPanel rightPanel = new JPanel();
        rightPanel.setPreferredSize(new Dimension(100, 80));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 20));


        RoundJButton followButton = new RoundJButton("Follow", 15,80,30);

        //Button followButton = new JButton("Follow");
        followButton.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        followButton.setFocusPainted(false);
        followButton.setBackground(new Color(135, 206, 250)); // 하늘색 for Follow
        followButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        //followButton.setPreferredSize(new Dimension(80, 30)); // Set preferred size for longer rounded button


        RoundJButton followingButton = new RoundJButton("Following", 15,80,30);

       // JButton followingButton = new JButton("Following");
        followingButton.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        followingButton.setFocusPainted(false);
        followingButton.setBackground(Color.WHITE); // 유지 (white for Following)
        followingButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
       // followingButton.setPreferredSize(new Dimension(80, 30)); // Set preferred size for longer rounded button

        if (user[4].equals("true")) {
            rightPanel.add(followingButton);
        } else {
            rightPanel.add(followButton);
        }

        // Add action listeners for both buttons
        followButton.addActionListener(e -> {
            user[4] = "true";
            rightPanel.remove(followButton);
            rightPanel.add(followingButton);
            rightPanel.revalidate();
            rightPanel.repaint();
            updateFollowingCount((int) followingUsers.stream().filter(u -> u[4].equals("true")).count());
        });

        followingButton.addActionListener(e -> {
            user[4] = "false";
            rightPanel.remove(followingButton);
            rightPanel.add(followButton);
            rightPanel.revalidate();
            rightPanel.repaint();
            updateFollowingCount((int) followingUsers.stream().filter(u -> u[4].equals("true")).count());
        });


        RoundJButton blockButton = new RoundJButton("Unblock", 15,80,30);

        //JButton blockButton = new JButton("Unblock");
        blockButton.setFont(new Font("마린 고딕", Font.PLAIN, 10));
        blockButton.setFocusPainted(false);
        blockButton.setBackground(new Color(255, 159, 159)); // 자몽색 for Unblock
        blockButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
       // blockButton.setPreferredSize(new Dimension(80, 30));

        blockButton.addActionListener(e -> {
            followingUsers.remove(user); // 유저를 목록에서 제거
            innerPanel.remove(userPanel); // UI에서도 제거
            innerPanel.revalidate();
            innerPanel.repaint();
        });
        blockButton.addActionListener(e -> {
            user[6] = "true"; // 사용자 차단 상태로 업데이트
            user[4] = "false"; // 팔로우 상태 초기화
            user[5] = "false"; // 팔로워 상태 초기화
            switchPage(5); // Block 페이지로 다시 전환하여 UI를 업데이트
        });

        userPanel.add(leftPanel, BorderLayout.WEST);
        userPanel.add(centerPanel, BorderLayout.CENTER);
        userPanel.add(rightPanel, BorderLayout.EAST);

        return userPanel;
    }


    private String getPageContentText(int index) {
        switch (index) {
            case 1:
                return "Follower page content";
            case 2:
                return "Recommend page content";
            case 3:
                return "Send Request page content";
            case 4:
                return "Received Request page content";
            case 5:
                return "Block page content";
            default:
                return "Following page content";
        }
    }

    public void updateFollowingCount(int newCount) {
        followingCountLabel.setText(String.valueOf(newCount));
    }

    public void updateFollowerCount(int newCount) {
        followerCountLabel.setText(String.valueOf(newCount));
    }



    private JLabel createNavIcon(String imagePath, int width, int height) {
        JLabel label = new JLabel();
        try {
            Image iconImage = ImageIO.read(new File(imagePath));
            Image resizedImage = iconImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            label.setIcon(new ImageIcon(resizedImage));
        } catch (IOException e) {
            e.printStackTrace();
            label.setText("Icon");
        }
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        label.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        label.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                System.out.println("Icon clicked: " + imagePath);
            }
        });

        return label;
    }

    class RoundedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            g2.setColor(Color.black);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }
    }


}
