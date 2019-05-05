package passwordapplication.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import passwordapplication.models.Wordlist;
import passwordapplication.services.GeneratedKeyHolderFactory;

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

    @Autowired
    GeneratedKeyHolderFactory generatedkeyholderfactory;

    /**
     * Method for adding a Wordlist-object to the table
     *
     * @param wordlist - the Wordlist-object to be added.
     * @return the primary key of the added list
     * @throws SQLException  if there was a database error during the operation
     */
    public Integer insert(Wordlist wordlist) throws SQLException {
        //A Keyholder is created for storing the id of the created sql-row.
        KeyHolder keyholder = generatedkeyholderfactory.newKeyHolder();
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
        }, keyholder);
        int id = (int) keyholder.getKey();

        return id;
    }

    /**
     * Method for reading the blacklist-status of a wordlist.
     *
     * @param id - id (primary key) of the list to be read
     * @return Boolean object, that corresponds to blacklist-status.
     * @throws SQLException if there was a database error during the operation
     */
    public Boolean readBlacklistStatus(Integer id) throws SQLException {

        String blacklist = jdbcTemplate.queryForObject(
                "SELECT blacklist FROM Wordlist WHERE id = ?",
                String.class, id);
        if (blacklist == "TRUE") {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Method to delete Wordlist from database. Please note, that this method
     * only deletes the information of the list, and not the words on that list.
     *
     * @param list_id - id (primary key) of the list to be delete.
     * @throws java.sql.SQLException if there was a database error during the operation
     */
    public void deleteList(Integer list_id) throws SQLException {
        jdbcTemplate.update("DELETE FROM Wordlist WHERE id = ?", list_id);
    }

    /**
     * Method to list the wordlists in the database.
     *
     * @return List of Wordlist-objects
     * @throws SQLException if there was a database error during the operation
     */
    public List<Wordlist> list() throws SQLException, BadSqlGrammarException {
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
