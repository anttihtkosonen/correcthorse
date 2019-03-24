package passwordapplication.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import passwordapplication.models.Wordlist;

/**
 * Wordlistdao is the class that handles the database operations for the
 * Wordlist-table, which hosts the information about the wordlists added by the
 * user. blacklist.
 *
 * @author antti
 */
@Component
public class WordlistDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Method for adding a Wordlist-object to the table
     *
     * @param wordlist - the Wordlist-object to be added.
     * @return the primary key of the added list
     * @throws SQLException
     */
    public Integer insert(Wordlist wordlist) throws SQLException {
        /**
         * A Keyholder is created for storing the id of the created sql-row.
         */
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(
                    "INSERT INTO Wordlist"
                    + " (name, timestamp, blacklist)"
                    + " VALUES (?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, wordlist.getName());
            stmt.setTimestamp(2, wordlist.getTimestamp());
            stmt.setBoolean(3, wordlist.getBlacklist());
            return stmt;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        return id;
    }

    /**
     * Method for reading a word from the table into a Wordlist-object
     *
     * @param id - id (primary key) of the list to be read
     * @return Wordlist-object
     * @throws SQLException
     */
    public Wordlist read(Integer id) throws SQLException {
        Wordlist wordlist = jdbcTemplate.queryForObject(
                "SELECT * FROM Wordlist WHERE id = ?",
                new BeanPropertyRowMapper<>(Wordlist.class),
                id);

        return wordlist;
    }

    /**
     * Method to delete Wordlist from database. Please note, that this method
     * only deletes the information of the list, and not the words on that list.
     *
     * @param list_id - id (primary key) of the list to be delete.
     */
    public void deleteList(Integer list_id) {
        jdbcTemplate.update("DELETE FROM Wordlist WHERE id = ?", list_id);
    }

    /**
     * Method to list the wordlists in the database.
     *
     * @return List of Wordlist-objects
     * @throws SQLException
     */
    public List<Wordlist> list() throws SQLException {

        List<Wordlist> wordlist = jdbcTemplate.query(
                "SELECT * FROM Wordlist",
                (rs, rowNum) -> new Wordlist(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getTimestamp("timestamp"),
                        rs.getBoolean("blacklist")));
        return wordlist;

    }

}
