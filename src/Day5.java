import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class Day5 {
    private static final String FILE_STRING = "inputs/Day5Input";

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

    private static String partA(ArrayList<String> lines) {
        Stack<String> crateRows = new Stack<>();
        int firstInstructionLine = 0;
        for (int i = 0; true; i++) {
            if (lines.get(i).contains("1")) {
                firstInstructionLine = i + 2;
                break;
            }
            crateRows.push(lines.get(i));
        }

        Stack<Character>[] stacks = new Stack[9];
        for (int i  = 0; i < stacks.length; i++) {
            stacks[i] = new Stack<>();
        }

        while (!crateRows.isEmpty()) {
            String crates = crateRows.pop();
            for (int i = 0; i < stacks.length; i++) {
                if (crates.length() >= 4*i+1) {
                    Character c = crates.charAt(4 * i + 1);
                    if (c != ' ') {
                        stacks[i].push(c);
                    }
                }
            }
        }

        int numLines = lines.size();
        for (int i = firstInstructionLine; i < numLines; i++) {
            String[] words = lines.get(i).split(" ");
            int numCrates = Integer.parseInt(words[1]);
            int from = Integer.parseInt(words[3]) - 1;
            int to = Integer.parseInt(words[5]) - 1;
            moveCrates(stacks, from, to, numCrates);
        }

        StringBuilder output = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            output.append(stack.peek());
        }

        return output.toString();
    }

    private static String partB(ArrayList<String> lines) {
        Stack<String> crateRows = new Stack<>();
        int firstInstructionLine = 0;
        for (int i = 0; true; i++) {
            if (lines.get(i).contains("1")) {
                firstInstructionLine = i + 2;
                break;
            }
            crateRows.push(lines.get(i));
        }

        Stack<Character>[] stacks = new Stack[9];
        for (int i  = 0; i < stacks.length; i++) {
            stacks[i] = new Stack<>();
        }

        while (!crateRows.isEmpty()) {
            String crates = crateRows.pop();
            for (int i = 0; i < stacks.length; i++) {
                if (crates.length() >= 4*i+1) {
                    Character c = crates.charAt(4 * i + 1);
                    if (c != ' ') {
                        stacks[i].push(c);
                    }
                }
            }
        }

        int numLines = lines.size();
        for (int i = firstInstructionLine; i < numLines; i++) {
            String[] words = lines.get(i).split(" ");
            int numCrates = Integer.parseInt(words[1]);
            int from = Integer.parseInt(words[3]) - 1;
            int to = Integer.parseInt(words[5]) - 1;
            moveCratesAltogether(stacks, from, to, numCrates);
        }

        StringBuilder output = new StringBuilder();
        for (Stack<Character> stack : stacks) {
            output.append(stack.peek());
        }

        return output.toString();
    }

    private static void moveCrates(Stack<Character>[] stacks, int from, int to, int numTimes) {
        for (int i = 0; i < numTimes; i++) {
            stacks[to].push(stacks[from].pop());
        }
    }

    private static void moveCratesAltogether(Stack<Character>[] stacks, int from, int to, int numTimes) {
        Stack<Character> tempStack = new Stack<>();
        for (int i = 0; i < numTimes; i++) {
            tempStack.push(stacks[from].pop());
        }
        while (!tempStack.isEmpty()) {
            stacks[to].push(tempStack.pop());
        }
    }
}
