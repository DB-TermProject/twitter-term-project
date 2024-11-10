package org.example.ui.component.panel;

import org.example.ui.component.label.InteractionLabel;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class InteractionPanel extends JPanel {
    private final JPanel commentPanel;
    private final JPanel heartPanel;
    private final JFrame parentFrame;
    private ActionListener commentClickListener;

    public InteractionPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        this.commentPanel = new JPanel();
        this.heartPanel = new JPanel();
        initializePanel();
    }

    private void initializePanel() {
        // 메인 패널 설정
        setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        setBackground(Color.WHITE);
        setName("interactionPanel");
        setBorder(BorderFactory.createEmptyBorder(0, 40, 0, 0));

        // 댓글 패널 설정
        commentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        commentPanel.setBackground(Color.WHITE);
        commentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 75));
        InteractionLabel commentLabel = new InteractionLabel("comment.png", "0", parentFrame);
        commentPanel.add(commentLabel);

        // 하트 패널 설정
        heartPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        heartPanel.setBackground(Color.WHITE);
        heartPanel.add(new InteractionLabel("heart.png", "0", parentFrame));

        // 패널들을 메인 패널에 추가
        add(commentPanel);
        add(heartPanel);
    }

    public void setCommentCount(String count) {
        updateCount(commentPanel, count);
    }

    public void setLikeCount(String count) {
        updateCount(heartPanel, count);
    }

    private void updateCount(JPanel panel, String count) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof InteractionLabel) {
                ((InteractionLabel) component).setCount(count);
                break;
            }
        }
    }

    public String getCommentCount() {
        return getCount(commentPanel);
    }

    public String getLikeCount() {
        return getCount(heartPanel);
    }

    private String getCount(JPanel panel) {
        Component[] components = panel.getComponents();
        for (Component component : components) {
            if (component instanceof InteractionLabel) {
                return String.valueOf(((InteractionLabel) component).getCount());
            }
        }
        return "0";
    }

    public boolean isLiked() {
        Component[] components = heartPanel.getComponents();
        for (Component component : components) {
            if (component instanceof InteractionLabel) {
                return ((InteractionLabel) component).isLiked();
            }
        }
        return false;
    }

    public void addCommentClickListener(ActionListener listener) {
        Component[] components = commentPanel.getComponents();
        for (Component comp : components) {
            if (comp instanceof InteractionLabel) {
                InteractionLabel label = (InteractionLabel) comp;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        listener.actionPerformed(new ActionEvent(label,
                                ActionEvent.ACTION_PERFORMED, "commentClick"));
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        setCursorHand();
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setCursorDefault();
                    }
                });
            }
        }
    }

    public void setCursorHand() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    public void setCursorDefault() {
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
}