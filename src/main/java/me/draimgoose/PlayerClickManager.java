package me.draimgoose;

import me.draimgoose.ui.ClickerGameUI;

public class PlayerClickManager {
    private ClickerGameUI gameUI;
    private int battery = 100;
    private final int maxBattery = 100;

    public PlayerClickManager(ClickerGameUI gameUI) {
        this.gameUI = gameUI;
    }

    public boolean canClick() {
        return battery > 0;
    }

    public void useClick() {
        if (battery > 0) {
            battery--;
            gameUI.updateBatteryDisplay(battery, maxBattery);  // Используем ClickerGameUI для обновления интерфейса
        }
    }

    public void rechargeBattery() {
        battery = maxBattery;
        gameUI.updateBatteryDisplay(battery, maxBattery);  // Используем ClickerGameUI для обновления интерфейса
    }
}
