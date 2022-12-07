import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day2 {
    private static final String FILE_STRING = "inputs/Day2Input";
    private static final int ROCK_POINTS = 1;
    private static final int PAPER_POINTS = 2;
    private static final int SCISSORS_POINTS = 3;
    private static final int WIN_POINTS = 6;
    private static final int DRAW_POINTS = 3;
    private enum RPS {
        ROCK, PAPER, SCISSORS
    }

    public static void main(String[] args) throws FileNotFoundException {
        File input = Path.of(FILE_STRING).toFile();
        Scanner inputScanner = new Scanner(input);
        System.out.println(partA(inputScanner));
        inputScanner.close();
        inputScanner = new Scanner(input);
        System.out.println(partB(inputScanner));
        inputScanner.close();
    }

    private static int partA(Scanner inputScanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            lines.add(inputScanner.nextLine());
        }
        AtomicInteger numPoints = new AtomicInteger();
        lines.forEach((String str) -> {
            RPS them = charToRPS(str.charAt(0));
            RPS me = charToRPS(str.charAt(2));
            numPoints.addAndGet(determinePoints(me, them));
        });
        return numPoints.get();
    }

    private static int partB(Scanner inputScanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            lines.add(inputScanner.nextLine());
        }
        AtomicInteger numPoints = new AtomicInteger();
        lines.forEach((String str) -> {
            RPS them = charToRPS(str.charAt(0));
            RPS me = determineMyChoice(them, str.charAt(2));
            numPoints.addAndGet(determinePoints(me, them));
        });
        return numPoints.get();
    }

    private static int determinePoints(RPS player1, RPS player2) {
        int numPoints = 0;

        if (player1 == RPS.ROCK) {
            numPoints += ROCK_POINTS;
            if (player2 == RPS.SCISSORS) {
                numPoints += WIN_POINTS;
            }
        }

        if (player1 == RPS.PAPER) {
            numPoints += PAPER_POINTS;
            if (player2 == RPS.ROCK) {
                numPoints += WIN_POINTS;
            }
        }

        if (player1 == RPS.SCISSORS) {
            numPoints += SCISSORS_POINTS;
            if (player2 == RPS.PAPER) {
                numPoints += WIN_POINTS;
            }
        }

        if (player1 == player2) {
            numPoints += DRAW_POINTS;
        }

        return numPoints;
    }

    private static RPS charToRPS(char c) {
        return switch (c) {
            case 'B', 'Y' -> RPS.PAPER;
            case 'C', 'Z' -> RPS.SCISSORS;
            default -> RPS.ROCK;
        };
    }

    private static RPS determineMyChoice(RPS them, char c) {
        if (c == 'Y') {
            return them;
        }
        if (c == 'X') {
            return switch (them) {
                case ROCK -> RPS.SCISSORS;
                case PAPER -> RPS.ROCK;
                default -> RPS.PAPER;
            };
        }
        return switch (them) {
            case ROCK -> RPS.PAPER;
            case PAPER -> RPS.SCISSORS;
            default -> RPS.ROCK;
        };
    }
}
