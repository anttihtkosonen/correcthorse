package passwordapplication.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import passwordapplication.dao.WhitewordDAO;

@RunWith(MockitoJUnitRunner.Silent.class)
public class PasswordGeneratorTest {

    @Mock
    JdbcTemplate mockjdbcTemplate;

    @Mock
    WhitewordDAO whiteworddao;

    @InjectMocks
    PasswordGenerator passwordgenerator;

    public PasswordGeneratorTest() {
    }

    @Test
    public void testGenerate() {
        System.out.println("generate");
        Integer amount = 2;
        Integer wordnumber = 3;
        ArrayList<String> dividers = new ArrayList();
        dividers.add("<");
        ArrayList<String> words = new ArrayList();
        words.add("bashment");
        words.add("bawbee");
        words.add("benthos");
        words.add("bergschrund");
        words.add("bezoar");
        words.add("bibliopole");

        List<String> expResult = new ArrayList();
        expResult.add("bashment<bawbee<benthos");
        expResult.add("bergschrund<bezoar<bibliopole");
        //PasswordGenerator passwordgenerator = new PasswordGenerator();
        List<String> result = passwordgenerator.generate(amount, wordnumber, words, dividers);
        assertEquals(expResult, result);

    }

    @Test
    public void testGetPasswords() {
        List<String> mockList = new ArrayList();
        mockList.add("mock1");
        mockList.add("mock2");
        mockList.add("mock3");
        mockList.add("mock4");

        try {
            when(whiteworddao.listNActiveStrings(4)).thenReturn((ArrayList<String>) mockList);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        //when(mockjdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(first, second, third, fourth);
        Integer amount = 2;
        Integer wordnumber = 2;
        ArrayList<String> dividers = new ArrayList();
        List<String> expResult = new ArrayList();
        expResult.add("mock1-mock2");
        expResult.add("mock3-mock4");
        List<String> result = new ArrayList();
        try {
            result = passwordgenerator.getPasswords(amount, wordnumber, dividers);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(expResult, result);
    }
    
        @Test
    public void testGetPasswordsWhenInsufficientWords() {
        List<String> mockList = new ArrayList();

        try {
            when(whiteworddao.listNActiveStrings(4)).thenReturn((ArrayList<String>) mockList);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        //when(mockjdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenReturn(first, second, third, fourth);
        Integer amount = 2;
        Integer wordnumber = 2;
        ArrayList<String> dividers = new ArrayList();
        List<String> expResult = new ArrayList();
        List<String> result = new ArrayList();
        try {
            result = passwordgenerator.getPasswords(amount, wordnumber, dividers);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(expResult, result);
    }

}
