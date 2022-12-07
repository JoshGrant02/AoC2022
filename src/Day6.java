import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.function.DoubleToIntFunction;

public class Day6 {
    private static final String FILE_STRING = "inputs/Day6Input";
    private static final int RECCURENCE_LENGTH = 14;

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
        String line = lines.get(0);
        char threeBack = line.charAt(0);
        char twoBack = line.charAt(1);
        char oneBack = line.charAt(2);
        char current = line.charAt(3);
        int length = line.length();
        if (!hasMatches(threeBack, twoBack, oneBack, current)) {
            return 4;
        }
        for (int i = 4; i < length; i++) {
            threeBack = twoBack;
            twoBack = oneBack;
            oneBack = current;
            current = line.charAt(i);
            if (!hasMatches(threeBack, twoBack, oneBack, current)) {
                return i + 1;
            }
        }
        return -1;
    }

    private static int partB(ArrayList<String> lines) {
        String line = lines.get(0);
        StringBuilder currentSection = new StringBuilder();
        PriorityQueue<Integer> matches = new PriorityQueue<>();
        //Fill up the initial 14 characters
        for (int i = 0; i < RECCURENCE_LENGTH; i++) {
            char currentChar = line.charAt(i);
            int lastIndex = currentSection.lastIndexOf("" + currentChar);
            if (lastIndex != -1) {
                matches.add(lastIndex);
            }
            currentSection.append(currentChar);
        }
        //Loop through the next characters
        int length = line.length();
        for (int i = RECCURENCE_LENGTH; i < length; i++) {
            //Removed any matches that have become obsolete
            while (matches.size() != 0 && i - RECCURENCE_LENGTH >= matches.element()) {
                matches.poll();
            }
            currentSection.deleteCharAt(0);
            char currentChar = line.charAt(i);
            //Add a new match if a new one has appeared
            int lastIndex = currentSection.lastIndexOf("" + currentChar);
            if (lastIndex != -1) {
                matches.add((i - RECCURENCE_LENGTH) + lastIndex + 1);
            }
            currentSection.append(currentChar);
            //If there are no matches, we are done
            if (matches.size() == 0) {
                return i + 1;
            }
        }
        return -1;
    }

    private static boolean hasMatches(char one, char two, char three, char four) {
        return (one == two) || (one == three) || (one == four) || (two == three) || (two == four) || (three == four);
    }
}
