package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This class models the state and behavior of a SpellChecker
 * utility. It has a dictionary and a spell checking algorithm
 * to validate the spelling of words.
 * @author Matthew F. Leader
 *
 */
public class proj4 {
	
	/** the hash table that forms the dictionary */
	private static final HashTable<String, String> dictionary = new HashTable<String, String>();
	/** the number of lookups performed by the spellcheck algorithm */
	private static int numLookups = 0;
	/** the dictionary source text file */
	private static final String dictionaryTextFile = "../project_docs/dict.txt";
	
	public static void main(String[] args) {
		
		displayIntro();
		
        Scanner console = new Scanner(System.in);        
		String line;
		Scanner lineScanner;	
		int wordCt = 0;
		int numMisspelled = 0;
		String inquiry;
		Scanner input;
        Scanner dictInput = null;
        
		// Make hash table dictionary
		try {
			dictInput = new Scanner(new File(dictionaryTextFile));
			while (dictInput.hasNextLine()) {
				line = dictInput.nextLine();
				dictionary.insert(line, line);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		dictInput.close();
				
		//Get text file
		inquiry = "Text document input file name: ";
		input = null;
		File textFile;		
		do {
			textFile = getFileName(console, inquiry);
			input = getInputScanner(textFile, inquiry);
		} while (input == null);
				
		//Calculate word count in text file
		while (input.hasNextLine()) {
			line = input.nextLine();
			lineScanner = new Scanner(line);
			while (lineScanner.hasNext()) {
				String word = lineScanner.next();
				if (word.matches("^(?=.*[a-zA-Z]).+$")) {
					wordCt++;
				}				
			}
		}

		//Make a second copy of the textFile
		System.out.println("second");
		try {
			input = new Scanner(textFile);
		} catch (FileNotFoundException fnfe) {
			System.out.println("lost the text document, sorry boss");
		}
		
		//Read in, check, and display misspelled words
		System.out.println("======Misspelled Word(s)======");		
		while (input.hasNextLine()) {
			line = input.nextLine();
			if (line.length() > 0) {
				lineScanner = new Scanner(line);
				while (lineScanner.hasNext()) {
					String word = lineScanner.next();				
					String[] words = word.split("-");				
					for (int k = 0; k < words.length; k++) {
						StringBuilder wordBuilder = new StringBuilder(words[k]);						
						//only check wordBuilder if it has at least one letter character
						if (wordBuilder.length() > 0 
							&& wordBuilder.toString().matches("^(?=.*[a-zA-Z]).+$") 
							&& !validateSpelling(wordBuilder)) {
							//Output misspelled words
							numMisspelled++;
							System.out.println(words[k]);
						}
					}
				}
			}
		}		
		input.close();

		//Display relevant spellcheck and hash table statistics
		System.out.println("=======SpellCheck Output=======");
		System.out.println("number of words in dictionary: " + dictionary.numKeys());
		System.out.println("number of words to spell check: " + wordCt);
		System.out.println("number of misspelled words: " + numMisspelled);
		System.out.println("number of dictionary probes: " + dictionary.numProbes());
		System.out.printf("avg number of probes per word of text file: %.3f", (double) dictionary.numProbes() / wordCt);		
		System.out.printf("\navg number of probes per lookup: %.3f", (double) dictionary.numProbes() / numLookups);		
		System.out.println();
		
		/*
		System.out.printf("\ndictionary load factor: %.3f", (double) dictionary.numKeys() / dictionary.TABLE_SIZE);
		System.out.println("Largest bucket size " + dictionary.largestBucket());
		System.out.println("Last bucket used: " + dictionary.lastBucket());
		*/
		
	}
	
	/**
	 * Validates whether or not a word has been spelled correctly. It first strips punctuation,
	 * and attempts to decapitalize words if necessary, and follows a handful of selected
	 * spelling rules to validate if a word has been spelled correctly. If after the modifications
	 * to the word, the word has been found in the dictionary, then it is correctly spelled.
	 * @param word
	 * 					the word to look up in the dictionary
	 * @return true if the word is found in some permutation in the dictionary, and false if
	 * 			the word is never found in the dictionary
	 */
	public static boolean validateSpelling(StringBuilder word) {
		String spellcheck = null;
		StringBuilder original = null;
		spellcheck = dictionary.lookup(word.toString());
		numLookups++;
		if (spellcheck == null) {	
			//Strip punctuation from front
			if (word.length() > 0 && word.charAt(0) == '"') {
				word.deleteCharAt(0);
			}
			//Strip punctuation from end
			while (word.length() > 0
				&& (word.charAt(word.length() - 1) == ',' 
				|| word.charAt(word.length() - 1) == '.'
				|| word.charAt(word.length() - 1) == '?'
				|| word.charAt(word.length() - 1) == '!'
				|| word.charAt(word.length() - 1) == ';'
				|| word.charAt(word.length() - 1) == '"')) {
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}
			if (word.length() > 0 && Character.isUpperCase(word.charAt(0))) {				
				word.setCharAt(0, Character.toLowerCase(word.charAt(0)));
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}		
			if (spellcheck == null && word.length() > 1 && word.charAt(word.length() - 1) == 's') {
				original = new StringBuilder(word);
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
				if (spellcheck == null) {
					word = original;
				}
			} 
			if (spellcheck == null && word.length() > 1 
				&& word.substring(word.length() - 2, word.length()).equals("es")) {
				word.delete(word.length() - 2, word.length());
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}
			if (spellcheck == null && word.length() > 1 
				&& word.substring(word.length() - 2, word.length()).equals("ed")) {
				original = new StringBuilder(word);
				word.delete(word.length() - 2, word.length());
				spellcheck = dictionary.lookup(word.toString());	
				numLookups++;
				word = original;
			}
			if (spellcheck == null && word.length() > 1 
			    && word.charAt(word.length() - 1) == 'd') {
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}
			if (spellcheck == null && word.length() > 1 
				&& word.substring(word.length() - 2, word.length()).equals("er")) {
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}
			if (spellcheck == null && word.length() > 1 
				&& word.substring(word.length() - 2, word.length()).equals("ly")) {
				word.delete(word.length() - 2, word.length());
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
				if (spellcheck == null) {
					word.append("le");
					spellcheck = dictionary.lookup(word.toString());
					numLookups++;
				}
			}
			if (spellcheck == null && word.length() > 2 
				&& word.substring(word.length() - 3, word.length()).equals("ing")) {
				original = new StringBuilder(word);
				word.delete(word.length() - 3, word.length());
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
				if (spellcheck == null) {
					word.append("e");
					spellcheck = dictionary.lookup(word.toString());
					numLookups++;
				}
			}			
			if (spellcheck == null) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Returns a File of a String filename
	 * @param console
	 * 						Scanner object from console
	 * @param inquiry
	 * 						an appropriate prompt for a filename
	 * @return a File object of the user's choice
	 */
	public static File getFileName(Scanner console, String inquiry) {
		File inFile = null;
		System.out.print(inquiry);
		String filename = console.nextLine();
		inFile = new File(filename);
		return inFile;
	}
	
    /**
     * Returns a Scanner for input from a file.
     *
     * @param console Scanner for console
     * @return Scanner for input from a file
     */
    public static Scanner getInputScanner(File inFile, String inquiry) {
        Scanner input = null;
        //while (input == null) {        		
            try {
                input = new Scanner(inFile);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        //}
        return input;
    }

    /**
     * Returns a Scanner for input from a file.
     *
     * @param console Scanner for console
     * @return Scanner for input from a file
     */
    public static Scanner getInputScanner(Scanner console, String inquiry) {
        Scanner input = null;
        while (input == null) {
            System.out.print(inquiry);
            String name = console.nextLine();
            try {
                input = new Scanner(new File(name));
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
        return input;
    }
    
    /**
     * Display program introduction to screen
     */
    public static void displayIntro() {
    	System.out.println("This program can spell check a few common misspelling in English.");
    }
}
