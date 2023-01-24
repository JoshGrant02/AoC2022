import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Day9 {
    private static final String FILE_STRING = "inputs/Day9Input";
    private static final int DIRECTION_PARAM = 0;
    private static final int DISTANCE_PARAM = 1;
    private static int tailX = 0;
    private static int tailY = 0;
    private static int headX = 0;
    private static int headY = 0;
    private static final Set<String> positionsVisited = new HashSet();


    public static void main(String[] args) throws FileNotFoundException {
        File input = Path.of(FILE_STRING).toFile();
        Scanner inputScanner = new Scanner(input);
        ArrayList<String> lines = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            lines.add(inputScanner.nextLine());
        };
        System.out.println(partA(lines));
        System.out.println(partB(lines));
    }

    private static int partA(ArrayList<String> lines) {
        int numUniquePositionsVisited = 1;
        positionsVisited.add("" + tailX + " " + tailY);
        for (String line : lines) {
            String[] params = line.split(" ");
            int distance = Integer.parseInt(params[DISTANCE_PARAM]);
            if (params[DIRECTION_PARAM].equals("U")) {
                for (int i = 0; i < distance; i++) {
                    ++headY;
                    if (updateTail()) {
                        ++numUniquePositionsVisited;
                        System.out.println("New position");
                    }
                }
            }
            else if (params[DIRECTION_PARAM].equals("D")) {
                for (int i = 0; i < distance; i++) {
                    --headY;
                    if (updateTail()) {
                        ++numUniquePositionsVisited;
                        System.out.println("New position");
                    }
                }
            }
            else if (params[DIRECTION_PARAM].equals("L")) {
                for (int i = 0; i < distance; i++) {
                    --headX;
                    if (updateTail()) {
                        ++numUniquePositionsVisited;
                        System.out.println("New position");
                    }
                }
            }
            else if (params[DIRECTION_PARAM].equals("R")) {
                for (int i = 0; i < distance; i++) {
                    ++headX;
                    if (updateTail()) {
                        ++numUniquePositionsVisited;
                        System.out.println("New position");
                    }
                }
            }
        }
        return numUniquePositionsVisited;
    }

    private static int partB(ArrayList<String> lines) {
        return 0;
    }

    private static boolean updateTail() {
        updateTailPosition();
        System.out.println("Head position (" + headX + "," + headY + "), Tail position (" + tailX + "," + tailY + ")");
        if (!positionsVisited.contains("" + tailX + " " + tailY)) {
            positionsVisited.add("" + tailX + " " + tailY);
            return true;
        }
        return false;
    }

    private static void updateTailPosition() {
        if (headX > tailX+1) {
            ++tailX;
            if (headY != tailY) {
                tailY += headY-tailY;
            }
        }
        else if (headX < tailX-1) {
            --tailY;
            if (headY != tailY) {
                tailY += headY-tailY;
            }
        }
        if (headY > tailY+1) {
            ++tailY;
            if (headX != tailX) {
                tailX += headX-tailX;
            }
        }
        else if (headY < tailY-1) {
            --tailY;
            if (headX != tailX) {
                tailX += headX-tailX;
            }
        }
    }
}
