package passwordapplication;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Class for operations related to showing user information about wordlists in
 * the database.
 *
 * @author antti
 */
@Component
public class ShowList {

    @Autowired
    WhitewordDao whiteworddao;

    @Autowired
    WordlistDao wordlistdao;

    @Autowired
    BlackwordDao blackworddao;

    /**
     * Method to show information about all lists in the database.
     *
     * @throws SQLException
     */
    public void showAll() throws SQLException {

        List<Wordlist> wordlist = new ArrayList();
        try {
            wordlist = wordlistdao.list();
        } catch (SQLException ex) {
            System.out.println("There was an error retrieving the information frim the database.");
        }
        if (wordlist.size() == 0) {
            System.out.println("There are no wordlists in database.");
        } else {
            System.out.println("Here are the lists in the database \nTen words from each list are also listed.");
            for (int i = 0; i < wordlist.size(); i++) {
                List<String> samplelist;
                System.out.println("\n\n-----------------");
                System.out.println("Name of list: " + wordlist.get(i).getName());
                System.out.println("Id number of list: " + wordlist.get(i).getId());
                if (wordlist.get(i).getBlacklist()) {
                    System.out.println("This list is a blacklist");
                    samplelist = blackworddao.listTenStringsFromList(wordlist.get(i).getId());
                } else {
                    System.out.println("This list is a normal list, not a blacklist");
                    samplelist = whiteworddao.listTenStringsFromList(wordlist.get(i).getId());
                }

                if (samplelist.size() == 0) {
                    System.out.println("There are no words on this list");
                } else {
                    System.out.println("Sample of words from this list:\n");
                    for (int j = 0; j < samplelist.size(); j++) {
                        System.out.println(samplelist.get(j));
                    }
                }

            }
        }
    }
}
