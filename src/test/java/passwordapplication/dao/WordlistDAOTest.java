package passwordapplication.dao;

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
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.KeyHolder;
import passwordapplication.models.Wordlist;
import passwordapplication.services.GeneratedKeyHolderFactory;

/**
 * The test class for the WordlistDAO-class.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class WordlistDAOTest {

    @Mock
    JdbcTemplate jdbcTemplate;

    @Mock
    Wordlist mockwordlistlist;

    @Mock
    KeyHolder keyholder;

    @Mock
    GeneratedKeyHolderFactory generatedkeyholderfactory;

    @InjectMocks
    private WordlistDAO wordlistdao;

    final private String mockname = "mockname";
    final private Timestamp mockstamp = new Timestamp(123456789);
    final private Boolean mockblacklist = true;
    final private Integer mockId = 666;
    final private ArgumentCaptor<Integer> idCaptor = ArgumentCaptor.forClass(Integer.class);

    /**
     * Test for the the insert method. The method should return the key in the
     * keyholder provided by the database call.
     *
     */
    @Test
    public void testInsert() {
        when(mockwordlistlist.getId()).thenReturn(mockId);
        when(mockwordlistlist.getName()).thenReturn(mockname);
        when(mockwordlistlist.getBlacklist()).thenReturn(mockblacklist);
        when(mockwordlistlist.getTimestamp()).thenReturn(mockstamp);
        when(generatedkeyholderfactory.newKeyHolder()).thenReturn(keyholder);
        Integer mockkeyholderkey = 42;
        when(keyholder.getKey()).thenReturn(mockkeyholderkey);

        Integer result = -1;
        try {
            result = wordlistdao.insert(mockwordlistlist);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mockkeyholderkey, result);
    }

    /**
     * Test for the readBlacklistStatus, when the status is "true". The method
     * is expected to return the value provided by the database.
     */
    @Test
    public void testReadBlacklistStatusWhenTrue() {
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class), anyInt())).thenReturn("TRUE");
        Boolean expResult = true;
        Boolean result = false;
        try {
            result = wordlistdao.readBlacklistStatus(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(expResult, result);
    }

    /**
     * Test for the readBlacklistStatus, when the status is "false". The method
     * is expected to return the value provided by the database.
     *
     */
    @Test
    public void testReadBlacklistStatusWhenFalse() {
        when(jdbcTemplate.queryForObject(anyString(), eq(String.class), anyInt())).thenReturn("FALSE");
        Boolean expResult = false;
        Boolean result = true;
        try {
            result = wordlistdao.readBlacklistStatus(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(expResult, result);
    }

    /**
     * Test for the deleteList-method. The method should call the database with
     * the correct id.
     */
    @Test
    public void testDeleteList() {
        try {
            wordlistdao.deleteList(mockId);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        verify(jdbcTemplate).update(any(), idCaptor.capture());
        assertEquals(idCaptor.getValue(), mockId);
    }

    /**
     * Test for the list-method. The method is expected to return the value
     * provided by the database.
     */
    @Test
    public void testList() {
        Wordlist mocklist = mock(Wordlist.class);
        List<Wordlist> mocklistlist = new ArrayList();
        mocklistlist.add(mocklist);
        when(jdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mocklistlist);
        List<Wordlist> retVal = new ArrayList();
        try {
            retVal = wordlistdao.list();
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mocklistlist, retVal);
    }
}
