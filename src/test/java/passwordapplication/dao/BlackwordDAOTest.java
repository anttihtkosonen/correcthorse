package passwordapplication.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
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
import passwordapplication.models.Word;
import passwordapplication.models.Wordlist;

/**
 * The test class for the BlackwordDAO-class. Unless otherwise commented, the
 * correct behaviour of class methods is tested by capturing the parameters of
 * the calls to methods of other classes made by the method being tested, and
 * comparing them to what is expected.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class BlackwordDAOTest {

    @Mock
    Word word;

    @Mock
    Wordlist wordlist;

    @Mock
    JdbcTemplate jdbcTemplate;

    @InjectMocks
    final private BlackwordDAO blackworddao = new BlackwordDAO();

    final private String mockword = "mockword";
    final private Integer mockId = 666;
    final private ArgumentCaptor<String> nameCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<String> wordCaptor = ArgumentCaptor.forClass(String.class);
    final private ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);

    /**
     * Test for the insert-method.
     */
    @Test
    public void TestInsert() {
        when(word.getWord()).thenReturn(mockword);
        when(word.getWordlist()).thenReturn(wordlist);
        when(wordlist.getId()).thenReturn(mockId);
        try {
            blackworddao.insert(word);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), nameCaptor.capture(), idCaptor.capture());
        assertEquals(nameCaptor.getValue(), mockword);
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the the second insert method, that takes the word-parameters
     * separately.
     */
    @Test
    public void TestSecondInsert() {
        try {
            blackworddao.insert(mockword, mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), nameCaptor.capture(), idCaptor.capture());
        assertEquals(nameCaptor.getValue(), mockword);
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the read-method.
     */
    @Test
    public void TestRead() {
        when(jdbcTemplate.queryForObject(anyString(), any(BeanPropertyRowMapper.class), idCaptor.capture())).thenReturn(word);
        Word retVal = new Word();
        try {
            retVal = blackworddao.read(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(word, retVal);

    }

    /**
     * Test for the deleteListWords-method.
     */
    @Test
    public void TestDeleteListWords() {
        try {
            blackworddao.deleteListWords(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), idCaptor.capture());
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the find-method, when the word is found.
     */
    @Test
    public void TestFindWhenFound() {
        List<Integer> mockList = new ArrayList();
        mockList.add(mockId);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), wordCaptor.capture())).thenReturn(mockList);
        Boolean retVal = null;
        try {
            retVal = blackworddao.find(mockword);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(wordCaptor.getValue(), mockword);
        assertEquals(true, retVal);
    }

    /**
     * Test for the find-method, when the word is not found.
     */
    @Test
    public void TestFindWhenNotFound() {
        List<Integer> mockList = new ArrayList();
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), wordCaptor.capture())).thenReturn(mockList);
        Boolean retVal = null;
        try {
            retVal = blackworddao.find(mockword);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(wordCaptor.getValue(), mockword);
        assertEquals(retVal, false);
    }

    /**
     * Test for the count-method.
     */
    @Test
    public void TestCount() {
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class), wordCaptor.capture())).thenReturn(mockId);
        Integer retVal = 0;
        try {
            retVal = blackworddao.count(mockword);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(wordCaptor.getValue(), mockword);
        assertEquals(mockId, retVal);
    }

    /**
     * Test for the listWords-method.
     */
    @Test
    public void TestListWords() {
        List<String> mockList = new ArrayList();
        mockList.add("mock");
        mockList.add("list");
        when(jdbcTemplate.query(anyString(), any(RowMapper.class), idCaptor.capture())).thenReturn(mockList);
        List<String> retVal = null;
        try {
            retVal = blackworddao.listWords(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(mockList, retVal);
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
            retVal = blackworddao.listTenStringsFromList(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
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
            retVal = blackworddao.getListSize(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(idCaptor.getValue(), mockId);
        assertEquals(mockId, retVal);
    }
}
