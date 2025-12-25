import model.Difficulty;
import model.Level;
import model.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.Random;

public class MazePanel extends JPanel implements KeyListener {


    Level level;
    int rows;
    int cols;
    private int moves = 0;
    private int combo = 0;

    private static final int CELL_SIZE = 30;
    private static final int WALL_THICKNESS = 6;
    private static final int OFFSET = 20;
    private final Player player;
    List<int[]> path;

    private int goalRow;
    private int goalCol;

    private MazeGenerator generator;
    private final RightSidePanel rightSidePanel;

    private GameState gameState = GameState.NOT_STARTED;

    private final Image goalImage;
    private Timer animationTimer;
    private int bestScore;


    public MazePanel(RightSidePanel rightSidePanel) {

        this.rightSidePanel = rightSidePanel;

        level = new Level(1, "Easy", AppColors.LEVEL_EASY(), Difficulty.EASY);
        rows = level.getRows();
        cols = level.getCols();


        generator = new MazeGenerator(rows, cols);
        generator.generateMaze();

        player = new Player(0, 0);

        rightSidePanel.setGameOverListener(() -> onLoss());


        goalImage = new ImageIcon("goal.png").getImage();


        animationTimer = new Timer(16, e -> {
            player.updatePosition(0.2);
            repaint();
        });
        animationTimer.start();
        setPreferredSize(new Dimension(cols * CELL_SIZE + 40, rows * CELL_SIZE + 40));
        setFocusable(true);
        addKeyListener(this);
        requestFocusInWindow();
        generateRandomGoal();
        openExitWall();
    }


    public void generateNewMaze() {
        Difficulty analyzed;
        do{
            generator = new MazeGenerator(rows, cols);
            generator.generateMaze();
            generateRandomGoal();
            openExitWall();
             analyzed =MazeDifficultyAnalyzer.analyzeDifficulty(
                    rows,
                    cols,
                    generator.wallsTop, generator.wallsBottom,
                    generator.wallsLeft,
                    generator.wallsRight,
                    0,0,
                    goalRow,goalCol

            );


        }while ( analyzed != level.getDifficulty());

        player.resetPosition(0, 0);
        path = null;
        moves = 0;
        combo = 0;
         rightSidePanel.setDifficulty(analyzed);
        repaint();
        requestFocusInWindow();
        repaint();
        requestFocusInWindow();
    }

    public void solve() {
        path = null;

        MazeSolver solver = new MazeSolver(
                rows, cols,
                generator.wallsTop,
                generator.wallsBottom,
                generator.wallsLeft,
                generator.wallsRight
        );

        path = solver.solve(0, 0, goalRow, goalCol);
        player.resetPosition(0, 0);

        repaint();
    }

    public void setLevel(Level level) {

        this.level = level;
        this.rows = level.getRows();
        this.cols = level.getCols();


        player.resetPosition(0, 0);

        rightSidePanel.setLevelText(level.getLevelTitle());
        rightSidePanel.setLevelColor(level.getColor());
        generateNewMaze();


        setPreferredSize(new Dimension(cols * CELL_SIZE + 40, rows * CELL_SIZE + 40));
        revalidate();
        repaint();
        requestFocusInWindow();
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        drawMaze(g2);
        drawPath(g2);
        drawPlayer(g2);
        drawGoal(g2);
    }

    private void drawMaze(Graphics2D g2) {

        g2.setStroke(new BasicStroke(
                WALL_THICKNESS,
                BasicStroke.CAP_ROUND,
                BasicStroke.JOIN_ROUND
        ));
        g2.setColor(AppColors.MAZE_WALL());

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {

                int x = c * CELL_SIZE + OFFSET;
                int y = r * CELL_SIZE + OFFSET;

                if (generator.wallsTop[r][c])
                    g2.drawLine(x, y, x + CELL_SIZE, y);

                if (generator.wallsRight[r][c])
                    g2.drawLine(x + CELL_SIZE, y, x + CELL_SIZE, y + CELL_SIZE);

                if (generator.wallsBottom[r][c])
                    g2.drawLine(x, y + CELL_SIZE, x + CELL_SIZE, y + CELL_SIZE);

                if (generator.wallsLeft[r][c])
                    g2.drawLine(x, y, x, y + CELL_SIZE);
            }
        }
    }

    private void drawPath(Graphics2D g2) {

        if (path == null) return;

        g2.setColor(AppColors.PATH());

        for (int i = 0; i < path.size() - 1; i++) {

            int[] c1 = path.get(i);
            int[] c2 = path.get(i + 1);

            int x1 = OFFSET + c1[1] * CELL_SIZE + CELL_SIZE / 2;
            int y1 = OFFSET + c1[0] * CELL_SIZE + CELL_SIZE / 2;

            int x2 = OFFSET + c2[1] * CELL_SIZE + CELL_SIZE / 2;
            int y2 = OFFSET + c2[0] * CELL_SIZE + CELL_SIZE / 2;

            g2.drawLine(x1, y1, x2, y2);
        }
    }

    private void drawPlayer(Graphics2D g2) {

        int margin = 6;
        int size = CELL_SIZE - 2 * margin;

        int x = (int) (player.getX() * CELL_SIZE) + OFFSET + margin;
        int y = (int) (player.getY() * CELL_SIZE) + OFFSET + margin;


        g2.setColor(AppColors.PLAYER());
        g2.fillOval(x, y, size, size);
    }

    private void drawGoal(Graphics2D g2) {

        int x = goalCol * CELL_SIZE + OFFSET;
        int y = goalRow * CELL_SIZE + OFFSET;

        g2.drawImage(goalImage, x, y, CELL_SIZE, CELL_SIZE, this);
    }


    @Override
    public void keyPressed(KeyEvent e) {

        if (gameState != GameState.PLAYING) return;

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) {

            if (player.moveUp(generator.wallsTop)) {
                moves++;
                combo++;
                rightSidePanel.onCombo(combo);
                rightSidePanel.onPlayerMove();
                SoundManager.play(Sound.MOVE);
                player.animateTo(player.getRow() - 1, player.getCol());
            } else {
                combo = 0;
                rightSidePanel.resetCombo();
                SoundManager.play(Sound.ERROR);
            }
        }

        if (key == KeyEvent.VK_DOWN) {
            if (player.moveDown(generator.wallsBottom, rows)) {
                combo++;
                rightSidePanel.onCombo(combo);
                moves++;
                rightSidePanel.onPlayerMove();
                SoundManager.play(Sound.MOVE);
                player.animateTo(player.getRow() + 1, player.getCol());
            } else {
                combo = 0;
                rightSidePanel.resetCombo();
                SoundManager.play(Sound.ERROR);
            }
        }

        if (key == KeyEvent.VK_LEFT) {
            if (player.moveLeft(generator.wallsLeft)) {
                moves++;
                combo++;
                rightSidePanel.onCombo(combo);
                rightSidePanel.onPlayerMove();
                SoundManager.play(Sound.MOVE);

                player.animateTo(player.getRow(), player.getCol() - 1);
            } else {
                combo = 0;
                rightSidePanel.resetCombo();
                SoundManager.play(Sound.ERROR);
            }
        }

        if (key == KeyEvent.VK_RIGHT) {
            if (player.moveRight(generator.wallsRight, cols)) {
                moves++;
                combo++;
                rightSidePanel.onCombo(combo);
                rightSidePanel.onPlayerMove();
                SoundManager.play(Sound.MOVE);
                player.animateTo(player.getRow(), player.getCol() + 1);
            } else {
                combo = 0;
                rightSidePanel.resetCombo();
                SoundManager.play(Sound.ERROR);
            }
        }

        checkWin();
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }


    private void generateRandomGoal() {
        Random rand = new Random();
        goalRow = rows - 1;
        goalCol = rand.nextInt(cols);
    }

    private void openExitWall() {
        generator.wallsBottom[goalRow][goalCol] = false;
    }

    private void checkWin() {

        if (player.getRow() == goalRow && player.getCol() == goalCol) {

            rightSidePanel.stopGame();
            gameState = GameState.FINISHED;
            SoundManager.play(Sound.WIN);
            navPanel.disableSolveBut();
            navPanel.enableLevelMenu();

            rightSidePanel.applyWinBonus(
                    rightSidePanel.getSeconds(),
                    path != null,
                    level
            );

            int finalScore = rightSidePanel.getScore();
            int oldBest = HighScoreManager.getBestScore(level);

            boolean isNewBest = finalScore > oldBest;


            if (isNewBest) {
                HighScoreManager.saveBestScore(level,finalScore);
                level.setBestScore(finalScore);
            }
            int displayedBest = isNewBest ? finalScore : oldBest;


            new GameSummaryDialog(
                    (JFrame) SwingUtilities.getWindowAncestor(this),
                    true,
                    level,
                    rightSidePanel.getSeconds(),
                    moves,
                    path != null,
                    finalScore,
                    rightSidePanel.getMaxCombo(),
                    displayedBest,
                    isNewBest
            ).setVisible(true);
        }
    }


    private void onLoss() {

        if (gameState != GameState.PLAYING) return;

        gameState = GameState.FINISHED;
        SoundManager.play(Sound.LOSS);

        int best = HighScoreManager.getBestScore(level);

        new GameSummaryDialog(
                (JFrame) SwingUtilities.getWindowAncestor(this),
                false,
                level,
                rightSidePanel.getSeconds(),
                moves,
                path != null,
                rightSidePanel.getScore(),
                rightSidePanel.getMaxCombo(),
                best,
                false
        ).setVisible(true);

        SoundManager.play(Sound.CLICK);
        rightSidePanel.stopGame();
        navPanel.enableLevelMenu();
    }
    public void applyTheme() {
        setBackground(AppColors.BACKGROUND());

        repaint();
    }

}

