package passwordapplication.models;

/**
 * Whiteword is the entity used for words that are used to generate passwords
 * The object includes the word, the information about whether it can be used
 * for passwords and the Wordlist it is connected to. This class inherits the
 * Word-class
 *
 * @author antti
 */
public class Whiteword extends Word {

    Boolean active;

    /**
     * Empty constructor method.
     */
    public Whiteword() {
    }

    /**
     * Constructor method
     * @param word the word as string
     * @param active the activity status of the word
     * @param wordlist the wordlist that the word belongs to
     */
    public Whiteword(String word, Boolean active, Wordlist wordlist) {
        super(word, wordlist);
        this.active = active;
    }

    /**
     * Method to set the activity of the word
     * @param active the activity status to set
     */
    public void setActive(Boolean active) {
        this.active = active;
    }

    /**
     * Method to get the activity status of the word.
     * @return activity status
     */
    public Boolean getActive() {
        return this.active;
    }
}
