package me.draimgoose.panels;

import me.draimgoose.config.GameConfig;

import javax.swing.*;
import java.awt.*;

public class SettingsPanel {
    private JPanel panel;

    public SettingsPanel() {
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel titleLabel = new JLabel("Настройки");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        JCheckBox animationsCheckBox = new JCheckBox("Включить анимации");
        animationsCheckBox.setSelected(GameConfig.areAnimationsEnabled());
        animationsCheckBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        animationsCheckBox.addActionListener(e -> GameConfig.setAnimationsEnabled(animationsCheckBox.isSelected()));
        panel.add(animationsCheckBox);
    }

    public JPanel getPanel() {
        return panel;
    }
}
