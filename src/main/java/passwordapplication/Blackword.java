package passwordapplication;


public class Blackword {
    Integer id;
    String word;
    Wordlist wordlist;
    
    public Blackword(){
    }   
    
    
    public Blackword(Integer id, String word,  Wordlist wordlist){
        this.id = id;
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
