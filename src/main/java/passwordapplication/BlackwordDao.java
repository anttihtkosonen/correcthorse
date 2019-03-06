package passwordapplication;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
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
                + " (word, list_id)"
                + " VALUES (?, ?)",
                word, list_id);
    }

    public Blackword read(Integer id) throws SQLException {
        Blackword blackword = jdbcTemplate.queryForObject("SELECT * FROM Blackword WHERE id = ?",
                new BeanPropertyRowMapper<>(Blackword.class),
                id);

        return blackword;
    }
    
    public void deleteListWords (Integer list_id){
        jdbcTemplate.update("DELETE FROM Blackword WHERE list_id = ?", list_id);
    }
    
    public Boolean find (String word){
        //Find out if given word is on the blacklist, return true if so. Else return false.
        
        List<Integer> list;
        list = jdbcTemplate.query("SELECT id FROM Blackword WHERE word = ?",
                (rs, rowNum) -> new Integer(rs.getInt("id")), word);
        if ( list.isEmpty() ){
            return false;
        }else{ 
            return true;
        }
    }
    
    
    public Integer count(String word) {
        //method to get the count of instances of word
        Integer count;
        count = jdbcTemplate.queryForObject(
                "SELECT COUNT (*) FROM Blackword WHERE word = ?",
                Integer.class,
                word);
        return count;        
    }
    
    
    public List<String> listWords(Integer list_id) throws SQLException {
        //method to list the words belonging to list with id list_id
	List<String> list = jdbcTemplate.query(
                "SELECT word FROM Blackword WHERE list_id= ?",
                (rs, rowNum) -> rs.getString("word"), list_id);
        return list;
    }

    public List<String> listTenStringsFromList(Integer list_id) throws SQLException {
        
        List<String> stringlist = jdbcTemplate.query(
                "SELECT word FROM Blackword WHERE list_id = ? LIMIT 10", 
                new RowMapper<String>(){
                public String mapRow(ResultSet rs, int rowNum)
                    throws SQLException {
                        return rs.getString(1);
                    }
                }, list_id);
        return stringlist;    
    }
}    

