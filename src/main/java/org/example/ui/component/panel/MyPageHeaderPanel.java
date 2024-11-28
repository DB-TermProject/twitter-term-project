package org.example.ui.component.panel;

import org.example.ui.component.button.RoundButton;
import org.example.ui.page.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MyPageHeaderPanel extends JPanel {


    public MyPageHeaderPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(450, 40));

        // 왼쪽 로고와 텍스트 컨테이너
        JPanel leftContainer = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftContainer.setBackground(Color.WHITE);

        // 트위터 로고 이미지 크기 조정 및 JLabel 설정
        ImageIcon twitterLogoIcon = new ImageIcon("src/main/java/org/example/asset/twitter.png");
        Image scaledLogo = twitterLogoIcon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JLabel twitterLogo = new JLabel(new ImageIcon(scaledLogo));

        JLabel twitterText = new JLabel("Twitter");
        twitterText.setFont(new Font("맑은 고딕", Font.BOLD, 18));

        leftContainer.add(twitterLogo);
        leftContainer.add(twitterText);

        // 오른쪽 버튼 컨테이너
        JPanel rightContainer = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightContainer.setBackground(Color.WHITE);

        // 이미지 덮어씌울 버튼 1
        RoundButton button1 = new RoundButton("");
        button1.setContentAreaFilled(false);
        button1.setBorderPainted(false);
        button1.setFocusPainted(false);
        button1.setOpaque(false);
        button1.setBorder(null);
        button1.setIcon(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/myInformationChange.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getInstance().showPage("updateInformation");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button1.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button1.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        // 이미지 덮어씌울 버튼 2
        RoundButton button2 = new RoundButton("");
        button2.setContentAreaFilled(false);
        button2.setBorderPainted(false);
        button2.setFocusPainted(false);
        button2.setOpaque(false);
        button2.setBorder(null);
        button2.setIcon(new ImageIcon(new ImageIcon("src/main/java/org/example/asset/passwordChange.png").getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH)));
        button2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                MainFrame.getInstance().showPage("changePassword");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button2.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });


        rightContainer.add(button1);
        rightContainer.add(button2);

        // 왼쪽과 오른쪽 컨테이너를 헤더에 추가
        add(leftContainer, BorderLayout.WEST);
        add(rightContainer, BorderLayout.EAST);

    }
}