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
    private final Component parentComponent;  // JPanel 대신 Component 사용
    private final boolean showCommentIcon;
    private boolean isLiked = false;

    // 기본 생성자
    public InteractionPanel(Component parentComponent) {
        this(parentComponent, true);
    }

    // 메인 생성자
    public InteractionPanel(Component parentComponent, boolean showCommentIcon) {
        this.parentComponent = parentComponent;
        this.showCommentIcon = showCommentIcon;
        this.commentPanel = new JPanel();
        this.heartPanel = new JPanel();
        initializePanel();
    }

    private void initializePanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(0, -5, 0, 0));

        if (showCommentIcon) {
            setupCommentPanel();
        }
        setupHeartPanel();
    }

    private void setupCommentPanel() {
        commentPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        commentPanel.setBackground(Color.WHITE);
        commentPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 50));

        InteractionLabel commentLabel = new InteractionLabel("comment.png", "0", parentComponent);
        commentPanel.add(commentLabel);
        add(commentPanel);
    }

    private void setupHeartPanel() {
        heartPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 0));
        heartPanel.setBackground(Color.WHITE);

        InteractionLabel heartLabel = new InteractionLabel("heart.png", "0", parentComponent);
        heartPanel.add(heartLabel);
        add(heartPanel);
    }

    public void setCommentCount(String count) {
        if (showCommentIcon) {
            updateLabelCount(commentPanel, count);
        }
    }

    public void setLikeCount(String count) {
        updateLabelCount(heartPanel, count);
    }

    private void updateLabelCount(JPanel panel, String count) {
        for (Component component : panel.getComponents()) {
            if (component instanceof InteractionLabel) {
                ((InteractionLabel) component).setCount(count);
                break;
            }
        }
    }

    public String getCommentCount() {
        return showCommentIcon ? getLabelCount(commentPanel) : "0";
    }

    public String getLikeCount() {
        return getLabelCount(heartPanel);
    }

    private String getLabelCount(JPanel panel) {
        for (Component component : panel.getComponents()) {
            if (component instanceof InteractionLabel) {
                return ((InteractionLabel) component).getCount();
            }
        }
        return "0";
    }

    public boolean isLiked() {
        for (Component component : heartPanel.getComponents()) {
            if (component instanceof InteractionLabel) {
                return ((InteractionLabel) component).isLiked();
            }
        }
        return false;
    }

    public void addCommentClickListener(ActionListener listener) {
        if (!showCommentIcon) return;

        for (Component comp : commentPanel.getComponents()) {
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
                        setCursor(new Cursor(Cursor.HAND_CURSOR));
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                    }
                });
            }
        }
    }
}