package passwordapplication.domain;

import passwordapplication.models.Word;
import passwordapplication.models.Wordlist;
import org.junit.Test;
import static org.junit.Assert.*;


public class WordTest {
    
    @Test
    public void testSetGetWord() {
        System.out.println("getWord");
        Word instance = new Word();
        instance.setWord("testword");
        String expResult = "testword";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetGetWordlist() {
        System.out.println("getWordlist");
        Word instance = new Word();
        Wordlist wordlist = new Wordlist();
        Wordlist expResult = wordlist;
        instance.setWordlist(wordlist);
        Wordlist result = instance.getWordlist();
        assertEquals(expResult, result);
    }
    
}
