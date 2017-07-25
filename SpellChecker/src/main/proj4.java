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
	
	
	public static void main(String[] args) {
		
		displayIntro();
		
        Scanner console = new Scanner(System.in);
        HashTable<String, String> dictionary = new HashTable<String, String>();
		String line;
		Scanner lineScanner;	
		int wordCt = 0;
		int numMisspelled = 0;
		
        String inquiry = "Dictionary input file name: ";
        Scanner input = getInputScanner(console, inquiry);      
        PrintStream output = getOutputPrintStream(console);                   				
		
		// Make hash table dictionary
		while (input.hasNextLine()) {
			line = input.nextLine();
			dictionary.insert(line, line);
		}
		
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
				wordCt++;
			}
		}
		
		
		
	}
	
	public static boolean validateSpelling(HashTable<String, String> dictionary, StringBuilder word) {
		String spellcheck = null;
		spellcheck = dictionary.lookUp(word.toString());
		if (spellcheck == null) {			
			if (Character.isUpperCase(word.charAt(0))) {
				word.setCharAt(0, Character.toLowerCase(word.charAt(0)));
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (word.substring(word.length() - 2, word.length() - 1).equals("'s")) {
				word.delete(word.length() - 2, word.length() - 1);
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (word.charAt(word.length() - 1) == 's') {
				word.deleteCharAt(word.length() - 1);
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (word.substring(word.length() - 2, word.length() - 1).equals("ed")) {
				word.delete(word.length() - 2, word.length() - 1);
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (word.substring(word.length() - 2, word.length() - 1).equals("er")) {
				word.delete(word.length() - 2, word.length() - 1);
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (word.substring(word.length() - 3, word.length() - 1).equals("ing")) {
				word.delete(word.length() - 3, word.length() - 1);
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (word.substring(word.length() - 2, word.length() - 1).equals("ly")) {
				word.delete(word.length() - 2, word.length() - 1);
				spellcheck = dictionary.lookUp(word.toString());
			}
			if (spellcheck == null) {
				return false;
			}
		}
		return true;
	}
	
	public static File getFileName(Scanner console, String inquiry) {
		File inFile = null;
		System.out.println(inquiry);
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
        while (input == null) {        		
            try {
                input = new Scanner(inFile);
            } catch (FileNotFoundException e) {
                System.out.println("File not found. Please try again.");
            }
        }
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

    }
}
