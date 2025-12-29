package org.example;

import org.example.View.RewardView;
// import org.example.Controller.RewardController;

import javax.swing.*;

public class MainTestReward {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Test Reward");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(1000, 650);
            frame.setLocationRelativeTo(null);

            RewardView view = new RewardView();

            // Nếu m đã có controller thì mở dòng này:
            // new RewardController(view);

            frame.setContentPane(view);
            frame.setVisible(true);
        });
    }
}
