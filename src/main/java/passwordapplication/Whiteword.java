package passwordapplication;


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

    public String getWord() {
        return this.word;
    }
    
    public Boolean getActive() {
        return this.active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
    
    public Wordlist getWordlist() {
        return this.wordlist;
    }


}
