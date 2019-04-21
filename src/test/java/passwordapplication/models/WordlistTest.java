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
     * Test of the costructor and getters. The parameters should be set
     * correctly, and returned correctly by the getters.
     */
    @Test
    public void testWordlist() {
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
