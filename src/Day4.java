import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day4 {
    private static final String FILE_STRING = "inputs/Day4Input";

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
        AtomicInteger numOverlapping = new AtomicInteger();
        lines.forEach((String str) -> {
            String[] boundaries = str.split(",");
            String firstBoundary = boundaries[0];
            String secondBoundary = boundaries[1];
            int firstBeginning = Integer.parseInt(firstBoundary.substring(0, firstBoundary.indexOf("-")));
            int firstEnd = Integer.parseInt(firstBoundary.substring(firstBoundary.indexOf("-") + 1));
            int secondBeginning = Integer.parseInt(secondBoundary.substring(0, secondBoundary.indexOf("-")));
            int secondEnd = Integer.parseInt(secondBoundary.substring(secondBoundary.indexOf("-") + 1));
            if ((firstBeginning >= secondBeginning & firstEnd <= secondEnd) ||
                (firstBeginning <= secondBeginning & firstEnd >= secondEnd)
            ) {
                numOverlapping.incrementAndGet();
            }
        });
        return numOverlapping.get();
    }

    private static int partB(ArrayList<String> lines) {
        AtomicInteger numOverlapping = new AtomicInteger();
        lines.forEach((String str) -> {
            String[] boundaries = str.split(",");
            String firstBoundary = boundaries[0];
            String secondBoundary = boundaries[1];
            int firstBeginning = Integer.parseInt(firstBoundary.substring(0, firstBoundary.indexOf("-")));
            int firstEnd = Integer.parseInt(firstBoundary.substring(firstBoundary.indexOf("-") + 1));
            int secondBeginning = Integer.parseInt(secondBoundary.substring(0, secondBoundary.indexOf("-")));
            int secondEnd = Integer.parseInt(secondBoundary.substring(secondBoundary.indexOf("-") + 1));
            if (Math.min(firstEnd, secondEnd) - Math.max(firstBeginning, secondBeginning) >= 0) {
                numOverlapping.incrementAndGet();
            }
        });
        return numOverlapping.get();
    }
}
