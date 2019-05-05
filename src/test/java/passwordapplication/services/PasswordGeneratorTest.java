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
import passwordapplication.models.Password;

/**
 * Tests of the PasswordGenerator-class.
 *
 * @author antti
 */
@RunWith(MockitoJUnitRunner.Silent.class)
public class PasswordGeneratorTest {

    @Mock
    JdbcTemplate mockjdbcTemplate;

    @Mock
    WhitewordDAO whiteworddao;

    @InjectMocks
    PasswordGenerator passwordgenerator;

    /**
     * Test of the generate-method. The method should return correctly generated
     * passwords.
     */
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

    /**
     * Test of the getPasswords- and entropyCalculator-methods, when database
     * has sufficient words. The DAO is mocked. The method should return
     * correctly generated passwords and correct entropies for these.
     */
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

        List<Password> expResult = new ArrayList();
        expResult.add(new Password("mock1-mock2", 33));
        expResult.add(new Password("mock3-mock4", 33));
        List<Password> result = new ArrayList();
        try {
            result = passwordgenerator.getPasswords(amount, wordnumber, dividers);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expResult.get(i).getPassword(), result.get(i).getPassword());
            assertEquals(expResult.get(i).getEntropy(), result.get(i).getEntropy());
        }

    }

    /**
     * Test of the getPasswords-method, when database has insufficient words.
     * The DAO is mocked. The method should return an empty list.
     */
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
        List<Password> expResult = new ArrayList();
        List<Password> result = new ArrayList();
        try {
            result = passwordgenerator.getPasswords(amount, wordnumber, dividers);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        assertEquals(expResult, result);
    }

    /**
     * Test of the parseDividers-method. The method should remove unacceptable
     * dividers from the list provided, and return the shortened list.
     */
    @Test
    public void testParseDividers() {
        List<String> expectedResult = new ArrayList();
        expectedResult.add("!");
        expectedResult.add("2");
        expectedResult.add("-a-");

        List<String> dividerList = new ArrayList();
        dividerList.addAll(expectedResult);
        dividerList.add("++++");
        dividerList.add("  ");
        dividerList.add("\u0003");
        dividerList.add("");
        dividerList.add("¤");

        List<String> result = passwordgenerator.parseDividers(dividerList);

        assertEquals(expectedResult, result);
    }

}
