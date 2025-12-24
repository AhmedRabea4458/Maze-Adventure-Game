import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class RightSidePanel extends JPanel {


    private Timer gameTimer;
    private int seconds = 0;
    private int score = 1000;

    private final JLabel timeValue;
    private final JLabel scoreValue;
    private final JLabel levelValue;
    private final JPanel levelBox;

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
        add(createInfoBox("SCORE", scoreValue, AppColors.SCORE_RED));
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
                score -= 10;
                setScore(score);
            } else {
                stopGame();
                if (gameOverListener != null) {
                    gameOverListener.onGameOver();
                }
            }
        });
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

    public int getScore() {
        return score;
    }

    public void setLevelText(String level) {
        levelValue.setText(level);
    }

    public void setLevelColor(Color color) {
        levelBox.setBackground(color);
    }
}
