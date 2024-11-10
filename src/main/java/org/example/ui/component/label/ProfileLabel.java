package org.example.ui.component.label;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class ProfileLabel extends JLabel {
    private final int diameter;
    private final String imagePath;

    public ProfileLabel() {
        this(40, "src/main/java/org/example/asset/profileImage.png");  // 기본 크기와 경로
    }

    public ProfileLabel(int diameter, String imagePath) {
        this.diameter = diameter;
        this.imagePath = imagePath;
        initializeLabel();
    }

    private void initializeLabel() {
        try {
            ImageIcon profileIcon = new ImageIcon(imagePath);
            if (profileIcon.getIconWidth() > 0) {
                // 이미지가 정상적으로 로드된 경우
                setIcon(createRoundedIcon(profileIcon.getImage(), diameter));
            } else {
                // 이미지 로드 실패 시 기본 회색 원형 이미지 생성
                setIcon(createDefaultIcon());
            }
        } catch (Exception e) {
            System.out.println("프로필 이미지 로드 실패: " + e.getMessage());
            setIcon(createDefaultIcon());
        }
    }

    private ImageIcon createRoundedIcon(Image image, int size) {
        BufferedImage rounded = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = rounded.createGraphics();

        // 안티앨리어싱 설정
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // 원형 마스크 생성
        g2.setComposite(AlphaComposite.Clear);
        g2.fillRect(0, 0, size, size);
        g2.setComposite(AlphaComposite.Src);
        g2.setColor(Color.WHITE);
        g2.fillOval(0, 0, size-1, size-1);

        // 이미지 그리기
        g2.setComposite(AlphaComposite.SrcAtop);
        g2.drawImage(image, 0, 0, size-1, size-1, null);

        // 테두리 추가
        g2.setComposite(AlphaComposite.SrcOver);
        g2.setColor(Color.LIGHT_GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.drawOval(0, 0, size-2, size-2);

        g2.dispose();
        return new ImageIcon(rounded);
    }

    private ImageIcon createDefaultIcon() {
        BufferedImage defaultImage = new BufferedImage(diameter, diameter, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = defaultImage.createGraphics();

        // 안티앨리어싱 설정
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 회색 원 그리기
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillOval(0, 0, diameter-1, diameter-1);

        // 테두리 추가
        g2.setColor(Color.GRAY);
        g2.setStroke(new BasicStroke(1));
        g2.drawOval(0, 0, diameter-2, diameter-2);

        g2.dispose();
        return new ImageIcon(defaultImage);
    }

    // 이미지 경로 변경 메소드
    public void setImagePath(String newPath) {
        ImageIcon newIcon = new ImageIcon(newPath);
        if (newIcon.getIconWidth() > 0) {
            setIcon(createRoundedIcon(newIcon.getImage(), diameter));
        }
    }

    // 이미지 크기 변경 메소드
    public void setDiameter(int newDiameter) {
        ImageIcon currentIcon = (ImageIcon) getIcon();
        if (currentIcon != null) {
            setIcon(createRoundedIcon(currentIcon.getImage(), newDiameter));
        }
    }
}