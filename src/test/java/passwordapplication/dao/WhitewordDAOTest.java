package passwordapplication.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import passwordapplication.models.Whiteword;
import passwordapplication.models.Wordlist;

/**
 * The test class for the WhiteWordDAO-class. Unless otherwise commented, the
 * correct behaviour of class methods is tested by capturing the parameters of
 * the calls to methods of other classes made by the method being tested, and
 * comparing them to what is expected.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class WhitewordDAOTest {

    @Mock
    Whiteword word;

    @Mock
    Wordlist wordlist;

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    private WhitewordDAO whiteworddao = new WhitewordDAO();

    final private String mockword = "mockword";
    final private Boolean mockactive = true;
    final private Integer mockId = 666;
    final private ArgumentCaptor<Boolean> activeCaptor = ArgumentCaptor.forClass(Boolean.class);
    final private ArgumentCaptor<String> wordCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);

    /**
     * Test for the the insert method.
     */
    @Test
    public void TestInsert() {
        when(word.getWord()).thenReturn(mockword);
        when(word.getActive()).thenReturn(mockactive);
        when(word.getWordlist()).thenReturn(wordlist);
        when(wordlist.getId()).thenReturn(mockId);
        try {
            whiteworddao.insert(word);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), wordCaptor.capture(), activeCaptor.capture(), idCaptor.capture());
        assertEquals(wordCaptor.getValue(), mockword);
        assertEquals(activeCaptor.getValue(), mockactive);
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the the second insert method, that takes the word-parameters
     * separately.
     */
    @Test
    public void TestSecondInsert() {
        try {
            whiteworddao.insert(mockword, mockactive, mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), wordCaptor.capture(), activeCaptor.capture(), idCaptor.capture());
        assertEquals(wordCaptor.getValue(), mockword);
        assertEquals(activeCaptor.getValue(), mockactive);
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the read-method.
     */
    @Test
    public void TestRead() {
        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), idCaptor.capture())).thenReturn(word);
        Whiteword retVal = new Whiteword();
        try {
            retVal = whiteworddao.read(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(word, retVal);
    }

    /**
     * Test for the readNameAndActive-method.
     */
    @Test
    public void TestReadNameAndActive() {
        Pair mockpair = new Pair(mockword, mockactive);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), idCaptor.capture())).thenReturn(mockpair);
        Pair retVal = null;
        try {
            retVal = whiteworddao.readNameAndActive(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(mockpair, retVal);
    }

    /**
     * Test for the countActiveWords-method.
     */
    @Test
    public void TestCountActiveWords() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(mockId);
        Integer retVal = 0;
        try {
            retVal = whiteworddao.countActiveWords();
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(retVal, mockId);
    }

    /**
     * Test for the listWords-method.
     */
    @Test
    public void TestDeleteListWords() {
        try {
            whiteworddao.deleteListWords(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), idCaptor.capture());
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the setInactive-method.
     */
    @Test
    public void TestSetInactive() {
        try {
            whiteworddao.setInactive(mockword);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), wordCaptor.capture());
        assertEquals(wordCaptor.getValue(), mockword);
    }

    /**
     * Test for the setActive-method.
     */
    @Test
    public void TestSetActive() {
        try {
            whiteworddao.setActive(mockword);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), wordCaptor.capture());
        assertEquals(wordCaptor.getValue(), mockword);
    }

    /**
     * Test for the listTenStringsFromList-method.
     */
    @Test
    public void TestListTenStringsFromList() {
        List<String> mockList = new ArrayList();
        mockList.add("mock");
        mockList.add("list");
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), idCaptor.capture())).thenReturn(mockList);
        List<String> retVal = null;
        try {
            retVal = whiteworddao.listTenStringsFromList(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(mockList, retVal);
    }

    /**
     * Test for the testListNActiveStrings, when there are sufficient words in
     * the database. The method should return only the active words, and no
     * duplicates, even when the database provides inactive words and
     * duplicates.
     */
    @Test
    public void TestListNActiveStrings() {
        List<String> mockList = new ArrayList();
        mockList.add("mock1");
        mockList.add("mock2");
        mockList.add("mock3");
        Pair mockpair0 = new Pair("mock0", false);
        Pair mockpair1 = new Pair("mock1", true);
        Pair mockpair2 = new Pair("mock2", true);
        Pair mockpair3 = new Pair("mock3", true);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(mockpair0, mockpair1, mockpair1, mockpair2, mockpair3);
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(100);
        List<String> retVal = null;
        try {
            retVal = whiteworddao.listNActiveStrings(3);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mockList, retVal);
    }

    /**
     * Test for the testListNActiveStrings, when there is an insufficient number
     * of words in the database. The method should return an empty list.
     */
    @Test
    public void TestListNActiveStringsWhenInsufficientWords() {
        List<String> mockList = new ArrayList();
        Pair mockpair1 = new Pair("mock1", true);
        Pair mockpair2 = new Pair("mock2", true);
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(mockpair1, mockpair2);
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(2);
        List<String> retVal = null;
        try {
            retVal = whiteworddao.listNActiveStrings(3);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mockList, retVal);
    }

    /**
     * Test for the getListSize.
     */
    @Test
    public void TestGetListSize() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), idCaptor.capture())).thenReturn(mockId);
        Integer retVal = 0;
        try {
            retVal = whiteworddao.getListSize(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(mockId, retVal);
    }
}
