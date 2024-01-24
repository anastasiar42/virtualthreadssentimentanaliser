package ie.atu.sw;

import java.util.concurrent.ConcurrentMap;

/**
 * This class calculates sentiment based on an input file and a provided lexicon.
 * It analyses the text in the file, assigns sentiment scores to words, and generates a sentiment report.
 */
public class SentimentCalculator {

	private final ConcurrentMap<String, Double> lexicon;

    /**
     * Constructs a SentimentCalculator with the given lexicon.
     *
     * @param lexicon The lexicon containing words and their sentiment scores.
     * Running time: O(1) as the method performs a constant number of operations
     * to assign the provided lexicon to the instance variable.
     */	
	public SentimentCalculator(ConcurrentMap<String, Double> lexicon) {
		this.lexicon = lexicon;
	}

    /**
     * Analyses sentiment for a specified file using the provided lexicon.
     *
     * @param fileHandler The FileHandler instance for retrieving the input file path and reading text.
     * @param lexiconMap   The lexicon map to use for sentiment analysis.
     * @return The formatted sentiment report.
     * Running time: O(L * W) where L is the number of lines in the file and W is the number of words in each line,
     * as it reads the text from the file, splits it into lines and words, and processes each word against the lexicon.
     */	
	public String analyseSentiment(FileHandler fileHandler, ConcurrentMap<String, Double> lexiconMap) {
		// Retrieve the input file path from FileHandler
		String filePath = fileHandler.getInputFilePath();

		System.out.println("Analyzing sentiment for file: " + filePath);

		// Read the content from the file using FileHandler
		String text = fileHandler.readTextFromFile(filePath);

		// Check if text retrieval was successful
		if (text == null || text.isEmpty()) {
			System.out.println("Error reading text from file. Please check the file path and try again.");
			return "";
		}

		double score = 0.0;

		// Split the text into lines and process each line
		String[] lines = text.split("\n");
		for (String line : lines) {
			// Process each word in the line
			String[] words = line.trim().replaceAll("[^a-zA-Z@!?,.\\s]", "").toLowerCase().split("\\s+");
			for (String word : words) {
				// Include the word in the lexicon check (case-insensitive)
				if (lexiconMap.keySet().stream().anyMatch(word::equalsIgnoreCase)) {
					double wordScore = lexiconMap.getOrDefault(word, 0.0);

					// Update score
					score += wordScore;

				}
			}
		}
		// Return the formatted sentiment report
		return reportSentiment(score);
	}

    /**
     * Generates a sentiment report based on the calculated score.
     *
     * @param score The calculated sentiment score.
     * @return The formatted sentiment report.
     * Running time: O(1) as the method performs a constant number of operations
     * to generate the sentiment report based on the calculated score.
     */	
	private String reportSentiment(double score) {
		if (score > 0) {
			return "Positive Sentiment: " + score;
		} else if (score < 0) {
			return "Negative Sentiment: " + score;
		} else {
			return "Neutral Sentiment" + score;
		}
	}
}