package me.draimgoose.animations;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.Stack;

public class PlusOneAnimation implements AnimationManager.AnimatedObject {
    private static Stack<JLabel> labelPool = new Stack<>();
    private JLabel label;
    private JPanel panel;
    private int startY;
    private int steps;
    private List<JLabel> activePlusOneLabels;

    public PlusOneAnimation(JPanel panel, List<JLabel> activePlusOneLabels, int clickValue, AnimationManager manager) {
        this.panel = panel;
        this.activePlusOneLabels = activePlusOneLabels;
        this.steps = 0;

        if (!labelPool.isEmpty()) {
            label = labelPool.pop();
            label.setText("+" + clickValue);
        } else {
            label = new JLabel("+" + clickValue);
            label.setForeground(Color.RED);
            label.setFont(new Font("Arial", Font.BOLD, 18));
            label.setSize(50, 30);
        }

        int randomX = (int) (Math.random() * (panel.getWidth() - label.getWidth()));
        startY = (int) (Math.random() * (panel.getHeight() - label.getHeight()));

        label.setLocation(randomX, startY);
        panel.add(label);
        activePlusOneLabels.add(label);

        manager.addAnimatedObject(this);
    }

    @Override
    public boolean update() {
        steps++;
        label.setLocation(label.getX(), startY - steps * 2);
        if (steps > 20) {
            panel.remove(label);
            activePlusOneLabels.remove(label);
            labelPool.push(label);
            return false; // Анимация завершена
        }
        return true; // Анимация продолжается
    }
}
