package passwordapplication;

/**
 * Blackword is the entity used for words that are on blacklists (not to be
 * included in passwords) The object includes the word and the wordlist it is
 * connected to This class has only standard getters and setters.
 *
 * @author antti
 */
public class Blackword {

    String word;
    Wordlist wordlist;

    public Blackword() {
    }

    public Blackword(String word, Wordlist wordlist) {
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
