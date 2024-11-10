package org.example.ui.component.label;

import javax.swing.*;
import java.awt.*;

public class UserInfoLabel extends JPanel {
    private final JLabel nameLabel;
    private final JLabel handleLabel;

    // 기본 생성자
    public UserInfoLabel(String username, String handle) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));  // 왼쪽 정렬, 수평 간격 5
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));  // 여백 제거

        // 사용자 이름 레이블
        nameLabel = new JLabel(username);
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        // 핸들(@username) 레이블
        handleLabel = new JLabel(handle);
        handleLabel.setForeground(Color.GRAY);
        handleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        // 컴포넌트 추가
        add(nameLabel);
        add(handleLabel);
    }

    // 시간 정보가 포함된 생성자
    public UserInfoLabel(String username, String handle, String timeStamp) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));

        // 사용자 이름 레이블
        nameLabel = new JLabel(username);
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

        // 핸들과 시간 정보를 포함한 레이블
        handleLabel = new JLabel(handle + " · " + timeStamp);
        handleLabel.setForeground(Color.GRAY);
        handleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        // 컴포넌트 추가
        add(nameLabel);
        add(handleLabel);
    }

    // 사용자 이름 변경 메소드
    public void setUsername(String username) {
        nameLabel.setText(username);
    }

    // 핸들 변경 메소드
    public void setHandle(String handle) {
        handleLabel.setText(handle);
    }

    // 시간 정보 추가 메소드
    public void setTimeStamp(String timeStamp) {
        String currentHandle = handleLabel.getText().split(" · ")[0];
        handleLabel.setText(currentHandle + " · " + timeStamp);
    }

    // 사용자 이름 가져오기
    public String getUsername() {
        return nameLabel.getText();
    }

    // 핸들 가져오기
    public String getHandle() {
        return handleLabel.getText().split(" · ")[0];
    }

    // 시간 정보 가져오기
    public String getTimeStamp() {
        String[] parts = handleLabel.getText().split(" · ");
        return parts.length > 1 ? parts[1] : "";
    }

    // 폰트 크기 변경 메소드
    public void setFontSize(int nameSize, int handleSize) {
        nameLabel.setFont(new Font("맑은 고딕", Font.BOLD, nameSize));
        handleLabel.setFont(new Font("맑은 고딕", Font.PLAIN, handleSize));
    }
}