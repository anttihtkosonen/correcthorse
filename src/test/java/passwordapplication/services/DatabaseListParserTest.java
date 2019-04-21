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
 * Tests of the DatabaseListParserClass. Unless otherwise commented, the
 * correct behaviour of class methods is tested by capturing the parameters of
 * the calls to methods of other classes made by the method being tested, and
 * comparing them to what is expected.
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
    final private String mocklist = "mock1\nmock2";
    final private String mocklocation = "/home/user/mock.txt";
    final private Timestamp mockstamp = new Timestamp(123456789);
    final private Boolean mockblacklist = false;
    final private Integer mockId = 666;
    final private ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<String> listCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<Timestamp> timeCaptor = ArgumentCaptor.forClass(Timestamp.class);
    final private ArgumentCaptor<Boolean> booleanCaptor = ArgumentCaptor.forClass(Boolean.class);
    final private ArgumentCaptor<String> wordCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);

    /**
     * Test of the addListFromFile-method.
     */
    @Test
    public void testAddListFromFile() {
        when(filelistparser.getListFromFile(any(String.class))).thenReturn(mocklist);
        try {
            databaselistparser.addListFromFile(mockname, mocklocation, mockstamp, mockblacklist);
            doNothing().when(databaselistparser).addList(any(), any(), any(), any());
            verify(databaselistparser).addList(nameCaptor.capture(), listCaptor.capture(), timeCaptor.capture(), booleanCaptor.capture());
        } catch (IOException ex) {
            fail("IOException");
        } catch (SQLException ex) {
            fail("SQLException");
        }

        assertEquals(nameCaptor.getValue(), mockname);
        assertEquals(listCaptor.getValue(), mocklist);
        assertEquals(timeCaptor.getValue(), mockstamp);
        assertEquals(booleanCaptor.getValue(), mockblacklist);
    }

    /**
     * Test of the addList-method.
     */
    @Test
    public void testAddList() {
        try {
            when(wordlistdao.insert(any(Wordlist.class))).thenReturn(mockId);
            databaselistparser.addList(mockname, mocklist, mockstamp, mockblacklist);
            doNothing().when(databaselistparser).parseLine(any(), any(), any());
            verify(databaselistparser, times(2)).parseLine(wordCaptor.capture(), booleanCaptor.capture(), idCaptor.capture());
        } catch (IOException ex) {
            fail("IOException");
        } catch (SQLException ex) {
            fail("SQLException");
        }

        List<String> capturedList = wordCaptor.getAllValues();
        assertEquals(capturedList.get(0), "mock1");
        assertEquals(capturedList.get(1), "mock2");
        assertEquals(booleanCaptor.getValue(), mockblacklist);
        assertEquals(idCaptor.getValue(), mockId);

    }

    /**
     * Test of the parseLine-method, when the word is a whiteword and active.
     */
    @Test
    public void testParseLineActiveWhiteword() {
        String word = "mockword";
        Boolean status = true;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(false);
            databaselistparser.parseLine(word, mockblacklist, mockId);
            verify(whiteworddao).insert(wordCaptor.capture(), booleanCaptor.capture(), idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }

        assertEquals(wordCaptor.getValue(), word);
        assertEquals(booleanCaptor.getValue(), status);
        assertEquals(idCaptor.getValue(), mockId);

    }

    /**
     * Test of the parseLine-method, when the word is a whiteword and not active.
     */
    @Test
    public void testParseLineInactiveWhiteword() {
        String word = "mockword";
        Boolean status = false;
        try {
            when(blackworddao.find(any(String.class))).thenReturn(true);
            databaselistparser.parseLine(word, mockblacklist, mockId);
            verify(whiteworddao).insert(wordCaptor.capture(), booleanCaptor.capture(), idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }
        assertEquals(wordCaptor.getValue(), word);
        assertEquals(booleanCaptor.getValue(), status);
        assertEquals(idCaptor.getValue(), mockId);

    }

    /**
     * Test of the parseLine-method, when the word is a blackword.
     */
    @Test
    public void testParseLineBlackword() {
        String word = "mockword";
        try {
            databaselistparser.parseLine(word, true, mockId);
            verify(blackworddao).insert(wordCaptor.capture(), idCaptor.capture());
        } catch (SQLException ex) {
            fail("SQLException");
        }

        assertEquals(wordCaptor.getValue(), word);
        assertEquals(idCaptor.getValue(), mockId);

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
}
