import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;
import java.util.Arrays;

public class WordSuggestions {

	//Declaring stop words explicitly is more efficient and avoids the overhead
	// of an external NLP library. It also promotes portability by adhering to Java's
	// "Write Once, Run Anywhere" principle, eliminating the need for additional library dependencies.
	private static final Set<String> stopWords = new HashSet<>(Arrays.asList(
			"about", "above", "after", "again", "against", "aren", "because", "been",
			"before", "being", "below", "between", "both", "cannot", "could",
			"does", "doing", "down", "during", "each", "from", "further",
			"have", "haven", "having", "here", "herself", "himself",
			"into", "just", "more", "most", "myself", "only", "other",
			"ourselves", "over", "same", "shan", "should", "some", "such",
			"than", "that", "their", "theirs", "them", "themselves", "then", "there",
			"these", "they", "this", "those", "through", "under", "until", "very",
			"were","what", "when", "where", "which", "while", "with", "would",
			"your", "yours", "yourself", "yourselves"
	));
	// stopWords less than 3 characters not included above



	/**
	 * Gets word frequencies from text files.
	 *
	 * @return ArrayList of WordCounter objects sorted by frequency
	 * @throws IOException if there's an error reading the files
	 */
	public ArrayList<WordCounter> getWordFrequencies() throws IOException {
		//Choose Files
		File[] files = getFiles();

		//Read words in Files
		Map<String, Integer> wordMap = getWordList(files);

        // Convert to ArrayList<WordCounter> and sort
        ArrayList<WordCounter> wordCounterList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : wordMap.entrySet()) {
			wordCounterList.add(new WordCounter(entry.getKey(), entry.getValue()));
		}

		//sort by frequency [Descending Order]
		wordCounterList.sort((a, b) -> Integer.compare(b.getCounter(), a.getCounter()));

        return wordCounterList;
	}

	/**
	 * Gets suggestions based on word frequencies.
	 *
	 * @param wordCountList List of WordCounter objects
	 * @return ArrayList of String arrays containing word and frequency
	 * @throws IOException if there's an error processing the data
	 */
	public ArrayList<String[]> getSuggestions(ArrayList<WordCounter> wordCountList) throws IOException {
		ArrayList<String[]> suggestions = new ArrayList<>();

		for (WordCounter wc : wordCountList) {
			suggestions.add(new String[]{wc.getWord(), String.valueOf(wc.getCounter())});
		}
		return suggestions;
	}


	/**
	 * Counts words in a list and returns WordCounter objects.
	 *
	 * @param wordList List of words to count
	 * @return ArrayList of WordCounter objects
	 */
	public ArrayList<WordCounter> countWords(ArrayList<String> wordList) {
		Map<String, Integer> wordFrequencyMap = new HashMap<>();
		ArrayList<WordCounter> wordCounterList = new ArrayList<>();

		// Count word frequencies
		for (String word : wordList) {
			// If the key is not present in the Map, getOrDefault(word, 0) returns the defaultValue
			// specified in the second argument (in this case, 0).
			// And then it increments the retrieved value by 1
			wordFrequencyMap.put(word, wordFrequencyMap.getOrDefault(word, 0) +1);
		}
		// Create WordCounter objects and add them to the list
		for (Map.Entry<String, Integer> entry : wordFrequencyMap.entrySet()) {
			String word = entry.getKey();
			int frequency = entry.getValue();
			wordCounterList.add(new WordCounter(word, frequency));
		}

		return wordCounterList;
	}

	/**
	 * Gets a word list from an array of files.
	 * @param fileArray Array of files to process
	 * @return Map of words and their frequencies
	 * @throws IOException if there's an error reading the files
	 */
	public Map<String, Integer> getWordList(File[] fileArray) throws IOException {
		// Since TreeMap maintains the sorted order of keys by itself, it adds additional overhead that's not
		// necessary since we sort the words separately later.

		Map<String, Integer> wordMap = new HashMap<>();

		// Iterate over each file in the array
		for (File file : fileArray) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				// Read each line of the file
				while ((line = br.readLine()) != null) {
					// Split the line into words
					String[] words = line.split("\\W+");
					// Add each word longer than 3 characters to the map
					for (String word : words) {
						if ((word.length() > 3) && !stopWords.contains(word)) {
							String lowercaseWord = word.toLowerCase();
							wordMap.put(lowercaseWord, wordMap.getOrDefault(lowercaseWord, 0) + 1);
						}
					}
				}
			} catch (IOException e) {
				System.err.println("Error reading file " + file.getName() + ": " + e.getMessage());
			}
		}
		return wordMap;
	}

	/**
	 * 
	 * @return  
	 */
	public File[] getFiles() {
		String filePath = "Enter the file path";
		File file = new File(filePath);

		if (!file.exists() || !file.isFile()) {
			System.out.println("File not found: " + filePath);
			return new File[0];
		}

		System.out.println("Processing file: " + file.getName());
		return new File[]{file};
	}

		 
	/**This method checks if P exists in T
	 * 
	 * @param P Pattern to match
	 * @param T Text to search
	 * 
	 * @return true if P exists in T
	 */
	public boolean badCharacterRuleMatch(String P, String T) {
        
		int n = T.length();        
		int m = P.length();

        
		 int e = 256;
		 int left[][] = new int[m][e];
        
		 for (int i = 0; i < m; i++)            
			 for (int j = 0; j < e; j++)                
				 left[i][j] = -1;
        
		 for (int i = 0; i < m; i++) {            
			 if (i != 0)                
				 for (int j = 0; j < e; j++)                    
					 left[i][j] = left[i - 1][j];            
			 left[i][P.charAt(i)] = i;        
		 }

        
		 boolean hasMatch = false;
		 
		 int skip;       
		 for (int i = 0; i < n - m + 1; i += skip) {            
			 skip = 0;            
			 for (int j = m - 1; j >= 0; j--) {                
				 if (P.charAt(j) != T.charAt(i + j)) {                    
					 skip = Math.max(1, j - left[j][T.charAt(i + j)]);                    
					 break;                
				 }            
			 }
            
			 if (skip == 0) {               
				 hasMatch = true;               
				 break;    
			 }        
		 }        
		 return hasMatch;
	 }

	 /**
	  * main() method stub
	  */
	 public static void main(String args[]) {
		 WordSuggestions ws = new WordSuggestions();
		 try {
			 ArrayList<WordCounter> wordFrequencies = ws.getWordFrequencies();
			 ArrayList<String[]> suggestions = ws.getSuggestions(wordFrequencies);

			 // Print top 10 most frequent words
			 System.out.println("Top 10 most frequent words in this document are:");
			 for (int i = 0; i < Math.min(10, suggestions.size()); i++) {
				 String[] suggestion = suggestions.get(i);
				 System.out.printf("%s: %s%n", suggestion[0], suggestion[1]);
			 }
		 } catch (IOException e) {
			 System.err.println("Error processing files: " + e.getMessage());
		 }
	 }
}
