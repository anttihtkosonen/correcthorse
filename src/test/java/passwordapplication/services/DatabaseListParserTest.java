package passwordapplication.services;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import passwordapplication.dao.BlackwordDAO;
import passwordapplication.dao.WhitewordDAO;
import passwordapplication.dao.WordlistDAO;
import passwordapplication.models.Wordlist;

/**
 * Tests of the DatabaseListParserClass. Unless otherwise commented, the correct
 * behaviour of class methods is tested by capturing the parameters of the calls
 * to methods of other classes made by the method being tested, and comparing
 * them to what is expected.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class DatabaseListParserTest {

    @Mock
    WhitewordDAO whiteworddao;

    @Mock
    WordlistDAO wordlistdao;

    @Mock
    Wordlist wordlist;

    @Mock
    BlackwordDAO blackworddao;

    @Mock
    FileListParser filelistparser;

    @InjectMocks
    @Spy
    DatabaseListParser databaselistparser;

    final private String mockname = "mockname";
    final private String mocklist = "Mockone\nmocktwo\nmocktwo";
    final private String mocklocation = "/home/user/mock.txt";
    final private Timestamp mockstamp = new Timestamp(123456789);
    final private Boolean mockblacklist = false;
    final private Integer mockId = 666;
    final private ArrayList mockList = mock(ArrayList.class);
    final private ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<String> listCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<Timestamp> timeCaptor = ArgumentCaptor.forClass(Timestamp.class);
    final private ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
    final private ArgumentCaptor<String> wordCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);
    final private ArgumentCaptor<List<String>> ListCaptor = ArgumentCaptor.forClass(ArrayList.class);

    /**
     * Test of the addList-method.
     */
    @Test
    public void testAddList() {
        List<String> mockUnAcceptable = new ArrayList();
        mockUnAcceptable.add("Mockone");
        mockUnAcceptable.add("mocktwo");
        try {
            when(wordlistdao.insert(any(Wordlist.class))).thenReturn(mockId);
            databaselistparser.addList(mockname, mocklist, mockstamp, mockblacklist);
            //when(databaselistparser.parseLine(any(), any(), any())).thenReturn(false);
            verify(databaselistparser, times(3)).parseLine(wordCaptor.capture(), booleanCaptor.capture(), idCaptor.capture(), ListCaptor.capture());
        } catch (IOException ex) {
            fail("IOException");
        } catch (SQLException ex) {
            fail("SQLException");
        }

        List<String> capturedList = wordCaptor.getAllValues();
        assertEquals(capturedList.get(0), "Mockone");
        assertEquals(capturedList.get(1), "mocktwo");
        assertEquals(capturedList.get(2), "mocktwo");
        assertEquals(booleanCaptor.getValue(), mockblacklist);
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(ListCaptor.getValue(), mockUnAcceptable);

    }

    /**
     * Test of the addWord-method, when the word is a whiteword and active.
     */
    @Test
    public void testAddWordActiveWhiteword() {
        String word = "mockword";
        Boolean status = true;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(false);
            databaselistparser.addWord(word, mockblacklist, mockId);
            verify(whiteworddao).insert(wordCaptor.capture(), booleanCaptor.capture(), idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }

        assertEquals(wordCaptor.getValue(), word);
        assertEquals(booleanCaptor.getValue(), status);
        assertEquals(idCaptor.getValue(), mockId);

    }

    /**
     * Test of the addWord-method, with a inactive whiteword.
     */
    @Test
    public void testAddWordInactiveWhiteword() {
        String word = "mockword";
        Boolean status = false;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(true);
            databaselistparser.addWord(word, mockblacklist, mockId);
            verify(whiteworddao).insert(wordCaptor.capture(), booleanCaptor.capture(), idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }
        assertEquals(wordCaptor.getValue(), word);
        assertEquals(booleanCaptor.getValue(), status);
        assertEquals(idCaptor.getValue(), mockId);

    }

    /**
     * Test of the addWord-method, when the word is a blackword.
     */
    @Test
    public void testAddWordBlackword() {
        String word = "mockword";
        try {
            databaselistparser.addWord(word, true, mockId);
            verify(blackworddao).insert(wordCaptor.capture(), idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }

        assertEquals(wordCaptor.getValue(), word);
        assertEquals(idCaptor.getValue(), mockId);

    }

    /**
     * Test of the parseLine-method, when the word includes a
     * non-ascii-character.
     */
    @Test
    public void testParseLineNonASCII() {

        String word = "mockword¤";
        Boolean acceptable = true;
        Boolean expectation = false;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(false);
            acceptable = databaselistparser.parseLine(word, mockblacklist, mockId, mockList);
        } catch (SQLException ex) {
            fail("SQLException");
        }
        assertEquals(acceptable, expectation);
    }

    /**
     * Test of the parseLine-method, when the word is too long.
     */
    @Test
    public void testParseLineTooLong() {
        String word = "mockwordthatisfarlongerthanisallowed";
        Boolean acceptable = true;
        Boolean expectation = false;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(false);
            acceptable = databaselistparser.parseLine(word, mockblacklist, mockId, mockList);
        } catch (SQLException ex) {
            fail("SQLException");
        }
        assertEquals(acceptable, expectation);
    }

    /**
     * Test of the parseLine-method, when the word is empty.
     */
    @Test
    public void testParseEmptyLine() {
        String word = "";
        Boolean acceptable = true;
        Boolean expectation = false;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(false);
            acceptable = databaselistparser.parseLine(word, mockblacklist, mockId, mockList);
        } catch (SQLException ex) {
            fail("SQLException");
        }
        assertEquals(acceptable, expectation);
    }

    /**
     * Test of the removeList-method, when the list is not a blacklist.
     */
    @Test
    public void testRemoveListWhenNotBlacklist() {
        try {
            when(wordlistdao.readBlacklistStatus(anyInt())).thenReturn(false);
            databaselistparser.removeList(mockId);
            doNothing().when(whiteworddao).deleteListWords(anyInt());
            verify(whiteworddao).deleteListWords(idCaptor.capture());
            doNothing().when(wordlistdao).deleteList(anyInt());
            verify(wordlistdao).deleteList(idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }

        List<Integer> capturedList = idCaptor.getAllValues();
        assertEquals(capturedList.get(0), mockId);
        assertEquals(capturedList.get(1), mockId);
    }

    /**
     * Test of the removeList-method, when the list is a blacklist.
     */
    @Test
    public void testRemoveListWhenBlacklist() {
        List<String> mockwordlist = new ArrayList();
        mockwordlist.add("some");
        mockwordlist.add("mock");
        mockwordlist.add("words");
        try {
            when(wordlistdao.readBlacklistStatus(anyInt())).thenReturn(true);
            when(blackworddao.listWords(mockId)).thenReturn(mockwordlist);
            when(blackworddao.count("some")).thenReturn(0);
            when(blackworddao.count("mock")).thenReturn(1);
            when(blackworddao.count("words")).thenReturn(2);
            databaselistparser.removeList(mockId);
            doNothing().when(whiteworddao).setActive(anyString());
            verify(whiteworddao).setActive(wordCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }

        List<String> capturedList = wordCaptor.getAllValues();
        if (capturedList.size() != 1) {
            fail("Words are being removed, that should not be");
        }
        assertEquals(capturedList.get(0), "mock");
    }

    /**
     * Test of the isAsciiLetter-method. The method is tested with four
     * ASCII-characters, each of which should not pass.
     */
    @Test
    public void testIsAsciiLetterUnacceptable() {
        char[] characters = {'@', '[', '`', '{'};
        Boolean accepted;
        Boolean expectation = false;
        for (char i : characters) {
            accepted = true;
            accepted = databaselistparser.isAsciiLetter(i);
            assertEquals(accepted, expectation);
        }

    }
    
        /**
     * Test of the isAsciiLetter-method. The method is tested with four
     * ASCII-characters, each of which should pass.
     */
    @Test
    public void testIsAsciiLetterAcceptable() {
        char[] characters = {'A', 'Z', 'a', 'z'};
        Boolean accepted;
        Boolean expectation = true;
        for (char i : characters) {
            accepted = false;
            accepted = databaselistparser.isAsciiLetter(i);
            assertEquals(accepted, expectation);
        }

    }

}
