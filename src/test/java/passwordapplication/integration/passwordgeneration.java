package passwordapplication.integration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javafx.util.Pair;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.AdditionalAnswers;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.junit4.SpringRunner;
import passwordapplication.dao.WhitewordDAO;
import passwordapplication.models.Password;
import passwordapplication.services.PasswordGenerator;

/**
 * This is a higher-level test that tests the correct behaviour of all the
 * classes that are called during password generation. The database-connection
 * is mocked.
 *
 * @author antti
 */

@RunWith(SpringRunner.class)
public class passwordgeneration {

    @MockBean
    JdbcTemplate jdbcTemplate;

    @Autowired
    WhitewordDAO whiteworddao;

    @Autowired
    PasswordGenerator passwordgenerator;

    /**
     * The test for password generation.
     */
    @Test
    public void testPasswordGeneration() {
        String[] wordArray = {
            "some", "words", "and", "then", "some", "other",
            "strings", "in", "the", "english", "language"
        };
        List<String> wordList = Arrays.asList(wordArray);
        List<Pair> pairlist = new ArrayList();

        //Add the two first words as non-active words
        pairlist.add(new Pair(wordList.get(0), false));
        pairlist.add(new Pair(wordList.get(1), false));

        //Add the rest as active ones
        for (int i = 2; i < wordList.size(); i++) {
            pairlist.add(new Pair(wordList.get(i), true));
        }

        //When the highest id of the database is queried, return a number
        when(jdbcTemplate.queryForObject(anyString(), eq(Integer.class))).thenReturn(100);

        //When the readNameAndActive-method of WhitewordDAO gets information from the database, return elements of pairlist
        when(jdbcTemplate.queryForObject(anyString(), any(RowMapper.class), anyInt())).thenAnswer(AdditionalAnswers.returnsElementsOf(pairlist));

        //The request parameters are specified here
        Integer amount = 4;
        Integer wordnumber = 2;
        List<String> dividers = new ArrayList();
        dividers.add("?");
        dividers.add("@");

        List<Password> result = new ArrayList();
        try {
            result = passwordgenerator.getPasswords(amount, wordnumber, dividers);
        } catch (SQLException ex) {
            fail("SQL error");
        }
        //check that correct amount of passwords have been generated
        Integer resultlength = result.size();
        assertEquals(amount, resultlength);

        //go through the list, and check that the the passwords are sound
        for (int i = 0; i < amount; i++) {
            if (
                    result.get(i) == null || 
                    result.get(i).getPassword().length() < 6 || 
                    result.get(i).getPassword() == null || 
                    result.get(i).getEntropy() < 12 ||
                    result.get(i).getEntropy() > 65 ||
                    result.get(i).getEntropy() == null
                    ) {
                fail("Unsound passwords");
            }
        }

    }

    @Configuration
    @Import(PasswordGenerator.class)
    static class PasswordGeneratorConfig {
    }

    @Configuration
    @Import(WhitewordDAO.class)
    static class WhitewordDAOConfig {
    }

}
