package passwordapplication.services;

import passwordapplication.dao.WhitewordDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for generating passwords.
 *
 * @author antti
 */
@Component
public class PasswordGenerator {

    @Autowired
    WhitewordDAO whiteworddao;

    /**
     * Method to generate passwords from words in database and an optional list
     * of dividers
     *
     * @param amount - number of passwords to generate.
     * @param wordnumber - the number of words in a generated password.
     * @param dividers - a list of dividers to be used in between words. Can be
     * and empty list.
     * @return List of strings. An empty list will be returned if there were an
     * insufficient number of words in the database to generate requested
     * password set.
     *
     * @throws java.sql.SQLException
     */
    public List<String> getPasswords(Integer amount, Integer wordnumber, List<String> dividers) throws SQLException {
        //Initialize list
        List<String> passwords = new ArrayList();

        //Get the needed number of words from database
        ArrayList<String> words = whiteworddao.listNActiveStrings(wordnumber * amount);
        /*
        If there are an insufficient number of words in the database, then 
        whiteworddao.listNActiveStrings will return an empty list. In this case
        an empty list should also be returned from this method.
         */
        if (words.isEmpty()) {
            return passwords;
        }

        //Parse the dividers-list to match rewuirements.
        dividers = this.parseDividers(dividers);

        //get passwprds
        passwords = this.generate(amount, wordnumber, words, dividers);
        return passwords;
    }

    /**
     * Method to generate passwords from given lists of strings and dividers
     *
     * @param amount - number of passwords to generate
     * @param wordnumber - the number of words in a generated password
     * @param words - a list of the words to generate the passwords from
     * @param dividers - a list of dividers to be used in between words
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
                int randomIndex = rand.nextInt(dividers.size());
                String divider = dividers.get(randomIndex);
                password += divider;
                password += words.remove(0);
            }
            passwords.add(password);
        }
        return passwords;
    }

    /**
     * Method to parse a list of dividers supplied by the user.
     *
     * @param dividers list supplied by user
     * @return a parsed list
     */
    public List<String> parseDividers(List<String> dividers) {
        List<String> output = new ArrayList();

        //Go through the list of dividers and copy all acceptable ones to output
        Boolean acceptable = true;
        for (int i = 0; i < dividers.size(); i++) {
            acceptable = true;

            //Get the string from list and strip whitespaces
            String st = dividers.get(i);
            st = st.replaceAll("\\s+", "");

            //if the string is empty or too long, mark as unacceptable
            if (st.length() > 3  || st.length() == 0) {
                acceptable = false;
            }

            /*
            If the string is still acceptable, go through the characters in the 
            string, and mark unacceptable if any of the characters is outside 
            the printable standard ASCII range
             */
            if (acceptable) {
                char[] chars = st.toCharArray();
                for (char c : chars) {
                    if (c < 33 || c > 126) {
                        acceptable = false;
                        break;
                    }
                }
            }
            //if still acceptable, copy to output
            if (acceptable) {
                output.add(dividers.get(i));
            }
        }

        // If user gave no dividers, or if none were acceptable, add "-"
        if (output.size() == 0) {
            output.add("-");
        }
        return output;
    }
}
