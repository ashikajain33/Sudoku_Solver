import java.util.Scanner;

public class SudokuSolver {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Sudoku Solver!");
        System.out.println("Enter the Sudoku puzzle below (use 0 for empty cells):");
        int[][] puzzle = readSudoku(scanner);
        System.out.println("\nSudoku puzzle entered:");
        printGrid(puzzle);

        if (solveSudoku(puzzle)) {
            System.out.println("\nSudoku puzzle solved:");
            printGrid(puzzle);
        } else {
            System.out.println("\nNo solution exists for the given Sudoku puzzle.");
        }
    }

    public static int[][] readSudoku(Scanner scanner) {
        int[][] puzzle = new int[9][9];
        for (int i = 0; i < 9; i++) {
            System.out.print("Row " + (i + 1) + ": ");
            String line = scanner.nextLine();
            for (int j = 0; j < 9; j++) {
                puzzle[i][j] = Character.getNumericValue(line.charAt(j));
            }
        }
        return puzzle;
    }

    public static boolean solveSudoku(int[][] grid) {
        int[] emptyLocation = findEmptyLocation(grid);
        int row = emptyLocation[0];
        int col = emptyLocation[1];

        // If there are no empty cells, the puzzle is solved
        if (row == -1 && col == -1) {
            return true;
        }

        for (int num = 1; num <= 9; num++) {
            if (isValidMove(grid, row, col, num)) {
                grid[row][col] = num;

                // Recursively try to solve the remaining grid
                if (solveSudoku(grid)) {
                    return true;
                }

                // If no valid solution is found, backtrack
                grid[row][col] = 0;
            }
        }

        // If no number leads to a solution, return false
        return false;
    }

    public static int[] findEmptyLocation(int[][] grid) {
        for (int row = 0; row < 9; row++) {
            for (int col = 0; col < 9; col++) {
                if (grid[row][col] == 0) {
                    return new int[]{row, col};
                }
            }
        }
        return new int[]{-1, -1};
    }

    public static boolean isValidMove(int[][] grid, int row, int col, int num) {
        // Check if the number is already used in the row
        for (int i = 0; i < 9; i++) {
            if (grid[row][i] == num) {
                return false;
            }
        }

        // Check if the number is already used in the column
        for (int i = 0; i < 9; i++) {
            if (grid[i][col] == num) {
                return false;
            }
        }

        // Check if the number is already used in the 3x3 grid
        int startRow = 3 * (row / 3);
        int startCol = 3 * (col / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (grid[startRow + i][startCol + j] == num) {
                    return false;
                }
            }
        }

        return true;
    }

    public static void printGrid(int[][] grid) {
        for (int[] row : grid) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }
}
