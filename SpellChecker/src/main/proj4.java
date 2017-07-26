package main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * 
 * @author Matthew F. Leader
 *
 */
public class proj4 {
	
	private static final HashTable<String, String> dictionary = new HashTable<String, String>();
	private static int numLookups = 0;
	private static final String dictionaryTextFile = "../docs/dict.txt";
	
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
		
		// Make hash table dictionary
        /*
         *         String inquiry = "Dictionary input file name: ";
        Scanner input = getInputScanner(console, inquiry);      
        PrintStream output = getOutputPrintStream(console);  
        */ 

		
		dictInput.close();
		
		
		//Get text file
		inquiry = "Text document input file name: ";
		input = null;
		File textFile;		
		do {
			textFile = getFileName(console, inquiry);
			input = getInputScanner(textFile, inquiry);
		} while (textFile == null && input == null);
		
		
		//Calculate word count in text file
		while (input.hasNextLine()) {
			line = input.nextLine();
			lineScanner = new Scanner(line);
			while (lineScanner.hasNext()) {
				lineScanner.next();
				wordCt++;
			}
		}

		
		try {
			input = new Scanner(textFile);
		} catch (FileNotFoundException fnfe) {
			System.out.println("lost the text document, sorry boss");
		}
		
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
						if (wordBuilder.length() > 0 && !validateSpelling(wordBuilder)) {
							//Output misspelled words
							numMisspelled++;
							System.out.println(words[k]);
						}
					}
				}
			}
		}
		
		input.close();

		System.out.println("=======SpellCheck Output=======");
		//Display number of words in dictionary
		System.out.println("number of words in dictionary: " + dictionary.numKeys());
		//Number of words in text to be spell checked
		System.out.println("number of words to spell check: " + wordCt);
		//Number of misspelled words in text
		System.out.println("number of misspelled words: " + numMisspelled);
		//Total number of probes during check phase
		System.out.println("number of dictionary probes: " + dictionary.numProbes());
		//Avg number of probes per word of original text file
		System.out.printf("avg number of probes per word of text file: %.3f", (double) dictionary.numProbes() / wordCt);		
		//Avg number of probes per lookup operation
		System.out.printf("\navg number of probes per lookup: %.3f", (double) dictionary.numProbes() / numLookups);		
		
		/*
		System.out.printf("\ndictionary load factor: %.3f", (double) dictionary.numKeys() / dictionary.TABLE_SIZE);
		System.out.println("Largest bucket size " + dictionary.largestBucket());
		System.out.println("Last bucket used: " + dictionary.lastBucket());
		*/
		
	}
	
	public static boolean validateSpelling(StringBuilder word) {
		//System.out.println("flag");
		String spellcheck = null;
		StringBuilder original = null;
		spellcheck = dictionary.lookup(word.toString());
		numLookups++;
		if (spellcheck == null) {			
			if (Character.isUpperCase(word.charAt(0))) {				
				word.setCharAt(0, Character.toLowerCase(word.charAt(0)));
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}
			//eliminate punctuation
			if (word.charAt(word.length() - 1) == ',' 
				|| word.charAt(word.length() - 1) == '.'
				|| word.charAt(word.length() - 1) == '?'
				|| word.charAt(word.length() - 1) == '!'
				|| word.charAt(word.length() - 1) == ';') {
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}		
			if (spellcheck == null && word.length() > 1 && word.charAt(word.length() - 1) == 's') {
				//System.out.println("flag3");
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
				//System.out.println("flag4");
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
				//System.out.println("flag5");
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookup(word.toString());
				numLookups++;
			}
			if (spellcheck == null && word.length() > 1 
				&& word.substring(word.length() - 2, word.length()).equals("ly")) {
				//System.out.println("flag7");
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
				//System.out.println("flag6");
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
     * Returns a PrintStream for output to a file. NOTE: If file exists, this
     * code will overwrite the existing file. It is likely that you want to add
     * additional tests.
     *
     * @param console Scanner for console.
     * @return PrintStream for output to a file
     */
    public static PrintStream getOutputPrintStream(Scanner console) {
        PrintStream outputFile = null;
        while (outputFile == null) {
            System.out.print("output file name? ");
            String name = console.nextLine();
            try {
                outputFile = new PrintStream(new File(name));
            } catch (FileNotFoundException e) {
                System.out.println("File unable to be written. Please try again.");
            }
        }
        return outputFile;
    }
    
    /**
     * Display program introduction to screen
     */
    public static void displayIntro() {
    	System.out.println("This program can spell check a few common misspelling in English.");
    }
}
