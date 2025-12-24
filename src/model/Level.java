package model;

import java.awt.Color;

public class Level {

    private final int levelNumber;
    private final int rows;
    private final int cols;
    private final String levelTitle;
    private final Color color;

    public Level(int levelNumber, String levelTitle, Color color) {
        this.levelNumber = levelNumber;
        this.levelTitle = levelTitle;
        this.color = color;

        this.rows = 10 + levelNumber * 2;
        this.cols = 10 + levelNumber * 2;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getLevelTitle() {
        return levelTitle;
    }

    public Color getColor() {
        return color;
    }
}
