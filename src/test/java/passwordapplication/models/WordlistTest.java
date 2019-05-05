package passwordapplication.models;

import java.sql.Timestamp;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * Test of the Wordlist-class
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class WordlistTest {

        /**
     * Test of the first costructor and getters. The parameters should be set
     * correctly, and returned correctly by the getters.
     */
    @Test
    public void testFirstWordlist() {
        Integer id = 666;
        String name = "mockname";
        Timestamp timestamp = mock(Timestamp.class);
        Boolean blacklist = false;
        Wordlist testwordlist = new Wordlist(id, name, timestamp, blacklist);
        assertEquals(testwordlist.getId(), id);
        assertEquals(testwordlist.getName(), name);
        assertEquals(testwordlist.getTimestamp(), timestamp);
        assertEquals(testwordlist.getBlacklist(), blacklist);
    }
    
    /**
     * Test of the second costructor and getters. The parameters should be set
     * correctly, and returned correctly by the getters.
     */
    @Test
    public void testSecondWordlist() {
        //Wordlist(Integer id, String name, Timestamp timestamp, Boolean blacklist) {
        String name = "mockname";
        Timestamp timestamp = mock(Timestamp.class);
        Boolean blacklist = false;
        Wordlist testwordlist = new Wordlist(name, timestamp, blacklist);
        assertEquals(testwordlist.getName(), name);
        assertEquals(testwordlist.getTimestamp(), timestamp);
        assertEquals(testwordlist.getBlacklist(), blacklist);
    }
}
