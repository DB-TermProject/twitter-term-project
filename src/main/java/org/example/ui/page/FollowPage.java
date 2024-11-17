package org.example.ui.page;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;


import org.example.ui.component.panel.*;

public class FollowPage extends JFrame {

    private JLabel[] optionLabels;
    private JLabel followingCountLabel;
    private JLabel followerCountLabel;
    private RoundedPanel contentPanel;
    private JScrollPane scrollPane;
    private List<User> followingUsers;
    private Connection connection;

    // Variables to store counts
    private int followerCount = 7;

    public FollowPage(Connection con) {
        this.connection = con;
        setTitle("Follow Page");
        setSize(450, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());  // BorderLayout 사용
        getContentPane().setBackground(Color.WHITE);

        // Initialize following users list with sample data
        followingUsers = new ArrayList<>();
        followingUsers.add(new User("User 1", "This is a description", Color.BLUE));
        followingUsers.add(new User("User 2", "Another description", Color.BLUE));
        followingUsers.add(new User("User 3", "More description", Color.BLUE));
        followingUsers.add(new User("User 4", "Description for User 4", Color.BLUE));
        followingUsers.add(new User("User 5", "Description for User 5", Color.BLUE));
        followingUsers.add(new User("User 6", "Description for User 6", Color.BLUE));
        followingUsers.add(new User("User 7", "Description for User 7", Color.BLUE));
        followingUsers.add(new User("User 8", "Description for User 8", Color.BLUE));


        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.WHITE);

        // Create the top panel
        JPanel topPanel = new JPanel();
        topPanel.setLayout(null);
        topPanel.setBounds(0, 0, 400, 140);
        topPanel.setBackground(Color.WHITE);
        add(topPanel);

        JLabel logo = new JLabel("●");
        logo.setFont(new Font("SansSerif", Font.PLAIN, 18));
        logo.setBounds(30, 10, 20, 20);
        topPanel.add(logo);

        JLabel twitterLabel = new JLabel("twitter");
        twitterLabel.setFont(new Font("SansSerif", Font.PLAIN, 18));
        twitterLabel.setBounds(55, 10, 100, 20);
        topPanel.add(twitterLabel);

        JLabel socialLabel = new JLabel("Social");
        socialLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        socialLabel.setBounds(30, 40, 100, 20);
        topPanel.add(socialLabel);

        String[] options = {"Following", "Follower", "Recommend", "Send Request", "Received Request", "Block"};
        optionLabels = new JLabel[options.length];
        int x = 30;
        int y = 80;

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

        followingCountLabel = new JLabel(String.valueOf(followingUsers.size())); // Display following count dynamically
        followingCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        followingCountLabel.setForeground(Color.BLACK);
        followingCountLabel.setBounds(90, 80, 50, 20);
        topPanel.add(followingCountLabel);

        followerCountLabel = new JLabel(String.valueOf(followerCount)); // Display follower count variable
        followerCountLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        followerCountLabel.setForeground(Color.BLACK);
        followerCountLabel.setBounds(210, 80, 50, 20);
        topPanel.add(followerCountLabel);

        // Rounded content panel to display different pages with a scrollable area
        contentPanel = new RoundedPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0)); // Add top margin of 10

        // Scroll pane for content panel without visible scrollbar
        scrollPane = new JScrollPane(contentPanel);
        scrollPane.setBounds(30, 150, 330, 385);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set scroll speed
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(scrollPane);

        // Enable mouse wheel scrolling
        scrollPane.addMouseWheelListener(e -> {
            JScrollBar verticalScrollBar = scrollPane.getVerticalScrollBar();
            int scrollAmount = e.getUnitsToScroll() * verticalScrollBar.getUnitIncrement();
            verticalScrollBar.setValue(verticalScrollBar.getValue() + scrollAmount);
        });

        setActiveOption(0);
        switchPage(0);

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
        contentPanel.removeAll();
        if (index == 0) {
            // Display following users list
            for (User user : followingUsers) {
                contentPanel.add(createUserPanel(user));
                contentPanel.add(Box.createVerticalStrut(10)); // Add space between user panels
            }
            contentPanel.revalidate();
            contentPanel.repaint();
        } else {
            JLabel placeholder = new JLabel("Content for " + optionLabels[index].getText() + " Page");
            placeholder.setHorizontalAlignment(SwingConstants.CENTER);
            placeholder.setBackground(Color.WHITE);
            placeholder.setOpaque(true);
            contentPanel.add(placeholder, BorderLayout.CENTER);
            contentPanel.revalidate();
            contentPanel.repaint();
        }
    }

    private JPanel createUserPanel(User user) {
        JPanel userPanel = new JPanel(new BorderLayout());
        userPanel.setPreferredSize(new Dimension(300, 60)); // Set each user panel size to 300x60
        userPanel.setMaximumSize(new Dimension(300, 60));
        userPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // Padding inside each user panel
        userPanel.setBackground(Color.WHITE);

        // Circular profile picture
        JLabel profilePic = new JLabel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(user.getProfileColor());
                g.fillOval(0, 0, getWidth(), getHeight());
            }
        };
        profilePic.setPreferredSize(new Dimension(50, 50));
        profilePic.setOpaque(false); // Transparent background for circular shape
        profilePic.setHorizontalAlignment(SwingConstants.CENTER);
        profilePic.setVerticalAlignment(SwingConstants.CENTER);

        // Name and description
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(Color.WHITE);
        textPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10)); // Padding between profile pic and text
        JLabel nameLabel = new JLabel(user.getName());
        JLabel descriptionLabel = new JLabel(user.getDescription());
        textPanel.add(nameLabel);
        textPanel.add(descriptionLabel);

        // Rounded Following button with toggle functionality
        JButton followButton = new RoundedButton("Following");
        setFollowingButtonStyle(followButton, true); // Set initial style

        followButton.addActionListener(new ActionListener() {
            private boolean isFollowing = true;

            @Override
            public void actionPerformed(ActionEvent e) {
                isFollowing = !isFollowing;
                followButton.setText(isFollowing ? "Following" : "Follow");
                setFollowingButtonStyle(followButton, isFollowing);
            }
        });

        // Add components to user panel with spacing
        userPanel.add(profilePic, BorderLayout.WEST);
        userPanel.add(textPanel, BorderLayout.CENTER);
        userPanel.add(followButton, BorderLayout.EAST);

        return userPanel;
    }

    private void setFollowingButtonStyle(JButton button, boolean isFollowing) {
        if (isFollowing) {
            button.setBackground(Color.WHITE);
            button.setForeground(Color.BLACK);
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        } else {
            button.setBackground(new Color(100, 150, 255));
            button.setForeground(Color.WHITE);
            button.setBorder(BorderFactory.createEmptyBorder());
        }

        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setOpaque(true);
        button.setPreferredSize(new Dimension(100, 30));
        button.setFont(new Font("SansSerif", Font.BOLD, 12));
    }

    // Custom rounded button class
    class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setFocusPainted(false);
            setContentAreaFilled(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            g2.setColor(getForeground());
            FontMetrics fm = g2.getFontMetrics();
            int x = (getWidth() - fm.stringWidth(getText())) / 2;
            int y = (getHeight() + fm.getAscent()) / 2 - 2;
            g2.drawString(getText(), x, y);
            g2.dispose();
            super.paintComponent(g);
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }
    }

    // User class to represent a following user
    static class User {
        private String name;
        private String description;
        private Color profileColor;

        public User(String name, String description, Color profileColor) {
            this.name = name;
            this.description = description;
            this.profileColor = profileColor;
        }

        public String getName() {
            return name;
        }

        public String getDescription() {
            return description;
        }

        public Color getProfileColor() {
            return profileColor;
        }
    }

    // Custom panel with rounded corners and black border
    class RoundedPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
        }

        @Override
        public void setBackground(Color color) {
            super.setBackground(color);
            repaint();
        }
    }


}
