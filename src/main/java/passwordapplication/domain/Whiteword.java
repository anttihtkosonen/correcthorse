package passwordapplication.domain;

/**
 * Whiteword is the entity used for words that are used to generate passwords
 * The object includes the word, the information about whether it can be used 
 * for passwords and the Wordlist it is connected to.
 * This class inherits the Word-class
 * @author antti
 */
public class Whiteword extends Word {
    Boolean active;
    
    public Whiteword(){
    }   
    
    public Whiteword(String word, Boolean active, Wordlist wordlist){
        super(word, wordlist);
        this.active = active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Boolean getActive() {
        return this.active;
    }
}
