import java.awt.*;

public class AppColors {
    public static Color HEADER() {
        return Theme.isDarkMode()
                ? new Color(60, 20, 120)   // Dark Purple
                : new Color(106, 8, 199);  // Original Purple
    }

    public static Color BACKGROUND() {
        return Theme.isDarkMode()
                ? new Color(18, 18, 18)
                : new Color(245, 245, 245);
    }

    public static Color CARD() {
        return Theme.isDarkMode()
                ? new Color(30, 30, 30)
                : Color.WHITE;
    }

    public static Color TEXT_PRIMARY() {
        return Theme.isDarkMode()
                ? Color.WHITE
                : Color.BLACK;
    }

    public static Color TEXT_MUTED() {
        return Theme.isDarkMode()
                ? new Color(180, 180, 180)
                : new Color(90, 90, 90);
    }

    public static Color SCORE() {
        return Theme.isDarkMode()
                ? new Color(220, 70, 90)
                : new Color(233, 54, 90);
    }

    public static Color TIME() {
        return Theme.isDarkMode()
                ? new Color(20, 150, 160)
                : new Color(14, 128, 132);
    }

    public static Color LEVEL_EASY() {
        return Theme.isDarkMode()
                ? new Color(0, 160, 100)
                : new Color(0, 180, 100);
    }

    public static Color LEVEL_MEDIUM() {
        return Theme.isDarkMode()
                ? new Color(200, 140, 30)
                : new Color(218, 132, 19);
    }

    public static Color LEVEL_HARD() {
        return Theme.isDarkMode()
                ? new Color(200, 80, 80)
                : new Color(200, 60, 60);
    }

    public static Color MAZE_WALL() {
        return Theme.isDarkMode()
                ? new Color(200, 200, 200)
                : Color.DARK_GRAY;
    }

    public static Color PATH() {
        return Theme.isDarkMode()
                ? new Color(120, 180, 255)
                : Color.BLUE;
    }

    public static Color PLAYER() {
        return Color.RED;
    }

    public static Color STAR() {
        return new Color(255, 215, 0); // Gold ⭐
    }
    public static Color BTN_START() {
        return Theme.isDarkMode()
                ? new Color(34, 197, 94)
                : new Color(11, 198, 61);
    }

    public static Color BTN_NEW_GAME() {
        return Theme.isDarkMode()
                ? new Color(90, 90, 90)
                : new Color(120, 120, 120);
    }

    public static Color BTN_SOLVE() {
        return Theme.isDarkMode()
                ? new Color(56, 189, 248)
                : new Color(14, 128, 132);
    }

    public static Color BTN_EXIT() {
        return Theme.isDarkMode()
                ? new Color(220, 38, 38)
                : Color.RED;
    }

    public static Color BTN_YES() {
        return Theme.isDarkMode()
                ? new Color(34, 197, 94)
                : new Color(80, 180, 80);
    }

    public static Color BTN_NO() {
        return Theme.isDarkMode()
                ? new Color(239, 68, 68)
                : new Color(200, 80, 80);
    }

    public static Color DIALOG_DEFAULT() {
        return Theme.isDarkMode()
                ? new Color(25, 25, 25)
                : Color.WHITE;
    }



}
