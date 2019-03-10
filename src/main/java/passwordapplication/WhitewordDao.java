package passwordapplication;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.util.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

/**
 * Whiteworddao is the class that handles the database operations for the
 * Whiteword-table, which hosts the words that are used to create the passwords,
 * as well as the information on each about whether or not the word is also on a
 * blacklist.
 *
 * @author antti
 */
@Component
public class WhitewordDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Method for adding a whiteword-object to the table
     *
     * @param word - the whiteword-object to be added
     * @throws SQLException
     */
    public void insert(Whiteword word) throws SQLException {
        jdbcTemplate.update("INSERT INTO Whiteword"
                + " (word, active, list_id)"
                + " VALUES (?, ?, ?)",
                word.getWord(),
                word.getActive(),
                word.getWordlist().getId());
    }

    /**
     * Method for adding a word to the table, with parameters as a separate
     * objects
     *
     * @param word - the word (string) to be added
     * @param active - the Boolean value, of whether the word is active (can be
     * used in password)
     * @param list_id - the id of the list the word belongs to
     * @throws SQLException
     */
    public void insert(String word, Boolean active, int list_id) throws SQLException {
        jdbcTemplate.update("INSERT INTO Whiteword"
                + " (word, active, list_id)"
                + " VALUES (?, ?, ?)",
                word, active, list_id);
    }

    /**
     * Method for reading a word from the table into a whiteword-object
     *
     * @param id - id (primary key) of the word to be read
     * @return whiteword-object
     * @throws SQLException
     */
    public Whiteword read(Integer id) throws SQLException {
        Whiteword word = jdbcTemplate.queryForObject("SELECT * FROM Whiteword WHERE id = ?",
                new BeanPropertyRowMapper<>(Whiteword.class),
                id);

        return word;
    }

    /**
     * Method to read the word (string) and activity of a word from the database
     * table
     *
     * @param id- id (primary key) of the word to be read
     * @return Pair with word and activity (Boolean)
     * @throws SQLException
     */
    public Pair readNameAndActive(Integer id) throws SQLException {
        Pair result = jdbcTemplate.queryForObject(
                "SELECT word, active FROM Whiteword WHERE id = ?",
                (rs, rowNum) -> new Pair(
                        rs.getString("word"),
                        rs.getBoolean("active")), id);
        return result;

    }

    /**
     * Method to delete all words on a wordlist from the table
     *
     * @param list_id - list to be deleted
     */
    public void deleteListWords(Integer list_id) {
        jdbcTemplate.update("DELETE FROM Whiteword WHERE list_id = ?", list_id);
    }

    //method to change all instances of word inactive in table
    /**
     * Method to set the activity of a word to false
     *
     * @param word
     */
    public void setInactive(String word) {
        jdbcTemplate.update("UPDATE Whiteword SET active = false WHERE word = ?", word);
    }

    //method to change all instances of word active in table
    /**
     * Method to set the activity of a word to true
     *
     * @param word
     */
    public void setActive(String word) {
        jdbcTemplate.update("UPDATE Whiteword SET active = true WHERE word = ?", word);
    }

    /**
     * Method to list ten words (strings) from table - used to show a sample
     * from list
     *
     * @param list_id - id-number of the list to list the words from
     * @return List of strings
     * @throws SQLException
     */
    public List<String> listTenStringsFromList(Integer list_id) throws SQLException {

        List<String> stringlist = jdbcTemplate.query(
                "SELECT word FROM Whiteword WHERE list_id = ? LIMIT 10",
                new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        }, list_id);
        return stringlist;
    }

    /**
     * Method to retrieve n usable strings from database for password-generation
     *
     * @param n - number of strings to retrieve
     * @return arraylist of strings
     * @throws SQLException
     */
    public ArrayList<String> listNActiveStrings(Integer n) throws SQLException {
        Integer maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM Whiteword", Integer.class);

        ArrayList<String> list = new ArrayList<String>();
        int m = 0;
        //Iterate through the steps until enough valid strings have been gathered
        while (m < n) {
            Pair word;
            do {
                //generate random number and fetch the corresponding word
                //if there is no word at that id, try again
                try {
                    int id = (int) (Math.random() * maxId);
                    word = this.readNameAndActive(id);
                    break;
                } catch (EmptyResultDataAccessException e) {
                }
            } while (true);

            Boolean active = (Boolean) word.getValue();
            String wordString = (String) word.getKey();

            //Check if word is active (not on blacklist) - if not, go to next iteration
            if (active) {
                //Iterate through the list of words drawn before, ignore this word if it is on list already
                Boolean ok = true;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i) == wordString) {
                        ok = false;
                    }
                }

                //if word is not on list, add it
                if (ok) {
                    list.add(wordString);
                    m++;
                }
            }

        }
        return list;
    }

}
