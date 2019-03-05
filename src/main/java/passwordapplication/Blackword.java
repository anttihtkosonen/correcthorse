package passwordapplication;


public class Blackword {
    String word;
    Wordlist wordlist;
    
    public Blackword(){
    }   
    
    
    public Blackword(String word,  Wordlist wordlist){
        this.word = word;
        this.wordlist = wordlist;
    }

    public String getWord() {
        return this.word;
    }
    
    public Wordlist getWordlist() {
        return this.wordlist;
    }


}
