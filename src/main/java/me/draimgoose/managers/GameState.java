package me.draimgoose.managers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.draimgoose.ui.ClickerGameUI;

import java.io.*;
import java.lang.reflect.Type;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameState {

    private static GameState instance;
    private static final Logger logger = Logger.getLogger(GameState.class.getName());

    private int score;
    private int autoClicks;
    private int battery;
    private int maxBattery;
    private int level;
    private double upgradeMultiplier;

    private final String SAVE_FILE = "game_state.json";

    private GameState() {
        // Инициализация начальных значений
        score = 0;
        autoClicks = 0;
        battery = 100;
        maxBattery = 100;
        level = 1;
        upgradeMultiplier = 1.0;
    }

    public static GameState getInstance() {
        if (instance == null) {
            instance = new GameState();
            instance.loadState();
        }
        return instance;
    }

    // Геттеры и сеттеры

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }


    public int getAutoClicks() {
        return autoClicks;
    }

    public void setAutoClicks(int autoClicks) {
        this.autoClicks = autoClicks;
    }

    public int getBattery() {
        return battery;
    }

    public void setBattery(int battery) {
        this.battery = battery;
    }

    public int getMaxBattery() {
        return maxBattery;
    }

    public void setMaxBattery(int maxBattery) {
        this.maxBattery = maxBattery;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public double getUpgradeMultiplier() {
        return upgradeMultiplier;
    }

    public void setUpgradeMultiplier(double upgradeMultiplier) {
        this.upgradeMultiplier = upgradeMultiplier;
    }

    // Сохранение состояния игры
    public void saveState() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(SAVE_FILE)) {
            gson.toJson(this, writer);
            logger.info("Состояние игры успешно сохранено.");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при сохранении состояния игры: ", e);
            // Дополнительно можно уведомить пользователя через UI
        }
    }

    // Загрузка состояния игры
    public void loadState() {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(SAVE_FILE)) {
            Type type = new TypeToken<GameState>() {}.getType();
            GameState loadedState = gson.fromJson(reader, type);
            if (loadedState != null) {
                this.score = loadedState.score;
                this.autoClicks = loadedState.autoClicks;
                this.battery = loadedState.battery;
                this.maxBattery = loadedState.maxBattery;
                this.level = loadedState.level;
                this.upgradeMultiplier = loadedState.upgradeMultiplier;
                logger.info("Состояние игры успешно загружено.");
            }
        } catch (FileNotFoundException e) {
            logger.log(Level.WARNING, "Файл сохранения не найден. Используются начальные значения.", e);
            // Возможно, показать уведомление пользователю
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Ошибка при загрузке состояния игры: ", e);
            // Дополнительно можно уведомить пользователя через UI
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Неизвестная ошибка при загрузке состояния игры: ", e);
        }
    }
}
