package passwordapplication.models;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test of the Word-class.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class WordTest {

    /**
     * Test of the costructor and getters. The parameters should be set
     * correctly, and returned correctly by the getters.
     */
    @Test
    public void testWord() {
        String word = "mockword";
        Wordlist wordlist = mock(Wordlist.class);
        Word testword = new Word(word, wordlist);
        assertEquals(testword.getWord(), word);
        assertEquals(testword.getWordlist(), wordlist);
    }

    /**
     * Test of the word-setter.
     */
    @Test
    public void testSetGetWord() {
        System.out.println("getWord");
        Word instance = new Word();
        instance.setWord("testword");
        String expResult = "testword";
        String result = instance.getWord();
        assertEquals(expResult, result);
    }

    /**
     * Test of the wordlist-setter.
     */
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
