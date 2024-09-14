package me.draimgoose.config;

public class GameConfig {
    private static boolean animationsEnabled = true;  // По умолчанию анимации включены

    public static boolean areAnimationsEnabled() {
        return animationsEnabled;
    }

    public static void setAnimationsEnabled(boolean enabled) {
        animationsEnabled = enabled;
    }
}
