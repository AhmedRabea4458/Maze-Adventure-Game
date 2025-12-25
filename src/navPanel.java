import model.Difficulty;
import model.Level;

import javax.swing.*;
import java.awt.*;

public class navPanel extends JPanel {
    private static JMenu levelMenu = null;
    private static JButton solveBtn = null;
    public navPanel(MazePanel mazePanel, RightSidePanel rightSidePanel) {

        setLayout(new BorderLayout());
        setBackground(AppColors.HEADER_COLOR);
        setBorder(BorderFactory.createEmptyBorder(8, 20, 8, 20));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        leftPanel.setOpaque(false);

        ImageIcon logoIcon = resizeIcon(
                new ImageIcon(getClass().getResource("/icons/solution.png")), 24
        );
        JLabel title = new JLabel("Maze Adventure");
        title.setFont(new Font("Arial", Font.BOLD, 20));
        title.setForeground(Color.WHITE);

        leftPanel.add(new JLabel(logoIcon));
        leftPanel.add(title);

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 10));
        rightPanel.setOpaque(false);

        ImageIcon volumeOn = resizeIcon(
                new ImageIcon(getClass().getResource("/icons/volume_on.png")), 24
        );
        ImageIcon volumeOff = resizeIcon(
                new ImageIcon(getClass().getResource("/icons/volume_off.png")), 24
        );

        JButton startBtn = new JButton("Start Game");
        JButton newGameBtn = new JButton("New Game");
         solveBtn = new JButton("Solve");
        JButton exitBtn = new JButton("Exit");

        JButton muteBtn = new JButton(volumeOn);
        muteBtn.setBorderPainted(false);
        muteBtn.setFocusPainted(false);
        muteBtn.setContentAreaFilled(false);
        muteBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        styleButton(startBtn, AppColors.BTN_START);
        styleButton(newGameBtn, AppColors.BTN_NEW_GAME);
        styleButton(solveBtn, AppColors.BTN_SOLVE);
        styleButton(exitBtn, AppColors.BTN_EXIT);

        solveBtn.setEnabled(false);
        newGameBtn.setEnabled(false);

        JMenuBar levelMenuBar = new JMenuBar();
        levelMenuBar.setBorder(null);

        levelMenu = new JMenu("Choose Level");
        levelMenu.setFont(new Font("Arial", Font.BOLD, 14));
        levelMenu.setForeground(Color.WHITE);
        levelMenu.setBackground(AppColors.LEVEL_MEDIUM);
        levelMenu.setOpaque(true);

        JMenuItem easyItem = new JMenuItem("Easy");
        JMenuItem mediumItem = new JMenuItem("Medium");
        JMenuItem hardItem = new JMenuItem("Hard");

        styleMenuItem(easyItem, AppColors.LEVEL_EASY);
        styleMenuItem(mediumItem, AppColors.LEVEL_MEDIUM);
        styleMenuItem(hardItem, AppColors.LEVEL_HARD);

        levelMenu.add(easyItem);
        levelMenu.add(mediumItem);
        levelMenu.add(hardItem);
        levelMenuBar.add(levelMenu);

        rightPanel.add(muteBtn);
        rightPanel.add(startBtn);
        rightPanel.add(newGameBtn);
        rightPanel.add(levelMenuBar);
        rightPanel.add(solveBtn);
        rightPanel.add(exitBtn);

        add(leftPanel, BorderLayout.WEST);
        add(rightPanel, BorderLayout.EAST);


        muteBtn.addActionListener(e -> {
            SoundManager.toggleMute();
            muteBtn.setIcon(SoundManager.isMuted() ? volumeOff : volumeOn);

            if (!SoundManager.isMuted()) {
                SoundManager.play(Sound.CLICK);
            }
            mazePanel.requestFocusInWindow();
        });

        startBtn.addActionListener(e -> {
            SoundManager.play(Sound.START);
            levelMenu.setEnabled(false);

            mazePanel.setGameState(GameState.PLAYING);
            rightSidePanel.startGame();

            solveBtn.setEnabled(true);
            newGameBtn.setEnabled(true);
            startBtn.setEnabled(false);

            mazePanel.requestFocusInWindow();
        });

        newGameBtn.addActionListener(e -> {
            SoundManager.play(Sound.CLICK);
            levelMenu.setEnabled(false);
            solveBtn.setEnabled(true);

            boolean confirm = CustomMessage.showDialog(
                    this,
                    "Confirm",
                    "Start a new game?",
                    null,
                    "Yes",
                    "No",
                    Color.WHITE
            );

            if (confirm) {
                SoundManager.play(Sound.START);
                mazePanel.setGameState(GameState.PLAYING);
                mazePanel.generateNewMaze();
                rightSidePanel.resetGame();
                rightSidePanel.startGame();
                mazePanel.requestFocusInWindow();
            }
        });

        solveBtn.addActionListener(e -> {
            SoundManager.play(Sound.CLICK);

            CustomMessage.showDialog(
                    this,
                    "Info",
                    "DFS is used to solve the maze",
                    null,
                    "OK",
                    null,
                    Color.WHITE
            );

            mazePanel.solve();
            rightSidePanel.onSolveUsed();

            mazePanel.requestFocusInWindow();

        });

        exitBtn.addActionListener(e -> {
            SoundManager.play(Sound.CLICK);

            boolean confirm = CustomMessage.showDialog(
                    this,
                    "Confirm",
                    "Are you sure?",
                    null,
                    "Yes",
                    "No",
                    Color.WHITE
            );

            if (confirm) {
                System.exit(0);
            }
        });

        easyItem.addActionListener(e -> {
            SoundManager.play(Sound.SELECT);
            mazePanel.setLevel(new Level(1, "Easy", AppColors.LEVEL_EASY, Difficulty.EASY));
            mazePanel.requestFocusInWindow();
            rightSidePanel.stopGame();

            startBtn.setEnabled(true);
        });

        mediumItem.addActionListener(e -> {
            SoundManager.play(Sound.SELECT);
            mazePanel.setLevel(new Level(2, "Medium", AppColors.LEVEL_MEDIUM,Difficulty.MEDIUM));
            mazePanel.requestFocusInWindow();
            rightSidePanel.stopGame();

            startBtn.setEnabled(true);

        });

        hardItem.addActionListener(e -> {
            SoundManager.play(Sound.SELECT);
            mazePanel.setLevel(new Level(3, "Hard", AppColors.LEVEL_HARD,Difficulty.HARD));
            mazePanel.requestFocusInWindow();
            rightSidePanel.stopGame();
            startBtn.setEnabled(true);

        });
    }


    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private void styleMenuItem(JMenuItem item, Color bg) {
        item.setFont(new Font("Arial", Font.BOLD, 13));
        item.setOpaque(true);
        item.setBackground(bg);
        item.setForeground(Color.WHITE);
        item.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        item.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    private ImageIcon resizeIcon(ImageIcon icon, int size) {
        Image img = icon.getImage().getScaledInstance(size, size, Image.SCALE_SMOOTH);
        return new ImageIcon(img);
    }
    public static void enableLevelMenu() {
        levelMenu.setEnabled(true);
    }
    public static void disableSolveBut() {solveBtn.setEnabled(false);}
        public static void enableSolveBut() {
            solveBtn.setEnabled(true);
        }



}
