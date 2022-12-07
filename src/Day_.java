import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;

public class Day_ {
    private static final String FILE_STRING = "inputs/Day_Input";

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
        return 0;
    }

    private static int partB(ArrayList<String> lines) {
        return 0;
    }
}
