package me.draimgoose;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PlayerClickManager {
    private GamePanel gamePanel;
    private int maxBattery = 100; // Максимальная батарея
    private int currentBattery = maxBattery; // Текущая батарея
    private int clickValue = 1; // Очки за клик
    private Timer batteryRechargeTimer; // Таймер для восстановления батареи

    public PlayerClickManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        startBatteryRecharge(); // Запуск таймера для восстановления батареи
    }

    public boolean canClick() {
        return currentBattery > 0;
    }

    public void useClick() {
        if (currentBattery > 0) {
            currentBattery--;
            gamePanel.updateBatteryDisplay(currentBattery, maxBattery);
        }
    }

    public int getClickValue() {
        return clickValue;
    }

    public void increaseMaxBattery(int amount) {
        maxBattery += amount;
        currentBattery += amount; // Увеличиваем текущую батарею на столько же
        gamePanel.updateBatteryDisplay(currentBattery, maxBattery);
    }

    public void increaseClickValue() {
        clickValue++;
    }

    private void startBatteryRecharge() {
        batteryRechargeTimer = new Timer(60000, new ActionListener() { // Восстанавливаем батарею каждые 60 секунд
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBattery = maxBattery;
                gamePanel.updateBatteryDisplay(currentBattery, maxBattery);
            }
        });
        batteryRechargeTimer.setRepeats(true);
        batteryRechargeTimer.start();
    }
}
