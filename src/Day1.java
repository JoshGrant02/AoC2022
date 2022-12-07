import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.Scanner;

public class Day1 {
    private static final String FILE_STRING = "inputs/Day1Input";

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
        int mostCalories = 0;
        int currentCalories = 0;
        while (inputScanner.hasNextLine()) {
            String nextLine = inputScanner.nextLine();
            if (nextLine.equals("")) {
                mostCalories = Math.max(mostCalories, currentCalories);
                currentCalories = 0;
            } else {
                currentCalories += Integer.parseInt(nextLine);
            }
        }
        return Math.max(mostCalories, currentCalories);
    }

    private static int partB(Scanner inputScanner) {
        int mostCalories = 0;
        int secondMostCalories = 0;
        int thirdMostCalories = 0;
        int currentCalories = 0;
        while (inputScanner.hasNextLine()) {
            String nextLine = inputScanner.nextLine();
            if (nextLine.equals("")) {
                if (currentCalories > mostCalories) {
                    thirdMostCalories = secondMostCalories;
                    secondMostCalories = mostCalories;
                    mostCalories = currentCalories;
                } else if (currentCalories > secondMostCalories) {
                    thirdMostCalories = secondMostCalories;
                    secondMostCalories = currentCalories;
                } else if (currentCalories > thirdMostCalories) {
                    thirdMostCalories = currentCalories;
                }
                currentCalories = 0;
            } else {
                currentCalories += Integer.parseInt(nextLine);
            }
        }

        return mostCalories + secondMostCalories + Math.max(currentCalories, thirdMostCalories);
    }
}
