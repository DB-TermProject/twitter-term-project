package org.example.ui.page;

import org.example.ui.component.button.RoundJButton;
import org.example.ui.component.panel.HeaderPanel;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

public class FollowPage extends JPanel {
    private JLabel[] optionLabels;
    private JLabel followingCountLabel;
    private JLabel followerCountLabel;
    private RoundedPanel contentPanel;
    private JScrollPane scrollPane;
    private List<String[]> followingUsers;
    private Connection connection;
    private int followerCount = 7;
    private List<String[]> recommendUsers;

    public FollowPage(Connection connection) {
        this.connection = connection;
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Initialize following users list with sample data
        initializeFollowingUsers();

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // 헤더 패널 추가
        HeaderPanel headerPanel = new HeaderPanel(connection);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 컨텐츠를 담을 패널 생성
        JPanel contentContainer = new JPanel();
        contentContainer.setLayout(new BorderLayout());
        contentContainer.setBackground(Color.WHITE);

        // 상단 패널 (Social 섹션과 옵션들)
        JPanel topPanel = createTopPanel();
        contentContainer.add(topPanel, BorderLayout.NORTH);

        // 컨텐츠 패널 (스크롤 영역)
        contentPanel = new RoundedPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        // 스크롤 패널 설정
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBorder(null);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        // 스크롤 패널 설정 시 항상 세로 스크롤바 공간 확보
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // 스크롤 패널을 감싸는 패널
        JPanel scrollWrapper = new JPanel(new BorderLayout());
        scrollWrapper.setBackground(Color.WHITE);
        scrollWrapper.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
        scrollWrapper.add(scrollPane);

        contentContainer.add(scrollWrapper, BorderLayout.CENTER);
        mainPanel.add(contentContainer, BorderLayout.CENTER);
        add(mainPanel);

        // 초기 페이지 설정
        setActiveOption(0);
        switchPage(0);
    }
    private void setActiveOption(int index) {
        for (JLabel label : optionLabels) {
            label.setForeground(Color.BLACK);
        }
        optionLabels[index].setForeground(Color.BLUE);
    }

    private void switchPage(int index) {
        contentPanel.removeAll();
        if (index == 0) {  // Following page
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            for (String[] user : followingUsers) {
                if (user[6].equals("true")) {
                    // 차단된 유저의 팔로우 상태와 팔로워 상태를 false로 설정
                    user[4] = "false";  // 팔로우 상태 초기화
                    user[5] = "false";  // 팔로워 상태 초기화
                }
                if (user[4].equals("true")) {
                    JPanel userPanel = createUserPanel(user);
                    contentPanel.add(userPanel);
                }
            }
        } else if (index == 1) {  // Follower page
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            for (String[] user : followingUsers) {
                if (user[5].equals("true")) {
                    JPanel userPanel = createUserPanel(user);
                    contentPanel.add(userPanel);
                }
            }
        } else if (index == 2) {  // Recommend page
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            for (String[] user : recommendUsers) {
                if (user[4].equals("false")) {
                    JPanel userPanel = createRecommendPanel(user);
                    contentPanel.add(userPanel);
                }
            }
        } else if (index == 3) {  // Send Request page
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            for (String[] user : followingUsers) {
                if (user[4].equals("true") && user[5].equals("false")) {
                    JPanel userPanel = createUserPanel(user);
                    JButton followButton = (JButton) ((JPanel) userPanel.getComponent(2)).getComponent(0);
                    followButton.setText("Cancel");
                    followButton.setBackground(new Color(255, 159, 159));
                    contentPanel.add(userPanel);
                }
            }
        } else if (index == 4) {  // Received Request page
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            for (String[] user : followingUsers) {
                if (user[4].equals("false") && user[5].equals("true")) {
                    JPanel userPanel = createUserPanel(user);
                    JButton followButton = (JButton) ((JPanel) userPanel.getComponent(2)).getComponent(0);
                    followButton.setText("Accept");
                    followButton.setBackground(new Color(135, 206, 250));
                    contentPanel.add(userPanel);
                }
            }
        } else if (index == 5) {  // Block page
            contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
            for (String[] user : followingUsers) {
                if (user[6].equals("true")) {
                    JPanel userPanel = createUserPanel(user);
                    RoundJButton unblockButton = new RoundJButton("Unblock", 15, 80, 30);
                    unblockButton.setFont(new Font("마린 고딕", Font.PLAIN, 10));
                    unblockButton.setFocusPainted(false);
                    unblockButton.setBackground(new Color(255, 159, 159));
                    unblockButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

                    unblockButton.addActionListener(e -> {
                        user[6] = "false";  // 차단 해제
                        switchPage(5);  // Block 페이지 다시 로드하여 UI 업데이트
                    });

                    JPanel rightPanel = (JPanel) userPanel.getComponent(2);
                    rightPanel.removeAll();
                    rightPanel.add(unblockButton);
                    rightPanel.revalidate();
                    rightPanel.repaint();
                    contentPanel.add(userPanel);
                }
            }
        } else {
            JLabel contentLabel = new JLabel();
            contentLabel.setHorizontalAlignment(SwingConstants.CENTER);
            contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            contentLabel.setText(getPageContentText(index));
            contentPanel.add(contentLabel, BorderLayout.CENTER);
        }
        contentPanel.revalidate();
        contentPanel.repaint();
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
            contentPanel.remove(userPanel); // UI에서도 제거 (innerPanel을 contentPanel로 변경)
            contentPanel.revalidate();
            contentPanel.repaint();
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
            contentPanel.remove(userPanel); // innerPanel을 contentPanel로 변경
            contentPanel.revalidate();
            contentPanel.repaint();
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
            public void mouseClicked(MouseEvent e) {
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

    private void initializeFollowingUsers() {
        // User 객체 생성 부분 제거
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

    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setPreferredSize(new Dimension(450, 120));
        topPanel.setBackground(Color.WHITE);

        JLabel socialLabel = new JLabel("Social");
        socialLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        socialLabel.setBounds(30, 20, 100, 20);
        topPanel.add(socialLabel);

        String[] options = {"Following", "Follower", "Recommend", "Send Request", "Received Request", "Block"};
        optionLabels = new JLabel[options.length];
        int x = 30;
        int y = 50;

        for (int i = 0; i < options.length; i++) {
            JLabel optionLabel = new JLabel(options[i]);
            optionLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
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

        followingCountLabel = new JLabel(String.valueOf(followingUsers.size()));
        followingCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        followingCountLabel.setForeground(Color.BLACK);
        followingCountLabel.setBounds(90, 50, 50, 20);
        topPanel.add(followingCountLabel);

        followerCountLabel = new JLabel(String.valueOf(followerCount));
        followerCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        followerCountLabel.setForeground(Color.BLACK);
        followerCountLabel.setBounds(210, 50, 50, 20);
        topPanel.add(followerCountLabel);

        return topPanel;
    }


}
