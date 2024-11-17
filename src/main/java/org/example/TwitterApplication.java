package org.example;

import org.example.ui.page.HomeFeedPage;
import org.example.ui.page.LoginPage;
import org.example.util.config.JdbcConfig;
import org.example.ui.page.NewTweetPage;
import org.example.ui.page.MyPage;

import org.example.ui.page.FollowPage;

import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;

public class TwitterApplication {
    public static void main(String[] args) {
        try (Connection finalCon = JdbcConfig.getConnection()) {
            SwingUtilities.invokeLater(() -> new HomeFeedPage(finalCon));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}