package model;
public class Player {

    private int row, col;
    private double x, y;

    public Player(int row, int col) {
        this.row = row;
        this.col = col;
        this.x = col;
        this.y = row;
    }

    public void resetPosition(int r, int c) {
        row = r;
        col = c;
        x = c;
        y = r;
    }

    public void animateTo(int newRow, int newCol) {
        row = newRow;
        col = newCol;
    }

    public void updatePosition(double speed) {
        x += (col - x) * speed;
        y += (row - y) * speed;
    }

    public double getX() { return x; } // col
    public double getY() { return y; } // row
    public int getRow() { return row; } // col
    public int getCol() { return col; } // row



    public boolean moveUp(boolean[][] wallsTop) {
        if (row > 0 && !wallsTop[row][col]) {
         return true;
        }
        return false;
    }

    public boolean moveDown(boolean[][] wallsBottom, int maxRows) {
        if (row < maxRows - 1 && !wallsBottom[row][col]) {
            return true;
        }
        return false;
    }

    public boolean moveLeft(boolean[][] wallsLeft) {
        if (col > 0 && !wallsLeft[row][col]) {
            return true;
        }
        return false;
    }

    public boolean moveRight(boolean[][] wallsRight, int maxCols) {
        if (col < maxCols - 1 && !wallsRight[row][col]) {
            return true;
        }
        return false;
    }


}
