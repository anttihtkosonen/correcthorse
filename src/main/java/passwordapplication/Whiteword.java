package passwordapplication;

/**
 * Whiteword is the entity used for words that are used to generate passwords
 * The object includes the word, the information about whether it can be used 
 * for passwords and the wordlist it is connected to.
 * This class has only standard getters and setters.
 * @author antti
 */
public class Whiteword {
    String word;
    Boolean active;
    Wordlist wordlist;
    
    public Whiteword(){
    }   
    
    
    public Whiteword(String word, Boolean active, Wordlist wordlist){
        this.word = word;
        this.active = active;
        this.wordlist = wordlist;
    }

    public void setWord(String word) {
        this.word = word;
    }
    
    public String getWord() {
        return this.word;
    }
    
    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Boolean getActive() {
        return this.active;
    }
    
    public void setWordlist(Wordlist wordlist) {
        this.wordlist = wordlist;
    }

    public Wordlist getWordlist() {
        return this.wordlist;
    }


}
