package me.draimgoose;

public class Clicker {
    private GamePanel gamePanel;
    private int autoClicks = 0;
    private int autoClickInterval = 1000; // Интервал автокликов в миллисекундах

    public Clicker(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        startAutoClick();
    }

    public void startAutoClick() {
        new javax.swing.Timer(autoClickInterval, e -> {
            if (autoClicks > 0) {
                gamePanel.setScore(gamePanel.getScore() + autoClicks);
            }
        }).start();
    }

    public void increaseAutoClicks() {
        autoClicks++;
        gamePanel.updateAutoClickDisplay(autoClicks);
    }
}
