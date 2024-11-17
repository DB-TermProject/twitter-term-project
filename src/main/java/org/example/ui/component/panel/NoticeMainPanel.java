package org.example.ui.component.panel;

import javax.swing.*;
import java.awt.*;

public class NoticeMainPanel extends JPanel {
    public NoticeMainPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.WHITE);

        // 샘플 알림 데이터
        String[][] notifications = {
                {"경규님이 회원님의 게시물을 좋아합니다.", "방금 전"},
                {"찬욱님이 회원님의 게시물에 댓글을 작성했습니다.", "10분 전"},
                {"현서님이 회원님의 댓글에 답글을 작성하였습니다.", "30분 전"},
                {"선민님이 회원님의 댓글을 좋아합니다.", "1시간 전"},
                {"경규님이 회원님의 댓글을 좋아합니다.", "3시간 전"},
                {"선민님이 회원님의 게시물을 좋아합니다.", "7시간 전"},
                {"찬욱님이 회원님의 댓글을 좋아합니다.", "1일 전"},
                {"현서님이 팔로우 요청을 보냈습니다.", "2일 전"},
                {"경규님이 회원님의 댓글을 좋아합니다.", "4일 전"},
                {"찬욱님이 회원님의 게시물을 좋아합니다.", "6일 전"},
                {"현서님이 팔로우하기 시작했습니다.", "2024-10-05"},
                {"경규님이 팔로우 요청을 수락하였습니다.", "2024-10-05"},
                {"현서님이 팔로우 요청을 보냈습니다.", "2024-10-04"},
                {"선민님이 팔로우 요청을 수락하였습니다.", "2024-10-04"},
                {"찬욱님이 팔로우하기 시작했습니다.", "2024-10-04"},
                {"선민님이 팔로우하기 시작했습니다.", "2024-10-03"}
        };

        // 알림 항목 추가
        for (String[] notification : notifications) {
            NoticePanel notificationPanel = new NoticePanel();
            notificationPanel.setLayout(new BorderLayout());
            notificationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            notificationPanel.setBackground(Color.WHITE);
            notificationPanel.setMaximumSize(new Dimension(400, 50));
            notificationPanel.setPreferredSize(new Dimension(400, 50));
            notificationPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            // 알림 내용
            JLabel messageLabel = new JLabel(notification[0]);
            messageLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            notificationPanel.add(messageLabel, BorderLayout.WEST);

            // 알림 시간
            JLabel timeLabel = new JLabel(notification[1]);
            timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
            notificationPanel.add(timeLabel, BorderLayout.EAST);

            add(notificationPanel);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Notice Panel Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(450, 700);
        frame.add(new NoticeMainPanel());
        frame.setVisible(true);
    }
}



