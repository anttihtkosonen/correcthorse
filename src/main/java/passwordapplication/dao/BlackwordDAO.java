package passwordapplication.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import passwordapplication.models.Word;

/**
 * Blackworddao is the class that handles the database operations for the
 * Blackword-table, which hosts the words that cannot be use in passwords.
 *
 * @author antti
 */
@Component
public class BlackwordDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Method for adding a word-object to the Blackword-table
     *
     * @param word - The word-object to be added
     * @throws SQLException
     */
    public void insert(Word word) throws SQLException {
        jdbcTemplate.update("INSERT INTO Blackword"
                + " (word, list_id)"
                + " VALUES (?, ?)",
                word.getWord(),
                word.getWordlist().getId());
    }

    /**
     * Method for adding a word to the table, with parameters as a separate
     * objects
     *
     * @param word - the word (string) to be added
     * @param list_id - the id of the list the word belongs to
     * @throws SQLException
     */
    public void insert(String word, int list_id) throws SQLException {
        jdbcTemplate.update("INSERT INTO Blackword"
                + " (word, list_id)"
                + " VALUES (?, ?)",
                word, list_id);
    }

    /**
     * Method for reading a word from the table into a blackword-object
     *
     * @param id - id (primary key) of the word to be read
     * @return blackword-object
     * @throws SQLException
     */
    public Word read(Integer id) throws SQLException {
        Word blackword = jdbcTemplate.queryForObject(
                "SELECT * FROM Blackword WHERE id = ?",
                new BeanPropertyRowMapper<>(Word.class),
                id);
        return blackword;
    }

    /**
     * Method to delete all words on a wordlist from the table
     *
     * @param list_id - list to be deleted
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public void deleteListWords(Integer list_id) throws SQLException {
        jdbcTemplate.update("DELETE FROM Blackword WHERE list_id = ?", list_id);
    }

    /**
     * Method to find whether a word (string) exists in the Blackword-table
     *
     * @param word - string to find
     * @return - true if word exists in the table, false if not
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public Boolean find(String word) throws SQLException {
        List<Integer> list;
        list = jdbcTemplate.query("SELECT id FROM Blackword WHERE word = ?",
                (rs, rowNum) -> new Integer(rs.getInt("id")), word);
        if (list.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Method to count how many times a word is listed in the blackword-table
     *
     * @param word - string to count
     * @return number of times found
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public Integer count(String word) throws SQLException {
        Integer count;
        count = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM Blackword WHERE word = ?",
                Integer.class,
                word);
        return count;
    }

    /**
     * Method to list the words belonging to list with id list_id
     *
     * @param list_id - Wordlist to list from
     * @return List of strings
     * @throws SQLException
     */
    public List<String> listWords(Integer list_id) throws SQLException {
        List<String> list = jdbcTemplate.query(
                "SELECT word FROM Blackword WHERE list_id= ?",
                (rs, rowNum) -> rs.getString("word"), list_id);
        return list;
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
                "SELECT word FROM Blackword WHERE list_id = ? LIMIT 10",
                new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                return rs.getString(1);
            }
        }, list_id);
        return stringlist;
    }

    /**
     * Method to get the size of a list in the database
     *
     * @param list_id - the list to find the information for
     * @return the number of rows in the list
     * @throws SQLException
     */
    public Integer getListSize(Integer list_id) throws SQLException {
        Integer size = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM Blackword WHERE list_id = ?",
                Integer.class,
                list_id);
        return size;
    }
}
