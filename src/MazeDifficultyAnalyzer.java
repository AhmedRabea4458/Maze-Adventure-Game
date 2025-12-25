import model.Difficulty;
import java.util.List;

public class MazeDifficultyAnalyzer {

    public static Difficulty analyzeDifficulty(
            int rows,
            int cols,
            boolean[][] top,
            boolean[][] bottom,
            boolean[][] left,
            boolean[][] right,
            int startRow,
            int startCol,
            int goalRow,
            int goalCol
    ) {

        MazeSolver solver = new MazeSolver(
                rows, cols,
                top, bottom, left, right
        );

        List<int[]> path = solver.solve(
                startRow, startCol,
                goalRow, goalCol
        );

        int pathLength = path.size();
        int deadEnds=0;
        int branches=0;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if(r==startRow||c==goalCol )
                    continue;
                int openings = 0;

                if (!top[r][c]) openings++;
                if (!bottom[r][c]) openings++;
                if (!left[r][c]) openings++;
                if (!right[r][c]) openings++;
                 if(openings==1){
                     deadEnds++;
                 }else if(openings>=3){
                     branches++;
                 }

            }
        }


         int difficultyScore =
                 (int) (pathLength * 1.0
                                         + deadEnds  * 2.0
                                         + branches  * 1.5);

        if (difficultyScore < 80) {
            return Difficulty.EASY;
        } else if (difficultyScore < 150) {
            return Difficulty.MEDIUM;
        } else {
            return Difficulty.HARD;
        }
    }
}
