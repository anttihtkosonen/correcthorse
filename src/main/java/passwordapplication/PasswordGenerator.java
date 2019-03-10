package passwordapplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for generating passwords
 *
 * @author antti
 */
@Component
public class PasswordGenerator {
    /**
     * The number of words that are used to create a password.
     */
    Integer wordnumber = 3;
    
    @Autowired
    WhitewordDao whiteworddao;

    
    /**
     * Method to generate  passwords
     *
     * @param amount - number of passwords to generate
     * @return List of strings
     */    
    public List<String> getPasswords (Integer amount) throws SQLException{
        //First get the needed number of active words from database
        ArrayList<String> words = whiteworddao.listNActiveStrings(wordnumber * amount);
        List<String> passwords = this.generate(amount, words);
        return passwords;
    }
    
    
    /**
     * Method to generate passwords from given list of strings
     *
     * @param amount - number of passwords to generate
     * @return List of strings
     */
    public List<String> generate(Integer amount, ArrayList<String> words) {
        
        //Initialize the list of passwords
        List<String> passwords = new ArrayList<String>();
        //Generate the required number of passwords from the fetched words
        //Remove the used word from the list of words in each iteration
        for (int i = 0; i < amount; i++) {
            String password = words.remove(0);
            for (int j = 1; j < wordnumber; j++) {
                password += "-";
                password += words.remove(0);
            }
            passwords.add(password);
        }
        return passwords;
    }
}
