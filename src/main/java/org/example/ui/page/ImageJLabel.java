package org.example.ui.page;

import javax.swing.*;
import java.awt.*;

// 생성자: 이미지 경로, 너비, 높이를 받아 JLabel에 리사이징된 이미지를 설정
public class ImageJLabel extends JLabel {
    public ImageJLabel(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(getClass().getResource(path)); // 이미지 아이콘을 경로로부터 로드
        Image originalImage = originalIcon.getImage(); // 원본 이미지 가져오기
        Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH); // 지정된 크기로 이미지 리사이즈
        this.setIcon(new ImageIcon(resizedImage)); // 리사이즈된 이미지를 JLabel의 아이콘으로 설정
    }
}
