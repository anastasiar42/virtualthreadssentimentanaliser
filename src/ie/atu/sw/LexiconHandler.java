package ie.atu.sw;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.Executors;

/**
 * This class is responsible for processing lexicon data from files, text, or manually provided strings.
 * It utilises virtual threads for parallel processing of lexicons and maintains a lexicon counts map.
 */
public class LexiconHandler {
	private final ConcurrentMap<String, Double> lexiconCounts = new ConcurrentHashMap<>();

    /**
     * Reads lexicon data from the specified file path and processes it using virtual threads.
     *
     * @param lexiconFilePath The path to the lexicon file.
     * Running time: O(N) where N is the number of lines in the lexicon file,
     * as it reads the file, splits it into lines, and processes each line in parallel using virtual threads.
     */
	public void processLexiconFromFile(String lexiconFilePath) {
		try {
			String lexicon = new String(Files.readAllBytes(Paths.get(lexiconFilePath)), StandardCharsets.UTF_8);
			processLexicon(lexicon);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
    /**
     * Processes lexicon data using virtual threads.
     *
     * @param lexicon The lexicon data as a string.
     * Running time: O(N) where N is the number of lines in the lexicon,
     * as it splits the lexicon into lines and processes each line in parallel using virtual threads.
     */
	public void processLexicon(String lexicon) {
		try (var pool = Executors.newVirtualThreadPerTaskExecutor()) {
			Arrays.stream(lexicon.split("\\r?\\n")).forEach(line -> pool.execute(() -> process(line)));
		}
	}
	
    /**
     * Processes a single line of lexicon data.
     *
     * @param line A line of lexicon data in the format "word, score".
     * Running time: O(1) as the method performs a constant number of operations
     * for splitting the line, extracting word and score, and updating the lexicon counts.
     */
	private void process(String line) {
		String[] parts = line.split(",");
		if (parts.length == 2) {
			String word = parts[0].trim();
			double score = Double.parseDouble(parts[1].trim());
			lexiconCounts.put(word, score);
			System.out.println("Processing lexicon...");

		}
	}

    /**
     * Processes a block of text and updates the lexicon counts.
     *
     * @param text The text to process.
     * Running time: O(W) where W is the number of words in the text,
     * as it splits the text into words and updates the lexicon counts for each word.
     */	
	public void processText(String text) {
		Arrays.stream(text.split("\\s+")).forEach(word -> {
			lexiconCounts.merge(word, 1.0, Double::sum);
		});
	}
	
    /**
     * Sets the lexicon counts using the provided map.
     *
     * @param lexiconCounts The map containing lexicon counts to set.
     * Running time: O(L) where L is the number of entries in the provided map,
     * as it puts all entries from the provided map into the lexiconCounts map.
     */
	public void setLexiconCounts(ConcurrentMap<String, Double> lexiconCounts) {
		this.lexiconCounts.putAll(lexiconCounts);
	}
	
    /**
     * Gets the lexicon counts map.
     *
     * @return The lexicon counts map.
     * Running time: O(1) as the method performs a constant number of operations
     * to return the lexicon counts map.
     */
	public ConcurrentMap<String, Double> getLexiconCounts() {
		return lexiconCounts;
	}

}
