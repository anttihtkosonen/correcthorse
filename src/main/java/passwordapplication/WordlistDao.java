package passwordapplication;


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

@Component
public class WordlistDao {

    @Autowired
    JdbcTemplate jdbcTemplate;


    public Integer insert(Wordlist wordlist) throws SQLException {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement stmt = connection.prepareStatement(
            "INSERT INTO Wordlist"
                + " (name, blacklist)"
                + " VALUES (?, ?)",
                Statement.RETURN_GENERATED_KEYS);
                stmt.setString(1, wordlist.getName());
                stmt.setBoolean(2, wordlist.getBlacklist());
                return stmt;
        }, keyHolder);
        int id = keyHolder.getKey().intValue();
        return id;
    }
    
 
    public Wordlist read(Integer id) throws SQLException {
        Wordlist wordlist = jdbcTemplate.queryForObject(
                "SELECT * FROM Wordlist WHERE id = ?",
                new BeanPropertyRowMapper<>(Wordlist.class),
                id);

        return wordlist;
    }


    public List<Wordlist> list() throws SQLException {

        List<Wordlist> wordlist = jdbcTemplate.query(
                "SELECT * FROM Wordlist",
                (rs, rowNum) -> new Wordlist(
                        rs.getInt("id"), 
                        rs.getString("name"),
      //                  rs.getTimestamp("datetime"),
                        rs.getBoolean("blacklist")));
        return wordlist;

    }
    

    
}
