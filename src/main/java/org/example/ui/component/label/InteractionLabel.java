package org.example.ui.component.label;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InteractionLabel extends JPanel {
    private final JLabel iconLabel;
    private final JLabel countLabel;
    private final boolean[] isLiked = {false};  // 좋아요 상태 추적
    private final JFrame parentFrame;

    public InteractionLabel(String iconName, String count, JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setBackground(Color.WHITE);

        // 아이콘과 카운트 레이블 초기화
        iconLabel = createIconLabel(iconName);
        countLabel = createCountLabel(count);

        // 이벤트 리스너 추가
        if (iconName.equals("heart.png")) {
            addHeartListener();
        } else if (iconName.equals("comment.png")) {
            addCommentListener();
        }

        // 컴포넌트 추가
        add(iconLabel);
        add(countLabel);
    }


    private JLabel createIconLabel(String iconName) {
        try {
            ImageIcon icon = new ImageIcon("src/main/java/org/example/asset/" + iconName);
            Image scaledImage = icon.getImage().getScaledInstance(18, 18, Image.SCALE_SMOOTH);
            return new JLabel(new ImageIcon(scaledImage));
        } catch (Exception e) {
            System.out.println("아이콘 로드 실패: " + iconName);
            return new JLabel();
        }
    }

    private JLabel createCountLabel(String count) {
        JLabel label = new JLabel(count);
        label.setForeground(Color.GRAY);
        return label;
    }

    private void addHeartListener() {
        MouseAdapter heartListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!isLiked[0]) {
                    int currentCount = Integer.parseInt(countLabel.getText());
                    countLabel.setText(String.valueOf(currentCount + 1));
                    countLabel.setForeground(new Color(249, 24, 128));  // 좋아요 색상
                    isLiked[0] = true;
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                parentFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };

        iconLabel.addMouseListener(heartListener);
        countLabel.addMouseListener(heartListener);
    }

    private void addCommentListener() {
        MouseAdapter commentListener = new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("댓글 아이콘이 클릭되었습니다.");
                // TODO: 댓글 창으로 이동하는 기능 구현
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                parentFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                parentFrame.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        };

        iconLabel.addMouseListener(commentListener);
        countLabel.addMouseListener(commentListener);
    }

    // 상태 확인 메소드
    public boolean isLiked() {
        return isLiked[0];
    }

    // InteractionLabel 클래스의 getCount 메소드
    public String getCount() {
        return countLabel.getText();
    }

    // 카운트 설정하기
    public void setCount(String count) {
        countLabel.setText(count);
    }
}