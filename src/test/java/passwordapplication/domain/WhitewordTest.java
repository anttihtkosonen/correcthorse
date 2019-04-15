package passwordapplication.domain;

import passwordapplication.models.Whiteword;
import passwordapplication.models.Wordlist;
import org.junit.Test;
import static org.junit.Assert.*;


public class WhitewordTest {
    


    @Test
    public void testGetWord() {
        System.out.println("getWord");
        Whiteword instance = new Whiteword();
        instance.setWord("testword");
        String expResult = "testword";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }


    @Test
    public void testGetWordlist() {
        System.out.println("getWordlist");
        Whiteword instance = new Whiteword();
        Wordlist wordlist = new Wordlist();
        Wordlist expResult = wordlist;
        instance.setWordlist(wordlist);
        Wordlist result = instance.getWordlist();
        assertEquals(expResult, result);
    }


    @Test
    public void testSetGetActive() {
        System.out.println("getActive");
        Whiteword instance = new Whiteword();
        instance.setActive(true);
        Boolean expResult = true;
        Boolean result = instance.getActive();
        assertEquals(expResult, result);
    }
    
}
