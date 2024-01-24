package ie.atu.sw;

import java.nio.file.*;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * The main class that orchestrates the execution of the Sentiment
 * Analysis application. Handles user input and invokes corresponding
 * functionalities.
 *
 * 
 * 
 * @author Anastasiia Ryzhkova
 * @version 1.0
 * @since 21
 */
public class Runner {
	private static Scanner scanner = new Scanner(System.in);

	private static LexiconHandler fileParser = new LexiconHandler();
	private static ConcurrentMap<String, Double> lexiconMap = new ConcurrentHashMap<>();
	private static boolean lexiconsConfigured = false;
	private static FileHandler fileHandler = new FileHandler();

	/**
	 * The main entry point of the application. Displays the menu and handles user
	 * input until the user chooses to exit.
	 *
	 * @param args Command line arguments (not used in this application).
	 * @throws Exception If an exception occurs during the execution of the program.
	 */
	public static void main(String[] args) throws Exception {
		int userChoice;

		do {
			Menu.displayMenu();
			userChoice = Menu.getUserChoice(scanner);

			switch (userChoice) {
			case 1:
				handleConfigureLexicons();
				break;
			case 2:
				handleExecuteAnalyseReport();
				break;
			case 3:
				handleQuitting();
				break;
			default:
				System.out.println("Invalid choice. Please choose a valid option.");
			}

		} while (userChoice != 3);

		System.out.println("Program terminated.");
	}

	/**
	 * Validates the provided file path for a regular file existence.
	 *
	 * @param filePath The path of the file to validate.
	 * @return True if the file path is valid; false otherwise.
	 * Running time: O(1) as the method has a constant number of
	 *           operations.
	 */
	private static boolean isValidInputFilePath(String filePath) {
		Path path = Paths.get(filePath);

		if (filePath.trim().isEmpty()) {
			System.out.println("Error: File path cannot be empty.");
			return false;
		}

		if (!Files.exists(path)) {
			System.out.println("Error: File does not exist at the specified path: " + filePath);
			return false;
		}

		if (!Files.isRegularFile(path)) {
			System.out.println("Error: Specified path is not a file: " + filePath);
			return false;
		}

		return true;
	}

	/**
	 * Handles the configuration of lexicons by taking the user through the process
	 * of specifying the lexicon file path, processing the lexicons, and displaying
	 * the results.
	 * 
	 * Running time: O(N) where N is the number of lexicons processed, as
	 *           it iterates through the lexicons and performs processing for each
	 *           one.
	 */
	private static void handleConfigureLexicons() {
		System.out.println("Executing option 1: Configure Lexicons");

		// Set the lexicon file path
		fileHandler.setLexiconFilePath();

		// Now get the lexicon file path
		String lexiconPath = fileHandler.getLexiconFilePath();

		if (isValidLexiconFilePath(lexiconPath)) {
			System.out.println("Specified lexicon file path: " + lexiconPath);

			try {
				fileParser.processLexiconFromFile(lexiconPath);
				lexiconMap = fileParser.getLexiconCounts(); // Populate lexiconMap
				lexiconsConfigured = true;
				System.out.println("Result of lexicon processing: Success");

			} catch (Exception e) {
				System.out.println("Error processing lexicon:");
				e.printStackTrace();
			}
		} else {
			System.out.println("Invalid lexicon file path. Please check the path and try again.");
		}
	}

	/**
	 * Validates the provided file path for a regular file existence.
	 *
	 * @param lexiconFilePath The path of the file to validate.
	 * @return True if the file path is valid; false otherwise.
	 * Running time: O(1) as the method has a constant number of
	 *           operations.
	 */
	private static boolean isValidLexiconFilePath(String lexiconFilePath) {
		Path path = Paths.get(lexiconFilePath);

		if (lexiconFilePath.trim().isEmpty()) {
			System.out.println("Error: File path cannot be empty.");
			return false;
		}

		if (!Files.exists(path)) {
			System.out.println("Error: File does not exist at the specified path: " + lexiconFilePath);
			return false;
		}

		if (!Files.isRegularFile(path)) {
			System.out.println("Error: Specified path is not a file: " + lexiconFilePath);
			return false;
		}

		return true;
	}

	/**
	 * Handles the execution, analysis, and reporting of sentiment based on the
	 * user-specified input file and configured lexicons. 
	 * Running time: O(N * M) where N is the number of lines in the input file and M is the
	 * average number of lexicons per line, as it iterates through the lines and
	 * lexicons, performing sentiment analysis for each.
	 */
	private static void handleExecuteAnalyseReport() {
		System.out.println("Executing option 2: Execute, Analyze, and Report");

		if (!lexiconsConfigured) {
			System.out.println("Error: Lexicon not configured. Please configure lexicons first.");
			return;
		}

		fileHandler.setInputFilePath(); // Set the input file path using FileHandler

		if (isValidInputFilePath(fileHandler.getInputFilePath())) {
			System.out.println("Specified file path: " + fileHandler.getInputFilePath());
		} else {
			System.out.println("Failed to open the file. Please check the path and try again.");
			return; // Return early if there's an issue with the file path
		}

		SentimentCalculator sentimentCalculator = new SentimentCalculator(lexiconMap);

		// Call analyzeSentiment with FileHandler instance and lexiconMap
		String sentimentReport = sentimentCalculator.analyseSentiment(fileHandler, lexiconMap);

		// Print the sentiment report to the console
		System.out.println("Sentiment Report:");
		System.out.println(sentimentReport);
	}

	/**
	 * Handles the process of quitting the application, closing resources, and
	 * exiting.
	 * 
	 * Running time: O(1) as the method has a constant number of
	 *           operations.
	 */
	private static void handleQuitting() {
		// Logic for handling option 3 (Quitting)
		System.out.println("Exiting...");
		scanner.close();
		System.exit(0);
	}
}
