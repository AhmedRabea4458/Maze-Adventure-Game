public class Theme {
    private static boolean darkMode = false;

    public static void toggleDarkMode() {
        darkMode = !darkMode;
    }

    public static boolean isDarkMode() {

        return darkMode;
    }


}
