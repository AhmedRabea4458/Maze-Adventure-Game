import java.util.ArrayList;
import java.util.List;

public class MazeSolver {


    private final int rows;
    private final int cols;

    private final boolean[][] visited;

    private final boolean[][] wallsTop;
    private final boolean[][] wallsBottom;
    private final boolean[][] wallsLeft;
    private final boolean[][] wallsRight;


    public MazeSolver(
            int rows,
            int cols,
            boolean[][] wallsTop,
            boolean[][] wallsBottom,
            boolean[][] wallsLeft,
            boolean[][] wallsRight
    ) {
        this.rows = rows;
        this.cols = cols;
        this.wallsTop = wallsTop;
        this.wallsBottom = wallsBottom;
        this.wallsLeft = wallsLeft;
        this.wallsRight = wallsRight;

        visited = new boolean[rows][cols];
    }


    public List<int[]> solve(
            int startRow,
            int startCol,
            int goalRow,
            int goalCol
    ) {
        List<int[]> path = new ArrayList<>();

        if (dfs(startRow, startCol, goalRow, goalCol, path)) {
            return path;
        }
        return null;
    }


    private boolean dfs(
            int row,
            int col,
            int goalRow,
            int goalCol,
            List<int[]> path
    ) {

        if (row < 0 || row >= rows || col < 0 || col >= cols)
            return false;

        if (visited[row][col])
            return false;

        visited[row][col] = true;
        path.add(new int[]{row, col});

        if (row == goalRow && col == goalCol)
            return true;

        if (!wallsTop[row][col] &&
                dfs(row - 1, col, goalRow, goalCol, path))
            return true;

        if (!wallsBottom[row][col] &&
                dfs(row + 1, col, goalRow, goalCol, path))
            return true;

        if (!wallsLeft[row][col] &&
                dfs(row, col - 1, goalRow, goalCol, path))
            return true;

        if (!wallsRight[row][col] &&
                dfs(row, col + 1, goalRow, goalCol, path))
            return true;

        path.remove(path.size() - 1);
        return false;
    }
}
