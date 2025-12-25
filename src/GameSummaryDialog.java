import model.Level;

import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class GameSummaryDialog extends JDialog {

    private static final int BASE_SCORE = 1000;

    private final int bestScore;
    private final boolean isNewBest;

    public GameSummaryDialog(
            JFrame parent,
            boolean isWin,
            Level level,
            int seconds,
            int moves,
            boolean usedSolve,
            int finalScore,
            int maxCombo,
            int bestScore,
            boolean isNewBest
    ) {
        super(parent, true);
        this.bestScore = bestScore;
        this.isNewBest = isNewBest;

        setTitle("Game Summary");
        setSize(460, 600);
        setLocationRelativeTo(parent);
        setResizable(false);

        initUI(isWin, level, seconds, moves, usedSolve, finalScore, maxCombo);
    }

    private void initUI(
            boolean isWin,
            Level level,
            int seconds,
            int moves,
            boolean usedSolve,
            int finalScore,
            int maxCombo
    ) {

        Color mainColor = isWin
                ? new Color(34, 197, 94)
                : new Color(239, 68, 68);

        JPanel root = new JPanel();
        root.setLayout(new BoxLayout(root, BoxLayout.Y_AXIS));
        root.setBackground(AppColors.DIALOG_DEFAULT());
root.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));

        // ===== HEADER =====
        root.add(createHeader(isWin, mainColor));
        root.add(Box.createVerticalStrut(20));

        // ===== INFO =====
        root.add(createInfoGrid(level, seconds, moves, usedSolve));
        root.add(Box.createVerticalStrut(15));
        root.add(separator());

        // ===== SCORE =====
        int timeBonus = calculateTimeBonus(seconds, level);
        int noSolveBonus = usedSolve ? 0 : calculateNoSolveBonus(level);
        int comboBonus = calculateComboBonus(maxCombo);

        root.add(Box.createVerticalStrut(10));
        root.add(createScoreCard(timeBonus, noSolveBonus, comboBonus, finalScore));

        // ===== ACHIEVEMENTS =====
        if (isWin) {
            root.add(Box.createVerticalStrut(15));
            root.add(separator());
            root.add(Box.createVerticalStrut(10));
            root.add(createAchievements(seconds, usedSolve, maxCombo));
        }

        // ===== BUTTON =====
        root.add(Box.createVerticalStrut(25));
        JButton ok = new JButton("OK");
        ok.setAlignmentX(Component.CENTER_ALIGNMENT);
        ok.addActionListener(e -> dispose());
        root.add(ok);

        setContentPane(root);

        SwingUtilities.updateComponentTreeUI(this);

    }

    // ================= HEADER =================
    private JPanel createHeader(boolean isWin, Color color) {

        JPanel header = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        header.setOpaque(false);

        ImageIcon icon = loadIconSafe(
                isWin ? "/icons/win.png" : "/icons/loss.png", 32
        );
        if (icon != null) {
            header.add(new JLabel(icon));
        }

        JLabel title = new JLabel(isWin ? "LEVEL COMPLETED" : "GAME OVER");
        title.setFont(new Font("Arial", Font.BOLD, 22));
        title.setForeground(color);

        header.add(title);
        return header;
    }

    // ================= INFO GRID =================
    private JPanel createInfoGrid(Level level, int seconds, int moves, boolean usedSolve) {

        JPanel grid = new JPanel(new GridLayout(4, 2, 12, 8));
        grid.setOpaque(false);

        grid.add(infoKey("Level", "/icons/score.png"));
        grid.add(infoValue(level.getLevelTitle()));

        grid.add(infoKey("Time", "/icons/time.png"));
        grid.add(infoValue(formatTime(seconds)));

        grid.add(infoKey("Moves", "/icons/moves.png"));
        grid.add(infoValue(String.valueOf(moves)));

        grid.add(infoKey("Solve Used", "/icons/solve.png"));
        grid.add(infoValue(usedSolve ? "Yes" : "No"));

        return grid;
    }

    // ================= SCORE CARD =================
    private JPanel createScoreCard(
            int timeBonus,
            int noSolveBonus,
            int comboBonus,
            int finalScore
    ) {

        JPanel card = new JPanel(new GridLayout(6, 2, 10, 6));
        card.setBackground(
                Theme.isDarkMode()
                        ? new Color(40, 40, 40)
                        : new Color(243, 244, 246)
        );
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        addScoreRow(card, "Base Score", BASE_SCORE, "/icons/score.png");
        addScoreRow(card, "Time Bonus", "+" + timeBonus, "/icons/time.png");
        addScoreRow(card, "No Solve Bonus", "+" + noSolveBonus, "/icons/brain.png");
        addScoreRow(card, "Combo Bonus", "+" + comboBonus, "/icons/combo.png");

        addScoreRow(card, "Final Score", finalScore, "/icons/score.png", true);

        // ===== BEST SCORE =====
        JPanel key = infoKey("Best Score", "/icons/bestScore.png");
        JLabel value = infoValue(String.valueOf(bestScore));

        if (isNewBest) {
            value.setText(bestScore + "  NEW!");
            value.setForeground(new Color(34, 197, 94));
            value.setFont(new Font("Arial", Font.BOLD, 15));
        }

        card.add(key);
        card.add(value);

        return card;
    }

    private void addScoreRow(JPanel card, String title, Object value, String icon) {
        addScoreRow(card, title, value, icon, false);
    }

    private void addScoreRow(JPanel card, String title, Object value, String icon, boolean bold) {
        card.add(infoKey(title, icon));
        JLabel v = infoValue(String.valueOf(value));
        if (bold) {
            v.setFont(new Font("Arial", Font.BOLD, 16));
        }
        card.add(v);
    }

    // ================= ACHIEVEMENTS =================
    private JPanel createAchievements(int seconds, boolean usedSolve, int maxCombo) {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);

        JLabel title = new JLabel("Achievements");
        title.setForeground(AppColors.TEXT_PRIMARY());
        title.setFont(new Font("Arial", Font.BOLD, 16));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);
        panel.add(Box.createVerticalStrut(8));

        boolean any = false;

        if (seconds < 40) {
            panel.add(achievement("Speed Runner", "/icons/run.png"));
            any = true;
        }
        if (!usedSolve) {
            panel.add(achievement("No Solver", "/icons/brain.png"));
            any = true;
        }
        if (seconds < 40 && !usedSolve) {
            panel.add(achievement("Perfect Run", "/icons/target.png"));
            any = true;
        }
        if (maxCombo >= 5) {
            panel.add(achievement("Combo Master", "/icons/combo.png"));
            any = true;
        }

        if (!any) {
            panel.add(new JLabel("— None —"));
        }

        return panel;
    }

    // ================= HELPERS =================
    private JPanel infoKey(String text, String iconPath) {

        JPanel p = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        p.setOpaque(false);

        ImageIcon icon = loadIconSafe(iconPath, 16);
        if (icon != null) {
            p.add(new JLabel(icon));
        }

        JLabel l = new JLabel(text + ":");
        l.setForeground(AppColors.TEXT_PRIMARY());
        l.setFont(new Font("Arial", Font.BOLD, 14));
        p.add(l);

        return p;
    }

    private JLabel infoValue(String text) {
        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        l.setForeground(AppColors.TEXT_PRIMARY());
        return l;
    }

    private JPanel achievement(String text, String iconPath) {

        JPanel row = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        row.setOpaque(false);

        ImageIcon icon = loadIconSafe(iconPath, 18);
        if (icon != null) {
            row.add(new JLabel(icon));
        }

        JLabel l = new JLabel(text);
        l.setFont(new Font("Arial", Font.PLAIN, 14));
        l.setForeground(AppColors.TEXT_PRIMARY());

        row.add(l);

        return row;
    }

    private ImageIcon loadIconSafe(String path, int size) {
        URL url = getClass().getResource(path);
        if (url == null) return null;

        Image img = new ImageIcon(url).getImage();
        return new ImageIcon(img.getScaledInstance(size, size, Image.SCALE_SMOOTH));
    }

    private JSeparator separator() {
        JSeparator sep = new JSeparator();
        sep.setMaximumSize(new Dimension(Integer.MAX_VALUE, 1));
        return sep;
    }

    private String formatTime(int seconds) {
        int min = seconds / 60;
        int sec = seconds % 60;
        return String.format("%02d:%02d", min, sec);
    }

    // ================= SCORE LOGIC =================
    private int calculateTimeBonus(int seconds, Level level) {
        return switch (level.getLevelTitle()) {
            case "Easy" -> seconds < 20 ? 100 : 0;
            case "Medium" -> seconds < 50 ? 200 : 0;
            case "Hard" -> seconds < 70 ? 300 : 0;
            default -> 0;
        };
    }

    private int calculateNoSolveBonus(Level level) {
        return switch (level.getLevelTitle()) {
            case "Easy" -> 50;
            case "Medium" -> 100;
            case "Hard" -> 150;
            default -> 0;
        };
    }

    private int calculateComboBonus(int maxCombo) {
        if (maxCombo >= 7) return 150;
        if (maxCombo >= 5) return 100;
        if (maxCombo >= 3) return 50;
        return 0;
    }
}
