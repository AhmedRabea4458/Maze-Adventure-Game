import model.Level;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RightSidePanel extends JPanel {


    private Timer gameTimer;
    private int seconds = 0;
    private int maxCombo = 0;

    private int score = 1000;
    private static final int START_SCORE = 1000;
    private static final int TIME_PENALTY = 2;
    private static final int MOVE_PENALTY = 5;
    private static final int SOLVE_PENALTY = 300;

    private final JLabel timeValue;
    private final JLabel scoreValue;
    private final JLabel levelValue;
    private final JPanel levelBox;
    private JLabel scoreDeltaLabel;
    private Timer deltaTimer;
    private GameOverListener gameOverListener;


    public void setGameOverListener(GameOverListener listener) {
        this.gameOverListener = listener;
    }


    public RightSidePanel() {

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(220, 600));
        setBackground(AppColors.BACKGROUND_LIGHT);

        scoreValue = new JLabel("0");
        timeValue = new JLabel("00:00");
        levelValue = new JLabel("Easy");

        add(Box.createVerticalStrut(20));
        add(createScoreBox());
        add(Box.createVerticalStrut(15));

        add(createInfoBox("TIME", timeValue, AppColors.TIME_BLUE));
        add(Box.createVerticalStrut(15));

        levelBox = createInfoBox("LEVEL", levelValue, AppColors.LEVEL_EASY);
        add(levelBox);

        add(Box.createVerticalStrut(25));
        add(createHowToPlay());

        initTimer();
    }


    private JPanel createInfoBox(String title, JLabel value, Color color) {

        JPanel box = new JPanel(new GridLayout(2, 1));
        box.setPreferredSize(new Dimension(200, 90));
        box.setBackground(color);
        box.setBorder(new LineBorder(AppColors.BACKGROUND_LIGHT, 2, true));

        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setForeground(Color.WHITE);

        value.setHorizontalAlignment(SwingConstants.CENTER);
        value.setFont(new Font("Arial", Font.BOLD, 18));
        value.setForeground(Color.WHITE);

        box.add(titleLabel);
        box.add(value);

        return box;
    }
    private JPanel createScoreBox() {

        JPanel box = new JPanel(new GridLayout(3, 1));
        box.setPreferredSize(new Dimension(200, 90)); // نفس الباقي
        box.setBackground(AppColors.SCORE_RED);
        box.setBorder(new LineBorder(AppColors.BACKGROUND_LIGHT, 2, true));

        JLabel title = new JLabel("SCORE", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 14));
        title.setForeground(Color.WHITE);

        scoreValue.setHorizontalAlignment(SwingConstants.CENTER);
        scoreValue.setFont(new Font("Arial", Font.BOLD, 18));
        scoreValue.setForeground(Color.WHITE);

        scoreDeltaLabel = new JLabel("", SwingConstants.CENTER);
        scoreDeltaLabel.setFont(new Font("Arial", Font.BOLD, 14));
        scoreDeltaLabel.setVisible(false);

        box.add(title);
        box.add(scoreValue);
        box.add(scoreDeltaLabel);

        return box;
    }


    private JPanel createHowToPlay() {

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(Color.WHITE);
        panel.setBorder(new LineBorder(AppColors.BACKGROUND_LIGHT, 2, true));

        panel.add(new JLabel("How to Play"));
        panel.add(new JLabel("→ Use arrow keys to move"));
        panel.add(new JLabel("→ Reach the green flag"));
        panel.add(new JLabel("→ Watch DFS solve the maze"));
        panel.add(new JLabel("→ Faster time = higher score"));

        return panel;
    }


    private void initTimer() {

        gameTimer = new Timer(1000, e -> {

            seconds++;

            int minutes = seconds / 60;
            int remainingSeconds = seconds % 60;
            timeValue.setText(
                    String.format("%02d:%02d", minutes, remainingSeconds)
            );

            if (score > 0) {
                score -=TIME_PENALTY;
                setScore(score);
            } else {
                stopGame();
                if (gameOverListener != null) {
                    gameOverListener.onGameOver();
                }
            }
        });
    }
    private void showScoreDelta(int delta) {

        if (delta == 0) return;

        if (deltaTimer != null && deltaTimer.isRunning()) {
            deltaTimer.stop();
        }

        if (delta > 0) {
            scoreDeltaLabel.setText("+" + delta);
            scoreDeltaLabel.setForeground(new Color(0, 255, 120));

        } else {
            scoreDeltaLabel.setText(String.valueOf(delta));
            scoreDeltaLabel.setForeground(Color.WHITE);
        }

        scoreDeltaLabel.setVisible(true);



        deltaTimer = new Timer(2000, e -> scoreDeltaLabel.setVisible(false));
        deltaTimer.setRepeats(false);
        deltaTimer.start();
    }

    public void onPlayerMove() {
        score -= MOVE_PENALTY;
        setScore(score);
    }

    public void onSolveUsed() {
        score -= SOLVE_PENALTY;
        setScore(score);
        showScoreDelta(-SOLVE_PENALTY);
    }

    public void applyWinBonus(int seconds, boolean usedSolve, Level level) {

        int timeBonus = 0;
        int noSolveBonus = 0;

        switch (level.getLevelTitle()) {

            case "Easy" -> {
                if (seconds < 20) timeBonus = 100;
                if (!usedSolve) noSolveBonus = 50;
            }

            case "Medium" -> {
                if (seconds < 50) timeBonus = 200;
                if (!usedSolve) noSolveBonus = 100;
            }

            case "Hard" -> {
                if (seconds < 70) timeBonus = 300;
                if (!usedSolve) noSolveBonus = 150;
            }
        }

        int totalBonus = timeBonus + noSolveBonus;

        score += totalBonus;
        setScore(score);
        showScoreDelta(totalBonus);
    }

    public void onCombo(int combo) {

        if (combo == 10) {
            score += 50;
            maxCombo++;
            combo=0;
            setScore(score);
            showScoreDelta(50);
        }

    }


    public void resetCombo() {
    }


    public void startGame() {
        gameTimer.start();
    }

    public void stopGame() {
        gameTimer.stop();
    }

    public void resetGame() {
        seconds = 0;
        score = 1000;
        timeValue.setText("00:00");
        setScore(score);
        gameTimer.restart();
    }


    public void setScore(int score) {
        scoreValue.setText(String.valueOf(score));
    }
    public int getMaxCombo() {
return maxCombo;    }

    public int getScore() {
        return score;
    }
    public int getSeconds() {
        return seconds;
    }

    public void setLevelText(String level) {
        levelValue.setText(level);
    }

    public void setLevelColor(Color color) {
        levelBox.setBackground(color);
    }


}
