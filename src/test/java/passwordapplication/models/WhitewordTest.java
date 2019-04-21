package passwordapplication.models;

import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test for the Whiteword-class
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class WhitewordTest {

    @InjectMocks
    Whiteword whiteword;

    /**
     * Test of the costructor and getters. The parameters should be set
     * correctly, and returned correctly by the getters.
     */
    @Test
    public void testWhiteword() {
        String word = "mockword";
        Boolean active = true;
        Wordlist wordlist = mock(Wordlist.class);
        Whiteword testword = new Whiteword(word, active, wordlist);
        assertEquals(testword.getWord(), word);
        assertEquals(testword.getActive(), active);
        assertEquals(testword.getWordlist(), wordlist);
    }

    /**
     * test of the active-setter.
     */
    @Test
    public void testSetGetActive() {
        whiteword.setActive(true);
        Boolean expResult = true;
        Boolean result = whiteword.getActive();
        assertEquals(expResult, result);
    }

}
