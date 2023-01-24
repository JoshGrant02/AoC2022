import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Day7 {
    private static final String FILE_STRING = "inputs/Day7Input";
    private static final int NAME = 1;
    private static final int SIZE = 0;
    private static final int DIR = 0;
    private static final int INSTRUCTION = 0;
    private static final int MAX_VALUE_TO_ADD = 100000;
    private static final int DISK_SPACE = 70000000;
    private static final int DISK_SPACE_NEEDED = 30000000;

    public static void main(String[] args) throws FileNotFoundException {
        java.io.File input = Path.of(FILE_STRING).toFile();
        Scanner inputScanner = new Scanner(input);
        Directory parentDirectory = createFileStructure(inputScanner);
        System.out.println(partA(parentDirectory));
        System.out.println(partB(parentDirectory));
    }

    private static Directory createFileStructure(Scanner inputScanner) {
        Directory parentDirectory = new Directory("/");
        String currentLine = inputScanner.nextLine();
        Directory currentDirectory = parentDirectory;
        while (!currentLine.equals("$ end")) {
            if (currentLine.equals("$ ls")) {
                currentLine = inputScanner.nextLine();
                while (currentLine.charAt(INSTRUCTION) != '$') {
                    String[] ops = currentLine.split(" ");
                    if (ops[DIR].equals("dir")) {
                        new Directory(ops[NAME], currentDirectory);
                    } else {
                        new File(ops[NAME], Integer.parseInt(ops[SIZE]), currentDirectory);
                    }
                    currentLine = getNextLine(inputScanner);
                }
            }
            if (currentLine.startsWith("$ cd")) {
                String[] ops = currentLine.split(" ");
                if (ops[2].equals("..")) {
                    currentDirectory = currentDirectory.getParent();
                } else {
                    ArrayList<Child> children = currentDirectory.getChildren();
                    for (Child child : children) {
                        if (child instanceof Directory) {
                            Directory dir = (Directory) child;
                            if (dir.name.equals(ops[2])) {
                                currentDirectory = dir;
                            }
                        }
                    }
                }
                currentLine = getNextLine(inputScanner);
            }
        }
        return parentDirectory;
    }

    private static String getNextLine(Scanner scanner) {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        } else {
            return "$ end";
        }
    }

    private static int partA(Directory parentDirectory) {
        ArrayList<Child> children = parentDirectory.getChildren();
        AtomicInteger cost = new AtomicInteger();
        for (Child child : children) {
            if (child instanceof Directory) {
                Directory dir = (Directory) child;
                if (dir.totalSize < MAX_VALUE_TO_ADD) {
                    cost.addAndGet(dir.totalSize);
                }
                cost.addAndGet(partA(dir));
            }
        }
        return cost.get();
    }

    private static int partB(Directory parentDirectory) {
        int parentDirectorySize = parentDirectory.totalSize;
        int currentSpaceFree = DISK_SPACE - parentDirectorySize;
        int spaceNeeded = DISK_SPACE_NEEDED - currentSpaceFree;
        return getCheapestGoodValue(parentDirectorySize, spaceNeeded, parentDirectory);
    }

    private static int getCheapestGoodValue(int currentCheapestGoodSize, int sizeNeeded, Directory currentDir) {
        ArrayList<Child> children = currentDir.getChildren();
        if (currentDir.totalSize > sizeNeeded) {
            AtomicInteger thisCheapestGoodSize = new AtomicInteger(Math.min(currentCheapestGoodSize, currentDir.totalSize));
            for (Child child : children) {
                if (child instanceof Directory) {
                    Directory dir = (Directory) child;
                    thisCheapestGoodSize.set(Math.min(thisCheapestGoodSize.get(), getCheapestGoodValue(thisCheapestGoodSize.get(), sizeNeeded, dir)));
                }
            }
            return thisCheapestGoodSize.get();
        }
        return currentCheapestGoodSize;
    }
}

interface Child {
    Directory getParent();
}

class Directory implements Child {
    public String name;
    public int totalSize;
    private final Directory parentDirectory;
    private final ArrayList<Child> children;

    public Directory(String name) {
        this.name = name;
        parentDirectory = null;
        totalSize = 0;
        children = new ArrayList<>();
    }

    public Directory(String name, Directory parentDirectory) {
        this.name = name;
        this.parentDirectory = parentDirectory;
        totalSize = 0;
        children = new ArrayList<>();
        parentDirectory.addToChildren(this);
    }

    @Override
    public Directory getParent() {
        return parentDirectory;
    }

    public ArrayList<Child> getChildren() {
        return children;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void addToTotalSize(int size) {
        totalSize += size;
        if (parentDirectory != null) {
            parentDirectory.addToTotalSize(size);
        }
    }

    public void addToChildren(Child child) {
        children.add(child);
    }
}

class File implements Child {
    public String name;
    private final int fileSize;
    private final Directory parentDirectory;

    @Override
    public Directory getParent() {
        return parentDirectory;
    }

    public int getFileSize() {
        return fileSize;
    }

    public File(String name, int fileSize, Directory parentDirectory) {
        this.name = name;
        this.fileSize = fileSize;
        this.parentDirectory = parentDirectory;
        parentDirectory.addToTotalSize(fileSize);
        parentDirectory.addToChildren(this);
    }
}