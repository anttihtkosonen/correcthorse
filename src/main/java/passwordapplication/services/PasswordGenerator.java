package passwordapplication.services;

import passwordapplication.dao.WhitewordDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
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
    
    @Autowired
    WhitewordDao whiteworddao;

    
    /**
     * Method to generate  passwords
     *
     * @param amount - number of passwords to generate
     * @return List of strings
     */    
    public List<String> getPasswords (Integer amount, Integer wordnumber, List<String> dividers) throws SQLException{
        //First get the needed number of active words from database
        ArrayList<String> words = whiteworddao.listNActiveStrings(wordnumber * amount);
        // If user gave no dividers, use line
        if (dividers.isEmpty()){
            dividers.add("-");
        }
        //get passwprds
        List<String> passwords = this.generate(amount, wordnumber, words, dividers);
        return passwords;
    }
    
    
    /**
     * Method to generate passwords from given lists of strings and dividers
     *
     * @param amount - number of passwords to generate
     * @return List of strings
     */
    public List<String> generate(Integer amount, Integer wordnumber, ArrayList<String> words, List<String> dividers) {
        
        //Initialize the list of passwords
        List<String> passwords = new ArrayList<String>();
        //Generate the required number of passwords from the fetched words
        //Remove the used word from the list of words in each iteration
        for (int i = 0; i < amount; i++) {
            String password = words.remove(0);
            for (int j = 1; j < wordnumber; j++) {
                Random rand = new Random();
                String divider = dividers.get(rand.nextInt(dividers.size()));
                password += divider;
                password += words.remove(0);
            }
            passwords.add(password);
        }
        return passwords;
    }
}
