package passwordapplication.dao;

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
import passwordapplication.models.Whiteword;

/**
 * Whiteworddao is the class that handles the database operations for the
 * Whiteword-table, which hosts the words that are used to create the passwords,
 * as well as the information on each about whether or not the word is also on a
 * blacklist.
 *
 * @author antti
 */
@Component
public class WhitewordDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Method for adding a whiteword-object to the table
     *
     * @param word - the whiteword-object to be added
     * @throws SQLException if there was a database error during the operation
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
     * @throws SQLException if there was a database error during the operation
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
     * @throws SQLException if there was a database error during the operation
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
     * @param id - id (primary key) of the word to be read
     * @return Pair with word and activity (Boolean)
     * @throws SQLException if there was a database error during the operation
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
     * Method to count how many active words there are in the database
     *
     * @return number active words
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public Integer countActiveWords() throws SQLException {
        Integer count;
        count = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM Whiteword WHERE active = TRUE",
                Integer.class);
        return count;
    }

    /**
     * Method to delete all words on a wordlist from the table
     *
     * @param list_id - list to be deleted
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public void deleteListWords(Integer list_id) throws SQLException {
        jdbcTemplate.update("DELETE FROM Whiteword WHERE list_id = ?", list_id);
    }

    //method to change all instances of word inactive in table
    /**
     * Method to set the activity of a word to false
     *
     * @param word the word (string) to set the activity for
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public void setInactive(String word) throws SQLException {
        jdbcTemplate.update("UPDATE Whiteword SET active = false WHERE word = ?", word);
    }

    //method to change all instances of word active in table
    /**
     * Method to set the activity of a word to true
     *
     * @param word the word (string) to set the activity for
     * @throws java.sql.SQLException if there was a database error during the
     * operation
     */
    public void setActive(String word) throws SQLException {
        jdbcTemplate.update("UPDATE Whiteword SET active = true WHERE word = ?", word);
    }

    /**
     * Method to list ten words (strings) from table - used to show a sample
     * from list. If list has ten words or less, all words are returned.
     *
     * @param list_id - id-number of the list to list the words from
     * @return List of strings
     * @throws SQLException if there was a database error during the operation
     */
    public List<String> listTenStringsFromList(Integer list_id) throws SQLException {
        List<String> stringlist = jdbcTemplate.query(
                "SELECT word FROM Whiteword WHERE list_id = ? LIMIT 10",
                new RowMapper<String>() {
            public String mapRow(ResultSet rs, int rowNum) throws SQLException {
                return rs.getString(1);
            }
        }, list_id);
        return stringlist;
    }

    /**
     * Method to retrieve n usable strings from database for
     * password-generation.
     *
     * @param n - number of strings to retrieve
     * @return ArrayList of strings. An empty list is returned, if there are an
     * insufficient number of words in the database
     * @throws SQLException if there was a database error during the operation
     */
    public ArrayList<String> listNActiveStrings(Integer n) throws SQLException {
        //initialize arraylist
        ArrayList<String> list = new ArrayList<String>();

        //get the amount of active words in database, and return empty list, 
        //if the number in insufficient
        Integer wordcount = this.countActiveWords();
        if (wordcount < n) {
            return list;
        }

        //get the highest id number in the database, so that unnecessarily large 
        //random numbers are not generated later
        Integer maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM Whiteword", Integer.class);

        /*
        Iterate through the steps until enough valid strings have been gathered. 
        The maximum number of iterations is 10n - if this is reached, it is 
        assumed that there are not enough unique active words in the database, 
        and an empty list is therefore returned.
         */
        int m = 0;
        int iteration = 0;
        while (m < n) {
            iteration++;
            //Check if limit has been reached
            if (iteration == 10 * n) {
                return new ArrayList();
            }

            //word is initialized with dummy value
            Pair word = new Pair("dummy", false);
            Boolean wordOK = false;
            do {
                wordOK = true;
                //generate random number and fetch the word with corresponding id
                //if there is no word at that id, try again
                int id = (int) (Math.random() * maxId);
                try {
                    word = this.readNameAndActive(id);
                } catch (EmptyResultDataAccessException ex) {
                    wordOK = false;
                }
            } while (wordOK = false);

            Boolean active = (Boolean) word.getValue();
            String wordString = (String) word.getKey();

            //Check if word is active (not on blacklist) - if not, go to next iteration
            if (active) {
                Boolean ok = true;
                //Iterate through the list of words drawn before, ignore this word if it is on list already
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).equals(wordString)) {
                        ok = false;
                    }
                }
                //Add the word to the list, if it is ok.
                if (ok) {
                    list.add(wordString);
                    m++;
                }
            }

        }

        return list;
    }

    /**
     * Method to get the size of a list in the database
     *
     * @param list_id - the list to find the information for
     * @return the number of rows in the list
     * @throws SQLException if there was a database error during the operation
     */
    public Integer getListSize(Integer list_id) throws SQLException {
        Integer size = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM Whiteword WHERE list_id = ?",
                Integer.class,
                list_id);
        return size;
    }

}
