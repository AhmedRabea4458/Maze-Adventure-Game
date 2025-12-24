import java.util.Random;
import java.util.Stack;

public class MazeGenerator {


    private final int rows;
    private final int cols;

    public final boolean[][] wallsTop;
    public final boolean[][] wallsRight;
    public final boolean[][] wallsBottom;
    public final boolean[][] wallsLeft;

    private final boolean[][] visited;


    public MazeGenerator(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;

        wallsTop = new boolean[rows][cols];
        wallsRight = new boolean[rows][cols];
        wallsBottom = new boolean[rows][cols];
        wallsLeft = new boolean[rows][cols];

        visited = new boolean[rows][cols];
    }


    public void generateMaze() {

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                visited[r][c] = false;
                wallsTop[r][c] = true;
                wallsRight[r][c] = true;
                wallsBottom[r][c] = true;
                wallsLeft[r][c] = true;
            }
        }

        dfs();

        wallsTop[0][0] = false;
    }


    private void dfs() {

        Stack<int[]> stack = new Stack<>();
        Random random = new Random();

        stack.push(new int[]{0, 0});
        visited[0][0] = true;

        while (!stack.isEmpty()) {

            int[] cell = stack.pop();
            int row = cell[0];
            int col = cell[1];

            int[][] directions = {
                    {row - 1, col, 0}, // top
                    {row + 1, col, 1}, // bottom
                    {row, col - 1, 2}, // left
                    {row, col + 1, 3}  // right
            };

            for (int i = 0; i < directions.length; i++) {
                int randomIndex = random.nextInt(directions.length);
                int[] temp = directions[i];
                directions[i] = directions[randomIndex];
                directions[randomIndex] = temp;
            }

            for (int[] d : directions) {

                int nr = d[0];
                int nc = d[1];
                int dir = d[2];

                if (nr < 0 || nr >= rows || nc < 0 || nc >= cols)
                    continue;

                if (visited[nr][nc])
                    continue;

                switch (dir) {
                    case 0 -> {
                        wallsTop[row][col] = false;
                        wallsBottom[nr][nc] = false;
                    }
                    case 1 -> {
                        wallsBottom[row][col] = false;
                        wallsTop[nr][nc] = false;
                    }
                    case 2 -> {
                        wallsLeft[row][col] = false;
                        wallsRight[nr][nc] = false;
                    }
                    case 3 -> {
                        wallsRight[row][col] = false;
                        wallsLeft[nr][nc] = false;
                    }
                }

                visited[nr][nc] = true;

                stack.push(new int[]{row, col});
                stack.push(new int[]{nr, nc});
                break;
            }
        }
    }
}
