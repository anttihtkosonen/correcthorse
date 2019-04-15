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

    public Word() {
    }

    public Word(String word, Wordlist wordlist) {
        this.word = word;
        this.wordlist = wordlist;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return this.word;
    }

    public void setWordlist(Wordlist wordlist) {
        this.wordlist = wordlist;
    }

    public Wordlist getWordlist() {
        return this.wordlist;
    }
}
