package passwordapplication;


import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class BlackwordDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert(Blackword word) throws SQLException {
        jdbcTemplate.update("INSERT INTO Blackword"
                + " (blackword, list_id)"
                + " VALUES (?, ?)",
                word.getWord(),
                word.getWordlist().getId());
    }
    
    public void insert(String word, int list_id) throws SQLException {
        jdbcTemplate.update("INSERT INTO Blackword"
                + " (blackword, list_id)"
                + " VALUES (?, ?)",
                word, list_id);
    }

    public Blackword read(Integer id) throws SQLException {
        Blackword blackword = jdbcTemplate.queryForObject("SELECT * FROM Blackword WHERE id = ?",
                new BeanPropertyRowMapper<>(Blackword.class),
                id);

        return blackword;
    }
    
    public Boolean find (String word){
        //Find out if given word is on the blacklist, return true if so. Else return false.
        Integer id = jdbcTemplate.queryForObject("SELECT id FROM Blackword WHERE word = ?", Integer.class, word);
        if (id != null) {
            return true;
        }
        else {
            return false;
        }
    }
}
