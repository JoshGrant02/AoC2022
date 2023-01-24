import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day8 {
    private static final String FILE_STRING = "inputs/Day8Input";
    private static final int NUM_EDGE_POSITIONS = 392;

    public static void main(String[] args) throws FileNotFoundException {
        File input = Path.of(FILE_STRING).toFile();
        Scanner inputScanner = new Scanner(input);
        int[][] grid = new int[99][];
        int currentRow = 0;
        while (inputScanner.hasNextLine()) {
            String[] nextLine = inputScanner.nextLine().split("");
            grid[currentRow] = Arrays.stream(nextLine).mapToInt(Integer::parseInt).toArray();
            ++currentRow;
        };
        int[][] southVisibilityGrid = getSouthVisibilityGrid(grid);
        int[][] eastVisibilityGrid = getEastVisibilityGrid(grid);
        System.out.println(partA(grid, southVisibilityGrid, eastVisibilityGrid));
        System.out.println(partB(grid));
    }

    private static int[][] getSouthVisibilityGrid(int[][] grid) {
        int gridWidth = grid.length;
        int gridHeight = grid[0].length;
        int[][] visibilityGrid = new int[gridWidth][gridHeight];
        visibilityGrid[gridHeight-1] = grid[gridHeight-1];
        for (int row = gridHeight-2; row >= 0; row--) {
            for (int col = 0; col < gridWidth; col++) {
                visibilityGrid[row][col] = Math.max(visibilityGrid[row+1][col], grid[row][col]);
            }
        }
        return visibilityGrid;
    }

    private static int[][] getEastVisibilityGrid(int[][] grid) {
        int gridWidth = grid.length;
        int gridHeight = grid[0].length;
        int[][] visibilityGrid = new int[gridWidth][gridHeight];
        for (int row = 0; row < gridHeight; row++) {
            visibilityGrid[row][gridWidth-1] = grid[row][gridWidth-1];
        }
        for (int col = gridHeight-2; col >= 0; col--) {
            for (int row = 0; row < gridHeight; row++) {
                visibilityGrid[row][col] = Math.max(visibilityGrid[row][col+1], grid[row][col]);
            }
        }
        return visibilityGrid;
    }

    private static int partA(int[][] grid, int[][] southVisibilityGrid, int[][] eastVisibilityGrid) {
        int gridWidth = grid.length;
        int gridHeight = grid[0].length;
        int numTreesCanBeSeen = NUM_EDGE_POSITIONS;
        int[] northHeight = new int[gridWidth];
        int westHeight = 0;
        System.arraycopy(grid[0], 0, northHeight, 0, gridWidth);
        for (int row = 1; row < gridHeight-1; row++) {
            westHeight = grid[row][0];
            for (int col = 1; col < gridWidth-1; col++) {
                int currentCell = grid[row][col];
                boolean isTall =
                        currentCell > westHeight ||
                        currentCell > northHeight[col] ||
                        currentCell > eastVisibilityGrid[row][col+1] ||
                        currentCell > southVisibilityGrid[row+1][col];
                northHeight[col] = Math.max(northHeight[col], currentCell);
                westHeight = Math.max(westHeight, currentCell);
                numTreesCanBeSeen += isTall ? 1 : 0;
            }
        }
        return numTreesCanBeSeen;
    }

    private static int partB(int[][] grid) {
        int gridWidth = grid.length;
        int gridHeight = grid[0].length;
        int maxScenicScore = 0;
        for (int row = 1; row < gridWidth-1; row++) {
            for (int col = 1; col < gridHeight-1; col++) {
                int treeHeight = grid[row][col];
                int currentRow = row;
                int currentCol = col;
                boolean canStillSee = true;
                int northSight = 0;
                while (canStillSee) {
                    if (--currentRow < 0) {
                        canStillSee = false;
                    } else {
                        ++northSight;
                        if (grid[currentRow][currentCol] >= treeHeight) {
                            canStillSee = false;
                        }
                    }
                }

                currentRow = row;
                canStillSee = true;
                int eastSight = 0;
                while (canStillSee) {
                    if (++currentCol >= gridWidth) {
                        canStillSee = false;
                    } else {
                        ++eastSight;
                        if (grid[currentRow][currentCol] >= treeHeight) {
                            canStillSee = false;
                        }
                    }
                }

                currentCol = col;
                canStillSee = true;
                int southSight = 0;
                while (canStillSee) {
                    if (++currentRow >= gridHeight) {
                        canStillSee = false;
                    } else {
                        ++southSight;
                        if (grid[currentRow][currentCol] >= treeHeight) {
                            canStillSee = false;
                        }
                    }
                }

                currentRow = row;
                canStillSee = true;
                int westSight = 0;
                while (canStillSee) {
                    if (--currentCol < 0) {
                        canStillSee = false;
                    } else {
                        ++westSight;
                        if (grid[currentRow][currentCol] >= treeHeight) {
                            canStillSee = false;
                        }
                    }
                }

                int scenicScore = northSight*eastSight*southSight*westSight;
                maxScenicScore = Math.max(maxScenicScore, scenicScore);
            }
        }
        return maxScenicScore;
    }
}
