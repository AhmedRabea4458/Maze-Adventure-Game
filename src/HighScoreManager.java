import model.Level;
import java.util.prefs.Preferences;

public class HighScoreManager {

    private static final Preferences prefs =
            Preferences.userNodeForPackage(HighScoreManager.class);

    private static String key(Level level) {
        return "best_level_" + level.getLevelNumber();
    }

    public static void saveBestScore(Level level, int finalScore) {
        prefs.putInt(key(level), finalScore);
    }


    public static int getBestScore(Level level) {
        return prefs.getInt(key(level), 0);
    }
    public static void clearBestScore(Level level) {
        prefs.remove(key(level));
    }
}
