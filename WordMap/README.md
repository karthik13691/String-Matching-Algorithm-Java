# CC7.3-String Matching Algorithm

## Scenario

Your current client is an international firm that provides text mining solutions to its clients. Their product basically works as follows: First, they obtain a text file or string, which could be a product review on a web page, a blog, or tweets about the product. Then, they identify each word in the text file or string, count the number of times each word appears, and finally sort the words from most frequent to least frequent. These frequency lists are used for obtaining text mining statistics.

In text mining, some data preparation is required before the actual process begins. These preparations include changing every character to lowercase, removing punctuation marks, and so on. For this project, you need to transform text files into wordlists, count each word, and remove every symbol and punctuation mark.

## Best Practices

1. You may choose if a HashMap or TreeMap is best suited for this application.
2. Avoid checking each pair more than once.
3. For easy readability use full variable names instead of short 2-3 character abbreviation.
4. For code reusability, develop separate methods when available.

Before you begin, read the _London.txt_ and _Rome.txt_ files, which can be found in folder, textfiles. A simple _WordCounter.java_ class is created for storing word and counter pairs (that is, the word and its frequency in the file). Use the _WordSuggestions.java_ file to develop methods.

Implement the `getWordFrequencies()` method. This method should take the text file, read the text, and convert it into a wordlist. Then, the wordlist should be counted and sorted. Lastly, the counted and sorted wordlist should be returned. In order to do this, the `getWordFrequencies()` method must implement the `getWordList()` and `countWords()` methods.

## Tasks:

**Task #01:** Develop the `getSuggestions(ArrayList<WordCounter> wordCountList)` method as the base method of the class.

**Task #02:** Develop the `countWords(ArrayList<String> wordList)` method to find the frequencies of all words in the text file.

**Task #03:** Develop the `getWordList(File[] fileArray)` method to get all words in the text file. Ignore the words that have 3 or less characters.


