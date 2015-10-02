import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
/**
 * SpellCheck.java
 * A Java program that takes two files, one dictionary and one source file,
 * and checks if any words in the source file is not in the dictionary.
 * Created by Frederik Roenn Stensaeth and Javier Moran.
 * 05.29.14
 */
public class SpellCheck {
    /** Set that contains all the words in the dictionary. */
    private static Set<String> dict_set;
    /** Set that contians all the words in the source file. */
    private static Set<String> words_set;

    /** Constructor for SpellCheck. Loads the source file and the dictionary,
     * as well as comparing the words in the source file to the words in the
     * dictionary. */
    public SpellCheck(String wordsFile, String dictionaryFile) {
        // Calls load() method, giving it wordsFile and dictionaryFile.
        load(wordsFile, dictionaryFile);
        checker();
    }

    /** Compares the words in a source file to the words in a dictionary. */
    private static void checker() {
        // Checks if every word in source file is in the dictionary.
        // Prints out the words that are not in the dictionary.
        for(Object item : words_set.toArray()) {
            if(!dict_set.contains((String) item)) {
                System.out.println(item);
            }
        }
        // Print statements used for debugging.
        // System.out.println(dict_set.contains("days"));
        // System.out.println(words_set.contains("days"));
    }

    /** Takes two files and reads them into two seperate sets, choosing a
     * root that increases the probability of getting a balanced BST set. */
    private static void load(String wordsFile, String dictionaryFile) {
        dict_set = new BstSetImplementation<String>();
        words_set = new BstSetImplementation<String>();
        List<String> dict_list = new MysteryListImplementation<String>();
        List<String> words_list = new MysteryListImplementation<String>();
        // Sets up scanner.
        Scanner reader_words = null;
        Scanner reader_dict = null;
        // Tries to scan file, if file not found then prints error message
        // and exits system.
        try {
            reader_words = new Scanner(new File(wordsFile)).useDelimiter(
                "[_\\s\\d\\W]+");
            reader_dict = new Scanner(new File(dictionaryFile));
        } catch (FileNotFoundException e) {
            System.err.println(e);
            System.exit(1);
        }
        // Loops over the words and adds them to a set.
        while(reader_words.hasNext()) {
            String current = reader_words.next();
            words_list.add(current.toLowerCase());
        }
        while(reader_dict.hasNextLine()) {
            String current = reader_dict.nextLine();
            dict_list.add(current.toLowerCase());
        }
        // Sets up random variable used to generate random indexes.
        Random random = new Random();
        String dict_first = dict_list.at(0);
        String dict_middle = dict_list.at(dict_list.length() / 2);
        String dict_last = dict_list.at(dict_list.length() - 1);
        String words_first = dict_list.at(0);
        String words_middle = dict_list.at(dict_list.length() / 2);
        String words_last = dict_list.at(dict_list.length() - 1);
        // Compares first, middle, and last word in dictionary to find best
        // suited root for set.
        // Best suited root is the value that falls between the two other.
        if(dict_first.compareTo(dict_middle) <= 0) {
            if(dict_middle.compareTo(dict_last) <= 0) {
                dict_set.add(dict_middle);
                dict_list.remove(dict_list.length() / 2);
            } else if(dict_first.compareTo(dict_last) <= 0) {
                dict_set.add(dict_last);
                dict_list.remove(dict_list.length() - 1);
            } else {
                dict_set.add(dict_first);
                dict_list.remove(0);
            }
        } else if(dict_middle.compareTo(dict_last) <= 0) {
            if(dict_first.compareTo(dict_last) <= 0) {
                dict_set.add(dict_first);
                dict_list.remove(0);
            } else {
                dict_set.add(dict_last);
                dict_list.remove(dict_list.length() - 1);
            }
        } else {
            dict_set.add(dict_middle);
            dict_list.remove(dict_list.length() / 2);
        }
        // Extracts random items from dict_list and adds them to set.
        while(dict_list.length() > 0) {
            int random_num = random.nextInt(dict_list.length());
            dict_set.add(dict_list.remove(random_num));
        }
        // Compares first, middle, and last word in source file to find best
        // suited root for set.
        // Best suited root is the value that falls between the two other.
        if(words_first.compareTo(words_middle) <= 0) {
            if(words_middle.compareTo(words_last) <= 0) {
                words_set.add(words_middle);
                words_list.remove(words_list.length() / 2);
            } else if(words_first.compareTo(words_last) <= 0) {
                words_set.add(words_last);
                words_list.remove(words_list.length() - 1);
            } else {
                words_set.add(words_first);
                words_list.remove(0);
            }
        } else if(words_middle.compareTo(words_last) <= 0) {
            if(words_first.compareTo(words_last) <= 0) {
                words_set.add(words_first);
                words_list.remove(0);
            } else {
                words_set.add(words_last);
                words_list.remove(words_list.length() - 1);
            }
        } else {
            words_set.add(words_middle);
            words_list.remove(words_list.length() / 2);
        }
        // Extracts random items from dict_list and adds them to set.
        while(words_list.length() > 0) {
            int random_num = random.nextInt(words_list.length());
            words_set.add(words_list.remove(random_num));
        }
    }

    public static void main(String[] args) {
        // Checks if arguments given in terminal are valid, 
        // prints usgae message.
        if(args.length != 2) {
            System.out.println(
                "Usage: java SpellCheck source_file dictionary_file");
            System.exit(1);
        }
        SpellCheck spell_check = new SpellCheck(args[0], args[1]);
    }
}
