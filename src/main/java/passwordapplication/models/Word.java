package passwordapplication.models;

/**
 * Word is the entity used for storing and accessing information about words.
 * The object includes the word and the Wordlist it is connected to. This class
 * has only standard getters and setters. It is inherited by the
 * Whiteword-class.
 *
 * @author antti
 */
public class Word {

    String word;
    Wordlist wordlist;

    /**
     * Empty constructor method.
     */
    public Word() {
    }

    /**
     * Constructor method
     *
     * @param word the word as string
     * @param wordlist the wordlist that the word belongs to
     */
    public Word(String word, Wordlist wordlist) {
        this.word = word;
        this.wordlist = wordlist;
    }

    /**
     * Method to set the word-parameter in the object
     *
     * @param word String to set as word
     */
    public void setWord(String word) {
        this.word = word;
    }

    /**
     * Method to get the word-parameter of the object
     *
     * @return the word as String
     */
    public String getWord() {
        return this.word;
    }

    /**
     * Method to set the wordlist-parameter in the object
     * @param wordlist the Wordlist object to set
     */
    public void setWordlist(Wordlist wordlist) {
        this.wordlist = wordlist;
    }

    /**
     * Method to get the wordlist-parameter of the object
     * @return the wordlist-object
     */
    public Wordlist getWordlist() {
        return this.wordlist;
    }
}
