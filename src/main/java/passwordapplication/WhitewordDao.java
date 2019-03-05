package passwordapplication;


import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class WhitewordDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void insert(Whiteword word) throws SQLException {
        jdbcTemplate.update("INSERT INTO Whiteword"
                + " (word, active, list_id)"
                + " VALUES (?, ?, ?)",
                word.getWord(),
                word.getActive(),
                word.getWordlist().getId());
    }
    
    public void insert(String word, Boolean active, int list_id) throws SQLException {
        jdbcTemplate.update("INSERT INTO Whiteword"
                + " (word, active, list_id)"
                + " VALUES (?, ?, ?)",
                word, active, list_id);
    }

    public Whiteword read(Integer id) throws SQLException {
        Whiteword word = jdbcTemplate.queryForObject("SELECT * FROM Whiteword WHERE id = ?",
                new BeanPropertyRowMapper<>(Whiteword.class),
                id);

        return word;
    }
    
    //method to change all instances of word inactive in table
    public void setInactive (String word) {
        jdbcTemplate.update("UPDATE Whiteword SET active = false WHERE word = ?", word);
    }
    
    //method to change all instances of word active in table
    public void setActive (String word) {
        jdbcTemplate.update("UPDATE Whiteword SET active = true WHERE word = ?", word);        
    }
    
    //method to retrieve n usable strings from database
    public List<String> listNActiveStrings(Integer n) throws SQLException {
	Integer maxId = jdbcTemplate.queryForObject("SELECT MAX(id) FROM Whiteword", Integer.class);

        List<String> list = null; 
        while (list.size() < n) {
            int id = (int) (Math.random()*maxId);
            Whiteword word = this.read(id);
            Boolean active = word.getActive();
            String wordString = word.getWord();
            
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
                if(ok) {
                    list.add(wordString);
                }
            }
        
        }
        return list;
    }


    
}
