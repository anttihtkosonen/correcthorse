package passwordapplication.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import passwordapplication.models.Wordlist;

@RunWith(MockitoJUnitRunner.Silent.class)
public class WordlistDAOTest {

    @Mock
    JdbcTemplate mockjdbcTemplate;

    @InjectMocks
    private WordlistDAO wordlistdao = new WordlistDAO();

    public WordlistDAOTest() {
    }

    /*
    @Test
    public void testInsert() throws Exception {
        Wordlist mocklist = mock(Wordlist.class);
        Integer result = -1;
        try {
            result = wordlistdao.insert(mocklist);
        } catch (SQLException ex) {
            fail("SQL error");
        } 
        Integer expResult = 1;
        assertEquals(expResult, result);
    }
     */
    @Test
    public void testReadBlacklistStatus() throws Exception {
        when(mockjdbcTemplate.queryForObject(anyString(), eq(String.class), anyInt())).thenReturn("TRUE");
        Integer id = 1;
        Boolean expResult = true;
        Boolean result = wordlistdao.readBlacklistStatus(id);
        assertEquals(expResult, result);
    }

    @Test
    public void testDeleteList() {
        try {
            wordlistdao.deleteList(1);
        } catch (SQLException ex) {
            fail("SQL error");
        }
    }

    @Test
    public void testList() throws Exception {
        Wordlist mocklist = mock(Wordlist.class);
        List<Wordlist> mocklistlist = new ArrayList();
        mocklistlist.add(mocklist);
        when(mockjdbcTemplate.query(anyString(), any(RowMapper.class))).thenReturn(mocklistlist);
        List<Wordlist> retVal = new ArrayList();
        try {
            retVal = wordlistdao.list();
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(mocklistlist, retVal);
    }
}
