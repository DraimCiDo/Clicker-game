package me.draimgoose.animations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Stack;

public class PlusOneAnimation {
    private static final int MAX_PLUS_ONE_LABELS = 10;
    private static Stack<JLabel> labelPool = new Stack<>(); // Пул для меток "+1"

    public static void showPlusOneAnimation(JPanel panel, List<JLabel> activePlusOneLabels, int clickValue) {
        JLabel plusOneLabel;

        if (!labelPool.isEmpty()) {
            plusOneLabel = labelPool.pop(); // Берем метку из пула
            plusOneLabel.setText("+" + clickValue);
        } else {
            plusOneLabel = new JLabel("+" + clickValue);
            plusOneLabel.setForeground(Color.RED);
            plusOneLabel.setFont(new Font("Arial", Font.BOLD, 18));
            plusOneLabel.setSize(50, 30);
        }

        // Генерируем случайные координаты для метки "+1"
        int randomX = (int) (Math.random() * (panel.getWidth() - plusOneLabel.getWidth()));
        int randomY = (int) (Math.random() * (panel.getHeight() - plusOneLabel.getHeight()));

        plusOneLabel.setLocation(randomX, randomY);
        panel.add(plusOneLabel);
        activePlusOneLabels.add(plusOneLabel); // Добавляем метку в список активных

        Timer plusOneTimer = new Timer(50, new ActionListener() {
            int steps = 0;
            int startY = randomY;

            @Override
            public void actionPerformed(ActionEvent e) {
                steps++;
                plusOneLabel.setLocation(randomX, startY - steps * 2);
                if (steps > 20) {
                    ((Timer) e.getSource()).stop();
                    panel.remove(plusOneLabel);
                    activePlusOneLabels.remove(plusOneLabel); // Удаляем метку из списка активных
                    labelPool.push(plusOneLabel); // Возвращаем метку в пул
                    panel.repaint();
                }
            }
        });
        plusOneTimer.setRepeats(true); // Обеспечиваем повторение
        plusOneTimer.start();
    }
}
