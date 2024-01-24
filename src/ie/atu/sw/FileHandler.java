package ie.atu.sw;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * This class handles file-related operations, including user input for file paths,
 * formatting paths, and reading text from files.
 */
public class FileHandler {
    private String inputFilePath = "";
    private String lexiconFilePath = "";

    /**
     * Gets the input file path from user input.
     * 
     * Running time: O(N), where N is the length of the input path,
     * as the method performs a constant-time operation for each character in the path.
     */
    public void setInputFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Input File Path: ");
        inputFilePath = formatPath(scanner.nextLine().trim());
    }

    /**
     * Retrieves the input file path.
     *
     * @return The input file path.
     * Running time: O(1) because the time it takes to returns the value of the inputFilePath field 
     * does not depend on the size of any input or data structures.
     */
    public String getInputFilePath() {
        return inputFilePath;
    }

    /**
     * Gets the lexicon file path from user input.
     * Running time: O(1) as it involves reading a single line of input,
     * which is an operation with constant time complexity.
     */
    public void setLexiconFilePath() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter Lexicon File Path: ");
        lexiconFilePath = formatPath(scanner.nextLine().trim());
    }

    /**
     * Retrieves the lexicon file path.
     *
     * @return The lexicon file path.
     * Running time: O(1) as it simply returns the value of the lexiconFilePath field.
     */
    public String getLexiconFilePath() {
        return lexiconFilePath;
    }

    /**
     * Helper method to format file paths by replacing backslashes with forward slashes.
     *
     * @param path The input file path to be formatted.
     * @return The formatted file path.
     * Running time: O(N), where N is the length of the input path,
     * as the method performs a constant-time operation for each character in the path.
     */
    private String formatPath(String path) {
        return path.replace("\\", "/");
    }

    /**
     * Reads text from a file.
     *
     * @param filePath The path of the file to read.
     * @return The text read from the file.
     * Running time: O(M), where M is the number of characters in the file,
     * as the method reads the lines from the file and concatenates them.
     */
    public String readTextFromFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.lines(path).collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
