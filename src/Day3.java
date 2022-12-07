import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Day3 {
    private static final String FILE_STRING = "inputs/Day3Input";
    private static final int END_UPPERCASE_ASCII = 90;
    private static final int UPPERCASE_ASCII_OFFSET = 38;
    private static final int LOWERCASE_ASCII_OFFSET = 96;

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
        AtomicInteger sumPriorities = new AtomicInteger();
        lines.forEach((String str) -> {
            int strLength = str.length();
            int halfStrLength = strLength/2;
            String firstHalf = str.substring(0, halfStrLength);
            String secondHalf = str.substring(halfStrLength);
            for (int i = 0; i < halfStrLength; i++) {
                if (secondHalf.contains(firstHalf.substring(i, i+1))) {
                    sumPriorities.addAndGet(determinePriority(firstHalf.charAt(i)));
                    break;
                }
            }
        });
        return sumPriorities.get();
    }

    private static int partB(Scanner inputScanner) {
        ArrayList<String> lines = new ArrayList<>();
        while (inputScanner.hasNextLine()) {
            lines.add(inputScanner.nextLine());
        }
        int numLines = lines.size();
        int sumPriorities = 0;
        for (int i = 0; i < numLines; i += 3) {
            LinkedHashSet<String> firstRow = new LinkedHashSet<>(Arrays.stream(lines.get(i).split("")).toList());
            LinkedHashSet<String> secondRow = new LinkedHashSet<>(Arrays.stream(lines.get(i+1).split("")).toList());
            LinkedHashSet<String> thirdRow = new LinkedHashSet<>(Arrays.stream(lines.get(i+2).split("")).toList());

            for (String element : firstRow) {
                if (secondRow.contains(element) & thirdRow.contains(element)) {
                    sumPriorities += determinePriority(element.charAt(0));
                    break;
                }
            }
        }
        return sumPriorities;
    }

    private static int determinePriority(char c) {
        if (c <= END_UPPERCASE_ASCII) {
            return c - UPPERCASE_ASCII_OFFSET;
        }
        return c - LOWERCASE_ASCII_OFFSET;
    }
}
